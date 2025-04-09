
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ExplorationPanel extends JLayeredPane{
    MainFrame frame;

    JButton[] directionButtons = new JButton[4];
    JPanel directionButtonsPanel = new JPanel();

    public ExplorationPanel(MainFrame frame) throws IOException{
        this.frame = frame;
        this.setSize(new Dimension(frame.SCREENWIDTH, frame.SCREENHEIGHT));
        this.setLayout(null);

        setBackground();
        setButtons();
        
    }

    private void setButtons(){
        this.add(directionButtonsPanel, Integer.valueOf(1));
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton();
            button.setSize(100,50);

            switch (i) {
                case 0:
                    break;
                case 1:
                    
                    break;

                case 2:
                        
                    break;

                case 3:
                    
                    break;
                default:
    
            }

            directionButtonsPanel.add(button);
            directionButtons[i] = button;
        }
    }

    private void setBackground() throws IOException{
        BufferedImage menuBackgroundImage = ImageIO.read(new File("Text-Based RPG\\Images\\BattleGround1.jpg"));
        Image scaledImage = menuBackgroundImage.getScaledInstance(frame.SCREENWIDTH, frame.SCREENHEIGHT, Image.SCALE_SMOOTH);
        BackgroundPanel backgroundPanel = new BackgroundPanel(scaledImage);

        backgroundPanel.setMaximumSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
        backgroundPanel.setSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
        this.add(backgroundPanel, Integer.valueOf(0));

        
    }
}
