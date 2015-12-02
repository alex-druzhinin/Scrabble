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
 *
 * get array list of potential anchor tiles
 *
 * go to first tile in anchor tiles array list
 *
 * if easy: place up to 3 tiles next to anchor tile
 * if hard: place up to 7 tiles next to anchor tile
 *
 * if above is empty place up to 3 above
 * if left is empty place up to 3 left
 * if below is empty place up to 3 below
 * if right is empty place up to 3 right
 *
 * if a tile has been placed in any of these locations,
 * use board class methods to find words and check words
 *
 *
 *
 *
 *
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

        findPlace(currentState); //find place for word

        sleep(MIN_TIME); //delay for minimum time so changes do not look instantaneous to player

        game.sendAction(new EndTurnAction(this, word)); //end turn
    }

    protected void checkSurroundingTarget(ScrabbleTile target) {
        //checkbox
    }

    protected void getWord(int targetIndex, int length) {
        //go through dictionary and find word with parameters
        //check if all letters are in bag and if not then keep looking
        //set equal to 'word'
    }

    protected void findPlace(ScrabbleState state) {
        //for loop check board tiles
        //call checkSurrounding state.isTileThere()
        //ifs to see if can place word: use max methods

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
        }
        else {
            if (availableSpaces > thresholdLength) availableSpaces = thresholdLength;
            //if (availableSpaces < 2) break;
                //go to checking horizontally;
            if (availableSpaces > 1)
                wordLength = r.nextInt(availableSpaces - 1) + 1;
        }



        //determine targetIndex:
        if (maxAbove == 0){
            targetIndex = 0;
        }
        else if (maxBelow == 0) {
            targetIndex = wordLength;
        }
        else ((maxAbove != 0) && (maxBelow != 0))
            targetIndex = r.nextInt(wordLength);

        getWord(targetIndex, wordLength);
        break;



        //determine maxes in either horizontal direction
        if (!surrounding[0] && !surrounding[1] && !surrounding[2]) {
            this.maxLettersLeft();
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
        }
        else if (hard == false){
            if (availableSpaces > thresholdLength) availableSpaces = thresholdLength;
            if (availableSpaces < 2) break;
            //go to checking horizontally;
            if (availableSpaces > 1)
                wordLength = r.nextInt(availableSpaces - 1) + 1;
        }



        //determine targetIndex:
        if (maxAbove == 0){
            targetIndex = 0;
        }
        else if (maxBelow == 0) {
            targetIndex = wordLength;
        }
        else (maxAbove != 0 && maxBelow != 0)
        targetIndex = r.nextInt(wordLength);

        getWord(targetIndex, wordLength);

        //random letter length for word
        //random letter index of target tile on board

        //call method to get word

        //method to tally score, add word to board, and take tiles from bag
    }




    //get maximum letters that can be placed above letter on board
    protected void maxLettersAbove() {
        //change maxAbove
        //l & r above diagonal
    }

    //get maximum letters that can be placed below letter on board
    protected void maxLettersBelow() {
        //change maxBelow
        //l & r down diagonal
    }

    protected void maxLettersLeft() {
        //change maxLeft
        //left diagonals
    }

    protected void maxLettersRight() {
        //change maxRight
        //right diagonals
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
