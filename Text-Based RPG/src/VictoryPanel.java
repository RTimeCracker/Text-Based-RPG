import java.awt.*;
import javax.swing.*;

public class VictoryPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel victoryLabel;
    private JButton continueButton;

    public VictoryPanel(MainFrame frame, Player player) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK); // For a sleek look

        // VICTORY title
        titleLabel = new JLabel("VICTORY!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Victory message
        victoryLabel = new JLabel("You have defeated the 3 main bosses! Congratulations!", SwingConstants.CENTER);
        victoryLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        victoryLabel.setForeground(Color.WHITE);
        victoryLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(victoryLabel, BorderLayout.CENTER);

        // Continue button
        continueButton = new JButton("Continue to Main Menu");
        continueButton.setFont(new Font("Arial", Font.PLAIN, 18));

        continueButton.addActionListener(e -> {
            frame.updateGameState(MainFrame.GameState.MainMenu);
            frame.BGMClip.stop(); // Stop the current BGM
        


        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(continueButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}