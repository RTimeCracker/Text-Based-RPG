
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainMenuPanel extends JPanel{
    public MainMenuPanel(MainFrame frame, JPanel mainPanel, CardLayout cardLayout) throws IOException{
        this.setPreferredSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));

        BufferedImage menuBackgroundImage = ImageIO.read(new File("Text-Based RPG\\Images\\MenuBackground.png"));
        Image scaledImage = menuBackgroundImage.getScaledInstance(frame.SCREENWIDTH, frame.SCREENHEIGHT, Image.SCALE_SMOOTH);
        BackgroundPanel backgroundPanel = new BackgroundPanel(scaledImage);
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        backgroundPanel.setMaximumSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
        backgroundPanel.setPreferredSize(new Dimension(frame.SCREENWIDTH,frame.SCREENHEIGHT));
        frame.add(backgroundPanel);
        backgroundPanel.add(this);

        JLabel titleLabel = new JLabel("<html>Vaiken: <br/>----Last Legacy----</html>");
        titleLabel.setFont(new Font("Roboto",Font.BOLD,60));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(100,0,0,0));
        
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonsListPanel = new JPanel();
        buttonsListPanel.setPreferredSize(new Dimension(0,350));
        buttonsListPanel.setLayout(new BoxLayout(buttonsListPanel, BoxLayout.PAGE_AXIS));
        buttonsListPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonsListPanel.setOpaque(false);
        buttonsListPanel.setBackground(new Color(0,0,0,0));
        
        JButton playButton = new JButton("Play");
        JButton creditsButton = new JButton("Credits");
        JButton exitButton = new JButton("Exit");

        playButton.setFont(new Font("Serif",Font.BOLD,32));
        playButton.setFocusPainted(false);

        creditsButton.setFont(new Font("Serif",Font.BOLD,32));
        creditsButton.setFocusPainted(false);

        exitButton.setFont(new Font("Serif",Font.BOLD,32));
        exitButton.setFocusPainted(false);


        playButton.setMaximumSize(new Dimension(100, 50));
        playButton.setPreferredSize(new Dimension(100, 50));

        creditsButton.setMaximumSize(new Dimension(150, 50));
        creditsButton.setPreferredSize(new Dimension(150, 50));

        exitButton.setMaximumSize(new Dimension(100, 50));
        exitButton.setPreferredSize(new Dimension(100, 50));

        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsListPanel.add(playButton);
        buttonsListPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonsListPanel.add(creditsButton);
        buttonsListPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonsListPanel.add(exitButton);

        this.add(buttonsListPanel, BorderLayout.PAGE_END);

        //Credits panel
        JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.PAGE_AXIS));
        JLabel name1 = new JLabel("Lead Director: Lanz Utitco");
        JLabel name2 = new JLabel("Manager/Assistant Programmer: Kenneth Sabangan");
        JLabel name3 = new JLabel("Item Director: Ryan Llano");
        JLabel name4 = new JLabel("Art Director: John Louie Sion");
        JLabel name5 = new JLabel("Music Director: Leonard Orit");
        JLabel name6 = new JLabel("Database Master: Khervin Yagdulas");
        JButton closeCreditsButton = new JButton("Close");

        name1.setBorder(new EmptyBorder(50,0,0,0));
        name1.setAlignmentX(Component.CENTER_ALIGNMENT);
        name2.setAlignmentX(Component.CENTER_ALIGNMENT);
        name3.setAlignmentX(Component.CENTER_ALIGNMENT);
        name4.setAlignmentX(Component.CENTER_ALIGNMENT);
        name5.setAlignmentX(Component.CENTER_ALIGNMENT);
        name6.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeCreditsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        creditsPanel.add(name1);
        creditsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creditsPanel.add(name2);
        creditsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creditsPanel.add(name3);
        creditsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creditsPanel.add(name4);
        creditsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creditsPanel.add(name5);
        creditsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creditsPanel.add(name6);
        creditsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        creditsPanel.add(closeCreditsButton);
        

        mainPanel.add(backgroundPanel, "MainMenuPanel");
        mainPanel.add(creditsPanel,"Credits");
        playButton.addActionListener(e -> {frame.updateGameState(MainFrame.GameState.Introduction);});
        creditsButton.addActionListener(e -> cardLayout.show(mainPanel, "Credits"));
        closeCreditsButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenuPanel"));
        exitButton.addActionListener(e -> System.exit(0));

        frame.add(mainPanel);


        frame.setLocationRelativeTo(null); //Sets the position of the frame to be at the center of the screen
        frame.setVisible(true);
    }
}
