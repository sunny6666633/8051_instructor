package GUI;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TutorPage extends JFrame{
    private final JPanel panel1;
    private final JPanel panel2;
    private final SpecialButton nextButton;
    private final SpecialButton backButton;
    private final JButton[] instructions1 = new JButton[10];
    private final JButton[] instructions2 = new JButton[10];
    private final String[] instructionsName = {"NOP","AJMP","LJMP","RR","INC","JBC","ACALL","LCALL","RRC","DEC","JB","RET","RL","ADD","JNB","RETl","RLC","ADDC","JC","ORL","JNC","ANL","JZ","XRL","JNZ","JMP","MOV","SJMP","MOVC","DIV","SUBB","CPL","CJNE","PUSH","CLR","SWAP","XCH","POP","SETB","DA","DJNZ","MOVX"}; //42

    public TutorPage(){
        super("8051 Instructor");
        setLayout(null);

        panel1 = new JPanel();      //左邊指令選項10
        panel1.setBounds(100, 20, 200, 350);
        panel1.setBackground(Color.WHITE);
        panel2 = new JPanel();      //右邊指令選項10
        panel2.setBounds(300, 20, 200, 350);
        panel2.setBackground(Color.WHITE);

        for(int i = 0; i < 10; i++){
            instructions1[i] = new JButton(instructionsName[i]);
            setButton(instructions1[i], i);
            instructions1[i].addActionListener(new MyHandler2(instructionsName[i]));
            panel1.add(instructions1[i]);

            instructions2[i] = new JButton(instructionsName[i+10]);
            setButton(instructions2[i], i+10);
            instructions2[i].addActionListener(new MyHandler2(instructionsName[i+10]));
            panel2.add(instructions2[i]);
        }

        nextButton = new SpecialButton(100, 30, 15, 15, Color.GRAY, Color.WHITE, "NEXT");
        nextButton.setBounds(350, 400, 100, 30);
        backButton = new SpecialButton(100, 30, 15, 15, Color.GRAY, Color.WHITE, "BACK");
        backButton.setBounds(150, 400, 100, 30);

        add(panel1);
        add(panel2);
        add(nextButton);
        add(backButton);

        MyHandler handler = new MyHandler();
        nextButton.addActionListener(handler);
        backButton.addActionListener(handler);
    }

    public void setButton(JButton button, int i){
        ImageIcon img;
        try{
            img = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/" + i + ".PNG")));
            button.setBorder(null);
            button.setIcon(img);
            button.setOpaque(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setPreferredSize(new Dimension(100,30));
            button.setHorizontalAlignment(SwingConstants.LEFT);
        }catch (IOException e){

        }

        //button.setBorder(BorderFactory.createRaisedBevelBorder());
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
            insPage.setLocationRelativeTo(null);
            insPage.setVisible(true);
            insPage.setLocationRelativeTo(null);
        }
    }

    private class MyHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == backButton){    //回初始頁面
                InitialPage initial = new InitialPage();
                initial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                initial.setSize(600,500);
                Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
                int x1 = (int) screensize.getWidth() / 2 - 600 / 2;
                int y1 = (int) screensize.getHeight() / 2 - 500 / 2;
                initial.setLocation(x1, y1);
                initial.setVisible(true);
                setVisible(false);
            }
            else if(e.getSource() == nextButton){   //到下一頁
                TutorPage2 tutorPage2 = new TutorPage2();
                tutorPage2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                tutorPage2.setSize(600,500);
                Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
                int x1 = (int) screensize.getWidth() / 2 - 600 / 2;
                int y1 = (int) screensize.getHeight() / 2 - 500 / 2;
                tutorPage2.setLocation(x1, y1);
                tutorPage2.setVisible(true);
                setVisible(false);
            }
        }
    }
}