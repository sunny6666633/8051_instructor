package GUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class ShowDescription extends JFrame{
    public ShowDescription(String str){
        super("解說");

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setText(str);
        descriptionArea.setFont(new Font("標楷體", Font.BOLD, 18));
        panel.add(descriptionArea, BorderLayout.CENTER);
        JScrollPane jScrollPane = new JScrollPane(panel);
        add(jScrollPane);
    }
}