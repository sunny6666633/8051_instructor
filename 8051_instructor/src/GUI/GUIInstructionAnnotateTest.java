package GUI;

import javax.swing.*;

public class GUIInstructionAnnotateTest {
    public GUIInstructionAnnotateTest(){
        GUIInstructionAnnotate annotation = new GUIInstructionAnnotate();
        annotation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        annotation.setSize(680, 250); // set frame size
        annotation.setLocationRelativeTo(null);
        annotation.setVisible(true); // display frame
    }
}
