package com.example.administrator.scrabble.Scrabble;

import android.graphics.Point;

import junit.framework.TestCase;

/**
 * Created by webber18 on 11/18/2015.
 */
public class ScrabbleBoardTest extends TestCase {

    public void testGetWords() throws Exception {

    }

    public void testCheckBonus() throws Exception {

        ScrabbleBoard testBoard = new ScrabbleBoard();
        ScrabbleTile tile = new ScrabbleTile('a', 5);
        
        //Triple word test
        tile.setLocation(14, 7);
        tile.setBonusValue(testBoard.checkBonus(tile));
        assertEquals(ScrabbleBoard.TRIPLE_WORD, tile.getBonusValue());

        //Triple letter test
        tile.setLocation(1, 5);
        tile.setBonusValue(testBoard.checkBonus(tile));
        assertEquals(ScrabbleBoard.TRIPLE_LETTER, tile.getBonusValue());

        //Double word test
        tile.setLocation(1, 1);
        tile.setBonusValue(testBoard.checkBonus(tile));
        assertEquals(ScrabbleBoard.DOUBLE_WORD, tile.getBonusValue());

        //Doulbe letter test
        tile.setLocation(3, 0);
        tile.setBonusValue(testBoard.checkBonus(tile));
        assertEquals(ScrabbleBoard.DOUBLE_LETTER, tile.getBonusValue());

        //No bonus test
        tile.setLocation(1, 0);
        tile.setBonusValue(testBoard.checkBonus(tile));
        assertEquals(ScrabbleBoard.NO_BONUS, tile.getBonusValue());

    }
}