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
        MainMenu, Introduction, Exploration, Encounter, Village, Ending
    }

    GameState currentGameState = GameState.MainMenu;

    File menuMusicFile = new File("Text-Based RPG\\Music\\RPGMENUBGM.wav");
    File villageMusicFile = new File("Text-Based RPG\\Music\\Village Music.WAV");
    File explorationMusicFile = new File("Text-Based RPG\\Music\\Main game BGM.WAV");
    Clip BGMClip;

    CardLayout cardLayout;
    JPanel mainPanel;

    IntroductionClickablePanel introductionPanel;
    ExplorationPanel explorationPanel;
    EncounterPanel encounterPanel;
    VillagePanel villagePanel;
    EndingRetryPanel endingPanel;

    String[] introductionDialogues = {"Welcome Vaiken: Last Legacy", "A game where you can explore, fight enemies, and gather loot!", "classchoice", "namechoice"};
    
    Player player;

    public Database database;


    public MainFrame(String title) throws IOException, LineUnavailableException{
        super(title);

        database = new Database();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(SCREENWIDTH,SCREENHEIGHT);

        BGMClip = AudioSystem.getClip();
        BGMClip.loop(Clip.LOOP_CONTINUOUSLY);
        PlayMusic(menuMusicFile, BGMClip);


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initMainMenu();
        initIntroductionInterface();
        initEndingPanel();

        this.add(mainPanel);

        
    }

    public void updateGameState(GameState gameState) {
        currentGameState = gameState;
        StopMusic(BGMClip);

        switch (currentGameState) {
            case MainMenu:
                System.out.println("Menu");
                PlayMusic(menuMusicFile, BGMClip);
                break;
            case Introduction:
                switchPanel("IntroductionPanel");
                
                break;
            case Exploration:
                switchPanel("ExplorationPanel");
                PlayMusic(explorationMusicFile, BGMClip);
                break;
            case Encounter:
                switchPanel("EncounterPanel");
                break;
            case Village:
                villagePanel.updateGoldDisplay();
                switchPanel("VillagePanel");
                PlayMusic(villageMusicFile, BGMClip);
                break;
            case Ending:
                switchPanel("EndingPanel");
                StopMusic(BGMClip);
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
        explorationPanel = new ExplorationPanel(this, player, database);
        mainPanel.add(explorationPanel, "ExplorationPanel");
        player.setFrame(this);

    }

    public void initEndingPanel() {
        endingPanel = new EndingRetryPanel(this, mainPanel, cardLayout);
        mainPanel.add(endingPanel, "EndingPanel");
    }


    public void initEncounterPanel(){
        encounterPanel = new EncounterPanel(this,player);

        this.add(encounterPanel);
        mainPanel.add(encounterPanel, "EncounterPanel");
    }

    public void initPlayer(String inputtedName, int classChosen){
        this.player = Player.createPlayer(inputtedName, classChosen, this);
        this.player.setFrame(this);
    }

    private void switchPanel(String text){
        cardLayout.show(mainPanel, text);
    } 

    public void resetGame() {
        player = null;
        introductionPanel = new IntroductionClickablePanel(this, introductionDialogues);
        mainPanel.add(introductionPanel, "IntroductionPanel");
    }

    public void initVillageInterface() {
        villagePanel = new VillagePanel(this, player);
        mainPanel.add(villagePanel, "VillagePanel");
    }

    public static void PlayMusic(File musicPath, Clip clip){
        try {

            if(musicPath.exists()){
                if(clip.isOpen()){
                    clip.close();
                }
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

    public static void StopMusic(Clip clip){
        try {
            
            if(clip != null){
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
