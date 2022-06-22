package GUI;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class InstructionPage extends JFrame{
    private final JPanel bigPanel;
    private static final String space = "    ";

    public InstructionPage(String ins) throws Exception {
        super(ins);
        getJson get = new getJson();
        get.set(ins);

        ArrayList<String> insName = get.getIns();
        ArrayList<String> operand = get.getOperand();
        ArrayList<String> chinese = get.getChinese();
        ArrayList<String> english = get.getEnglish();
        ArrayList<Long> bytes = get.getBytes();
        ArrayList<String> operation = get.getOperation();

        int size = insName.size();
        bigPanel = new JPanel(new GridLayout(size,1,20,0));


        for(int i = 0; i < size; i++){
            //JPanel panel = new JPanel(new GridLayout(3,1));
            JPanel titlePanel = new JPanel(new GridLayout(5, 1, 50, 0));
            JPanel valuePanel = new JPanel(new GridLayout(5, 1, 10, 0));
            JPanel smallPanel = new JPanel(new BorderLayout());
            JTextField[] content = new JTextField[5];

            JLabel insLabel = buildLabel("Instruction");
            titlePanel.add(insLabel);
            content[0] = set();
            content[0].setText(space + insName.get(i) + " " + operand.get(i));
            content[0].setFont(new Font("Consolos", Font.BOLD, 12));
            valuePanel.add(content[0]);

            JLabel byteLabel = buildLabel("Bytes");
            titlePanel.add(byteLabel);
            content[1] = set();
            content[1].setText(space + Long.toString(bytes.get(i)));
            valuePanel.add(content[1]);

            JLabel operaLabel = buildLabel("Operation");
            titlePanel.add(operaLabel);
            content[2] = set();
            content[2].setText(space + operation.get(i));
            valuePanel.add(content[2]);

            JLabel chLabel = buildLabel("中文解說");
            titlePanel.add(chLabel);
            content[3] = set();
            content[3].setText(space + chinese.get(i));
            valuePanel.add(content[3]);

            JLabel enLabel = buildLabel("English");
            titlePanel.add(enLabel);
            content[4] = set();
            content[4].setText(space + english.get(i));
            valuePanel.add(content[4]);

            smallPanel.add(titlePanel, BorderLayout.WEST);
            smallPanel.add(valuePanel, BorderLayout.CENTER);
            smallPanel.setBorder(BorderFactory.createEtchedBorder());
            bigPanel.add(smallPanel);
        }

        JScrollPane jScrollPane = new JScrollPane(bigPanel);
        add(jScrollPane, BorderLayout.CENTER);
    }

    private JTextField set(){
        JTextField field = new JTextField("");
        field.setEditable(false);
        return field;
    }

    private JLabel buildLabel(String title) {
        JLabel label = new JLabel(space + title + space);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        return label;
    }
}