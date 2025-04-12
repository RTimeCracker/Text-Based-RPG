import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.*;

public class IntroductionClickablePanel extends JPanel implements MouseListener{

    int count = 1;
    JLabel label;
    String[] texts;
    boolean isContinuable;
    MainFrame frame;

    String inputtedName;
    int classChosen;

    public IntroductionClickablePanel(MainFrame frame, String[] introductionDialogues) {
        this.frame = frame;

        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setSize(new Dimension(800, 700));

        addMouseListener(this);

        JLabel introductionLabel = new JLabel("<html>" + introductionDialogues[0] + "<html>");
        Font font = new Font("SansSerif",Font.PLAIN,32);

        this.insertDialogues(introductionLabel,introductionDialogues,true);
        introductionLabel.setForeground(Color.white);
        introductionLabel.setBounds(new Rectangle(200,100, 400,400));
        introductionLabel.setFont(font);
        introductionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introductionLabel.setVerticalAlignment(SwingConstants.TOP);

        this.add(introductionLabel);

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
    frame.initPlayer(inputtedName, classChosen);
    try {
        frame.initExplorationInterface();
        frame.initEncounterPanel();
        frame.initVillageInterface();  // Make sure village panel is initialized
        frame.updateGameState(MainFrame.GameState.Exploration);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
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
