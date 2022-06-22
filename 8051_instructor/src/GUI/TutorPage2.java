package GUI;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class TutorPage2 extends JFrame{
    private final JPanel panel3;
    private final JPanel panel4;
    private final JButton nextButton;
    private final JButton backButton;
    private final JButton[] instructions3 = new JButton[10];
    private final JButton[] instructions4 = new JButton[10];
    private final String[] instructionsName = {"NOP","AJMP","LJMP","RR","INC","JBC","ACALL","LCALL","RRC","DEC","JB","RET","RL","ADD","JNB","RETl","RLC","ADDC","JC","ORL","JNC","ANL","JZ","XRL","JNZ","JMP","MOV","SJMP","MOVC","DIV","SUBB","CPL","CJNE","PUSH","CLR","SWAP","XCH","POP","SETB","DA","DJNZ","MOVX"}; //42

    public TutorPage2(){
        super("8051 Instructor");
        setLayout(null);

        panel3 = new JPanel();
        panel3.setBounds(100, 20, 200, 350);
        panel3.setBackground(Color.WHITE);
        panel4 = new JPanel();
        panel4.setBounds(300, 20, 200, 350);
        panel4.setBackground(Color.WHITE);

        for(int i = 0; i < 10; i++){
            instructions3[i] = new JButton(instructionsName[i+20]);
            setButton(instructions3[i], i+20);
            instructions3[i].addActionListener(new MyHandler2(instructionsName[i+20]));
            panel3.add(instructions3[i]);

            instructions4[i] = new JButton(instructionsName[i+30]);
            setButton(instructions4[i], i+30);
            instructions4[i].addActionListener(new MyHandler2(instructionsName[i+30]));
            panel4.add(instructions4[i]);
        }

        nextButton = new SpecialButton(100, 30, 15, 15, Color.GRAY, Color.WHITE, "NEXT");
        nextButton.setBounds(350, 400, 100, 30);
        backButton = new SpecialButton(100, 30, 15, 15, Color.GRAY, Color.WHITE, "BACK");
        backButton.setBounds(150, 400, 100, 30);

        add(panel3);
        add(panel4);
        add(nextButton);
        add(backButton);

        MyHandler handler = new MyHandler();
        nextButton.addActionListener(handler);
        backButton.addActionListener(handler);
    }

    public void setButton(JButton button, int i){
        try{
            Icon img = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/" + i + ".PNG")));
            button.setBorder(null);
            button.setIcon(img);
            button.setOpaque(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setPreferredSize(new Dimension(100,30));
            button.setHorizontalAlignment(SwingConstants.LEFT);
        }catch (IOException e){}
    }

    private class MyHandler2 implements ActionListener{
        private String ins;

        public MyHandler2(String name){
            ins = name;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            InstructionPage insPage = null;
            try {
                insPage = new InstructionPage(ins);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            insPage.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            insPage.setSize(600,500);
            insPage.setVisible(true);
            insPage.setLocationRelativeTo(null);
        }
    }

    private class MyHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == backButton){
                TutorPage tutorPage = new TutorPage();
                tutorPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tutorPage.setSize(600,500);
                tutorPage.setLocationRelativeTo(null);
                tutorPage.setVisible(true);
                setVisible(false);
            }
            else if(e.getSource() == nextButton){
                TutorPage3 tutorPage3 = new TutorPage3();
                tutorPage3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tutorPage3.setSize(600,500);
                tutorPage3.setLocationRelativeTo(null);
                tutorPage3.setVisible(true);
                setVisible(false);
            }
        }
    }
}