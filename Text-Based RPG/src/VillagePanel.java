import java.awt.*;
import java.util.List;
import javax.swing.*;

public class VillagePanel extends JLayeredPane {
    private MainFrame frame;
    private Player player;
    private JLabel goldLabel;
    private JTextArea dialogueArea;
    
    public VillagePanel(MainFrame frame, Player player) {
        this.frame = frame;
        this.player = player;
        this.setSize(new Dimension(frame.SCREENWIDTH, frame.SCREENHEIGHT));
        this.setLayout(null);
        
        setupBackground();
        setupUI();
    }
    
    private void setupBackground() {
        try {
            ImageIcon bgIcon = new ImageIcon("Text-Based RPG\\Images\\Backgrounds\\VillageBackground.jpg");
            Image scaledImage = bgIcon.getImage().getScaledInstance(frame.SCREENWIDTH, frame.SCREENHEIGHT, Image.SCALE_SMOOTH);
            JLabel background = new JLabel(new ImageIcon(scaledImage));
            background.setBounds(0, 0, frame.SCREENWIDTH, frame.SCREENHEIGHT);
            this.add(background, Integer.valueOf(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setupUI() {
        // Gold display
        goldLabel = new JLabel("Gold: " + player.money);
        goldLabel.setBounds(650, 20, 150, 30);
        goldLabel.setFont(new Font("Serif", Font.BOLD, 20));
        goldLabel.setForeground(Color.YELLOW);
        this.add(goldLabel, Integer.valueOf(2));
        
        // Dialogue area
        dialogueArea = new JTextArea("Welcome to the village!\nWhat would you like to do?");
        dialogueArea.setBounds(50, 380, 600, 300);
        dialogueArea.setFont(new Font("Serif", Font.PLAIN, 18));
        dialogueArea.setWrapStyleWord(true);
        dialogueArea.setLineWrap(true);
        dialogueArea.setOpaque(false);
        dialogueArea.setEditable(false);
        dialogueArea.setForeground(Color.WHITE);
        this.add(dialogueArea, Integer.valueOf(2));
        
        // Buttons
        JButton buyButton = createButton("Buy Items", 580, 400);
        buyButton.addActionListener(e -> showShop(true));
        
        JButton sellButton = createButton("Sell Items", 580, 475);
        sellButton.addActionListener(e -> showShop(false));
        
        JButton leaveButton = createButton("Leave Village", 580, 550);
        leaveButton.addActionListener(e -> frame.updateGameState(MainFrame.GameState.Exploration));
        
        this.add(buyButton, Integer.valueOf(2));
        this.add(sellButton, Integer.valueOf(2));
        this.add(leaveButton, Integer.valueOf(2));
    }
    
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 200, 50);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.YELLOW);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
        });
        
        return button;
    }
    
    private void showShop(boolean isBuying) {
        removeAll();
        setupBackground();
        
        JLabel title = new JLabel(isBuying ? "Village Shop - Buy" : "Village Shop - Sell");
        title.setBounds(50, 380, 200, 30);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        this.add(title, Integer.valueOf(2));
        
        goldLabel = new JLabel("Gold: " + player.money);
        goldLabel.setBounds(450, 380, 150, 30);
        goldLabel.setFont(new Font("Serif", Font.BOLD, 20));
        goldLabel.setForeground(Color.YELLOW);
        this.add(goldLabel, Integer.valueOf(2));
        
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(itemPanel);
        scrollPane.setBounds(30, 410, 530, 180);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        this.add(scrollPane, Integer.valueOf(2));
        
        List<Item> items = isBuying ? Zone.SHOP_ITEMS : player.inventory;
        
        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel(isBuying ? "No items available for purchase" : "Your inventory is empty!");
            emptyLabel.setFont(new Font("Serif", Font.PLAIN, 18));
            emptyLabel.setForeground(Color.WHITE);
            itemPanel.add(emptyLabel);
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                JPanel itemEntry = createItemEntry(item, i, isBuying);
                itemPanel.add(itemEntry);
                itemPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        JButton backButton = createButton("Back", 580, 400);
        backButton.addActionListener(e -> {
            removeAll();
            setupUI();
            repaint();
        });
        this.add(backButton, Integer.valueOf(2));
        
        revalidate();
        repaint();
    }
    
    private JPanel createItemEntry(Item item, int index, boolean isBuying) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(450, 60));
        
        JLabel nameLabel = new JLabel((index + 1) + ". " + item.name);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        
        JLabel descLabel = new JLabel(item.description);
        descLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        descLabel.setForeground(Color.LIGHT_GRAY);
        
        JLabel priceLabel = new JLabel(isBuying ? "Price: 100 gold" : "Sell Price: 50 gold");
        priceLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        priceLabel.setForeground(Color.YELLOW);
        
        JButton actionButton = new JButton(isBuying ? "Buy" : "Sell");
        actionButton.setPreferredSize(new Dimension(80, 30));
        actionButton.addActionListener(e -> handleItemTransaction(item, isBuying));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.add(nameLabel);
        leftPanel.add(descLabel);
        leftPanel.add(priceLabel);
        
        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(actionButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private void handleItemTransaction(Item item, boolean isBuying) {
        if (isBuying) {
            if (player.money >= 100) {
                player.money -= 100;
                player.addItemToInventory(item);
                goldLabel.setText("Gold: " + player.money);
                JOptionPane.showMessageDialog(this, "You bought " + item.name + "!", "Purchase Complete", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough gold!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            player.money += 50;
            player.inventory.remove(item);
            goldLabel.setText("Gold: " + player.money);
            JOptionPane.showMessageDialog(this, "You sold " + item.name + " for 50 gold!", "Sale Complete", JOptionPane.INFORMATION_MESSAGE);
            showShop(false); // Refresh sell view
        }
    }
    
    public void updateGoldDisplay() {
        if (goldLabel != null) {
            goldLabel.setText("Gold: " + player.money);
        }
    }
}