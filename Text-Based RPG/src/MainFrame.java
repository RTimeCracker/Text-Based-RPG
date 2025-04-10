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
        MainMenu, Introduction, Exploration, Encounter
    }

    GameState currentGameState = GameState.MainMenu;

    File menuMusicFile;
    Clip menuClip;

    CardLayout cardLayout;
    JPanel mainPanel;

    IntroductionClickablePanel introductionPanel;
    ExplorationPanel explorationPanel;
    EncounterPanel encounterPanel;

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
        
    }

    public void updateGameState(GameState gameState){
        currentGameState = gameState;

        if(null != currentGameState)switch (currentGameState) {
            case MainMenu:
                System.out.println("Menu");
                break;

            case Introduction:
                switchPanel("IntroductionPanel");
                StopMusic(menuMusicFile, menuClip);
                break;

            case Exploration:
                switchPanel("ExplorationPanel");
                break;

            case Encounter:
                switchPanel("EncounterPanel");
                break;
            default:
                break;
        }
    }

    public void update(){
        if(currentGameState == GameState.Exploration){
            explorationPanel.rePaint();
        }else if(currentGameState == GameState.Encounter){
            encounterPanel.reSetup();
        }
    }
    
    private void initMainMenu() throws IOException{
        MainMenuPanel mainMenuPanel = new MainMenuPanel(this, mainPanel, cardLayout);
    }

    private void initIntroductionInterface(){
        introductionPanel = new IntroductionClickablePanel(this,introductionDialogues);
        
        //Add panels to both frame and cardLayout
        this.add(introductionPanel);
        mainPanel.add(introductionPanel, "IntroductionPanel");
    }

    public void initExplorationInterface() throws IOException{
        explorationPanel = new ExplorationPanel(this, player);

        this.add(explorationPanel);
        mainPanel.add(explorationPanel, "ExplorationPanel");

    }

    public void initEncounterPanel(){
        encounterPanel = new EncounterPanel(this,player);

        this.add(encounterPanel);
        mainPanel.add(encounterPanel, "EncounterPanel");
    }

    public void initPlayer(String inputtedName, int classChosen){
        this.player = Player.createPlayer(inputtedName, classChosen);
    }

    private void switchPanel(String text){
        cardLayout.show(mainPanel, text);
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
