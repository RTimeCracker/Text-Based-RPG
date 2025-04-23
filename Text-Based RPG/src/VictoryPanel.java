import java.awt.*;
import javax.swing.*;


public class VictoryPanel extends JPanel {



    private JLabel victoryLabel;
    private JButton continueButton;

    public VictoryPanel() {
        setLayout(new BorderLayout());

        
        victoryLabel = new JLabel("You have defeated the boss!", SwingConstants.CENTER);
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(victoryLabel, BorderLayout.CENTER);

       
        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.PLAIN, 18));
        add(continueButton, BorderLayout.SOUTH);
    }

    public JButton getContinueButton() {
        return continueButton;
    }    

}