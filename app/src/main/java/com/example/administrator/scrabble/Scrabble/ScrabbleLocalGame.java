package com.example.administrator.scrabble.Scrabble;

import android.graphics.Point;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.LocalGame;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

import java.util.ArrayList;

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
        super();
        masterState = new ScrabbleState();
    }

    /**
     * Sends the updated state to the player
     * @param p
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        //Send a copied version of the state to the player
        p.sendInfo(new ScrabbleState(masterState));
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


    /**
     * Makes a move for the current player
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return
     *          True/False based on whether or not the move was legal
     */
    @Override
    protected boolean makeMove(GameAction action) {
        ////////////////////////////////////////////
        // Depending on the action received, we will make move for the player
        ////////////////////////////////////////////

        if (action instanceof ExchangeTileAction){
            ExchangeTileAction exchangeAction = (ExchangeTileAction) action;
            //Get the tiles we need to exchange
            ArrayList<ScrabbleTile> tilesToExchange = exchangeAction.getTilesToExchange();

            //Get the player id of the action
            int playerId = getPlayerIdx(exchangeAction.getPlayer());

            //Exchange their tiles out
            masterState.exchangeTiles(tilesToExchange, playerId);

            //Set the flag that says our player has exchanged for this turn
            ScrabbleHumanPlayer player = (ScrabbleHumanPlayer) action.getPlayer();
            player.setHasExchanged(true);

        }
        else if (action instanceof EndTurnAction){

            EndTurnAction endTurnAction = (EndTurnAction) action; //cast to EndTurnAction

            //get player hand
            ArrayList<ScrabbleTile> playerHand = masterState.getPlayerHand(getPlayerIdx(endTurnAction.getPlayer()));
            ScrabbleBoard board =  masterState.getScrabbleBoard(); //state
            ArrayList<ScrabbleTile> wordTiles = endTurnAction.getWordTiles(); //tiles placed on board in turn
            ArrayList<String> words = board.getWords(wordTiles); //get all words made on board
            ArrayList<ScrabbleTile> boardTiles = board.getBoardTiles(); //get board tiles
            int playerID = getPlayerIdx(endTurnAction.getPlayer());

            //go through all the strings that are made on the board
            for(String word: words) {
                //check if word is not valid
                if (! board.checkWord(word)) {
                    return false; //if there is a word that is not valid
                }
            }

            //go through all tiles the player placed down
            for(ScrabbleTile wordTile: wordTiles) {
                boardTiles.add(wordTile); //add tiles to board
                playerHand.remove(wordTile); //remove tiles from player hand
            }

            board.setBoard(boardTiles); //update board tiles

            //go through all words that are made by newly placed tiles
            for(String word: words) {
                masterState.setPlayerScore(masterState.tallyWordScore(word)); //tally points from each word
            }

            masterState.drawTiles(playerHand); //give player a new hand

            //next player's turn
            if(playerID == 1) {
                masterState.setCurrentPlayer(0);
            }
            else {
                masterState.setCurrentPlayer(1);
            }

            return true; //word was successfully placed and player's turn is over
        }
        else if (action instanceof EndGameAction){





        }





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
