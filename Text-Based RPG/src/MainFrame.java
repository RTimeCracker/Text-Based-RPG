
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

public class MainFrame extends JFrame{
    final int SCREENWIDTH = 800;
    final int SCREENHEIGHT = 700;

    public enum GameState{
        MainMenu, Game
    }

    GameState currentGameState = GameState.MainMenu;

    File menuMusicFile;
    Clip menuClip;

    CardLayout cardLayout;
    JPanel mainPanel;

    String[] introductionDialogues = {"Welcome Vaiken: Last Legacy", "A game where you can explore, fight enemies, and gather loot!", "classchoice", "namechoice"};

    public MainFrame(String title) throws IOException, LineUnavailableException{
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(SCREENWIDTH,SCREENHEIGHT);

        menuMusicFile = new File("Text-Based RPG\\Music\\RPGMENUBGM.wav");
        menuClip = AudioSystem.getClip();
        menuClip.loop(Clip.LOOP_CONTINUOUSLY);
        PlayMusic(menuMusicFile, menuClip);


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initMainMenu(this, mainPanel, cardLayout);
        initIntroductionInterface(this, mainPanel, cardLayout);
    }

    public void updateGameState(GameState gameState){
        currentGameState = gameState;

        if(currentGameState == GameState.MainMenu){
            System.out.println("Menu");
        }else if(currentGameState == GameState.Game){
            cardLayout.show(mainPanel, "GamePanel");
            StopMusic(menuMusicFile, menuClip);
        }
    }
    
    private void initMainMenu(MainFrame frame, JPanel mainPanel, CardLayout cardLayout) throws IOException{
        MainMenuPanel mainMenuPanel = new MainMenuPanel(frame, mainPanel, cardLayout);
        
    }

    private void initIntroductionInterface(JFrame frame, JPanel mainPanel, CardLayout cardLayout){
        IntroductionClickablePanel introductionPanel = new IntroductionClickablePanel();
        introductionPanel.setBackground(Color.BLACK);
        introductionPanel.setLayout(null);
        introductionPanel.setSize(new Dimension(800, 700));

        JLabel introductionLabel = new JLabel("<html>" + introductionDialogues[0] + "<html>");
        Font font = new Font("SansSerif",Font.PLAIN,32);

        introductionPanel.insertDialogues(introductionLabel,introductionDialogues,true);
        introductionLabel.setForeground(Color.white);
        introductionLabel.setBounds(new Rectangle(200,100, 400,400));
        introductionLabel.setFont(font);
        introductionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introductionLabel.setVerticalAlignment(SwingConstants.TOP);

        //Add panels to both frame and cardLayout
        frame.add(introductionPanel);
        mainPanel.add(introductionPanel, "GamePanel");
        introductionPanel.add(introductionLabel);

        

    }

    public static void PlayMusic(File musicPath, Clip clip){
        try {

            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

                clip.open(audioInput);
                clip.start();
            }else{
                System.out.println("Can't find file.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void StopMusic(File musicPath, Clip clip){
        try {
            
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                
                if(clip.isOpen()){
                    clip.stop();
                }
            }else{
                System.out.println("Can't find file.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
