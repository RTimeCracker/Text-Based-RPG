import java.awt.*;
import java.awt.event.MouseListener;
import javax.swing.*;

public class IntroductionClickablePanel extends JPanel implements MouseListener{

    int count = 1;
    JLabel label;
    String[] texts;
    boolean isContinuable;

    CardLayout cardLayout;
    JPanel mainPanel;
    MainFrame frame;

    String inputtedName;
    int classChosen;

    public IntroductionClickablePanel(CardLayout cardLayout, JPanel mainPanel, MainFrame frame) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.frame = frame;

        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setSize(new Dimension(800, 700));

        addMouseListener(this);
    }
    
    public void insertDialogues(JLabel label, String[] texts, boolean isContinuable){
        this.isContinuable = isContinuable;
        this.label = label;
        this.texts = texts;
    }

    private void nameChoice(JLabel mainLabel){
        JTextField nameInput = new JTextField("Input your name here.");
        JButton submitButton = new JButton("Submit");

        nameInput.setBounds(200, 350, 400, 20);
        nameInput.setForeground(Color.blue);
        
        submitButton.setBounds(362, 400, 75, 40);
        submitButton.addActionListener(e -> onSubmitButtonClick(submitButton, nameInput));

        mainLabel.setText("What is your name?");
        
        this.setLayout(null);
        this.repaint();
        this.add(nameInput);
        this.add(submitButton);
        
    }

    private void onSubmitButtonClick(JButton button, JTextField nameInput){
        inputtedName = nameInput.getText();
        //show next cardlayout.
        frame.initPlayer(inputtedName, classChosen);
        frame.updateGameState(MainFrame.GameState.Game);
        cardLayout.show(mainPanel, "ExplorationPanel");
    }

    private void onClassesButtonClick(JButton[] buttons, JPanel classesPanel, int classChoice){
        classChosen = classChoice;
        for(JButton i: buttons){
            i.setVisible(false);
            classesPanel.remove(i);
            
        }
        isContinuable = true; 
        loopThroughDialogues();
    }

    private void loopThroughDialogues(){
        if(!isContinuable) return;

        if(count <= texts.length - 1){
            label.setText("<html>" + texts[count] + "<html>");

            if("classchoice".equals(texts[count])){
                label.setText("Please choose your class.");
                isContinuable = false;

                JPanel classesPanel = new JPanel();
                classesPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
                
                classesPanel.setBounds(200, 350, 400, 100);
                classesPanel.setOpaque(false);
                classesPanel.setBackground(new Color(0,0,0,0));

                JButton warriorButton = new JButton("Warrior");
                JButton mageButton = new JButton("Mage");
                JButton tankButton = new JButton("Tank");
                JButton summonerButton = new JButton("Summoner");
                JButton[] buttons = {warriorButton, mageButton, tankButton, summonerButton};

                warriorButton.addActionListener(event -> {; onClassesButtonClick(buttons, classesPanel, 0);});
                mageButton.addActionListener(event -> {onClassesButtonClick(buttons, classesPanel, 1); });
                tankButton.addActionListener(event -> {onClassesButtonClick(buttons, classesPanel, 2);});
                summonerButton.addActionListener(event -> {onClassesButtonClick(buttons, classesPanel, 3);});

                classesPanel.add(warriorButton);
                classesPanel.add(mageButton);
                classesPanel.add(tankButton);
                classesPanel.add(summonerButton);
                this.repaint();

                this.add(classesPanel);
            }


            if("namechoice".equals(texts[count])){
                isContinuable = false;
                nameChoice(label);
            }
            count++;
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
       loopThroughDialogues();
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
    }
}
