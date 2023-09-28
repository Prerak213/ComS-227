package hw4;

import api.Point;
import api.PositionVector;

public class DeadEndLink extends AbstractLink
{

    /**
     * The first end point
     */
    private Point endPointA;
    /**
     * The second end point
     */
    private Point endPointB;
    public void shiftPoints(PositionVector positionVector){

    }

    /**
     * @author Prerak
     */
    @Override
    public Point getConnectedPoint(Point point) {
        if (point == endPointA) {
            return endPointB;
        } else {
            return endPointA;
        }
    }
    /**
     * @author Prerak
     */
    @Override
    public int getNumPaths(){
        return 1;
    }

}