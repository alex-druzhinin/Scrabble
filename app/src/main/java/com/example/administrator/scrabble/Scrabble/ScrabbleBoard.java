package com.example.administrator.scrabble.Scrabble;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceView;

import com.example.administrator.scrabble.R;

import java.util.ArrayList;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the board that each player will play on. All methods and attributes
 * pertaining to the board are included in this class.
 */
public class ScrabbleBoard {

    // ----- Instance Variables ----- //

    //The tiles that are currently placed on the board
    private ArrayList<ScrabbleTile> boardTiles;

    //holds the tiles that make up a horizontal word
    ArrayList<ScrabbleTile> wordTiles;

    ScrabbleTile temp;

    //The size of the tiles in pixels, used for drawing each bitmap
    private final int TILE_SIZE = 75;

    //The bonus indicators to mark which bonus a tile has
    public static final int NO_BONUS = 0;
    public static final int DOUBLE_LETTER = 1;
    public static final int DOUBLE_WORD = 2;
    public static final int TRIPLE_LETTER = 3;
    public static final int TRIPLE_WORD = 4;

    //The Paint used to color the board
    private Paint boardPaint;

    //The locations of the bonus tiles
    int[][] tripleWords = {{0,0}, {7,0}, {14,0}, {7,0}, {7,14}, {14,14}, {14,7}, {0,14}};
    int[][] doubleWords = {{1,1}, {2,2}, {3,3}, {4,4}, {10, 10}, {11,11}, {12,12}, {13,13},
            {1,13}, {2,12}, {3,11}, {4,10}, {10,4}, {11,3}, {12,2}, {13,1}};
    int[][] tripleLetters = {{5,1}, {9,1}, {1,5}, {1,9}, {5, 5}, {5,9}, {5, 13}, {9,9}, {13,9},
            {9,13},{13,5}, {9,5}};
    int[][] doubleLetter = {{3,0}, {0,3}, {11, 0}, {0,11}, {6,2}, {8,2}, {7,3}, {2,6}, {2,8}, {3,7},
            {6,6}, {8,8}, {8,5}, {5,8}, {14,3}, {3,14}, {14, 11}, {11, 14}, {12,6}, {11,7}, {12,8},
            {6,12}, {7,11}, {8,12}};

    /**
     * Constructors
     */

    public ScrabbleBoard(){
        boardTiles = new ArrayList<>();
        wordTiles = new ArrayList<>();
        temp = new ScrabbleTile(' ', 0);
    }

    /**
     * Sets the board equal to the new, updated board
     *
     * @param updatedBoardTiles
     *      The updated boardtiles we want to copy from
     */
    public void setBoard(ArrayList<ScrabbleTile> updatedBoardTiles){
        this.boardTiles = updatedBoardTiles;
    }

    /**
     * Tentatively superimposes the new prospective tiles onto the current board and checks
     * returns all of the words that the new tiles would create with the existing tiles on the
     * board.
     *
     * @param prospectiveTiles
 *          The tiles that we want to place on the board
     * @return
 *          The words that the prospectiveTiles create with the existing tiles on the board
     */
    public ArrayList<String> getWords(ArrayList<ScrabbleTile> prospectiveTiles){

        //Our return array, storing the words this move made
        ArrayList<String> wordsMade = new ArrayList<>();

        //For each tile that the user placed down, grab all of the words that tile makes
        String vertWord, horizWord = "";
        for (ScrabbleTile tile : prospectiveTiles){
            vertWord = findVerticalWords(tile);
            horizWord = findHorizontalWords(tile);

            //If these words don't exist, add them to the
            if (! wordsMade.contains(vertWord)){
                wordsMade.add(vertWord);
            }
            if (! wordsMade.contains(horizWord)){
                wordsMade.add(horizWord);
            }
        }

        //Purge our list of any "" words
        boolean[] badWords = new boolean[wordsMade.size()];
        //Mark each word we need to remove
        for (String word : wordsMade){
            if (word.equals("")){
                badWords[wordsMade.indexOf(word)] = true;
            }
        }
        //Remove each word as per badWords array
        for (int i = 0; i < badWords.length; i++){
            if (badWords[i]){
                wordsMade.remove(i);
            }
        }


        //List should now only contains words that were made, but still includes 1 letter words
        return wordsMade;
    }

    /**
     * Helper method for getWords(), searches vertically for any words that the a new tile would
     * create.
     *
     * @param tile
     *      The tile we want to place on the board
     * @return
     *      Any words that the new tile would create in the vertical direction
     */
    public String findVerticalWords(ScrabbleTile tile){

        String word = "";

        //Move up until we can't anymore
        int currX = tile.getXLocation();
        int currY = tile.getYLocation();
        while (isTileThere(tile.getXLocation(), currY - 1)){
            currY -= 1;
        }

        //Grab all of the tiles with the same y coordinate as our tile
        for (ScrabbleTile vertTile : boardTiles){
            if (vertTile.getXLocation() == tile.getXLocation()){
                wordTiles.add(vertTile);
            }
        }

        //Store all of the tiles that are touching our tile in order of y-coordinate in order
        while (isTileThere(currX, currY)){
            //Find the tile at this location
            for (ScrabbleTile tempTile : wordTiles){
                if (tempTile.getXLocation() == currX && tempTile.getYLocation() == currY){
                    temp = tempTile;
                    break;
                }
            }

            //Add it to our word
            word += temp.getLetter();

            //Move our location
            currY++;

        }

        wordTiles.clear();

        //Return our new word
        return word;
    }

