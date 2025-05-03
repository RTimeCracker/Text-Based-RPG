
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

public class ExplorationPanel extends JLayeredPane{
    MainFrame frame;
    JButton[] directionButtons = new JButton[4];
    JLabel dialogueLabel = new JLabel();
    JLabel coordinatesLabel = new JLabel();
    BackgroundPanel backgroundPanel;
    Player player;
    Database database;
    private File currentlyPlayingSFX;
    File footstepSFX = new File("Text-Based RPG\\SFX\\Footsteps.wav");
    private Clip SFXClip;

    public ExplorationPanel(MainFrame frame, Player player, Database database) throws IOException{
        this.frame = frame;
        this.setSize(new Dimension(frame.SCREENWIDTH, frame.SCREENHEIGHT));
        this.setLayout(null);
        this.player = player;
        this.database = database;

        setBackground();
        setButtons();
        setDialogue();
        setCoordinates();
        try {
            SFXClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void setDialogue(){
        dialogueLabel.setText("Where would you like to go?");
        dialogueLabel.setBounds(100, 40, 600 ,300);
        dialogueLabel.setFont(new Font("Roboto",Font.BOLD,32));
        dialogueLabel.setForeground(Color.BLACK);
        dialogueLabel.setHorizontalAlignment(JLabel.CENTER);
        dialogueLabel.setVerticalAlignment(JLabel.TOP);

        this.add(dialogueLabel, Integer.valueOf(1));
    }

    private void setCoordinates(){
        coordinatesLabel.setText("X: " + player.xPos + " Y: " + player.yPos);
        coordinatesLabel.setBounds(0, 0, 200 ,100);
        coordinatesLabel.setFont(new Font("Roboto",Font.BOLD,32));
        coordinatesLabel.setForeground(Color.WHITE);
        coordinatesLabel.setHorizontalAlignment(JLabel.LEFT);
        coordinatesLabel.setVerticalAlignment(JLabel.TOP);

        this.add(coordinatesLabel, Integer.valueOf(1));
    }

    private void onButtonClick(int x, int y) {
        player.move(x, y);
        PlaySFX(footstepSFX, SFXClip);

        if (player.zone != null && player.zone.zoneType == ZoneType.Village) {
            frame.updateGameState(MainFrame.GameState.Village);
        } else if (player.zone.zoneType == ZoneType.Encounter) {
            player.currentEnemy = Enemy.generateRandom(database);
            frame.updateGameState(MainFrame.GameState.Encounter);
            frame.update();
        } else if(player.zone.zoneType == ZoneType.Dungeon){
            player.currentEnemy = Enemy.generateBoss(database);
            frame.updateGameState(MainFrame.GameState.Encounter);
            frame.update();
        }
        rePaint();
    }
    
    private void setButtons(){

        for (int i = 0; i < 4; i++) {
            JButton button = new JButton(); 
            

            switch (i) {
                case 0:
                    button.setText("North");
                    button.setBounds(360,350, 80,50);
                    button.addActionListener(e -> onButtonClick(0, 1));
                    break;
                case 1:
                    button.setText("East");
                    button.setBounds(520,450, 80,50);
                    button.addActionListener(e -> onButtonClick(1, 0));
                    break;

                case 2:
                    button.setText("South");
                    button.setBounds(360,575, 80,50);
                    button.addActionListener(e -> onButtonClick(0, -1));
                    break;

                case 3:
                button.setText("West");
                button.setBounds(200,450, 80,50);
                button.addActionListener(e -> onButtonClick(-1, 0));
                    break;
                default:
    
            }
            
            this.add(button, Integer.valueOf(1));
            directionButtons[i] = button;
        }
    }

    public  void setBackground(){
        try {
            BufferedImage menuBackgroundImage;
            menuBackgroundImage = ImageIO.read(new File("Text-Based RPG\\\\Images\\\\Backgrounds\\\\BattleGround1.jpg"));
            System.out.println(player.zone.zoneType);
            if(player.zone.zoneType == ZoneType.Dungeon){
                menuBackgroundImage = ImageIO.read(new File("Text-Based RPG\\Images\\Backgrounds\\Battleground4.jpg"));
            }
            
            Image scaledImage = menuBackgroundImage.getScaledInstance(frame.SCREENWIDTH, frame.SCREENHEIGHT, Image.SCALE_SMOOTH);
            backgroundPanel = new BackgroundPanel(scaledImage);
    
            backgroundPanel.setMaximumSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
            backgroundPanel.setSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
            this.add(backgroundPanel, Integer.valueOf(0));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void rePaint(){
        setBackground();
        coordinatesLabel.setText("X: " + player.xPos + " Y: " + player.yPos);
    }

    public void PlaySFX(File musicPath, Clip clip){
        try {

            if(musicPath.exists()){
                if(clip.isOpen() && musicPath.equals(currentlyPlayingSFX)){
                    clip.setFramePosition(0);
                    clip.start();
                    return;
                }

                clip.close();
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

                clip.open(audioInput);
                clip.start();
                this.currentlyPlayingSFX = musicPath;
            }else{
                System.out.println("Can't find file.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


