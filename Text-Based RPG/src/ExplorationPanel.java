
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ExplorationPanel extends JLayeredPane{
    MainFrame frame;

    JButton[] directionButtons = new JButton[4];
    JLabel dialogueLabel = new JLabel();

    Player player;

    public ExplorationPanel(MainFrame frame, Player player) throws IOException{
        this.frame = frame;
        this.setSize(new Dimension(frame.SCREENWIDTH, frame.SCREENHEIGHT));
        this.setLayout(null);
        this.player = player;

        setBackground();
        setButtons();
        setDialogue();
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

    private void onButtonClick(int x, int y){
        this.player.move(x,y);
        if(player.zone.zoneType == ZoneType.Encounter || player.zone.zoneType == ZoneType.Dungeon){
            frame.updateGameState(MainFrame.GameState.Encounter);
        }
        //either encounter, village or dungeon
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

    private void setBackground() throws IOException{
        BufferedImage menuBackgroundImage = ImageIO.read(new File("Text-Based RPG\\Images\\Backgrounds\\BattleGround1.jpg"));
        Image scaledImage = menuBackgroundImage.getScaledInstance(frame.SCREENWIDTH, frame.SCREENHEIGHT, Image.SCALE_SMOOTH);
        BackgroundPanel backgroundPanel = new BackgroundPanel(scaledImage);

        backgroundPanel.setMaximumSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
        backgroundPanel.setSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
        this.add(backgroundPanel, Integer.valueOf(0));

        
    }
}
