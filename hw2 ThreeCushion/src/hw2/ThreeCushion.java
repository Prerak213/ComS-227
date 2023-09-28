package hw2;
import api.BallType;
import api.PlayerPosition;

import java.util.Arrays;

/**
 * author Prerak Jain
 */
public class ThreeCushion {
    private int countBallStrike=0;
    private BallType tempBall;
    private boolean forceBreak;
    private boolean lagWinnerChose;
    private PlayerPosition lagWinner;
    private final int pointsToWin;
    private boolean inningStarted;
    private PlayerPosition currentPlayer;
    private int inningNumber;
    private BallType cueBall;
    private int playerA_Score;
    private int playerB_Score;
    private boolean bankShot;
    private boolean breakShot;
    private boolean gameOver;
    private boolean shotStarted;
    private BallType playerA_Ball;
    private BallType playerB_Ball;
    private boolean breakerChosen;
    private boolean validShot;
    private String shotPattern;
    private boolean validFirstShot;


    /**
     * Creates a new game of three-cushion billiards with a given lag winner and the predetermined number of points required to win the game.
     * @param lagWinner winner of the lag
     * @param pointsToWin number of points to win
     */
    public ThreeCushion(PlayerPosition lagWinner, int pointsToWin){
        this.lagWinner=lagWinner;
        this.pointsToWin=pointsToWin;
        currentPlayer=lagWinner;
        inningNumber=1;
        inningStarted=false;
        breakerChosen=false;
        forceBreak=false;
        shotStarted=false;
        tempBall=null;
        countBallStrike=0;
    }

    /**
     * Indicates the given ball has impacted the given cushion.
     */
    public void	cueBallImpactCushion(){
        if (!shotStarted){
            return;
        }
        if (shotStarted){
            shotPattern += "1";
            setValidShot();
        }
        isBreakShot();
        if (breakShot && playerA_Score==0 && playerB_Score==0 || forceBreak){
            if (shotPattern.equals("1")){
                foul();
            }
        }


    }

    /**
     * Indicates the player's cue ball has struck the given ball.
     * @param ball the ball that has been struck by the cue ball
     */
    public void	cueBallStrike(BallType ball) {
        countBallStrike+=1;
        if (tempBall==null){
            tempBall=ball;
        }
        if (shotStarted==false){
            return;
        }

        isBreakShot();
        if ((breakShot && playerA_Score==0 && playerB_Score==0)|| forceBreak){
            if (!shotPattern.contains("0")){
                if (ball!=BallType.RED){
                    foul();
                }
            }
            if (shotPattern.equals(""))
                if (currentPlayer==PlayerPosition.PLAYER_A){
                    if (ball==playerB_Ball){
                        foul();
                    }
                }
                else{
                    if (ball==playerA_Ball){
                        foul();
                    }
                }
        }

        if (countBallStrike>1){
            if (tempBall==ball){
                return;
            }
        }
        if (shotStarted) {
            shotPattern += "0";
            setValidShot();
        }
    }

    private void setValidShot(){
        if (shotPattern!=null) {
            String[] invalidShots = {"1001", "001"};
            boolean containsInvalid = Arrays.stream(invalidShots).anyMatch(shotPattern::contains);
            if (shotPattern.length()<5) {
                char a;
                char b = '0';
                int count=0;
                for (int i = 0; i < shotPattern.length(); i++) {
                    a = shotPattern.charAt(i);
                    if (a == b) {
                        count += 1;
                    }
                }
                if (count>1){
                    foul();
                }
                else {
                    validShot=true;
                    return;
                }
            }

            if (shotPattern.equals("") || shotPattern.equals("0") || shotPattern.equals("00") || shotPattern.equals("10") || shotPattern.equals("01") || shotPattern.equals("110") || shotPattern.equals("011") || shotPattern.equals("0110") || shotPattern.equals("0101") || containsInvalid || shotPattern.equals("010") || shotPattern.equals("1100")) {
                validShot = false;

                foul();
            }
            else{
                validShot=true;
            }
        }

    }

    /**
     * Indicates the cue stick has struck the given ball.
     * @param ball the ball that has been struck by the cue stick
     */
    public void	cueStickStrike(BallType ball){
        if (!gameOver) {
            if (currentPlayer == PlayerPosition.PLAYER_A) {
                validFirstShot = playerA_Ball == ball;
            } else {
                validFirstShot = playerB_Ball == ball;
            }
            shotPattern = "";
            shotStarted = true;
            inningStarted = true;
            if (getCueBall() == ball) {
                shotStarted = true;
            } else {
                shotStarted = false;
                inningNumber += 1;
                inningStarted = false;
                changePlayer();

            }
        }
    }

    /**
     * changes the current player
     */
    private void changePlayer(){
        if (currentPlayer==PlayerPosition.PLAYER_A){
            currentPlayer=PlayerPosition.PLAYER_B;
        }
        else {
            currentPlayer = PlayerPosition.PLAYER_A;
        }
    }

    /**
     * Indicates that all balls have stopped motion.
     */
    public void	endShot(){
        if (!lagWinnerChose){
            return;
        }
        if (shotPattern !=null) {
            String[] invalidShots = {"1001", "001"};
            boolean containsInvalid = Arrays.stream(invalidShots).anyMatch(shotPattern::contains);
            if (shotPattern.equals("") || shotPattern.equals("0") || shotPattern.equals("00") || shotPattern.equals("10") || shotPattern.equals("01") || shotPattern.equals("110") || shotPattern.equals("011") || shotPattern.equals("0110") || shotPattern.equals("0101") || containsInvalid || shotPattern.equals("010") || shotPattern.equals("1100")) {
                validShot = false;
            }
        }
        if (!validShot || !validFirstShot) {
            if (!shotStarted) {
                inningStarted = false;
            }
            else {
                shotStarted = false;
                inningNumber += 1;
                inningStarted = false;
                changePlayer();

            }
        }
        else{
            shotStarted=false;
            if(currentPlayer==PlayerPosition.PLAYER_A){
                playerA_Score+=1;
            }
            else{
                playerB_Score+=1;
            }
        }
        if (playerA_Score==pointsToWin || playerB_Score==pointsToWin){
            gameOver=true;
            inningStarted=false;
        }
        if (inningNumber>2){
            forceBreak=false;
        }
        tempBall=null;
        countBallStrike=0;
    }

