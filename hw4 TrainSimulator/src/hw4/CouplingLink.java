package hw4;

import api.Point;

public class CouplingLink extends AbstractLink
{

    /**
     * The first end point
     */
    private Point endPointA;
    /**
     * The second end point
     */
    private Point endPointB;
    /**
    * @author Prerak
     */

    public CouplingLink (Point endpoint1, Point endpoint2){
        endPointA = endpoint1;
        endPointB = endpoint2;
    }
    /**
     * @author Prerak
     */
    @Override
    public Point getConnectedPoint(Point point) {
        if (point == endPointA) {
            return endPointB;
        }
        return endPointA;
    }

    /**
    * @author Prerak
    */
    @Override
    public int getNumPaths() {
        return 2;
    }


}