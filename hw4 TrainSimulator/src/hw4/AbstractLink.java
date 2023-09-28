package hw4;

import api.Point;
import api.PositionVector;
import api.Crossable;


public abstract class AbstractLink implements Crossable {

    /**
     * @author Prerak
     * AbstractLink contains shiftPoints, trainEnteredCrossing and trainExitedCrossing as they are common for all classes.
     * trainEnteredCrossing and trainExitedCrossing have been left blank as most classes do not require it to perform any function.
     * the classes that do require these methods override this one.
     * shiftPoints here is common to all classes and has the top priority.
     */

    /**
     * @author Prerak
     * @param positionVector
     *
     */
    @Override
    public void shiftPoints(final PositionVector positionVector) {
        //first 'if' statement checks if the point is at the last index and assigns newPointA accordingly
//        Point newPointA;
//        if (positionVector.getPointA().getPointIndex()== positionVector.getPointA().getPath().getNumPoints()-1) {
//            newPointA = this.getConnectedPoint(positionVector.getPointA()).getPath().getLowpoint();
//        }
//        else{
//            newPointA = this.getConnectedPoint(positionVector.getPointB());
//        }
        Point newPointA;
        newPointA = this.getConnectedPoint(positionVector.getPointB());
        
        //if statement checks whether the point is at the last index or first and assigns newPointB accordingly
        Point newPointB;
        if (newPointA.getPointIndex() == 0) {
            newPointB = newPointA.getPath().getPointByIndex(1);
        }
        else {
            newPointB = newPointA.getPath().getPointByIndex(newPointA.getPointIndex()-1);
        }
        positionVector.setPointA(newPointA);
        positionVector.setPointB(newPointB);
    }

    /**
     * @author Prerak
     */
    @Override
    public void trainEnteredCrossing() {

    }
    /**
     * @author Prerak
     */
    @Override
    public void trainExitedCrossing() {

    }


}