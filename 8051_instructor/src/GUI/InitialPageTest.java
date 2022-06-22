package GUI;
import javax.swing.JFrame;

public class InitialPageTest{
    public static void main(String[] args){
        InitialPage initial = new InitialPage();
        initial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initial.setSize(600,500);
        initial.setLocationRelativeTo(null);
        initial.setVisible(true);
    }
}