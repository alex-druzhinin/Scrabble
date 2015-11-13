package com.example.administrator.scrabble.Scrabble;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by webber18 on 11/13/2015.
 */
public class ScrabbleBoard {

    // ----- Instance Variables ----- //

    //The tiles that are currently placed on the board
    private ArrayList<ScrabbleTile> boardTiles;

    //The size of the tiles in pixels, used for drawing each bitmap
    private final int tileSize = 75;

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
        return 0;
    }

    // ----- Getters ----- //
    public ArrayList<ScrabbleTile> getBoardTiles() { return boardTiles; }
    public int getTileSize() { return tileSize; }



}
