package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GameComputerPlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameInfo;

import java.util.ArrayList;

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
    protected ArrayList<ScrabbleTile> hand;
    protected ScrabbleState currentState;
    protected String word;

    //The minimum amount of time (ms) the AI waits to perform an action
    final int MIN_TIME = 5000;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public ScrabbleComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {

    }

    //Check letters in hand to make the best word possible
    protected String findWord(ArrayList<ScrabbleTile> hand) {
        char[] lettersToUse = boardLettersToUse(); //get letters on board to use

        //use hand and letters to use to find best word by points

        //check numletters above and below to see if word fits

        return ""; //return word
    }

    //Get all the letters on the board that the computer could use to make words
    protected char[] boardLettersToUse() {
        char[] use = null;

        return use;
    }

    //get maximum letters that can be placed above letter on board
    protected int maxLettersAbove() {
        return 0;
    }

    //get maximum letters that can be placed below letter on board
    protected int maxLettersBelow() {
        return 0;
    }

    /**
     * Searches the board and finds possible words for the agent to play,
     * then picks one based on whether or not the agent is on difficult setting
     * to play.
     *
     * @return
     *      An ArrayList of ScrabbleTiles that will be the word the
     *      agent wants to place.
     */
    public ArrayList<ScrabbleTile> getWord(){
        return null;
    }


    // ----- Game Actions ----- //

    /**
     * Method to indicate that the player wants to exchange tiles
     *
     *
     * @return
     *      An ExchangeTileAction containing this player
     */
    public GameAction exchangeTiles(){
        return new ExchangeTileAction(this);
    }

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
