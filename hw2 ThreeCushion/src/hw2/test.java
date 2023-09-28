package hw2;

import static api.BallType.RED;
import static api.BallType.WHITE;
import static api.BallType.YELLOW;
import static api.PlayerPosition.PLAYER_A;

/**
 * Some simple tests that may help you to get started implementing the
 * ThreeCushion class. Warning: these tests do not cover all of the
 * specifications. Perform your own testing in addition to using the
 * specchecker.
 */
public class test {
    public static void main(String args[]) {
        ThreeCushion game = new ThreeCushion(PLAYER_A, 3);
        System.out.println("Test 1:");
        game.lagWinnerChooses(true, WHITE);
        game.cueStickStrike(WHITE);
        game.cueBallStrike(YELLOW);
        game.cueBallStrike(RED);


    }
}