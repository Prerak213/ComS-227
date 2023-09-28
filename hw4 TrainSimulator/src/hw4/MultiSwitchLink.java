package hw4;

import api.Point;
import api.PointPair;

public class MultiSwitchLink extends AbstractLink
{
    /**
     * Checks if train entered crossing
     */
    private boolean trainEnteredCrossing;
    /**
     * The array of PointPairs
     */
    private PointPair[] connections;
    public MultiSwitchLink(PointPair[] connections){
        this.connections = connections;
    }

    public Point getConnectedPoint(Point point) {
        for (PointPair connection : connections) {
            if (point == connection.getPointA()) {
                return connection.getPointB();
            } else {
                return connection.getPointA();
            }
        }
        return null;
    }

    /**
     * @author Prerak
     */
    @Override
    public void trainEnteredCrossing() {
        trainEnteredCrossing = true;
    }

    /**
     * @author Prerak
     */
    @Override
    public void trainExitedCrossing() {
        trainEnteredCrossing = false;
    }

    /**
     * @author Prerak
     */
    @Override
    public int getNumPaths() {
        return connections.length;
    }

    /**
     * @author Prerak
     */
    public void switchConnection(PointPair pointPair, int i) {
        if (!trainEnteredCrossing) {
            connections[i] = pointPair;
        }
    }
}