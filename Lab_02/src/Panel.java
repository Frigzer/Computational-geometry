import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {

    //private List<PointInfo> points;
    private List<Point> points;
    private List<Color> pColors;
    private List<Line> lines;
    private List<Color> lColors;
    private List<Line> segments;
    private List<Color> sColors;


    Panel () {
        this.setBackground(Color.black);
        points = new ArrayList<>();
        lines = new ArrayList<>();
        segments = new ArrayList<>();
        pColors = new ArrayList<>();
        lColors = new ArrayList<>();
        sColors = new ArrayList<>();
    }

    public void drawPoint(Point p, Color color) {
        //points.add(new PointInfo(p.x, p.y, color));
        points.add(p);
        pColors.add(color);
        repaint();
    }

    public void drawLine(Line line, Color color, boolean isLine) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i=getWidth() / 2; i<getWidth(); i+=20)
        {
            g.setColor(new Color(45, 49, 58));
            g.drawLine(i, getHeight(), i,  0);
            g.setColor(Color.white);
            g.drawLine(i, getHeight() / 2 + 4, i,  getHeight() / 2 - 4);
        }
        for(int i=getWidth() / 2; i>0; i-=20)
        {
            g.setColor(new Color(45, 49, 58));
            g.drawLine(i, getHeight(), i,  0);
            g.setColor(Color.white);
            g.drawLine(i, getHeight() / 2 + 4, i,  getHeight() / 2 - 4);
        }
        for(int i=getHeight() / 2; i<getHeight(); i+=20)
        {
            g.setColor(new Color(45, 49, 58));
            g.drawLine(0, i, getWidth(),  i);
            g.setColor(Color.white);
            g.drawLine(getWidth() / 2 + 4, i, getWidth() / 2 - 4,  i);
        }
        for(int i=getHeight() / 2; i>0; i-=20)
        {
            g.setColor(new Color(45, 49, 58));
            g.drawLine(0, i, getWidth(),  i);
            g.setColor(Color.white);
            g.drawLine(getWidth() / 2 + 4, i, getWidth() / 2 - 4,  i);
        }

        // Rysowanie linii poziomej
        g.setColor(Color.white);
        int lineStartX = 0; // Współrzędna X początku linii
        int lineStartY = getHeight() / 2; // Współrzędna Y początku linii
        int lineEndX = getWidth(); // Współrzędna X końca linii
        int lineEndY = getHeight() / 2; // Współrzędna Y końca linii
        g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);

        // Rysowanie linii pionowej
        g.setColor(Color.white);
        lineStartX = getWidth() / 2; // Współrzędna X początku linii
        lineStartY = 0; // Współrzędna Y początku linii
        lineEndX = getWidth() / 2; // Współrzędna X końca linii
        lineEndY = getHeight(); // Współrzędna Y końca linii
        g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);



        int[] xPoints = {getWidth() - 25, getWidth() - 25, getWidth()};
        int[] yPoints = {getHeight() / 2 - 10, getHeight() / 2 + 10, getHeight() / 2};
        g.fillPolygon(xPoints, yPoints, 3);

        xPoints = new int[]{getWidth() / 2 - 10, getWidth() / 2 + 10, getWidth() / 2};
        yPoints = new int[]{25, 25, 0};
        g.fillPolygon(xPoints, yPoints, 3);

        int j=0;
        // Rysowanie odcinków
        for (Line line : segments) {

            g.setColor(sColors.get(j));
            g.drawLine((int) ((line.p1.x * 20) + (getWidth() / 2)), (int) ((line.p1.y * (-20)) + (getHeight() / 2)), (int) ((line.p2.x * 20) + (getWidth() / 2)), (int) ((line.p2.y * (-20)) + (getHeight() / 2)));

            j++;
        }

        int k=0;
        // rysowanie linii
        for (Line line : lines) {

            int x1 = -(getWidth() / 2);
            int y1 = (int) (line.a * x1 + line.b);
            int x2 = getWidth() / 2;
            int y2 = (int) (line.a * x2 + line.b);

            // Przesunięcie współrzędnych
            x1 *= 20;
            y1 *= 20;
            x2 *= 20;
            y2 *= 20;
            g.setColor(lColors.get(k));
            //g.drawLine(0, (int) (-(line.a * (-getWidth() / 2 ) + line.b) * (-20) + getHeight() / 2), getWidth(), (int) (-(line.a * (getWidth() / 2 ) + line.b) * (-20) + getHeight() / 2));
            g.drawLine(x1 + getWidth() / 2, -y1 + getHeight() / 2, x2 + getWidth() / 2, -y2 + getHeight() / 2);

        }
        int i=0;
        // Rysowanie punktów
        for (Point point : points) {
            g.setColor(pColors.get(i));
            g.fillOval((int) (point.x  * 20) - 5 + (getWidth() / 2), (int) ((point.y  *(- 20)) - 5 + (getHeight() / 2)), 10, 10);
            i++;
        }

    }

}
