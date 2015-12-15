package com.example.administrator.scrabble.Scrabble;

import android.graphics.Point;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.LocalGame;
import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameOverInfo;

import java.util.ArrayList;
import java.util.HashMap;

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

        //Tell the player which ID they are
        if (p instanceof ScrabbleHumanPlayer){
            ((ScrabbleHumanPlayer) p).setPlayerID(getPlayerIdx(p));
        }
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

        //Bag is empty, so check human player's hand
        for (GamePlayer player : this.players){
            if (masterState.getPlayerHand(0).size() == 0){
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
            return true; //tiles have been exchanged

        }
        else if (action instanceof EndTurnAction){ /**End turn action **/

            EndTurnAction endTurnAction = (EndTurnAction) action; //cast to EndTurnAction

            if(getPlayerIdx(endTurnAction.getPlayer()) == 0) {

                // ------
                // At this point, the tiles the user placed down have been verified,
                // so we can tally the score, remove the tiles from the player's hand, etc.
                // ------

                //Get the tiles the player wanted to place and our player's hand
                ArrayList<ScrabbleTile> wordTiles = endTurnAction.getWordTiles();
                ArrayList<ScrabbleTile> playerHand = masterState.getPlayerHand(getPlayerIdx(endTurnAction.getPlayer()));
                ArrayList<String> wordsMade = endTurnAction.getWordsMade();
                ArrayList<ScrabbleTile> boardTiles = masterState.getBoardTiles();


                String mainWord = "";
                for (ScrabbleTile tile : wordTiles) {
                    mainWord += tile.getLetter();
                }

                //Remove the tiles from the player's hand
                for (ScrabbleTile wordTile : wordTiles) {
                    playerHand.remove(wordTile); //remove tiles from player hand
                }

                //go through all words that are made by newly placed tiles
                for (String word : wordsMade) {
                    if (word.equals(mainWord)) {
                        masterState.setPlayerScore(masterState.tallyWordScore(wordTiles)); //tally points from each word
                    } else {
                        masterState.setPlayerScore(masterState.tallyWordScore(word)); //tally points from each word
                    }
                }

                masterState.drawTiles(playerHand); //give player a new hand
                masterState.setPlayerHand(getPlayerIdx(action.getPlayer()), playerHand);


                //set computer player's turn
                masterState.setCurrentPlayer(1);

            /*if(getPlayerIdx(endTurnAction.getPlayer()) == 1) {
                for(ScrabbleTile tile: wordTiles) {
                    boardTiles.add(tile);
                }

                masterState.setBoardTiles(boardTiles);
            }
                */
            }
            else
            {
                //this.masterState = ((ScrabbleComputerPlayer) endTurnAction.getPlayer()).getComputerPlayerState();
                masterState.setCurrentPlayer(0); //set human player's turn

            }

            //Send the new state to each player
            this.sendAllUpdatedState();

            return true; //word was successfully placed and player's turn is over
        }
        else if (action instanceof EndGameAction){

            EndGameAction endGameAction = (EndGameAction) action; //cast action to EndGameAction

            //Find out who won
            int[] scores = masterState.getPlayerScores();
            int maxIdx = 0;
            for (int i = 0; i < scores.length; i++){
                if (scores[i] > scores[maxIdx]){
                    maxIdx = i;
                }
            }

            GameOverInfo over;
            if (scores[0] == scores[1]){
                 over = new GameOverInfo("Game Over!\n" +
                        "Tie game!");
            }
            else{
                over = new GameOverInfo("Game Over!\n" +
                        "Player " + maxIdx + " won with " + scores[maxIdx]
                        + " points!");
            }

            masterState.setNumTurns(masterState.getNumTurns() + 1); //Update the current turn
            sendAllUpdatedState(); //sends every player the state

            for (GamePlayer p : this.players){
                p.sendInfo(over);
            }
        }

        return false; //could not complete action
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
