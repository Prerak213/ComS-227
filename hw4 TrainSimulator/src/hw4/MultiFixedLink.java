package hw4;

import api.Point;
import api.PointPair;

public class MultiFixedLink extends AbstractLink
{
    /**
     * The array of PointPairs
     */
    private PointPair[] pairs;

    public MultiFixedLink(PointPair[] pairs) {
        this.pairs = pairs;
    }


    /**
     * @author Prerak
     */
    @Override
    public Point getConnectedPoint(Point point){
        for (PointPair pair : pairs) {
            if (point == pair.getPointA()) {
                return pair.getPointB();

            } else if (point == pair.getPointB()){
                    return pair.getPointA();
                }
            else{
                return pair.getPointA();
            }
        }
        return null;
    }

    /**
     * @author Prerak
     */
    @Override
    public int getNumPaths() {
        return pairs.length;
    }
}