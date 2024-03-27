import java.util.ArrayList;
import java.util.List;

public class Triangle{
    private Line l1, l2, l3;
    public Point p1, p2, p3;

    Triangle(Line l1, Line l2, Line l3) {

        if(!isPossible(l1, l2, l3))
        {
            System.err.println("Nie można utworzyć trojkąta!!!");

        }
        else {
            this.l1 = l1;
            this.l2 = l2;
            this.l3 = l3;
        }

    }

    public boolean isPossible(Line l1, Line l2, Line l3) {

        p1 = l1.findIntersectionPoint(l2);
        p2 = l1.findIntersectionPoint(l3);
        if(p1 != null && p2 != null) {
            if((p1.x == p2.x) && (p1.y == p2.y))
                return false;
            else {
                p3 = l2.findIntersectionPoint(l3);
                if((p1.x == p3.x) && (p1.y == p3.y))
                    return false;
            }
            return l1.a != l2.a && l1.a != l3.a && l2.a != l3.a;
        }
        else return false;


    }

}
