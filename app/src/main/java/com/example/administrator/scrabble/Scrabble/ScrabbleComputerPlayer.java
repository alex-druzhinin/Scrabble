package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GameComputerPlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the AI for our game. This player may be either hard or easy.
 */
public class ScrabbleComputerPlayer extends GameComputerPlayer {

    // ----- Instance Variables ----- //

    //The current board, will be used to scan for possible words
    public ScrabbleBoard board;
    protected ArrayList<ScrabbleTile> boardTiles;
    protected ScrabbleState currentState;
    protected String word;
    protected boolean[] surrounding;
    protected int maxAbove;
    protected int maxBelow;
    protected int maxLeft;
    protected int maxRight;
    ScrabbleTile target;
    protected int availableSpaces;
    protected int targetIndex;
    protected int wordLength;
    protected boolean hard;
    protected int thresholdLength = 4;

    //The minimum amount of time (ms) the AI waits to perform an action
    final int MIN_TIME = 5000;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public ScrabbleComputerPlayer(String name, boolean hard) {
        super(name);
        surrounding = new boolean[8];
        this.hard = hard;
        Arrays.fill(surrounding, false); //initialize as false

        //assume can't place tiles in any direction
        maxAbove = 0;
        maxBelow = 0;
        maxLeft = 0;
        maxRight = 0;
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if( !(info instanceof ScrabbleState)) return; //not computer's turn if not scrabblestate

        currentState = (ScrabbleState) info; //cast to scrabblestate

        //get computer's hand
        boardTiles = currentState.getBoardTiles();

        findPlace(); //find place for word

        sleep(MIN_TIME); //delay for minimum time so changes do not look instantaneous to player

        game.sendAction(new EndTurnAction(this, word)); //end turn
    }

