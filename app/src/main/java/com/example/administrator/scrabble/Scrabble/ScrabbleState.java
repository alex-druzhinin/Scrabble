package com.example.administrator.scrabble.Scrabble;

import android.graphics.Point;

import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameState;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Morgan on 10/28/2015.
 *
 * Contains all of the information pertaining to the current state of the game.
 *
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
    private ArrayList<ScrabbleTile> boardTiles;

    //holds alphabet
    private char[] alphabet = new char[26];

    //letter frequency
    private final int[] numEachTile = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};

    //values of each letter
    private final int[] TILE_VAL = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

    //The scores of each player
    private int[] playerScores = new int[4];

    //"hands" of players/tiles in a players hand
    private int[] player1Hand = new int[7];
    private int[] player2Hand = new int[7];
    private int[] player3Hand = new int[7];
    private int[] player4Hand = new int[7];

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
        int alphabetLetter = 0; //index for alphabet array

        //get letters in alphabet
        for(int letter = 97; letter <= 122; letter++) {
            alphabet[alphabetLetter] = (char) letter;
            alphabetLetter++;
        }

        //execute for each letter in the alphabet
        for(int letter = 0; letter < alphabet.length; letter++) {
            //repeat for multiple tiles with the same letter
            for(int duplicates = 0; duplicates < numEachTile.length; duplicates++) {
                //create a tile and give the letter and value
                bagTiles.add(new ScrabbleTile(alphabet[letter], TILE_VAL[letter])); //add tile to tiles in the pool of unused tiles
            }
        }

    }

    /**
     * Rotate the current player who's turn it is to the next player
     */
    private void setCurrentPlayer(int theNextPlayer){
        this.currentPlayer = theNextPlayer;
    }

    /**
     * Receive actions from the players and performs accordingly
     *
     */
    private void receiveAction(GameAction action){

        if (action instanceof ExchangeTileAction){
            //Going to call the exchange tile method

            //Depending on the player, swap out their hands


        }
        else if (action instanceof EndTurnAction){
            //Check the word, tally all of the points and update them on the board
            //gives the current player new tiles, change the current player

            //Depending on the player, end their turn and tally their points

        }
        else if (action instanceof EndGameAction){
            //Prompt all users that one player wants to end the game
            //and ask them if they want to end as well, but remove that player
            //regardless

        }

    }


    /**
     * Tallies the score for a given word
     *
     * @param word the word we want to tally
     * @return the value of the word, or 0 if the word was empty
     */
    private int tallyWordScore(String word){

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
     * Exchanges tiles from a players hand
     *
     * @param tilesToExchange
     *      the tiles we want to exchange
     * @param playerHand
     *      the players hand we are exchanging
     * @return
     *      the players hand after the tiles have been exchanged
     */
    public ArrayList<ScrabbleTile> exchangeTile(ArrayList<ScrabbleTile> tilesToExchange, ArrayList<ScrabbleTile> playerHand){

        //Remove the tile from the players hand
        //add the tile back into the bag
        for (ScrabbleTile tile : tilesToExchange){
            //remove tile from player hand and add to bag
            playerHand.remove(tile);
            bagTiles.add(tile);

            //add a random tile to the players hand and remove it from the bag
            int randIndex = rand.nextInt(bagTiles.size());
            playerHand.add(bagTiles.get(randIndex));
            bagTiles.remove(randIndex);
        }


        return playerHand;
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
     *      An array containing the tiles drawn or null if no tiles remain
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

        for (int handSize = playerHand.size(); handSize < 8; handSize++){
            //Choose our tile
            randIndex = rand.nextInt(bagTiles.size());
            playerHand.add(bagTiles.get(randIndex));
            bagTiles.remove(randIndex);
        }

        return true;

    }

    /**
     * Checks if the tiles we want to add to the board form words
     * with the tiles already placed down
     *
     * @param tilesToAdd
     *      the tiles we want to add to the board, but we aren't sure
     *      if they form words when they are passed in
     * @return
     *
     */
    private boolean checkIsWord(ArrayList<ScrabbleTile> tilesToAdd){



        return true;
    }

    /**
     *
     *
     * @param tile
     * @return
     */
   private boolean findAdjacentTile(ScrabbleTile tile){


    return true;
   }

   private void orderWord(ArrayList<ScrabbleTile> tile) {

   }


   private void checkColumn(int x, int y) {
       ArrayList<ScrabbleTile> tilesInColumn = new ArrayList<>();

       for(ScrabbleTile tile: boardTiles) {

           if(tile.location.x == x) {
               tilesInColumn.add(tile);
           }
       }

   }

   private boolean getOrientation(ArrayList<ScrabbleTile> placedTiles) {

       return true;
   }

   private ScrabbleTile isTileThere(int x, int y){

       Point tileLocation;
       //For each tile in the board, check if it's coordinates are the ones we are
       //looking for
       for (ScrabbleTile tile : boardTiles){
           tileLocation = tile.location;

           if (tileLocation.x == x && tileLocation.y == y){
               //This is the tile we're looking for
               return tile;
           }
       }

       //If we get to this point then there are no tiles on the board with
       //the given coordinates
       return null;
   }


    /**
     * Helper method to let us know if the bag is empty
     *
     * @return true if the bag has no tiles in it, false otherwise
     */
    public boolean isBagEmpty(){
        return bagTiles.isEmpty();
    }


}
