package com.example.administrator.scrabble.Scrabble;

import android.graphics.Point;
import android.view.View;

import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameState;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Contains all of the information and methods pertaining to the current state of the game.
 */
public class ScrabbleState extends GameState {
    /*
     * Endturn:
     *  - Check word - array of words
     *  - Tally score
     *  - Rotate to next player
     *  - Update handTilesArrayList
     *  - Update bagTilesArrayList
     *  - Update boardTileArrayList
     */

    //Our random number generator
    private Random rand = new Random();

    // The arrays that hold the tiles in the bag and on the board
    private ArrayList<ScrabbleTile> bagTiles;
    private ScrabbleBoard scrabbleBoard;

    //holds alphabet
    private char[] alphabet = new char[26];

    //letter frequency
    private final int[] numEachTile = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};

    //values of each letter
    private final int[] TILE_VAL = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

    //The scores of each player
    private int[] playerScores;

    //"hands" of players/tiles in a players hand
    private ArrayList<ScrabbleTile> player1Hand;
    private ArrayList<ScrabbleTile> player2Hand;
    private ArrayList<ScrabbleTile> player3Hand;
    private ArrayList<ScrabbleTile> player4Hand;

    // The index of the player who's turn it is
    private int currentPlayer;

    /**
     *  A constructor to create a new game.
     *      1) Initializes the board to a clean board
     *      2) Randomly distributes tiles to all players in the game
     *      3) Selects a player to begin the game
     *      4) **Anything else?**
     */
    public ScrabbleState(){
        //initialize player hands
        player1Hand = new ArrayList<>();
        player2Hand = new ArrayList<>();
        player3Hand = new ArrayList<>();
        player4Hand = new ArrayList<>();

        //initialize player scores
        playerScores = new int[4];

        //initialize arraylists for tiles
        bagTiles = new ArrayList<>();
        scrabbleBoard = new ScrabbleBoard();

        int alphabetLetter = 0; //index for alphabet array

        //get letters in alphabet
        for(int letter = 97; letter <= 122; letter++) {
            alphabet[alphabetLetter] = (char) letter;
            alphabetLetter++;
        }

        //execute for each letter in the alphabet
        for(int letter = 0; letter < alphabet.length; letter++) {
            //repeat for multiple tiles with the same letter
            for(int duplicates = 0; duplicates < numEachTile[letter]; duplicates++) {
                //create a tile and give the letter and value
                bagTiles.add(new ScrabbleTile(alphabet[letter], TILE_VAL[letter])); //add tile to tiles in the pool of unused tiles
            }
        }

        //Give our players some tiles
        this.drawTiles(player1Hand);
        this.drawTiles(player2Hand);

    }

    /**
     * Copy Constructor: Copies previous game state into a new game state.
     * @param currentState
     *      The ScrabbleState we are wanting to copy
     */
    public ScrabbleState(ScrabbleState currentState) {
        //Call our default ctor
        this();

        //initialize array lists for tiles in bag and on board
        this.bagTiles = currentState.getBagTiles();
        this.scrabbleBoard.setBoard(currentState.getBoardTiles());

        //initialize player scores
        this.playerScores = currentState.getPlayerScores();

        //initialize player hands
        this.player1Hand = currentState.getPlayerHand(0);
        this.player2Hand = currentState.getPlayerHand(1);
        this.player3Hand = currentState.getPlayerHand(2);
        this.player4Hand = currentState.getPlayerHand(3);

        //initialize current player
        this.currentPlayer = currentState.getCurrentPlayer();
    }

    



    public boolean isTileThere(int x, int y) {
        return scrabbleBoard.isTileThere(x, y);
    }

    //get index of current player
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    //get tiles left in bag
    public ArrayList<ScrabbleTile> getBagTiles() { return bagTiles; }

    /**
     * Rotate the current player who's turn it is to the next player
     */
    private void setCurrentPlayer(int theNextPlayer){
        this.currentPlayer = theNextPlayer;
    }

    /**
     * Tallies the score for a given word
     *
     * @param word the word we want to tally
     * @return the value of the word, or 0 if the word was empty
     */
    public int tallyWordScore(String word){

        int wordScore = 0;

        //Convert the word to lower case
        char wordChars[] = word.toLowerCase().toCharArray();

        //Look at each value in word and add it to wordScore
        for (char c : wordChars){
            wordScore += TILE_VAL[(int) c - 97];
        }

        return wordScore;
    }

    /**
     * addBoardTiles                Add tiles to board after player ended turn and word has been
     *                              verified.
     * @param tilesToAdd            ArrayList of tiles the player wants to add to board.
     */
    public void addBoardTiles(ArrayList<ScrabbleTile> tilesToAdd) {

        //Add our tiles to the board
        ArrayList<ScrabbleTile> boardTiles = scrabbleBoard.getBoardTiles();
        for(ScrabbleTile tile: tilesToAdd) {
            boardTiles.add(tile);
        }

        //Set the new board tiles
        scrabbleBoard.setBoard(boardTiles);
    }

    public void addBoardTile(ScrabbleTile tileToAdd, ScrabbleHumanPlayer player){
        ArrayList<ScrabbleTile> boardTiles = scrabbleBoard.getBoardTiles();
        boardTiles.add(tileToAdd);
        scrabbleBoard.setBoard(boardTiles);
    }

    public ArrayList<ScrabbleTile> getBoardTiles() { return scrabbleBoard.getBoardTiles(); }

    /**
     * Exchanges tiles from a players hand
     *
     * @param tilesToExchange
     *      the tiles we want to exchange
     * @param playerId
     *      the id of the player we want to modify
     *
     * @return
     *      the players hand after the tiles have been exchanged
     */
    public void exchangeTiles(ArrayList<ScrabbleTile> tilesToExchange, int playerId){

        if (! isBagEmpty()) { //check for tiles in the bag

            //Grab the player's hand we want to modify
            ArrayList<ScrabbleTile> playerHand = getPlayerHand(playerId);

            //Remove the tile from the players hand
            //add the tile back into the bag
            for (ScrabbleTile tile : tilesToExchange) {
                if (isBagEmpty()){ break; } //Break our of our loop if we've emptied the bag

                playerHand.remove(tile); //remove tile from player hand
                bagTiles.add(tile); //add tile to bag

                //add a random tile to the players hand and remove it from the bag
                int randIndex = rand.nextInt(bagTiles.size());
                playerHand.add(bagTiles.get(randIndex));
                bagTiles.remove(randIndex);
            }

            //Set our player's new hand
            setPlayerHand(playerId, playerHand);
        }

    }



    /**
     * This method will be used to retrieve random tiles from the bag
     * when either the game ends or someone needs to exchange some tiles
     *
     * ---POSSIBLE ADDITIONS---
     *  1) Run through numEachTile and remember which tiles are available,
     *      then grab a tile from that pool.
     *
     * @param playerHand
     *      The hand we want to draw tiles into
     * @return
     *      True if we completed our action or false if the bag is empty
     */
    public boolean drawTiles(ArrayList<ScrabbleTile> playerHand){

        //We can't do anything if there are no tiles left in the bag
        if (isBagEmpty()){
            return false;
        }

        //Create our return array and empty it
        ScrabbleTile tilesDrawn[] = new ScrabbleTile[7];
        for (int i = 0; i < tilesDrawn.length; i++){
            tilesDrawn[i] = null;
        }

        //Grab some random tiles and add them to our array
        int randIndex = 0;

        for (int handSize = playerHand.size(); handSize < 7; handSize++){
            //Choose our tile
            randIndex = rand.nextInt(bagTiles.size());
            //Add the tile into the hand and remove it from the bag
            playerHand.add(bagTiles.get(randIndex));
            bagTiles.remove(randIndex);
            
            //if we run out of tiles, exit the loop
            if (bagTiles.size() == 0){
                break;
            }
        }

        return true;

    }

    /**
     * Marks a tile ready to be moved in the player hand
     * @param playerHand
     * @param tile
     * @param readyToMove
     */
    public void markTileReadyToMove(int playerHand, int tile, boolean readyToMove){
        //Grab our player's hand
        ArrayList<ScrabbleTile> newPlayerHand = getPlayerHand(playerHand);

        //Mark the tile ready to move
        newPlayerHand.get(tile).setReadyToMove(readyToMove);

        //Set the new player's hand back
        setPlayerHand(playerHand, newPlayerHand);

    }


    /**
     * emptyBag         This is a method to empty the bag to test functionality of other methods.
     *                  Will be deleted before deploying
     */
    public void emptyBag() {
        bagTiles.clear();
    }

    /**
     * Helper method to let us know if the bag is empty
     *
     * @return true if the bag has no tiles in it, false otherwise
     */
    public boolean isBagEmpty(){
        return bagTiles.isEmpty();
    }
    
    //----- Getters -----//

    /**
     * @return
     *      An integer array containing all of the player scores for the game. If a player's score
     *      is -1, then this player is not active.
     */
    public int[] getPlayerScores() {
        return playerScores;
    }

    /**
     * Returns the a players hand in the game
     * @param playerId
     *      The player whose hand we want
     * @return
     *      The hand of the player we wanted as an ArrayList<ScrabbleTile>
     */
    public ArrayList<ScrabbleTile> getPlayerHand(int playerId){

        switch (playerId){
            case 0:
                return player1Hand;
            case 1:
                return player2Hand;
            case 2:
                return player3Hand;
            case 3:
                return player4Hand;
            default:
                return null;
        }

    }

    /**
     * Sets a players hand
     *
     * @param playerId
     *          The id of the player we want to modify
     * @param newHand
     *          The new hand of the player
     *
     */
    public void setPlayerHand(int playerId, ArrayList<ScrabbleTile> newHand){

        switch (playerId){
            case 0:
                player1Hand = newHand;
            case 1:
                player2Hand = newHand;
            case 2:
                player3Hand = newHand;
            case 3:
                player4Hand = newHand;
            default:
                return;
        }

    }
}
