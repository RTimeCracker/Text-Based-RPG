import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class EncounterPanel extends JLayeredPane{
    MainFrame frame;
    Player player;

    JLabel enemyLabel;
    ImageIcon enemyImage;

    JPanel panelBox;
    JPanel panelOptions;
    JPanel panelDialogue;
    JPanel panelInventory;
    JScrollPane panelInventoryContents;
    JPanel panelStatus;
    JPanel panelCommands;

    JLabel labelDialogue;
    JLabel[] labelInventory;

    String[] playerDialogueTexts;
    int playerDialogueCount;

    String[] enemyDialogueTexts;
    int enemyDialogueCount;

    CardLayout panelCardLayout = new CardLayout();

    JLabel HP;
    JLabel LVL;

    BackgroundPanel backgroundPanel;

    public EncounterPanel(MainFrame frame, Player player){
        this.frame = frame;
        this.player = player;
        this.setLayout(null);

        setPanelBox();
    }

    private void setPanelBox(){
        //=====Panel Box======
        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK,3);
        panelBox = new JPanel();
        panelBox.setBounds(25,450,750,200);
        panelBox.setBackground(Color.white);
        panelBox.setOpaque(true);
        panelBox.setBorder(panelBorder);
        panelBox.setLayout(panelCardLayout);

        panelOptions = new JPanel();
        panelOptions.setBounds(25,450,750,200);
        panelOptions.setLayout(null);

        panelDialogue = new JPanel();
        panelDialogue.setBounds(25,450,750,200);
        panelDialogue.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelDialogue.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                onDialoguePanelClick();
            }
        });

        labelDialogue = new JLabel();
        labelDialogue.setFont(new Font("Roboto",Font.BOLD,32));

        panelDialogue.add(labelDialogue);


        //=====Panel Inventory======
        panelInventory = new JPanel();
        panelInventory.setBounds(25,450,750,200);

        panelInventoryContents = new JScrollPane();
        panelInventoryContents.setBounds(25,450,543,200);
        //panelInventoryContents.setLayout(new BoxLayout(panelInventoryContents, BoxLayout.PAGE_AXIS));
        panelInventoryContents.add(panelInventory);

        panelStatus = new JPanel();   
        panelStatus.setOpaque(false);        
        panelStatus.setBounds(3,3,372, 194);
        panelStatus.setLayout(new BoxLayout(panelStatus,BoxLayout.PAGE_AXIS));

        HP = new JLabel("HP: " + player.hp);
        HP.setMaximumSize(new Dimension(200, 50));
        HP.setPreferredSize(new Dimension(200, 30));
        HP.setFont(new Font("Roboto",Font.BOLD,32));

        LVL = new JLabel("LVL: " + player.level);
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

        AttackButton.addActionListener(e -> onAttackButtonClick());
        Inventory.addActionListener(e -> onInventoryButtonClick());
        
        panelCommands.add(AttackButton);
        panelCommands.add(Inventory);

        panelOptions.add(panelStatus);
        panelOptions.add(panelCommands);

        panelBox.add(panelOptions, "PanelOptions");        
        panelCardLayout.show(panelBox, "PanelOptions");

        panelBox.add(panelDialogue, "PanelDialogue");
        panelBox.add(panelInventory, "PanelInventory");

        this.add(panelBox, Integer.valueOf(1));
    }

    private void onDialoguePanelClick(){    
        if(playerDialogueCount <= playerDialogueTexts.length - 1){
            labelDialogue.setText(playerDialogueTexts[playerDialogueCount]);
            playerDialogueCount++;
        }else{
            enemyTurn();
            if(enemyDialogueCount <= enemyDialogueTexts.length - 1){
                labelDialogue.setText(enemyDialogueTexts[enemyDialogueCount]);
                enemyDialogueCount++;
            }else{
                panelCardLayout.show(panelBox, "PanelOptions");
            }
        }

    }

    private void onAttackButtonClick(){
        String[] playerAttackTexts = {"Attacked Enemy!"};
        playerDialogueTexts = playerAttackTexts;

        labelDialogue.setText(playerAttackTexts[0]);
        player.attackCommand();
        update();

        playerDialogueCount = 1;
        enemyDialogueCount = 0;
        panelCardLayout.show(panelBox, "PanelDialogue");
    }

    private void enemyTurn(){
        String[] enemyAttackTexts = {"Enemy Fought Back!"};
        enemyDialogueTexts = enemyAttackTexts;

        labelDialogue.setText(enemyAttackTexts[0]);
        System.out.println("Enemy TUrn");
        player.currentEnemy.attackCommand(player);
        update();

        panelCardLayout.show(panelBox, "PanelDialogue");
    }

    private void onInventoryButtonClick(){


        panelCardLayout.show(panelBox, "PanelInventory");
    }

    public void setEnemy() {
        if (enemyLabel != null) {
            this.remove(enemyLabel);
        }
        enemyLabel = new JLabel();
        enemyLabel.setBounds(275, 50, 250, 400);
        enemyLabel.setLayout(null);
        enemyLabel.setVisible(true);
        enemyLabel.setOpaque(false);
    
        // Safe image loading with fallback
        Image displayImage;
        try {
            Image originalImage = player.currentEnemy.getEnemyImage();
            
            if (originalImage == null) {
                System.err.println("Enemy image is null for: " + player.currentEnemy.name);
                displayImage = createPlaceholderImage();
            } else {
                int width = 200;
                int height = 200;
                displayImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }
        } catch (Exception e) {
            System.err.println("Error loading enemy image: " + e.getMessage());
            displayImage = createPlaceholderImage();
        }
        enemyLabel.setIcon(new ImageIcon(displayImage));
        enemyLabel.setText("<html><div style='text-align:center;'>" +
                         player.currentEnemy.name + "<br>" +
                         "HP: " + player.currentEnemy.hp + "</div></html>");
        enemyLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        enemyLabel.setForeground(Color.WHITE);
        enemyLabel.setHorizontalTextPosition(JLabel.CENTER);
        enemyLabel.setVerticalTextPosition(JLabel.BOTTOM);
        enemyLabel.setHorizontalAlignment(JLabel.CENTER);
        enemyLabel.setVerticalAlignment(JLabel.CENTER);

        this.add(enemyLabel, Integer.valueOf(1));
        // Force UI update
        this.revalidate();
        this.repaint();
        // Debug output
        System.out.println("Enemy set: " + player.currentEnemy.name);
        System.out.println("Image dimensions: " + displayImage.getWidth(null) + "x" + displayImage.getHeight(null));
    }
    
    private Image createPlaceholderImage() {
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Draw placeholder
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 200, 200);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("IMAGE NOT FOUND", 20, 100);
        
        g2d.dispose();
        return img;
    }

    private void setBackground(){
        
        try {
            BufferedImage menuBackgroundImage;
            menuBackgroundImage = ImageIO.read(new File("Text-Based RPG\\Images\\Backgrounds\\BattleGround1.jpg"));
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

    public void reSetup(){
        if(backgroundPanel != null && enemyLabel != null){
            this.remove(backgroundPanel);
            this.remove(enemyLabel);
            this.revalidate();
            this.repaint();
        }
        panelCardLayout.show(panelBox, "PanelOptions");
        setBackground();
        setEnemy();
        
    
    }

    public void update(){
        if(player.hp > 0 && player.currentEnemy.hp > 0){
            HP.setText("HP: " + player.hp);
            enemyLabel.setText("<html><body style='text-align:center;'>HP: "+ player.currentEnemy.hp +"<br>"+ player.currentEnemy.name +"</body></html>");
        }else{
            if(player.hp <= 0){
                System.exit(0);
            }else if(player.currentEnemy.hp <= 0){
                reSetup();
                frame.updateGameState(MainFrame.GameState.Exploration);
            }
        }
    }

    
}
