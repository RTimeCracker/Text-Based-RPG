import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class EncounterPanel extends JLayeredPane{
    MainFrame frame;
    Player player;

    JPanel panelBox;
    JPanel panelStatus;
    JPanel panelCommands;

    public EncounterPanel(MainFrame frame, Player player){
        this.frame = frame;
        this.player = player;
        this.setLayout(null);

        setPanelBox();
        setBackground();
        setEnemyImage();
    }

    private void setPanelBox(){
        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK,3);
        panelBox = new JPanel();
        panelBox.setBounds(25,450,750,200);
        panelBox.setBackground(Color.white);
        panelBox.setOpaque(true);
        panelBox.setBorder(panelBorder);
        panelBox.setLayout(null);

        panelStatus = new JPanel();   
        panelStatus.setOpaque(false);        
        panelStatus.setBounds(3,3,372, 194);
        panelStatus.setLayout(new BoxLayout(panelStatus,BoxLayout.PAGE_AXIS));

        JLabel HP = new JLabel("HP:");
        HP.setMaximumSize(new Dimension(200, 50));
        HP.setPreferredSize(new Dimension(200, 30));
        HP.setFont(new Font("Roboto",Font.BOLD,32));

        JLabel LVL = new JLabel("LVL:");
        LVL.setMaximumSize(new Dimension(200, 50));
        LVL.setPreferredSize(new Dimension(200, 30));
        LVL.setFont(new Font("Roboto",Font.BOLD,12));

        panelStatus.add(Box.createRigidArea(new Dimension(5,5)));
        panelStatus.add(HP);
        panelStatus.add(Box.createRigidArea(new Dimension(0,10)));
        panelStatus.add(LVL);

        panelCommands = new JPanel();
        panelCommands.setOpaque(false);        
        panelCommands.setBounds(375,3,372, 194);
        panelCommands.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton AttackButton = new JButton("Attack");
        JButton Inventory = new JButton("Inventory");
        
        panelCommands.add(AttackButton);
        panelCommands.add(Inventory);

        panelBox.add(panelStatus);        
        panelBox.add(panelCommands);
        this.add(panelBox, Integer.valueOf(1));
    }



    private void setEnemyImage(){
        JLabel enemyLabel = new JLabel("<html><body style='text-align:center;'>HP:<br>ORIT THE ENEMY</body></html>");
        enemyLabel.setBounds(275, 50, 250, 400);
        enemyLabel.setFont(new Font("Roboto",Font.BOLD,32));
        enemyLabel.setHorizontalAlignment(JLabel.CENTER);
        enemyLabel.setVerticalTextPosition(JLabel.TOP);
        enemyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        enemyLabel.setVerticalAlignment(JLabel.BOTTOM);
        ImageIcon enemyImage = new ImageIcon((new ImageIcon("Text-Based RPG\\Images\\Enemy\\OritEnemy.png").getImage()).getScaledInstance(250, 250,Image.SCALE_SMOOTH));
        
        enemyLabel.setIcon(enemyImage);
        this.add(enemyLabel,Integer.valueOf(1));

    }

    private void setBackground(){
        
        try {
            BufferedImage menuBackgroundImage;
            menuBackgroundImage = ImageIO.read(new File("Text-Based RPG\\\\Images\\\\Backgrounds\\\\BattleGround1.jpg"));
            System.out.println(player.zone.zoneType);
            if(player.zone.zoneType == ZoneType.Dungeon){
                System.out.println("Wat");
                menuBackgroundImage = ImageIO.read(new File("Text-Based RPG\\Images\\Backgrounds\\Battleground 4.jpg"));
            }
            
            Image scaledImage = menuBackgroundImage.getScaledInstance(frame.SCREENWIDTH, frame.SCREENHEIGHT, Image.SCALE_SMOOTH);
            BackgroundPanel backgroundPanel = new BackgroundPanel(scaledImage);
    
            backgroundPanel.setMaximumSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
            backgroundPanel.setSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
            this.add(backgroundPanel, Integer.valueOf(0));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      

        
    }
}
