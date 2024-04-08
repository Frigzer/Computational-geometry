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
    private List<Polygon> polygons;
    private List<Color> polColors;
    private List<ConvexHull> convexHulls;
    private List<Color> convexHullSideColors;
    private Polygon explosion;

    private int scale = 20;
    private int convexHullScale = 1;
    private int xAxisStart;
    private int yAxisStart;

    Panel() {
        this.setBackground(Color.black);

        this.setPreferredSize(new Dimension(1000, 1000));


        points = new ArrayList<>();
        lines = new ArrayList<>();
        segments = new ArrayList<>();
        pColors = new ArrayList<>();
        lColors = new ArrayList<>();
        sColors = new ArrayList<>();
        triangles = new ArrayList<>();
        tColors = new ArrayList<>();
        polygons = new ArrayList<>();
        polColors = new ArrayList<>();
        convexHulls = new ArrayList<>();
        convexHullSideColors = new ArrayList<>();
        explosion = new Polygon();
    }



    public void drawPoint(Point p, Color color) {
        if(p != null) {
            points.add(p);
            pColors.add(color);
            repaint();
        }
    }

    public void removePoint(Point p) {
        if(p!=null && (p.x > getWidth() || p.x < -getWidth() || p.y > getHeight() || p.y < -getHeight())) {
            points.remove(p);
            p.spawned = false;
            repaint();
        }
    }

    public void removePointAfterCollision(Point p) {
        if(p!=null) {
            points.remove(p);
            p.spawned = false;
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

    public void drawPolygon(Polygon polygon, Color color) {
        polygons.add(polygon);
        polColors.add(color);
        repaint();
    }

    public void drawConvexHull(ConvexHull convexHull) {
        convexHulls.add(convexHull);
        repaint();
        for(Line side : convexHull.sides)
            convexHullSideColors.add(Color.green);
    }

    public void changeConvexHullSideColor(ConvexHull convexHull, Line line) {
        int r = 0;
        for(Line side : convexHull.sides) {
            if(side.equals(line)) {
                convexHullSideColors.remove(r);
                convexHullSideColors.add(r, Color.red);
            }
            r++;
        }
    }

    public void drawExplosion(Point point) {
        Point[] points = {
                new Point(point.x + 20, point.y),
                new Point(point.x + 4, point.y + 4),
                new Point(point.x, point.y + 20),
                new Point(point.x - 4, point.y + 4),
                new Point(point.x - 20, point.y),
                new Point(point.x - 4, point.y - 4),
                new Point(point.x, point.y - 20),
                new Point(point.x + 4, point.y - 4)
        };
        for(Point p : points) {
            explosion.addPoint(p);
        }
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        // Środek układu współrzędnych
        xAxisStart = (this.getWidth() / 2);
        yAxisStart = (this.getHeight() / 2);


        for(int i = xAxisStart, j = 0; i < getWidth(); i+=scale, j++)
        {
            g2D.setPaint(new Color(45, 49, 58));
            g2D.drawLine(i, getHeight(), i,  0);
            /*
            g2D.setPaint(Color.white);
            g2D.drawLine(i, yAxisStart + 4, i,  yAxisStart - 4);


            // Dodajemy liczbę nad kreską
            if(i >= (xAxisStart) + scale) {
                g2D.setPaint(Color.white);
                g2D.drawString(Integer.toString(j), i-3, yAxisStart - 16);
            }

             */

        }
        for(int i = xAxisStart, j = 0; i>0; i-=scale, j--)
        {
            g2D.setPaint(new Color(45, 49, 58));
            g2D.drawLine(i, getHeight(), i,  0);
            /*
            g2D.setPaint(Color.white);
            g2D.drawLine(i, yAxisStart + 4, i,  yAxisStart - 4);


            // Dodajemy liczbę pod kreską
            if(i <= (xAxisStart) - scale) {
                g2D.setPaint(Color.white);
                g2D.drawString(Integer.toString(j), i - 6, yAxisStart + 20);
            }

             */
        }
        for(int i = yAxisStart, j = 0; i<getHeight(); i+=scale, j--)
        {
            g2D.setPaint(new Color(45, 49, 58));
            g2D.drawLine(0, i, getWidth(),  i);
            /*
            g2D.setPaint(Color.white);
            g2D.drawLine(xAxisStart + 4, i, xAxisStart - 4,  i);


            // Dodajemy liczbę obok kreski
            if(i >= (yAxisStart) + scale) {
                g2D.setPaint(Color.white);
                g2D.drawString(Integer.toString(j), xAxisStart + 16, i+3);
            }

             */
        }
        for(int i = yAxisStart, j = 0; i>0; i-=scale, j++)
        {
            g2D.setPaint(new Color(45, 49, 58));
            g2D.drawLine(0, i, getWidth(),  i);
            /*
            g2D.setPaint(Color.white);
            g2D.drawLine(xAxisStart + 4, i, xAxisStart - 4,  i);


            // Dodajemy liczbę obok kreski
            if(i <= (yAxisStart) - (scale * 2)) {
                g2D.setPaint(Color.white);
                g2D.drawString(Integer.toString(j), xAxisStart + 16, i+3);
            }

             */

        }

        /*
        // Rysowanie linii poziomej
        g2D.setPaint(Color.white);
        int lineStartX = 0; // Współrzędna X początku linii
        int lineStartY = yAxisStart; // Współrzędna Y początku linii
        int lineEndX = getWidth(); // Współrzędna X końca linii
        int lineEndY = yAxisStart; // Współrzędna Y końca linii
        g2D.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);

        // Rysowanie linii pionowej
        g2D.setPaint(Color.white);
        lineStartX = xAxisStart; // Współrzędna X początku linii
        lineStartY = 0; // Współrzędna Y początku linii
        lineEndX = xAxisStart; // Współrzędna X końca linii
        lineEndY = getHeight(); // Współrzędna Y końca linii
        g2D.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);





        int[] xPoints = {getWidth() - scale, getWidth() - scale, getWidth()};
        int[] yPoints = {yAxisStart - 10, yAxisStart + 10, yAxisStart};
        g2D.fillPolygon(xPoints, yPoints, 3);

        xPoints = new int[]{xAxisStart - 10, xAxisStart + 10, xAxisStart};
        yPoints = new int[]{scale, scale, 0};
        g2D.fillPolygon(xPoints, yPoints, 3);

         */

        int j=0;
        // Rysowanie odcinków
        for (Line line : segments) {

            g2D.setPaint(sColors.get(j));
            g2D.drawLine((int) ((line.p1.x * scale) + (xAxisStart)), (int) ((line.p1.y * (-scale)) + (yAxisStart)), (int) ((line.p2.x * scale) + (xAxisStart)), (int) ((line.p2.y * (-scale)) + (yAxisStart)));

            j++;
        }

        int k=0;
        // rysowanie linii
        for (Line line : lines) {

            if(line.p1.x == line.p2.x) {
                double x1 = line.p1.x;
                double y1 = 0;
                double x2 = line.p2.x;
                double y2 = getHeight();

                x1 *= scale;
                x2 *= scale;

                g2D.setPaint(lColors.get(k));
                g2D.drawLine((int) (x1 + xAxisStart), (int) y1, (int) (x2 + xAxisStart), (int) y2);
            }
            else {
                double x1 = -((double) xAxisStart);
                double y1 =  (line.a * x1 + line.b);
                double x2 = (double) xAxisStart;
                double y2 = (line.a * x2 + line.b);

                // Przesunięcie współrzędnych
                x1 *= scale;
                y1 *= scale;
                x2 *= scale;
                y2 *= scale;
                g2D.setPaint(lColors.get(k));
                //g.drawLine(0, (int) (-(line.a * (-xAxisStart ) + line.b) * (-20) + yAxisStart), getWidth(), (int) (-(line.a * (xAxisStart ) + line.b) * (-20) + yAxisStart));
                g2D.drawLine((int) (x1 + xAxisStart), (int) (-y1 + yAxisStart), (int) (x2 + xAxisStart), (int) (-y2 + yAxisStart));
            }
            k++;
        }

        int e=0;
        // Rysowanie trójkątów
        for (Triangle triangle : triangles) {
            g2D.setPaint(tColors.get(e));
            int[] xTPoints = {(int) (triangle.p1.x * scale) + (xAxisStart), (int) (triangle.p2.x * scale) + (xAxisStart), (int) (triangle.p3.x * scale) + (xAxisStart)};
            int[] yTPoints = {(int) (triangle.p1.y * (-scale)) + (yAxisStart), (int) (triangle.p2.y * (-scale)) + (yAxisStart), (int) (triangle.p3.y * (-scale)) + (yAxisStart)};
            g2D.fillPolygon(xTPoints, yTPoints, 3);
            e++;
        }

        int p=0;
        // Rysowanie wielokątów
        for (Polygon polygon : polygons) {
            g2D.setPaint(polColors.get(p));
            int[] xTPoints = new int[polygon.points.size()];
            int[] yTPoints = new int[polygon.points.size()];
            for (int i = 0; i < polygon.points.size(); i++) {
                xTPoints[i] = (int) (polygon.points.get(i).x * scale) + (xAxisStart);
                yTPoints[i] = (int) (polygon.points.get(i).y * (-scale) + (yAxisStart));
            }
            g2D.fillPolygon(xTPoints, yTPoints, polygon.points.size());
            p++;
        }


        // Rysowanie otoczki wypukłej
        for (ConvexHull convexHull : convexHulls) {
            g2D.setPaint(Color.blue);

            //System.out.println(convexHulls.size());

            for(Point point : convexHull.points) {
                //g2D.fillOval((int) (point.x  * convexHullScale) - 5 + xAxisStart, (int) ((point.y  *(- convexHullScale)) - 5 + (yAxisStart)), 10, 10);
                g2D.fillOval((int) (point.x  * convexHullScale) + xAxisStart, (int) ((point.y  *(- convexHullScale)) + (yAxisStart)), 2, 2);
            }




            g2D.setPaint(Color.green);

            int o=0;
            for(Line side : convexHull.sides) {
                g2D.setPaint(convexHullSideColors.get(o));
                g2D.drawLine((int) ((side.p1.x * convexHullScale) + (xAxisStart)), (int) ((side.p1.y * (-convexHullScale)) + (yAxisStart)), (int) ((side.p2.x * convexHullScale) + (xAxisStart)), (int) ((side.p2.y * (-convexHullScale)) + (yAxisStart)));
                //g2D.fillOval((int) (side.p1.x  * convexHullScale) - 5 + (xAxisStart), (int) ((side.p1.y  *(- convexHullScale)) - 5 + (yAxisStart)), 10, 10);
                //g2D.fillOval((int) (side.p2.x  * convexHullScale) - 5 + (xAxisStart), (int) ((side.p2.y  *(- convexHullScale)) - 5 + (yAxisStart)), 10, 10);
                o++;
            }

        }

        int i=0;
        // Rysowanie punktów
        for (Point point : points) {
            //System.out.println(points.size());
            g2D.setPaint(pColors.get(i));
            g2D.fillOval((int) (point.x  * convexHullScale) - 5 + (xAxisStart), (int) ((point.y  *(- convexHullScale)) - 5 + (yAxisStart)), 10, 10);
            i++;
        }

        g2D.setPaint(Color.orange);
        int[] xTPoints = new int[explosion.points.size()];
        int[] yTPoints = new int[explosion.points.size()];
        for (int l = 0; l < explosion.points.size(); l++) {
            xTPoints[l] = (int) (explosion.points.get(l).x * convexHullScale) + (xAxisStart);
            yTPoints[l] = (int) (explosion.points.get(l).y * (-convexHullScale) + (yAxisStart));
        }
        g2D.fillPolygon(xTPoints, yTPoints, explosion.points.size());
        g2D.setPaint(Color.red);
        g2D.drawPolygon(xTPoints, yTPoints, explosion.points.size());

        explosion = new Polygon();
    }

}
