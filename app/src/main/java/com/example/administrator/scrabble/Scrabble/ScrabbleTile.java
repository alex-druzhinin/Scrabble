package edu.up.cs301.Scrabble;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by webber18 on 11/1/2015.
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

    /** Getters **/


    /** Setters **/


}
