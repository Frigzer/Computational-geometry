import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {

    private List<Point> points;
    private List<Color> pColors;
    private List<Line> lines;
    private List<Color> lColors;
    private List<Line> segments;
    private List<Color> sColors;
    private List<Triangle> triangles;
    private List<Color> tColors;

    private int scale = 20;

    Panel () {
        this.setBackground(Color.black);

        this.setPreferredSize(new Dimension(800, 800));

        points = new ArrayList<>();
        lines = new ArrayList<>();
        segments = new ArrayList<>();
        pColors = new ArrayList<>();
        lColors = new ArrayList<>();
        sColors = new ArrayList<>();
        triangles = new ArrayList<>();
        tColors = new ArrayList<>();
    }

    public void drawPoint(Point p, Color color) {
        if(p != null) {
            points.add(p);
            pColors.add(color);
            repaint();
        }
    }

    public void drawLine(Line line, Color color, boolean isLine) {
        if(line != null) {
            if(isLine)
            {
                lines.add(line);
                lColors.add(color);
            }
            else
            {
                segments.add(line);
                sColors.add(color);
            }
            repaint();
        }
    }

    public void drawTriangle(Triangle triangle, Color color) {
        if(triangle.p1 != null && triangle.p2 != null && triangle.p3 != null) {
            triangles.add(triangle);
            tColors.add(color);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        for(int i=getWidth() / 2, j = 0; i<getWidth(); i+=scale, j++)
        {
            g2D.setPaint(new Color(45, 49, 58));
            g2D.drawLine(i, getHeight(), i,  0);
            g2D.setPaint(Color.white);
            g2D.drawLine(i, getHeight() / 2 + 4, i,  getHeight() / 2 - 4);

            // Dodajemy liczbę nad kreską
            if(i >= (getWidth() / 2) + scale) {
                g2D.setPaint(Color.white);
                g2D.drawString(Integer.toString(j), i-3, getHeight() / 2 - 16);
            }

        }
        for(int i=getWidth() / 2, j = 0; i>0; i-=scale, j--)
        {
            g2D.setPaint(new Color(45, 49, 58));
            g2D.drawLine(i, getHeight(), i,  0);
            g2D.setPaint(Color.white);
            g2D.drawLine(i, getHeight() / 2 + 4, i,  getHeight() / 2 - 4);

            // Dodajemy liczbę pod kreską
            if(i <= (getWidth() / 2) - scale) {
                g2D.setPaint(Color.white);
                g2D.drawString(Integer.toString(j), i - 6, getHeight() / 2 + 20);
            }
        }
        for(int i=getHeight() / 2, j = 0; i<getHeight(); i+=scale, j--)
        {
            g2D.setPaint(new Color(45, 49, 58));
            g2D.drawLine(0, i, getWidth(),  i);
            g2D.setPaint(Color.white);
            g2D.drawLine(getWidth() / 2 + 4, i, getWidth() / 2 - 4,  i);

            // Dodajemy liczbę obok kreski
            if(i >= (getHeight() / 2) + scale) {
                g2D.setPaint(Color.white);
                g2D.drawString(Integer.toString(j), getWidth() / 2 + 16, i+3);
            }
        }
        for(int i=getHeight() / 2, j = 0; i>0; i-=20, j++)
        {
            g2D.setPaint(new Color(45, 49, 58));
            g2D.drawLine(0, i, getWidth(),  i);
            g2D.setPaint(Color.white);
            g2D.drawLine(getWidth() / 2 + 4, i, getWidth() / 2 - 4,  i);

            // Dodajemy liczbę obok kreski
            if(i <= (getHeight() / 2) - (scale * 2)) {
                g2D.setPaint(Color.white);
                g2D.drawString(Integer.toString(j), getWidth() / 2 + 16, i+3);
            }

        }

        // Rysowanie linii poziomej
        g2D.setPaint(Color.white);
        int lineStartX = 0; // Współrzędna X początku linii
        int lineStartY = getHeight() / 2; // Współrzędna Y początku linii
        int lineEndX = getWidth(); // Współrzędna X końca linii
        int lineEndY = getHeight() / 2; // Współrzędna Y końca linii
        g2D.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);

        // Rysowanie linii pionowej
        g2D.setPaint(Color.white);
        lineStartX = getWidth() / 2; // Współrzędna X początku linii
        lineStartY = 0; // Współrzędna Y początku linii
        lineEndX = getWidth() / 2; // Współrzędna X końca linii
        lineEndY = getHeight(); // Współrzędna Y końca linii
        g2D.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);



        int[] xPoints = {getWidth() - scale, getWidth() - scale, getWidth()};
        int[] yPoints = {getHeight() / 2 - 10, getHeight() / 2 + 10, getHeight() / 2};
        g2D.fillPolygon(xPoints, yPoints, 3);

        xPoints = new int[]{getWidth() / 2 - 10, getWidth() / 2 + 10, getWidth() / 2};
        yPoints = new int[]{scale, scale, 0};
        g2D.fillPolygon(xPoints, yPoints, 3);

        int j=0;
        // Rysowanie odcinków
        for (Line line : segments) {

            g2D.setPaint(sColors.get(j));
            g2D.drawLine((int) ((line.p1.x * scale) + (getWidth() / 2)), (int) ((line.p1.y * (-scale)) + (getHeight() / 2)), (int) ((line.p2.x * scale) + (getWidth() / 2)), (int) ((line.p2.y * (-scale)) + (getHeight() / 2)));

            j++;
        }

        int k=0;
        // rysowanie linii
        for (Line line : lines) {

            double x1 = -(getWidth() / 2);
            double y1 =  (line.a * x1 + line.b);
            double x2 = getWidth() / 2;
            double y2 = (line.a * x2 + line.b);

            // Przesunięcie współrzędnych
            x1 *= scale;
            y1 *= scale;
            x2 *= scale;
            y2 *= scale;
            g2D.setPaint(lColors.get(k));
            //g.drawLine(0, (int) (-(line.a * (-getWidth() / 2 ) + line.b) * (-20) + getHeight() / 2), getWidth(), (int) (-(line.a * (getWidth() / 2 ) + line.b) * (-20) + getHeight() / 2));
            g2D.drawLine((int) (x1 + getWidth() / 2), (int) (-y1 + getHeight() / 2), (int) (x2 + getWidth() / 2), (int) (-y2 + getHeight() / 2));
            k++;
        }

        int e=0;
        // Rysowanie trójkątów
        for (Triangle triangle : triangles) {
            g2D.setPaint(tColors.get(e));
            int[] xTPoints = {(int) (triangle.p1.x * scale) + (getWidth() / 2), (int) (triangle.p2.x * scale) + (getWidth() / 2), (int) (triangle.p3.x * scale) + (getWidth() / 2)};
            int[] yTPoints = {(int) (triangle.p1.y * (-scale)) + (getHeight() / 2), (int) (triangle.p2.y * (-scale)) + (getHeight() / 2), (int) (triangle.p3.y * (-scale)) + (getHeight() / 2)};
            g2D.fillPolygon(xTPoints, yTPoints, 3);
            e++;
        }

        int i=0;
        // Rysowanie punktów
        for (Point point : points) {
            g2D.setPaint(pColors.get(i));
            g2D.fillOval((int) (point.x  * scale) - 5 + (getWidth() / 2), (int) ((point.y  *(- scale)) - 5 + (getHeight() / 2)), 10, 10);
            i++;
        }

    }

}
