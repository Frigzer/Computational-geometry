import javax.swing.*;
import javax.swing.text.IconView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Ustawienia okna
        Frame frame = new Frame();
        Panel panel = new Panel();

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        // --------------------------------------------------------------------------------



        // Zadanie 1


        /*
        ConvexHull convexHull = new ConvexHull("ksztalt_3.txt");
        //convexHull.calculateQuickHull();
        //convexHull.calculateJarvis();

        panel.drawConvexHull(convexHull);
        //System.out.println(convexHull.sides.size());

         */



        // Zadanie 2

        ConvexHull spaceship = new ConvexHull("craft1_ksztalt.txt");
        spaceship.calculateQuickHull();

        String space_craft = "space_craft1.txt";
        spaceship.setPosition(space_craft);

        panel.drawConvexHull(spaceship);

        List<Point> missiles = loadMissile("missiles4.txt");

        //missiles.addAll(loadMissile("missiles1.txt"));
        //missiles.addAll(loadMissile("missiles2.txt"));


        Timer timer = new Timer(10, new ActionListener() {
            double delay = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
                spaceship.setInMotion(space_craft);

                for(Point missile: missiles) {

                    if(missile.spawnTime == (delay/1000)) {
                        panel.drawPoint(missile, Color.red);
                        missile.spawned = true;
                    }
                    spaceship.detectCollision(missile, panel, delay/1000);
                    panel.removePoint(missile);
                }
                setInMotion(missiles, panel);
                panel.repaint();
                delay ++;
                if(delay/1000 == 2)
                    System.out.println("Minęły dwie sekundy!!!");
            }
        });
        timer.start(); // Uruchom timer

        // Dodaj przycisk do pauzowania i wznowienia symulacji
        JButton pauseButton = new JButton("Pauza/Wznów");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop(); // Zatrzymaj timer
                } else {
                    timer.start(); // Wznów timer
                }
            }
        });
        frame.add(pauseButton, BorderLayout.NORTH);
        frame.pack();


    }
    public static List<Point> loadMissile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<Point> newMissileList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 5) {

                    double spawnTime = Double.parseDouble(parts[0]);
                    String temp = String.format("%.3f", spawnTime).replace(',', '.');;
                    spawnTime = Double.parseDouble(temp);
                    double x0 = Double.parseDouble(parts[1]);
                    double y0 = Double.parseDouble(parts[2]);
                    double dx = Double.parseDouble(parts[3]) / 100.0;
                    double dy = Double.parseDouble(parts[4]) / 100.0;

                    newMissileList.add(new Point(spawnTime, x0, y0, dx, dy));
                }

            }
            return newMissileList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setInMotion(List<Point> missiles, Panel panel) {
        for (Point missile : missiles) {
            if (missile.spawned) {
                missile.translate();
                //panel.drawPoint(missile, Color.red);
            }
        }
    }
}