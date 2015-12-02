package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GameComputerPlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameInfo;

import java.util.ArrayList;
import java.util.Arrays;

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

    //The minimum amount of time (ms) the AI waits to perform an action
    final int MIN_TIME = 5000;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public ScrabbleComputerPlayer(String name) {
        super(name);
        surrounding = new boolean[8];
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

    protected void getWord(int target, int length) {
        //go through dictionary and find word with parameters
        //check if all letters are in bag and if not then keep looking
        //set equal to 'word'
    }

    protected void findPlace(ScrabbleState state) {
        //for loop check board tiles
        //call checkSurrounding state.isTileThere()
        //ifs to see if can place word: use max methods
        //add l&r, t&b

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
