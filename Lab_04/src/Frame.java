import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    Frame() {

        this.setTitle("Lab_03");
        //this.setSize(800, 800);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        ImageIcon image = new ImageIcon("logo.jpg");
        this.setIconImage(image.getImage());

        this.setVisible(true);
    }
}
