package GUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIInstructionAnnotate extends JFrame {
    private static final String space = "    ";
    private final JPanel titlePanel;
    private final JPanel valuePanel;
    private final JLabel Instruction;
    private final JLabel Encoding;
    private final JLabel CodeMemory;
    private final JLabel CodeMemoryHex;
    private final JLabel Description;
    private final JTextField InstructionValue;
    private final JTextField EncodingValue;
    private final JTextField CodeMemoryValue;
    private final JTextField CodeMemoryHexValue;
    private final JTextPane DescriptionValue;
    private final JButton Next;
    private final JButton description;
    private String str;

    public GUIInstructionAnnotate(){
        super("Instruction Annotation");
        setLayout(new BorderLayout());
        setFont(new Font("consolas", Font.PLAIN, 25));

        valuePanel = new JPanel(new GridLayout(4, 2));
        titlePanel = new JPanel(new GridLayout(4, 2));

        Instruction = buildTitleLabel("Instruction");
        Encoding = buildTitleLabel("Encoding");
        CodeMemory = buildTitleLabel("CodeMemory");
        CodeMemoryHex = buildTitleLabel("CodeMemoryHex");
        Description = buildTitleLabel("Description");

        titlePanel.add(Instruction);
        titlePanel.add(Encoding);
        titlePanel.add(CodeMemory);
        titlePanel.add(CodeMemoryHex);

        InstructionValue = buildValuePane();
        EncodingValue = buildValuePane();
        CodeMemoryValue = buildValuePane();
        CodeMemoryHexValue = buildValuePane();

        DescriptionValue = new JTextPane();
        DescriptionValue.setForeground(Color.BLUE);
        DescriptionValue.setFont(new Font("Consolos", Font.BOLD, 12));

        valuePanel.add(InstructionValue);
        valuePanel.add(EncodingValue);
        valuePanel.add(CodeMemoryValue);
        valuePanel.add(CodeMemoryHexValue);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        description = new SpecialButton(335, 30, 15, 15, Color.GRAY, Color.WHITE, "Description");

        Next = new SpecialButton(335, 30, 15, 15, Color.GRAY, Color.WHITE, "Next");
        buttonPanel.add(description);
        buttonPanel.add(Next);

        add(buttonPanel, BorderLayout.NORTH);
        add(titlePanel, BorderLayout.WEST);
        add(valuePanel, BorderLayout.CENTER);
        setText();

        MyEventListener listener = new MyEventListener();
        Next.addActionListener(listener);
        description.addActionListener(listener);
    }

    private JLabel buildTitleLabel(String title) {
        JLabel label = new JLabel(space + title + space);
        label.setHorizontalAlignment(JTextField.RIGHT);
        return label;
    }

    private JTextField buildValuePane() {
        JTextField field = new JTextField();
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setEditable(false);
        return field;
    }

    private void setText(){
        InstructionValue.setText(DescriptionTest.getInstructionValue());
        EncodingValue.setText(DescriptionTest.getEncodingValue());
        CodeMemoryValue.setText(DescriptionTest.getCodeMemoryValue());
        CodeMemoryHexValue.setText(DescriptionTest.getCodeMemoryValueHex());
        str = DescriptionTest.getOperationValue();
    }

    private class MyEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == Next)
                setText();
            else if(event.getSource() == description){
                ShowDescription showDescription = new ShowDescription(str);
                showDescription.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                showDescription.setSize(400,200);
                showDescription.setLocationRelativeTo(null);
                showDescription.setVisible(true);
            }
        }
    }
}
