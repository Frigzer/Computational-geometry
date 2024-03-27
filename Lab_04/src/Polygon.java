import java.util.ArrayList;
import java.util.List;

public class Polygon {


    List<Point> points;
    List<Line> sides;

    public Polygon() {
        points = new ArrayList<>();
        sides = new ArrayList<>();
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public void containsPoint(Point p) {
        createLines();
        Point temp1 = new Point(p.x - 5, p.y);
        Line l1 = new Line(p, temp1);

        Point temp2 = new Point(p.x, p.y - 5);
        Line l2 = new Line(p, temp2);

        int numOFLeftIntersections = 0;
        int numOFRightIntersections = 0;
        int numOFTopIntersections = 0;
        int numOFBotIntersections = 0;

        for(Line side : sides) {
            if(side.isPointOnSegment(p)) {
                numOFLeftIntersections = 1;
                numOFRightIntersections = 1;
                numOFTopIntersections = 1;
                numOFBotIntersections = 1;
                break;
            }
            else {
                Point intersection1 = side.findIntersectionSegment(l1);
                Point intersection2 = side.findIntersectionSegment(l2);
                if((intersection1 != null) && (intersection1.x < p.x)) {
                    numOFLeftIntersections++;
                }
                if((intersection1 != null) && (intersection1.x > p.x)) {
                    numOFRightIntersections++;
                }
                if((intersection2 != null) && (intersection2.y > p.y)) {
                    numOFTopIntersections++;
                }
                if((intersection2 != null) && (intersection2.y < p.y)) {
                    numOFBotIntersections++;
                }
            }
        }

        System.out.println(numOFLeftIntersections + numOFRightIntersections + numOFTopIntersections + numOFBotIntersections);
        if(numOFLeftIntersections == 0 || numOFRightIntersections == 0 || numOFTopIntersections == 0 || numOFBotIntersections == 0)
            System.out.println("Punkt nie znajduje sie w wielokacie");
        else if(numOFLeftIntersections % 2 == 0 && numOFRightIntersections % 2 == 0 && numOFTopIntersections % 2 == 0 && numOFBotIntersections % 2 == 0) {
            System.out.println("Punkt nie znajduje sie w wielokacie");
        }
        else System.out.println("Punkt znajduje sie w wielokacie");
    }

    void createLines() {
        int n = points.size();
        for (int i = 0; i < n - 1; i++) {
            sides.add(new Line(points.get(i), points.get(i + 1)));
        }
        sides.add(new Line(points.get(n - 1), points.getFirst()));
    }


}
