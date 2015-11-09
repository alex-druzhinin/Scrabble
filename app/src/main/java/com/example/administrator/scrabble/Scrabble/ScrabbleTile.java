package com.example.administrator.scrabble.Scrabble;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Authors: Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * Last Modified: 11/8/2015
 */
public class ScrabbleTile {

    char letter;
    int value;
    Point location;
    boolean onBoard;
    Bitmap tileImage;

    /**
     *
     * @param initLetter
     * @param initValue
     */
    public ScrabbleTile(char initLetter, int initValue){

        this.letter = initLetter;
        this.value = initValue;
        location = new Point();
        onBoard = false;

    }

    //get method for letter
    public char getLetter() { return letter; }

    //get method for value
    public int getValue() { return value; }

    /** Getters **/
    public Point getLocation(){return this.location;}

    /** Setters **/


}
