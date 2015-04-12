
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class coba {
    private JFrame frame;
    public coba() {
        frame = new JFrame("Coba");
        frame.setSize(640,480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JButton button = new JButton("Pencet");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"OK"};
                int n = JOptionPane.showOptionDialog(
                frame, "Duke is a cartoon mascot. \n" +
                "Do you still want to cast your vote?",
                "Warning",
                JOptionPane.ERROR_MESSAGE,
                JOptionPane.OK_OPTION,
                null,
                options,
                options[0]);
                System.out.println(n);
            }
        });
        frame.add(button);
        frame.setVisible(true);
    }
    
    /*public static void main(String[] args) {
        coba c = new coba();
    }*/
}
