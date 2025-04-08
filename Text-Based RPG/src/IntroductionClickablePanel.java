import java.awt.*;
import java.awt.event.MouseListener;
import javax.swing.*;

public class IntroductionClickablePanel extends JPanel implements MouseListener{

    int count = 1;
    JLabel label;
    String[] texts;
    boolean isContinuable;

    Player player;

    public IntroductionClickablePanel() {
        addMouseListener(this);
    }
    
    public void insertDialogues(JLabel label, String[] texts, boolean isContinuable){
        this.isContinuable = isContinuable;
        this.label = label;
        this.texts = texts;
    }

    private void nameChoice(JLabel mainLabel){
        TextArea nameInput = new TextArea("Input your name here.");

        nameInput.setBounds(200, 350, 400, 100);
        this.add(nameInput);
        mainLabel.setText("What is your name?");
    }

    private void destroyButtons(JButton[] buttons){
        for(JButton i: buttons){
            this.remove(i);
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        if(!isContinuable) return;

        if(count < texts.length){
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

                warriorButton.addActionListener(event -> {Player.chooseClass(0); destroyButtons(buttons);});
                mageButton.addActionListener(event -> {Player.chooseClass(1);});
                tankButton.addActionListener(event -> {Player.chooseClass(2);});
                summonerButton.addActionListener(event -> {Player.chooseClass(3);});

                classesPanel.add(warriorButton);
                classesPanel.add(mageButton);
                classesPanel.add(tankButton);
                classesPanel.add(summonerButton);
                this.repaint();

                this.add(classesPanel);
                isContinuable = true;
            }
            
            if("namechoice".equals(texts[count])){

            }
            count++;
        }
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
       
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
