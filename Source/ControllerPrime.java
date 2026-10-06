import javax.swing.*;

public class ControllerPrime {
    public static void main(String[] args) {
        String c=JOptionPane.showInputDialog("Enter number of columns:");
        int cols = Integer.parseInt(c);
        String r=JOptionPane.showInputDialog("Enter number of rows:");
        int rows = Integer.parseInt(r);
        String m=JOptionPane.showInputDialog("Enter number of mines:");
        int mines = Integer.parseInt(m);
        new MinesweeperPrime(mines,rows, cols);
    }
}
