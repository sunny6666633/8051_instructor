package GUI;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class InitialPage extends JFrame{
    private final JLabel title;
    private final JPanel buttonPanel;
    private SpecialButton tutor;
    private SpecialButton edit;
    public static TheEditor editor = new TheEditor();
    private final int x, y, x1, y1;

    public InitialPage(){
        super("8051 Instructor");
        setDefaultLookAndFeelDecorated(true);
        setLayout(new GridLayout(3,1));

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        x = (int) screensize.getWidth() / 2 - 1280 / 2;
        y = (int) screensize.getHeight() / 2 - 700 / 2;
        x1 = (int) screensize.getWidth() / 2 - 600 / 2;
        y1 = (int) screensize.getHeight() / 2 - 500 / 2;
        title = new JLabel("8051 Instructor", SwingConstants.CENTER);
        title.setFont(new Font("Consolos", Font.BOLD, 30));
        add(title, BorderLayout.NORTH);

        buttonPanel = new JPanel();

        tutor = new SpecialButton(120, 40, 15, 15, Color.GRAY, Color.WHITE, "指令解說");
        edit = new SpecialButton(120, 40, 15, 15, Color.GRAY, Color.WHITE, "程式編輯器");

        buttonPanel.add(tutor);
        buttonPanel.add(edit);
        add(buttonPanel, BorderLayout.CENTER);

        MyHandler handler = new MyHandler();
        tutor.addActionListener(handler);
        edit.addActionListener(handler);
    }

    private class MyHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == tutor){
                TutorPage tutorPage = new TutorPage();
                tutorPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
                tutorPage.setSize(600,500);
                tutorPage.setVisible(true);
                tutorPage.setLocation(x1, y1);
                setVisible(false);
            }
            else if(e.getSource() == edit){
                editor.setDefaultCloseOperation(EXIT_ON_CLOSE);
                editor.setSize(1280,700);
                editor.setVisible(true);
                editor.setLocation(x, y);
                setVisible(false);
            }
        }
    }
}