    /**
     * Helper method for getWords(), searches horizontally for any words that the a new tile would
     * create.
     *
     * @param tile
     *      The tile we want to place on the board
     * @return
     *      Any words that the new tile would create in the horizontal direction
     */
    public String findHorizontalWords(ScrabbleTile tile) {
        String word = "";
        int existsLeft = 1; //count for tiles to left of given tile
        int existsRight = 1; //count for tiles to right of given tile

        //check how many tiles are to the left of the given tile
        while (isTileThere(tile.getXLocation() - existsLeft, tile.getYLocation())) {
            ++existsLeft;
        }

        //check how many tiles are to the right of the given tile
        while (isTileThere(tile.getXLocation() + existsRight, tile.getYLocation())) {
            ++existsRight;
        }

        int currXLeft = tile.getXLocation()-existsLeft;
        int currXRight = tile.getXLocation()+existsRight;
        int currY = tile.getYLocation();

        //get all tiles on board next to left of given tile that's in the same row as the given tile
        for (ScrabbleTile boardTile : boardTiles) {
            //check if board tile is in same row and is next to the given tile
            if (boardTile.getYLocation() == currY &&
                    boardTile.getXLocation() > currXLeft &&
                    boardTile.getXLocation() < currXRight) {
                wordTiles.add(boardTile); //add tile
            }
        }

        ++currXLeft; //go to leftmost tile x position

        //check all tiles to left of given tile
        while(isTileThere(currXLeft, currY)) {
            //Find the tile at this location
            for (ScrabbleTile tempTile : wordTiles) {
                if (tempTile.getXLocation() == currXLeft) {
                    temp = tempTile;
                    break;
                }
            }

            word += temp.getLetter(); //Add letter to word

            currXLeft++; //Move to next location
        }

        wordTiles.clear(); //empty arrayList holding tiles for a word

        return word; //Return new horizontal word
    }

    /**
     * Tells us whether or not a scrabble tile exists at the given coordinates
     *
     * @param x
     *      The x-coordinate of the location in question
     * @param y
     *      The y-coordinate of the location in question
     * @return
     *      True if there is a tile on the board at this (x,y) location, false otherwise.
     */
    public boolean isTileThere(int x, int y){
        int tempTileX;
        int tempTileY;
        //For each tile in the board, check if it's coordinates are the ones we are
        //looking for
        for (ScrabbleTile tile : boardTiles){
            //Store the current tile's location
            tempTileX = tile.getXLocation();
            tempTileY = tile.getYLocation();

            //Compare it to our location in question
            if (tempTileX == x && tempTileY == y){
                //This is the tile we're looking for
                return true;
            }
        }

        //If we get to this point then there are no tiles on the board with
        //the given coordinates
        return false;
    }

    /**
     * Draws the board on the given canvas
     *
     * @param c
     *      The canvas we want to draw on
     */
    protected void drawBoard(Canvas c){



    }

    /**
     * Checks if a given tile is located on top of a bonus spot
     *
     * @param tile
     *      The tile we want to check
     * @return
     *      The type of bonus applicable to that tile.
     */
    public int checkBonus(ScrabbleTile tile){

        int tileX = tile.getXLocation();
        int tileY = tile.getYLocation();
        int[] tileLocation = {tileX, tileY};
        //Check for each bonus
        for (int[] bonusLocation : tripleLetters){
            if (bonusLocation[0] == tileLocation[0] && bonusLocation[1] == tileLocation[1]){
                return TRIPLE_LETTER;
            }
        }
        for (int[] bonusLocation : tripleWords){
            if (bonusLocation[0] == tileLocation[0] && bonusLocation[1] == tileLocation[1]){
                return TRIPLE_WORD;
            }
        }
        for (int[] bonusLocation : doubleLetter){
            if (bonusLocation[0] == tileLocation[0] && bonusLocation[1] == tileLocation[1]){
                return DOUBLE_LETTER;
            }
        }
        for (int[] bonusLocation : doubleWords){
            if (bonusLocation[0] == tileLocation[0] && bonusLocation[1] == tileLocation[1]){
                return DOUBLE_WORD;
            }
        }

        //If we get here then the tile isn't on a bonus tile
        return NO_BONUS;
    }

    // ----- Getters ----- //

    /**
     * @return
     *      The tiles that are currently on the board
     */
    public ArrayList<ScrabbleTile> getBoardTiles() { return boardTiles; }

    /**
     * @return
     *      The size of the tiles in pixels
     */
    public int getTileSize() { return TILE_SIZE; }



}
