import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

public class EncounterPanel extends JLayeredPane{
    MainFrame frame;
    Player player;

    JLabel enemyLabel;

    JPanel panelBox;
    JPanel panelOptions;
    JPanel panelDialogue;
    JPanel panelInventory;
    JPanel panelInventoryContents;
    JPanel panelStatus;
    JPanel panelCommands;
    JPanel talkPanel;
    JPanel talkPanelChoices;
    JPanel inventoryCommands;

    JButton attackButton;
    JButton talkButton;
    JButton talkChoice1;
    JButton talkChoice2;

    JLabel labelDialogue;
    

    Item selectedItem;

    JScrollPane scrollPaneInventoryContents;

    String[] playerDialogueTexts;
    int playerDialogueCount;

    String[] enemyDialogueTexts;
    int enemyDialogueCount;

    CardLayout panelCardLayout = new CardLayout();

    JLabel HP;
    JLabel LVL;

    boolean isEnemyTurn = false;

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

        panelStatus = new JPanel();   
        panelStatus.setOpaque(false);        
        panelStatus.setBounds(3,3,372, 194);
        panelStatus.setLayout(new BoxLayout(panelStatus,BoxLayout.PAGE_AXIS));

        HP = new JLabel("HP: " + player.hp + "/" + player.maxHp);
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

        attackButton = new JButton("Attack");
        JButton skillsButton = new JButton("Skills");
        JButton inventory = new JButton("Inventory");
        talkButton = new JButton();
        talkButton.setEnabled(false);

        attackButton.addActionListener(e -> onAttackButtonClick());
        skillsButton.addActionListener(e -> onSkillsButtonClick());
        inventory.addActionListener(e -> onInventoryButtonClick());
        talkButton.addActionListener(e -> onTalkButtonClick());
        
        panelCommands.add(attackButton);
        panelCommands.add(skillsButton);
        panelCommands.add(inventory);
        panelCommands.add(talkButton);
        talkButton.setOpaque(true);
        talkButton.setContentAreaFilled(false);
        talkButton.setBorderPainted(false);
        talkButton.setFocusPainted(false);

        panelOptions.add(panelStatus);
        panelOptions.add(panelCommands);

        //=====Panel Inventory======
        panelInventory = new JPanel();
        panelInventory.setBounds(0,0,750,200);
        panelInventory.setLayout(null);
        
        panelInventoryContents = new JPanel();
    
        panelInventoryContents.setLayout(new BoxLayout(panelInventoryContents,BoxLayout.PAGE_AXIS));
        scrollPaneInventoryContents = new JScrollPane(panelInventoryContents);
        scrollPaneInventoryContents.setBounds(10,10,533,180);
        scrollPaneInventoryContents.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelInventory.add(scrollPaneInventoryContents);

        inventoryCommands = new JPanel();
        inventoryCommands.setOpaque(false);        
        inventoryCommands.setBounds(568,3,157, 194);
        inventoryCommands.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton useButton = new JButton("Use");
        JButton exitButton = new JButton("Exit");

        useButton.addActionListener(e -> onUseButtonClick());
        exitButton.addActionListener(e -> onInventoryExitButtonClick());

        inventoryCommands.add(useButton);
        inventoryCommands.add(exitButton);
        panelInventory.add(inventoryCommands);

        //====Talk Panel====
        talkPanel = new JPanel();
        talkPanel.setBounds(0,0,750,200);
        talkPanel.setLayout(null);

        talkPanelChoices = new JPanel();
    
