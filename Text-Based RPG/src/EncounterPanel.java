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
    JPanel panelStatus;
    JPanel panelCommands;

    JLabel labelDialogue;

    String[] dialogueTexts;
    int dialogueCount;

    CardLayout panelCardLayout = new CardLayout();

    JLabel HP;
    JLabel LVL;

    BackgroundPanel backgroundPanel;

    public EncounterPanel(MainFrame frame, Player player){
        this.frame = frame;
        this.player = player;
        this.setLayout(null);

        setPanelBox();
        setBackground();
    }

    private void setPanelBox(){
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
            public void mouseClicked(MouseEvent e) {
                onDialoguePanelClick();
            }
        });

        labelDialogue = new JLabel();
        labelDialogue.setFont(new Font("Roboto",Font.BOLD,32));

        panelDialogue.add(labelDialogue);

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
        
        panelCommands.add(AttackButton);
        panelCommands.add(Inventory);

        panelOptions.add(panelStatus);
        panelOptions.add(panelCommands);

        panelBox.add(panelOptions, "PanelOptions");        
        panelCardLayout.show(panelBox, "PanelOptions");
        panelBox.add(panelDialogue, "PanelDialogue");

        this.add(panelBox, Integer.valueOf(1));
    }

    private void onDialoguePanelClick(){    
        if(dialogueCount < dialogueTexts.length - 1){
            labelDialogue.setText(dialogueTexts[dialogueCount]);
            dialogueCount++;
        }else{
            panelCardLayout.show(panelBox, "PanelOptions");
        }
    }

    private void insertDialogue(String[] texts){
        dialogueTexts = texts;
    }

    private void onAttackButtonClick(){
        String[] texts = {"Attacked Enemy"};
        insertDialogue(texts);

        labelDialogue.setText(texts[0]);
        player.attackCommand();
        update();

        panelCardLayout.show(panelBox, "PanelDialogue");
    }

    public void setEnemy(){
        enemyLabel = new JLabel("<html><body style='text-align:center;'>HP: "+ player.currentEnemy.hp +"<br>"+ player.currentEnemy.name +"</body></html>");
        enemyLabel.setBounds(275, 50, 250, 400);
        enemyLabel.setFont(new Font("Roboto",Font.BOLD,32));
        enemyLabel.setHorizontalAlignment(JLabel.CENTER);
        enemyLabel.setVerticalTextPosition(JLabel.TOP);
        enemyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        enemyLabel.setVerticalAlignment(JLabel.BOTTOM);
        enemyLabel.setForeground(Color.BLACK);

        enemyImage = new ImageIcon((new ImageIcon("Text-Based RPG\\Images\\Enemy\\OritEnemy.png").getImage()).getScaledInstance(250, 250,Image.SCALE_SMOOTH));
        
        enemyLabel.setIcon(enemyImage);
        this.add(enemyLabel,Integer.valueOf(1));

    }

    private void setBackground(){
        
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

    public void reSetup(){
        if(backgroundPanel != null && enemyLabel != null){
            this.remove(backgroundPanel);
            this.remove(enemyLabel);
        }
        setBackground();
        setEnemy();
    
    }

    public void update(){
        if(player.hp > 0 && player.currentEnemy.hp > 0){
            HP.setText("HP: " + player.hp);
            enemyLabel.setText("<html><body style='text-align:center;'>HP: "+ player.currentEnemy.hp +"<br>"+ player.currentEnemy.name +"</body></html>");
        }else{
            if(player.hp <= 0){

            }else if(player.currentEnemy.hp <= 0){
                reSetup();
                frame.updateGameState(MainFrame.GameState.Exploration);
            }
        }

    }
}
