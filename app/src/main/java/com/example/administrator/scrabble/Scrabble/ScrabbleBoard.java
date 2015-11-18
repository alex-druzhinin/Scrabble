package com.example.administrator.scrabble.Scrabble;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

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

    //The size of the tiles in pixels, used for drawing each bitmap
    private final int TILE_SIZE = 75;

    //The bonus indicators to mark which bonus a tile has
    public static final int NO_BONUS = 0;
    public static final int DOUBLE_LETTER = 1;
    public static final int DOUBLE_WORD = 2;
    public static final int TRIPLE_LETTER = 3;
    public static final int TRIPLE_WORD = 4;

    // The Bitmaps representing basic board squares for drawing the board
    private Bitmap tripleWordSqr,
                    tripleLetterSqr,
                    doubleWordSqr,
                    doubleLetterSqr,
                    centerSqr,
                    normalSqr;

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
     * Constructor
     */
    public ScrabbleBoard(){


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
        return null;
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
    private ArrayList<String> findVerticalWords(ScrabbleTile tile){
        return null;
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
    private ArrayList<String> findHorizontalWords(ScrabbleTile tile){
        return null;
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
    private boolean isTileThere(int x, int y){
        Point tileLocation = new Point();
        //For each tile in the board, check if it's coordinates are the ones we are
        //looking for
        for (ScrabbleTile tile : boardTiles){
            tileLocation.x = tile.getXLocation();
            tileLocation.y = tile.getYLocation();

            if (tileLocation.x == x && tileLocation.y == y){
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
     * @param canvas
     *      The canvas we want to draw on
     */
    public void drawBoard(Canvas canvas){

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
