package GUI;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class TutorPage3 extends JFrame{
    private final JPanel panel5;
    private final JButton backButton;
    private final JButton[] instructions5 = new JButton[10];
    private final String[] instructionsName = {"NOP","AJMP","LJMP","RR","INC","JBC","ACALL","LCALL","RRC","DEC","JB","RET","RL","ADD","JNB","RETl","RLC","ADDC","JC","ORL","JNC","ANL","JZ","XRL","JNZ","JMP","MOV","SJMP","MOVC","DIV","SUBB","CPL","CJNE","PUSH","CLR","SWAP","XCH","POP","SETB","DA","DJNZ","MOVX"}; //42

    public TutorPage3(){
        super("8051 Instructor");
        setLayout(null);

        panel5 = new JPanel();
        panel5.setBounds(150, 20, 100, 350);
        setBackground(Color.WHITE);

        for(int i = 0; i < 2; i++){
            instructions5[i] = new JButton(instructionsName[i+40]);
            setButton(instructions5[i], i+40);
            instructions5[i].addActionListener(new MyHandler2(instructionsName[i+40]));
            panel5.add(instructions5[i]);
        }

        backButton = new SpecialButton(100, 30, 15, 15, Color.GRAY, Color.WHITE, "BACK");
        backButton.setBounds(250, 400, 100, 30);

        add(panel5);
        add(backButton);

        MyHandler handler = new MyHandler();
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
                TutorPage2 tutorPage2 = new TutorPage2();
                tutorPage2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tutorPage2.setSize(600,500);
                tutorPage2.setLocationRelativeTo(null);
                tutorPage2.setVisible(true);
                setVisible(false);
            }
        }
    }
}