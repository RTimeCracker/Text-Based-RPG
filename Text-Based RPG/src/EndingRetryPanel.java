import java.awt.*;
import javax.swing.*;

public class EndingRetryPanel extends JPanel {
    public EndingRetryPanel(MainFrame frame, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(null);
        setBackground(Color.BLACK);

        

        JPanel retryPanel = new JPanel();
        retryPanel.setBackground(Color.BLACK);
        retryPanel.setBounds(150, 200, 500, 300);
        retryPanel.setLayout(new BoxLayout(retryPanel, BoxLayout.Y_AXIS));

        JLabel retryLabel = new JLabel("Would you like to retry?");
        retryLabel.setForeground(Color.WHITE);
        retryLabel.setFont(new Font("Serif", Font.BOLD, 22));
        retryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton retryButton = new JButton("Retry");
        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retryButton.addActionListener(e -> {
            frame.updateGameState(MainFrame.GameState.Introduction);
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));

        retryPanel.add(retryLabel);
        retryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        retryPanel.add(retryButton);
        retryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        retryPanel.add(exitButton);

        add(retryPanel);
    }
}