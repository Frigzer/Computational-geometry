package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {

        Frame frame4 = new Frame();
        Panel panel4 = new Panel();

        frame4.add(panel4);
        frame4.pack();
        frame4.setLocationRelativeTo(null);


        // kurvinox
        MeshGenerator kurvinox2 = new MeshGenerator("images/trudny_wariant2.txt", 7); // 3, 7
        panel4.drawFront(kurvinox2);

        Frame frame3 = new Frame();
        Panel panel3 = new Panel();

        frame3.add(panel3);
        frame3.pack();
        frame3.setLocationRelativeTo(null);


        // kurvinox
        MeshGenerator kurvinox = new MeshGenerator("images/trudny_wariant2.txt", 3); // 3, 7
        panel3.drawFront(kurvinox);

        Frame frame2 = new Frame();
        Panel panel2 = new Panel();

        frame2.add(panel2);
        frame2.pack();
        frame2.setLocationRelativeTo(null);

        // duszek
        MeshGenerator ghost = new MeshGenerator("images/pacman_duch.txt", 2); // 2, 4
        panel2.drawFront(ghost);

        Frame frame1 = new Frame();
        Panel panel1 = new Panel();

        frame1.add(panel1);
        frame1.pack();
        frame1.setLocationRelativeTo(null);

        // kółko
        MeshGenerator circle = new MeshGenerator("images/circle.txt", 1);
        panel1.drawFront(circle);



    }
}