        talkPanelChoices.setLayout(new BoxLayout(talkPanelChoices,BoxLayout.PAGE_AXIS));
        talkPanelChoices.setBounds(10,10,533,180);
        talkPanelChoices.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));

        talkChoice1 = new JButton();
        talkChoice2 = new JButton();

        talkChoice1.setVerticalAlignment(SwingConstants.TOP);
        talkChoice1.setHorizontalAlignment(SwingConstants.LEFT);
        talkChoice1.setForeground(Color.BLACK);
        talkChoice1.setBackground(Color.white);
        talkChoice1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        talkChoice1.setMaximumSize(new Dimension(500,80));
        talkChoice1.setPreferredSize(new Dimension(500,80));

        talkChoice2.setVerticalAlignment(SwingConstants.TOP);
        talkChoice2.setHorizontalAlignment(SwingConstants.LEFT);
        talkChoice2.setForeground(Color.BLACK);
        talkChoice2.setBackground(Color.white);
        talkChoice2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        talkChoice2.setMaximumSize(new Dimension(500,90));
        talkChoice2.setPreferredSize(new Dimension(500,90));

        talkPanelChoices.add(Box.createRigidArea(new Dimension(0,10)));
        talkPanelChoices.add(talkChoice1);
        talkPanelChoices.add(Box.createRigidArea(new Dimension(0,10)));
        talkPanelChoices.add(talkChoice2);
        talkPanelChoices.add(Box.createRigidArea(new Dimension(0,10)));

        talkPanel.add(talkPanelChoices);


        panelBox.add(panelOptions, "PanelOptions");        
        panelCardLayout.show(panelBox, "PanelOptions");

        panelBox.add(panelDialogue, "PanelDialogue");
        panelBox.add(panelInventory, "PanelInventory");
        panelBox.add(talkPanel, "TalkPanel");

        this.add(panelBox, Integer.valueOf(1));
    }

    private void onDialoguePanelClick(){    
        
        if(playerDialogueCount <= playerDialogueTexts.length - 1){
            labelDialogue.setText(playerDialogueTexts[playerDialogueCount]);
            playerDialogueCount++;
        }else{
            
            if(isEnemyTurn == false){
                enemyTurn();
            }
            
            if(enemyDialogueCount <= enemyDialogueTexts.length - 1){
                labelDialogue.setText(enemyDialogueTexts[enemyDialogueCount]);
                enemyDialogueCount++;
            }else{
                isEnemyTurn = false;
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

    private void onSkillsButtonClick(){

    }

    private void onTalkButtonClick(){
        Random random = new Random();
        String goodDialogue = player.GoodDialogue.get(random.nextInt(player.GoodDialogue.size()));
        String badDialogue = player.BadDialogue.get(random.nextInt(player.BadDialogue.size()));
        List<String> dialogues = new ArrayList();
        dialogues.add(badDialogue);
        dialogues.add(goodDialogue);
        Collections.shuffle(dialogues);

        talkChoice1.setText("<html>" + dialogues.get(0) + "</html>");
        talkChoice2.setText("<html>" + dialogues.get(1) + "</html>");

        if(talkChoice1.getText().equals(badDialogue)){
            talkChoice1.addActionListener(e -> onBadTalkButtonClick(talkChoice1.getText()));
            talkChoice2.addActionListener(e -> onGoodTalkButtonClick(talkChoice2.getText()));
        }else{
            talkChoice1.addActionListener(e -> onGoodTalkButtonClick(talkChoice2.getText()));
            talkChoice2.addActionListener(e -> onBadTalkButtonClick(talkChoice1.getText()));
        }

        panelCardLayout.show(panelBox, "TalkPanel");
    }

    private void onBadTalkButtonClick(String playerText){
        String[] playerTalkTexts = {playerText};
        playerDialogueTexts = playerTalkTexts;

        labelDialogue.setText(playerTalkTexts[0]);
        update();

        playerDialogueCount = 1;
        enemyDialogueCount = 0;
        panelCardLayout.show(panelBox, "PanelDialogue");
    }

    private void onGoodTalkButtonClick(String playerText){
        String[] playerTalkTexts = {playerText};
        playerDialogueTexts = playerTalkTexts;

        labelDialogue.setText(playerTalkTexts[0]);
        update();

        playerDialogueCount = 1;
        enemyDialogueCount = 0;
        panelCardLayout.show(panelBox, "PanelDialogue");
    }

    private void enemyTurn(){
        String[] enemyAttackTexts = {"Enemy Fought Back!"};
        isEnemyTurn = true;
        enemyDialogueTexts = enemyAttackTexts;

        labelDialogue.setText(enemyAttackTexts[0]);
        player.currentEnemy.attackCommand(player);
        update();

        panelCardLayout.show(panelBox, "PanelDialogue");
    }

    private void onInventoryButtonClick(){
        for(int i = 0; i <= player.inventory.size() -1; i++){
            JButton button = new JButton("<html> <body> <span style='font-size: 24px'>" + player.inventory.get(i).name + " </span> <br> Amount: </body></html>");
            final int buttonIndex = i;
            //button.setContentAreaFilled(false);
            //button.setBorderPainted(false);
            button.setOpaque(true);
            button.setForeground(Color.BLACK);
            button.setBackground(Color.white);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setMaximumSize(new Dimension(500,100));
            button.setPreferredSize(new Dimension(500,100));

            button.setIcon(new ImageIcon(player.inventory.get(i).image));
            button.setHorizontalAlignment(SwingConstants.LEFT);

            button.addActionListener(e -> onItemButtonClick(buttonIndex));
            panelInventoryContents.add(button);
            if(i < player.inventory.size() -1){
                panelInventoryContents.add(Box.createRigidArea(new Dimension(0,40)));
            }
        }
        

        panelCardLayout.show(panelBox, "PanelInventory");
    }

    private void onItemButtonClick(int buttonIndex){
        selectedItem = player.inventory.get(buttonIndex);
    }

    private void onUseButtonClick(){
        if(selectedItem != null){
            String[] playerItemTexts = {"Used " + selectedItem.name + "."};
            playerDialogueTexts = playerItemTexts;
    
            labelDialogue.setText(playerItemTexts[0]);
            update();
    
            playerDialogueCount = 1;
            enemyDialogueCount = 0;
            player.useItem(selectedItem);
            selectedItem = null;
            panelInventoryContents.removeAll();
            panelCardLayout.show(panelBox, "PanelDialogue");
        }
    }

    private void onInventoryExitButtonClick(){
        panelCardLayout.show(panelBox, "PanelOptions");
    }

    public void setEnemy() {
        if (enemyLabel != null) {
            this.remove(enemyLabel);
        }
        enemyLabel = new JLabel();
        enemyLabel.setBounds(275, 50, 250, 400);
        enemyLabel.setLayout(null);
        enemyLabel.setVisible(true);
    
        if(player.currentEnemy.getEnemyImage() != null){
            enemyLabel.setIcon(new ImageIcon(player.currentEnemy.getEnemyImage()));
        }
        

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
        LVL.setText("LVL: " + player.level);
        if(player.zone.zoneType == ZoneType.Dungeon){
            talkButton.setOpaque(true);
            talkButton.setContentAreaFilled(true);
            talkButton.setBorderPainted(true);
            talkButton.setFocusPainted(true);
            talkButton.setText("Talk");
            talkButton.setEnabled(true);
        }else{
            talkButton.setOpaque(false);
            talkButton.setContentAreaFilled(false);
            talkButton.setBorderPainted(false);
            talkButton.setFocusPainted(false);
            talkButton.setText("");
            talkButton.setEnabled(false);
        }

        panelCardLayout.show(panelBox, "PanelOptions");
        setBackground();
        setEnemy();
        
    
    }

    public void update(){
        if (player.hp > 0 && player.currentEnemy.hp > 0) {
            HP.setText("HP: " + player.hp + "/" + player.maxHp);
            enemyLabel.setText("<html><body style='text-align:center;'>HP: "+ player.currentEnemy.hp +"<br>"+ player.currentEnemy.name +"</body></html>");
        } else {
            if (player.hp <= 0) {
                frame.updateGameState(MainFrame.GameState.Ending);
            } else if (player.currentEnemy.hp <= 0) {
                player.currentEnemy.onDeath(player);
                reSetup();
                frame.updateGameState(MainFrame.GameState.Exploration);
            }
        }
    }

    
}
