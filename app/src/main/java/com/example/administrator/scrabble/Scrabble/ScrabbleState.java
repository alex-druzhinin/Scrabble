package edu.up.cs301.Scrabble;

import java.util.ArrayList;
import java.util.HashMap;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by Morgan on 10/28/2015.
 *
 * Contains all of the information pertaining to the current state of the game.
 *
 */
public class ScrabbleState extends GameState{
    /*
     * Endturn:
     *  - Check word - array of words
     *  - Tally score
     *  - Rotate to next player
     *  - Update handTilesArrayList
     *  - Update bagTilesArrayList
     *  - Update boardTileArrayList
     */

    // A data structure to hold all of the tiles in the game yet to be played
    ArrayList<ScrabbleTile> bagTiles;

    //holds alphabet
    char[] alphabet = new char[26];

    //letter frequency
    int[] numEachTile = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};

    //values of each letter
    int[] tileVal = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

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
                currentTile = new ScrabbleTile(alphabet[letter], tileVal[letter]);
                bagTiles.add(currentTile); //add tile to tiles in the pool of unused tiles
            }
        }

    }

    // Some game logic will be needed for the ending/beginning of a players turn

    /**
     * Change the current player
     */
    private void changeTurn(/* Args Here */){

    }

    /**
     *
     */










}
