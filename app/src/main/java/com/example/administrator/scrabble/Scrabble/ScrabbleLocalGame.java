package com.example.administrator.scrabble.Scrabble;

import android.graphics.Point;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.LocalGame;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the local game for each player
 */
public class ScrabbleLocalGame extends LocalGame{

    private ScrabbleState masterState;

    /**
     * Constructor
     */
    public ScrabbleLocalGame(){
        masterState = new ScrabbleState();
    }

    /**
     * Sends the updated state to the player
     * @param p
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(masterState);
    }

    /**
     * Determines whether or not a given player can move
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     *      True if this player can move, false if not
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if (playerIdx == masterState.getCurrentPlayer()){
            return true;
        }

        return false;
    }

    /**
     * Determines whether or not the game is over, which can happen if a player has no more tiles
     * and there are no more tiles in the bag
     * @return
     *      null if the game is not over or a message containing information about the winner
     *      if it is over
     */
    @Override
    protected String checkIfGameOver() {
        Boolean isBagEmpty = masterState.isBagEmpty();
        if (!isBagEmpty){
            //Bag isn't empty, so game can't be over
            return null;
        }

        //Bag is empty, so check each player's hand
        for (GamePlayer player : this.players){
            if (masterState.getPlayerHand(getPlayerIdx(player)).size() == 0){
                //This player's hand is empty and the bag is empty, so he/she won
                return "Player " + getPlayerIdx(player) + " wins!";
            }
        }

        //At this point, no players hand's are empty, so no one has won
        return null;

    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }


    // ----- Getters ----- //

    /**
     * @return
     *      The current state of the game
     */
    public ScrabbleState getMasterState(){
        return this.masterState;
    }
}
