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
        MainMenu, Introduction,Game
    }

    GameState currentGameState = GameState.MainMenu;

    File menuMusicFile;
    Clip menuClip;

    CardLayout cardLayout;
    JPanel mainPanel;

    String[] introductionDialogues = {"Welcome Vaiken: Last Legacy", "A game where you can explore, fight enemies, and gather loot!", "classchoice", "namechoice"};
    
    Player player;


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

        initMainMenu();
        initIntroductionInterface();
        initExplorationInterface();
        
    }

    public void updateGameState(GameState gameState){
        currentGameState = gameState;

        if(currentGameState == GameState.MainMenu){
            System.out.println("Menu");
        }else if(currentGameState == GameState.Introduction){
            cardLayout.show(mainPanel, "IntroductionPanel");
            StopMusic(menuMusicFile, menuClip);
        }else if(currentGameState == GameState.Game){
            cardLayout.show(mainPanel, "");
            System.out.println(player.name);
        }
    }
    
    private void initMainMenu() throws IOException{
        MainMenuPanel mainMenuPanel = new MainMenuPanel(this, mainPanel, cardLayout);
        
    }

    private void initIntroductionInterface(){
        IntroductionClickablePanel introductionPanel = new IntroductionClickablePanel(cardLayout, mainPanel, this);
        

        JLabel introductionLabel = new JLabel("<html>" + introductionDialogues[0] + "<html>");
        Font font = new Font("SansSerif",Font.PLAIN,32);

        introductionPanel.insertDialogues(introductionLabel,introductionDialogues,true);
        introductionLabel.setForeground(Color.white);
        introductionLabel.setBounds(new Rectangle(200,100, 400,400));
        introductionLabel.setFont(font);
        introductionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introductionLabel.setVerticalAlignment(SwingConstants.TOP);

        //Add panels to both frame and cardLayout
        this.add(introductionPanel);
        mainPanel.add(introductionPanel, "IntroductionPanel");
        introductionPanel.add(introductionLabel);
    }

    private void initExplorationInterface() throws IOException{
        ExplorationPanel explorationPanel = new ExplorationPanel(this);

        this.add(explorationPanel);
        mainPanel.add(explorationPanel, "ExplorationPanel");

    }

    public void initPlayer(String inputtedName, int classChosen){
        player = Player.createPlayer(inputtedName, classChosen);
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
