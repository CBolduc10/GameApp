import javax.swing.*;
import Breaker.Breaker;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Breaker breaker = new Breaker();
        obj.setBounds(10,10,700,600);
        obj.setTitle("Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(breaker);
    }
}
