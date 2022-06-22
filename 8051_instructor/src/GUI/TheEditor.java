package GUI;

import Instruction.Label;
import InstructionProcess.InstructionPosition;
import InstructionProcess.OperationDescription;
import InstructionProcess.instructionFetch;
import Memory.CodeMemory;
import Memory.DataMemory;
import Register.register;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TheEditor extends JFrame{
    private final JPanel theMemory;
    JFileChooser fileChooser = new JFileChooser();
    private static JTable dataTable, codeTable;
    private JTableHeader codehead;
    private final String[] memCol = {" ","0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    private final String[] memCol2 = {" ","0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    private final String[] memRow = {"00", "10", "20", "30", "40", "50", "60", "70"};
    private final String[] RRR = {"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7"};
    private final String[] PPP = {"P0", "P1", "P2", "P3"};
    private final String[] OOO = {"ACC", "B", "PC", "SP", "PSW", "DPTR"};
    private final JPanel codingEditor;     //編輯程式碼區塊
    private final JPanel buttonPanel;
    private final JPanel registerPanel; //暫存器總面板
    private final JPanel dataMemory, codeMemory, codeMemory2;
    private final JPanel regRPanel;     //R0-7
    private final JPanel regPPanel;     //P0-7
    private final JPanel regPanel;      //其他暫存器
    private final JTextField[] regRTextField = new JTextField[8];
    private final JTextField[] regPTextField = new JTextField[4];
    private final JTextField[] regTextField = new JTextField[6];
    private final JButton runButton, retButton, stepButton, saveButton, loadButton, backButton;
    private JTableHeader datahead;
    private JTextArea codingBlock;
    private static instructionFetch Fetch;
    private JLabel dataLabel, codeLabel;
    private ImageIcon save, load, run, step, back, ret;

    public TheEditor(){
        super("8051 Instructor");
        setLayout(new GridLayout(1, 2, 0, 20));

        dataLabel = new JLabel("Data Memory");
        codeLabel = new JLabel("Code Memory");


        theMemory = new JPanel(new GridLayout(3, 1));
        registerPanel = new JPanel(new GridLayout(1, 3, 0, 20));

        dataMemory = new JPanel();
        codeMemory = new JPanel();
        codeMemory2 = new JPanel();

        regRPanel = new JPanel(new GridLayout(8, 2));
        regPPanel = new JPanel(new GridLayout(4, 2));
        regPanel = new JPanel(new GridLayout(6, 2));

        String[][] mem = new String[8][17];
        String[][] mem2 = new String[8][17];

        for(int i = 0; i < 8; i++){
            JLabel rrr = new JLabel(RRR[i] + " ");
            rrr.setHorizontalAlignment(JLabel.RIGHT);
            regRPanel.add(rrr);
            regRTextField[i] = new JTextField("0x00");
            regRPanel.add(regRTextField[i]);

            if(i < 4){
                JLabel ppp = new JLabel(PPP[i] + " ");
                ppp.setHorizontalAlignment(JLabel.RIGHT);
                regPPanel.add(ppp);
                regPTextField[i] = new JTextField("0x00");
                regPPanel.add(regPTextField[i]);
            }

            if(i < 6){
                JLabel ooo = new JLabel(OOO[i] + " ");
                ooo.setHorizontalAlignment(JLabel.RIGHT);
                regPanel.add(ooo);
                if(i == 2 || i == 5)  //pc dptr
                    regTextField[i] = new JTextField("0x0000");
                else if(i == 3)  //sp
                    regTextField[i] = new JTextField("0x07");
                else
                    regTextField[i] = new JTextField("0x00");
                regPanel.add(regTextField[i]);
            }

            for(int j = 0; j < 17; j++){
                if(j == 0){
                    mem[i][j] = memRow[i];
                    mem2[i][j] = memRow[i];
                }
                else{
                    mem[i][j] = "00";
                    mem2[i][j] = "00";
                }
            }
        }

        dataTable = new JTable(mem, memCol);
        codeTable = new JTable(mem2, memCol2);

        setTable(dataTable);
        setTable(codeTable);

        datahead = dataTable.getTableHeader();
        codehead = codeTable.getTableHeader();
        codeTable.setEnabled(false);

        dataMemory.add(datahead, BorderLayout.NORTH);
        codeMemory.add(codehead, BorderLayout.NORTH);
        dataMemory.add(dataTable, BorderLayout.CENTER);
        codeMemory.add(codeTable, BorderLayout.CENTER);
        dataMemory.add(dataLabel, BorderLayout.SOUTH);
        codeMemory.add(codeLabel, BorderLayout.SOUTH);

        registerPanel.add(regRPanel);
        registerPanel.add(regPPanel);
        registerPanel.add(regPanel);

        theMemory.add(registerPanel);
        theMemory.add(dataMemory);
        theMemory.add(codeMemory);
        add(theMemory);

        codingEditor = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        codingBlock = new JTextArea();
        codingBlock.setTabSize(4);
        codingBlock.setFont(new Font("Consolos", Font.PLAIN, 20));
        JScrollPane jScrollPane = new JScrollPane(codingBlock);

        try{
            save = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/save-1.PNG")));
            run = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/run-1.png")));
            ret = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/ret-1.png")));
            step = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/step-1.png")));
            load = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/load-1.png")));
            back = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/back-1.png")));
        }catch (IOException e){}


        runButton = new JButton(run);
        runButton.setEnabled(false);
        retButton = new JButton(ret);
        stepButton = new JButton(step);
        saveButton = new JButton(save);
        loadButton = new JButton(load);
        backButton = new JButton(back);

        setButton(saveButton, "save");
        setButton(runButton, "run");
        setButton(retButton, "ret");
        setButton(stepButton, "step");
        setButton(loadButton, "load");
        setButton(backButton, "back");

        buttonPanel.add(retButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(runButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(backButton);
        codingEditor.add(buttonPanel, BorderLayout.NORTH);
        codingEditor.add(jScrollPane, BorderLayout.CENTER);
        add(codingEditor);

        MyHandler handler = new MyHandler();
        retButton.addActionListener(handler);
        saveButton.addActionListener(handler);
        loadButton.addActionListener(handler);
        runButton.addActionListener(handler);
        stepButton.addActionListener(handler);
        backButton.addActionListener(handler);
    }

    public void setTable(JTable table){
        table.setRowSelectionAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        resize(table);
    }

    public void resize(JTable table){
        for(int i = 0; i < 17; i++){
            table.getColumnModel().getColumn(i).setMaxWidth(33);
        }
    }

    public void setButton(JButton button, String str){
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setToolTipText(str);
        try{
            Icon icon = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("GUI/pic/" + str + "-2.png")));
            button.setPressedIcon(icon);
        }catch (IOException e){}

    }

    public void updateCodeTable() {
        String[][] mem = new String[8][17];

        for(int j = 0; j < 8; j++){
            for(int i = 0; i < 16; i++){
                if(i == 0)
                    mem[j][0] = memRow[j];
                String istr = Integer.toString(i, 16).toUpperCase();
                String jstr = Integer.toString(j, 16).toUpperCase();
                mem[j][i+1] = CodeMemory.getcodeMemory("00"+jstr+istr);
            }
        }
        codeMemory.removeAll();
        codeTable = new JTable(mem, memCol);
        setTable(codeTable);
        codeMemory.add(codehead, BorderLayout.NORTH);
        codeMemory.add(codeTable, BorderLayout.CENTER);
        codeMemory.add(codeLabel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
    public void setRegisterTableValue(){
        regRTextField[0].setText("0x"+register.getRegister("R0"));
        regRTextField[1].setText("0x"+register.getRegister("R1"));
        regRTextField[2].setText("0x"+register.getRegister("R2"));
        regRTextField[3].setText("0x"+register.getRegister("R3"));
        regRTextField[4].setText("0x"+register.getRegister("R4"));
        regRTextField[5].setText("0x"+register.getRegister("R5"));
        regRTextField[6].setText("0x"+register.getRegister("R6"));
        regRTextField[7].setText("0x"+register.getRegister("R7"));
        regPTextField[0].setText("0x"+register.getRegister("P0"));
        regPTextField[1].setText("0x"+register.getRegister("P1"));
        regPTextField[2].setText("0x"+register.getRegister("P2"));
        regPTextField[3].setText("0x"+register.getRegister("P3"));
        regTextField[0].setText("0x"+register.getRegister("ACC"));
        regTextField[1].setText("0x"+register.getRegister("B"));
        regTextField[2].setText(register.getPC());
        regTextField[3].setText("0x"+register.getRegister("SP"));
        regTextField[4].setText("0x"+register.getRegister("PSW"));
        regTextField[5].setText("0x"+register.getRegister("DPTR"));
    }

    public void setDataTableValue(){
        String[][] mem = new String[8][17];

        for(int j = 0; j < 8; j++){
            for(int i = 0; i < 16; i++){
                if(i == 0)
                    mem[j][0] = memRow[j];
                String istr = Integer.toString(i, 16).toUpperCase();
                String jstr = Integer.toString(j, 16).toUpperCase();
                mem[j][i+1] = DataMemory.getDataMemory(jstr+istr);
            }
        }
        dataMemory.removeAll();
        dataTable = new JTable(mem, memCol);
        setTable(dataTable);
        dataMemory.add(datahead, BorderLayout.NORTH);
        dataMemory.add(dataTable, BorderLayout.CENTER);
        dataMemory.add(dataLabel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private class MyHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == retButton){
                codingBlock.setText("");
            }
            else if(e.getSource() == saveButton){
                fileChooser.setFileFilter(new FileNameExtensionFilter("Assembly Code (*.asm)", "asm"));
                int userSelection = fileChooser.showSaveDialog(new JFrame());
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File saveFile = fileChooser.getSelectedFile();
                    String filePath = saveFile.getAbsolutePath();
                    if (!filePath.endsWith(".asm"))
                        filePath += ".asm";
                    try {
                        FileWriter fileWriter = new FileWriter(filePath);
                        fileWriter.append(codingBlock.getText());
                        fileWriter.close();
                        JOptionPane.showMessageDialog(TheEditor.this, "儲存成功");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            else if(e.getSource() == loadButton){
                codingBlock.setText("");

                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(TheEditor.this);
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Assembly Code (*.asm)", "asm");
                fileChooser.addChoosableFileFilter(fileNameExtensionFilter);

                if (returnValue == JFileChooser.APPROVE_OPTION){        //判斷是否選擇檔案
                    File selectedFile = fileChooser.getSelectedFile();      //指派給File
                    try {
                        FileReader fileReader = new FileReader(selectedFile);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        String string1 = "";
                        StringBuilder string2 = new StringBuilder();

                        while ((string1 = bufferedReader.readLine()) != null) {
                            string2.append(string1).append("\n");
                        }

                        codingBlock.setText(string2.toString());
                        bufferedReader.close();


                    } catch (IOException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
            else if(e.getSource() == runButton) {
                Fetch.RunInstruction(2);
                setDataTableValue();
                setRegisterTableValue();
            }
            else if(e.getSource() == stepButton){
                if(codingBlock.getText().equals("")){
                    codingBlock.setText("NOP");
                    revalidate();
                }
                CodeMemory codeMemory = new CodeMemory();
                DataMemory dataMemory = new DataMemory();
                Label.clearLabelList();
                DescriptionTest.setCount(0);
                OperationDescription.clearOperationList();
                InstructionPosition.clearInstructionPosition();
                Fetch = new instructionFetch(codingBlock.getText());
                runButton.setEnabled(true);
                updateCodeTable();
            }
            else if(e.getSource() == backButton){
                InitialPage initial = new InitialPage();
                initial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                initial.setSize(600,500);
                initial.setVisible(true);
                initial.setLocationRelativeTo(null);
                setVisible(false);
            }
        }
    }

    public void showErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(TheEditor.this, errorMessage);
    }
}