package com.example.administrator.scrabble.Scrabble;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Defines a Scrabble Tile object that is used to play our game.
 */
public class ScrabbleTile {

    //The letter and value of the tile
    private char letter;
    private int value;

    //The location of the tile on the grid
    //  x: 0-14
    //  y: 0-14
    private int x;
    private int y;

    //Whether or not the tile is on the board
    private boolean onBoard;

    //The image used to draw the tile
    private Bitmap tileImage;

    //The bonus value that is applied to the tile, values are static
    // values of the ScrabbleBoard class
    private int bonusValue;

    //Whether or not the tile has been exchanged on this turn and
    // whether or not the tile is marked for exchange
    private boolean hasBeenExchanged;
    private boolean toBeExchanged;

    //Used to tell whether or not the tile is marked to be moved onto the board
    private boolean readyToMove;
    public static int readyToMoveColor;

    /**
     *  Constructor
     * @param initLetter
     *      The letter of the tile
     * @param initValue
     *      The value of the tile
     */
    public ScrabbleTile(char initLetter, int initValue){

        this.letter = initLetter;
        this.value = initValue;
        this.x = 0;
        this.y = 0;
        onBoard = false;
        readyToMove = false;
        readyToMoveColor = Color.rgb(114, 114, 114);

    }

    public ScrabbleTile(char initLetter, int initValue, Bitmap initTileImage){
        this(initLetter, initValue);
        this.tileImage = initTileImage;
    }

    public ScrabbleTile(char initLetter, int initValue, int initX, int initY){
        this(initLetter, initValue);
        this.x = initX;
        this.y = initY;
    }


    // ----- Getters ----- //

    /**
     * @return
     *      The letter that the tile represents
     */
    public char getLetter() {
        return letter;
    }

    /**
     * @return
     *      The value of this tile
     */
    public int getValue() {
        return value;
    }

    /**
     * @return
     *      A Point containing the location of the tile on a
     *      15x15 grid
     */
    public int getXLocation() {
        return this.x;
    }
    public int getYLocation() { return this.y; }

    /**
     * @return
     *      True if the tile is on the board, false if not
     */
    public boolean isOnBoard() {
        return onBoard;
    }

    /**
     * @return
     *      The Bitmap image that represents the tile
     */
    public Bitmap getTileImage() {
        return tileImage;
    }

    /**
     * @return
     *      The bonus value of the tile
     */
    public int getBonusValue() {
        return bonusValue;
    }

    /**
     * @return
     *      True if the tile has been exchanged during this turn,
     *      false if not
     */
    public boolean isHasBeenExchanged() {
        return hasBeenExchanged;
    }

    /**
     * @return
     *      True if the tile is marked to be exchanged, false if not
     */
    public boolean isToBeExchanged() {
        return toBeExchanged;
    }


    // ----- Setters ----- //

    /**
     * Sets the tile's letter to a new letter
     * @param letter
     *      The letter we want the tile to be
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * Sets the tile's value to a new value
     * @param value
     *      The value we want the tile to be
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sets the tile's location to a new location
     * @param x
     *      The point that the tile is now located at
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set whether or not the tile is on the board
     * @param onBoard
     *      True if the tile is on the board, false if not
     */
    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    /**
     * Sets the bonus value of the tile for later calculations
     * @param bonusValue
     *      The bonus value of the tile, should be static values
     *      from the ScrabbleBoard class
     */
    public void setBonusValue(int bonusValue) {
        this.bonusValue = bonusValue;
    }

    /**
     * Sets whether or not the tile has been exchanged in the
     * current turn
     * @param hasBeenExchanged
     *      True if the tile has been exchanged, false if not
     */
    public void setHasBeenExchanged(boolean hasBeenExchanged) {
        this.hasBeenExchanged = hasBeenExchanged;
    }

    /**
     * Sets whether or not the tile is marked to be exchanged
     * @param toBeExchanged
     *      True if the tile is marked to be exchanged, false if not
     */
    public void setToBeExchanged(boolean toBeExchanged) {
        this.toBeExchanged = toBeExchanged;
    }

    /**
     * Tells us whether or not this tile is ready to move
     * @return
     *      True if the tile has been marked to move, false otherwise
     */
    public boolean isReadyToMove() {
        return readyToMove;
    }

    /**
     * Sets whether or not this tile is ready to move
     * @param readyToMove
     *      True if we want the tile to be marked ready to move, false otherwise
     */
    public void setReadyToMove(boolean readyToMove) {
        this.readyToMove = readyToMove;
    }

}