    /**
     * checkSurroundingTarget           Checks all spaces around the target tile to see if there
     *                                  are tiles there.
     *
     */
    protected void checkSurroundingTarget() {
        int index = 0; //counters for surrounding indexes

        //check all tiles around the target
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i == 0 && j == 0) { continue; } //do not need to check if target is there
                //assign true if there is a tile there and false if there is not
                surrounding[index] = currentState.isTileThere(target.getXLocation()+i, target.getYLocation()+j);
                ++index;
            }
        }
    }

    protected boolean getWord(int targetIndex, int length) {
        //go through dictionary and find word with parameters
        //check if all letters are in bag and if not then keep looking
        //set equal to 'word'
        return false;
    }

    protected void findPlace() {
        //for loop check board tiles
        for(ScrabbleTile boardTile: boardTiles) {
            target = boardTile; //make target the boardTile
            checkSurroundingTarget();

            //ifs to see if can place word: use max methods
            //add l&r, t&b

            //if there is at least one tile on either diagonal,
            //current tile cannot be used as anchor
            if ((surrounding[0] && surrounding[7]) || (surrounding[2] && surrounding[5])) {
                //move to next tile
            }
            //if there is at least one tile adjacent in hor direction
            // AND one tile adjacent in vert direction
            //this tile cannot be used as anchor
            if ((surrounding[3] || surrounding[4]) && (surrounding[1] || surrounding[6])) {
                //move to next tile
            }



        /*
        //check if word can only go downward
        if ((surrounding[0] || surrounding[5]) && (!(surrounding[2] || surrounding[4] || surrounding[7]))){
            this.maxLettersBelow();
            targetIndex = 0;
        }

        //check if word can go upward
        if (surrounding[2] || surrounding[7]) {
            this.maxLettersAbove();
            //targetIndex = end of chosen word length
        }

        //check if word can go up and down
        if (!(surrounding[0] || surrounding[2] || surrounding[6] || surrounding[4])){

        }
        */

            //determine maxes in either vertical direction
            if (!surrounding[0] && !surrounding[3] && !surrounding[5]) {
                this.maxLettersAbove();
            }
            if (!surrounding[2] && !surrounding[4] && !surrounding[7]) {
                this.maxLettersBelow();
            }
            availableSpaces = maxAbove + maxBelow;

            Random r = new Random();


            //determine length:
            if (hard == true) {
                if (availableSpaces >= thresholdLength)
                    wordLength = r.nextInt(availableSpaces - thresholdLength) + thresholdLength;
                //random num between 3 and availableSpaces
            } else {
                if (availableSpaces > thresholdLength) availableSpaces = thresholdLength;
                //if (availableSpaces < 2) break;
                //go to checking horizontally;
                if (availableSpaces > 1)
                    wordLength = r.nextInt(availableSpaces - 1) + 1;
            }


            //determine targetIndex:
            if (maxAbove == 0) {
                targetIndex = 0;
            } else if (maxBelow == 0) {
                targetIndex = wordLength;
            } else if ((maxAbove != 0) && (maxBelow != 0))
            targetIndex = r.nextInt(wordLength);

            if(getWord(targetIndex, wordLength))
                break;


            //determine maxes in either horizontal direction
            if (!surrounding[0] && !surrounding[1] && !surrounding[2]) {
                maxLettersLeft();
            }
            if (!surrounding[5] && !surrounding[6] && !surrounding[7]) {
                this.maxLettersRight();
            }
            availableSpaces = maxLeft + maxRight;


            //determine length:
            if (hard == true) {
                if (availableSpaces < thresholdLength) break;
                    //go to checking horizontally

                else if (availableSpaces >= thresholdLength)
                    wordLength = r.nextInt(availableSpaces - thresholdLength) + thresholdLength;
                //random num between 3 and availableSpaces
            } else if (hard == false) {
                if (availableSpaces > thresholdLength) availableSpaces = thresholdLength;
                if (availableSpaces < 2) break;
                //go to checking horizontally;
                if (availableSpaces > 1)
                    wordLength = r.nextInt(availableSpaces - 1) + 1;
            }


            //determine targetIndex:
            if (maxAbove == 0) {
                targetIndex = 0;
            } else if (maxBelow == 0) {
                targetIndex = wordLength;
            } else if (maxAbove != 0 && maxBelow != 0)
                targetIndex = r.nextInt(wordLength);

            getWord(targetIndex, wordLength);
        }

        //random letter length for word
        //random letter index of target tile on board

        //call method to get word

        //method to tally score, add word to board, and take tiles from bag
    }

    /**
     * maxLettersAbove                  Checks how many spaces above the target board tile
     *                                  can be used for a word.
     */
    protected void maxLettersAbove() {
        boolean noSpace = true; //empty spaces to move to
        boolean left = false; //space on top left diagonal to potential space is open
        boolean right = false; //space on top right diagonal to potential space is open
        boolean up = false; //space above potential space
        int x = target.getXLocation(); //target x position
        int y = target.getYLocation(); //target y position
        maxAbove = 0; //init max spaces can move above as 0

        for(int i = 1; !noSpace; i++) {
            left = currentState.isTileThere(x-1, y-i-1); //check top left diagonal
            right = currentState.isTileThere(x+1, y-i-1); //check top right diagonal
            up = currentState.isTileThere(x, y-i-1); //check space above potential space

            if(left == false && right == false && up == false) {
                ++maxAbove; //increment number of tiles that can go above the target
            }
            else { noSpace = true; } //if there is a tile
        }
    }

    /**
     * maxLettersBelow                  Checks how many spaces below the target board tile
     *                                  can be used for a word.
     */
    protected void maxLettersBelow() {
        boolean noSpace = true; //empty spaces to move to
        boolean left = false; //space on bottom left diagonal to potential space is open
        boolean right = false; //space on bottom right diagonal to potential space is open
        boolean down = false; //space below potential space
        int x = target.getXLocation(); //target x position
        int y = target.getYLocation();  //target y position
        maxBelow = 0; //init max spaces can move below as 0

        for(int i = 1; !noSpace; i++) {
            left = currentState.isTileThere(x-1, y+i+1); //check bottom left diagonal
            right = currentState.isTileThere(x+1, y+i+1); //check bottom right diagonal
            down = currentState.isTileThere(x, y+i+1); //check space below potential space

            if(left == false && right == false && down == false) {
                ++maxBelow; //increment number of tiles that can go below the target
            }
            else { noSpace = true; } //if there is a tile
        }
    }

    /**
     * maxLettersLeft                   Checks how many spaces to the left of the target board tile
     *                                  can be used for a word.
     */
    protected void maxLettersLeft() {
        boolean noSpace = true; //empty spaces to move to
        boolean leftTop = false; //space on top left diagonal to potential space is open
        boolean leftBottom = false; //space on bottom left diagonal to potential space is open
        boolean left = false; //space to left of potential space
        int x = target.getXLocation(); //target x position
        int y = target.getYLocation(); //target y position
        maxLeft = 0; //init max spaces can move to left as 0

        for(int i = 1; !noSpace; i++) {
            leftTop = currentState.isTileThere(x-i-1, y-1); //check top left diagonal
            leftBottom = currentState.isTileThere(x-i-1, y+1); //check bottom left diagonal
            left = currentState.isTileThere(x-i-1, y); //check spaces to left of potential space

            //check if valid move
            if(left == false && leftBottom == false && leftTop == false) {
                ++maxLeft; //increment number of tiles that can go left of the target
            }
            else { noSpace = true; } //if there is a tile
        }
    }

    /**
     * maxLettersRight              Checks how many spaces to the right of the target board tile
     *                              can be used for a word.
     */
    protected void maxLettersRight() {
        boolean noSpace = true; //empty spaces to move to
        boolean rightTop = false; //space on top right diagonal to potential space is open
        boolean rightBottom = false; //space on bottom right diagonal to potential space is open
        boolean right = false; //space to right of potential space
        int x = target.getXLocation(); //target x position
        int y = target.getYLocation(); //target y position
        maxRight = 0; //init max spaces can move to right as 0

        //check for valid tile placement to right until a right movement is not valid
        for(int i = 1; !noSpace; i++) {
            rightTop = currentState.isTileThere(x+i+1, y-1); //check top right diagonal
            rightBottom = currentState.isTileThere(x+i+1, y+1); //check bottom right diagonal
            right = currentState.isTileThere(x+i+1, y); //check spaces to right of potential space

            //check if valid tile placement spot
            if(right == false && rightBottom == false && rightTop == false) {
                ++maxRight; //increment number of tiles that can go above the target
            }
            else { noSpace = true; } //if there is a tile
        }
    }


    // ----- Game Actions ----- //

    /**
     * Method to indicate that the player wants to end the game
     *
     * @return
     *      An EndGameAction containing this player
     */
    public GameAction endGame(){
        return new EndGameAction(this);
    }

    /**
     * Method to indicate that the player wants to end his/her turn
     *
     * @return
     *      An EndTurnAction containing this player
     */
    public GameAction endTurn(){
        return new EndTurnAction(this, word);
    }
}
