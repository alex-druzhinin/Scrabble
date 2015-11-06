package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.LocalGame;
import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameState;

import java.util.ArrayList;
import java.util.HashMap;
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
    Random rand = new Random();

    // The arrays that hold the tiles in the bag and on the board
    ArrayList<ScrabbleTile> bagTiles;
    ArrayList<ScrabbleTile> boardTiles;

    //holds alphabet
    char[] alphabet = new char[26];

    //letter frequency
    int[] numEachTile = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};

    //values of each letter
    final int[] TILE_VAL = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};


    ScrabbleTile currentTile;

    // We will also need some way to store each player's hand, should probably be mutable

    // Should the dictionary of possible words be included in the game state?

    // The index of the player who's turn it is
    int currentPlayer;

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
        }

        //execute for each letter in the alphabet
        for(int letter = 0; letter < alphabet.length; letter++) {
            //repeat for multiple tiles with the same letter
            for(int duplicates = 0; duplicates < numEachTile.length; duplicates++) {
                //create a tile and give the letter and value
                currentTile = new ScrabbleTile(alphabet[letter], TILE_VAL[letter]);
                bagTiles.add(currentTile); //add tile to tiles in the pool of unused tiles
            }
        }

        //Determine current player (curreentPlayer = 0 -> players.length)

    }

    /**
     * Rotate the current player who's turn it is to the next player
     */
    private void changeTurn(/* Args Here */){




    }

    /**
     * Receive actions from the players and performs accordingly
     *
     */
    private void receiveAction(GameAction action){



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
     * This method will be used to retrieve random tiles from the bag
     * when either the game ends or someone needs to exchange some tiles
     *
     * ---POSSIBLE ADDITIONS---
     *  1) Run through numEachTile and remember which tiles are available,
     *      then grab a tile from that pool.
     *
     * @param numTiles
     *      The number of tiles we wish to draw
     * @return
     *      An array containing the tiles drawn or null if no tiles remain
     */
    public ScrabbleTile[] drawTiles(int numTiles){

        //No one should ever draw more than 7 at one time
        if (numTiles > 7){
            return null;
        }

        //We can't do anything if there are no tiles left in the bag
        if (isBagEmpty()){
            return null;
        }

        //Create our return array and empty it
        ScrabbleTile tilesDrawn[] = new ScrabbleTile[7];
        for (int i = 0; i < tilesDrawn.length; i++){
            tilesDrawn[i] = null;
        }

        //Grab some random tiles and add them to our array
        int nextTile = rand.nextInt(26); // 0 -> 25;
        for (int i = 0; i < numTiles; i++){
            //If we are out of this tile, choose another one
            //THIS IS A BAD METHOD, COULD TAKE A VERY LONG TIME
            while (numEachTile[nextTile] == 0){
                nextTile = rand.nextInt(26); // 0 -> 25
            }

            //This tile exists in the bag, so choose it and add to return array
            char newTile = (char) (nextTile + 97);
            tilesDrawn[i] = new ScrabbleTile(newTile, TILE_VAL[nextTile]);
            numEachTile[nextTile]--;
        }

        return tilesDrawn;

    }

    public boolean isBagEmpty(){

        //If any value in the bag is greater than 0, then bag is not empy
        for (int i : this.numEachTile){
            if (i >= 0){
                return false;
            }
        }

        //No tiles are available, so bag is empty
        return true;
    }


}