    /**
     * A foul immediately ends the player's inning, even if the current shot has not yet ended.
     */
    public void	foul() {
        if (lagWinnerChose && !gameOver) {
            shotStarted = true;
            validShot = false;
            if (isBreakShot()){
                forceBreak=true;
            endShot();
            }
        }
    }

    /**
     * Gets the cue ball of the current player.
     * @return Ball type
     */
    public BallType	getCueBall(){

        if (currentPlayer==PlayerPosition.PLAYER_A){
            cueBall=playerA_Ball;
        }
        else{
            cueBall=playerB_Ball;
        }
        return cueBall;

    }

    /**
     *Gets the inning number.
     * @return inning number
     */
    public int getInning(){
        return inningNumber;
    }


    /**
     * Gets the current player.
     * @return current player
     */
    public PlayerPosition getInningPlayer(){
        return currentPlayer;
    }


    /**
     * Gets the number of points scored by Player A.
     * @return player A score
     */
    public int	getPlayerAScore(){
        return playerA_Score;
    }


    /**
     * Gets the number of points scored by Player B.
     * @return player B score
     */
    public int	getPlayerBScore(){
        return playerB_Score;
    }


    /**
     * Returns true if and only if the most recently completed shot was a bank shot.
     * @return if the shot was a bank shot
     */
    public boolean	isBankShot(){
        if (validShot) {
            bankShot = shotPattern.contains("11100");
        }
        int length = shotPattern.length();
        int count=0;
        String subPattern=shotPattern.substring(2,length);
        char a;
        char b='0';


        if (validShot) {
            for (int i = 0; i < subPattern.length(); i++) {
                a=subPattern.charAt(i);
                if (a==b){
                    count+=1;
                }

            }
            if (count == 2 || count > 2) {
                bankShot = true;
            }
        }

        return bankShot;

    }


    /**
     * Returns true if and only if this is the break shot (i.e., the first shot of the game).
     * @return if the shot is the break shot
     */
    public boolean	isBreakShot(){
        breakShot= inningNumber == 1 || forceBreak;
        return breakShot;
    }


    /**
     *  Returns true if the game is over (i.e., one of the players has reached the designated number of points to win).
     * @return if the game is over
     */
    public boolean	isGameOver(){
        return gameOver;
    }


    /**
     * Returns true if the shooting player has taken their first shot of the inning.
     * @return if the inning started
     */
    public boolean	isInningStarted(){

        return inningStarted;
    }


    /**
     * Returns true if a shot has been taken (see cueStickStrike()), but not ended (see endShot()).
     * @return if the shot is going on
     */
    public boolean	isShotStarted(){
        return shotStarted;
    }


    /**
     * Sets whether the player that won the lag chooses to break (take first shot), or chooses the other player to break.
     * @param selfBreak who is breaking
     * @param cueBall the ball choice
     */
    public void	lagWinnerChooses(boolean selfBreak, BallType cueBall){
        lagWinnerChose=true;
        BallType otherCueBall;
        //sets the player who breaks

        if (!selfBreak) {
            if (lagWinner == PlayerPosition.PLAYER_A) {
                currentPlayer = PlayerPosition.PLAYER_B;
            }
            else {
                currentPlayer = PlayerPosition.PLAYER_A;
            }
        }
        else{
            currentPlayer=lagWinner;
        }
        breakerChosen=true;

        //sets the player's cue ball

        if (lagWinner == PlayerPosition.PLAYER_A) {
            playerA_Ball = cueBall;
            if (cueBall==BallType.YELLOW){
                otherCueBall=BallType.WHITE;
            }
            else{
                otherCueBall=BallType.YELLOW;
            }
            playerB_Ball=otherCueBall;

        }
        else {
            playerB_Ball = cueBall;
            if (cueBall==BallType.YELLOW){
                otherCueBall=BallType.WHITE;
            }
            else{
                otherCueBall=BallType.YELLOW;
            }
            playerA_Ball=otherCueBall;

        }


    }

    /**
     * Returns a one-line string representation of the current game state.
     * @return one-line string representation of the current game state
     */
    public java.lang.String toString(){
        String inningStatus;
        String asterisk_A="";
        String asterisk_B="";
        String isInningOver="";
        if (playerA_Score==pointsToWin || playerB_Score==pointsToWin){
            isInningOver=", game result final";
        }
        if (!inningStarted){
            inningStatus="not started";
        }
        else {
            inningStatus="started";
        }
        if (breakerChosen) {
            if (currentPlayer == PlayerPosition.PLAYER_A) {
                asterisk_A = "*";
                asterisk_B = "";
            }
            else if (currentPlayer == PlayerPosition.PLAYER_B) {
                asterisk_B = "*";
                asterisk_A = "";
            }
            else {
                asterisk_A = "";
                asterisk_B = "";
            }
        }



        return "Player A"+asterisk_A+": "+getPlayerAScore()+", Player B"+asterisk_B+": "+getPlayerBScore()+", Inning: "+getInning()+ " "+inningStatus+ isInningOver;
    }






}
