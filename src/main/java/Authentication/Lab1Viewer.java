package Authentication;

import javax.swing.JFrame;
import java.io.*;
public class Lab1Viewer {
    public static void loginAuth() {
        try {
            JFrame frame = new Lab1Frame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        }
    }
}