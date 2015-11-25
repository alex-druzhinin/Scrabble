package com.example.administrator.scrabble.Scrabble;

import android.content.Context;
import android.graphics.Point;

import junit.framework.TestCase;

import java.util.ArrayList;

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

    public void testIsTileThere() throws Exception {
        ScrabbleBoard board = new ScrabbleBoard();
        ArrayList<ScrabbleTile> tempBoard = new ArrayList<>();
        ScrabbleTile tempTile = new ScrabbleTile('c', 5);
        tempTile.setLocation(1,1);
        assertFalse(board.isTileThere(1,1));
        tempBoard.add(tempTile);
        board.setBoard(tempBoard);
        assertTrue(board.isTileThere(1,1));
    }

    public void testFindVerticalWords() throws Exception {
        ScrabbleBoard board = new ScrabbleBoard();
        ArrayList<ScrabbleTile> newBoardTiles = new ArrayList<>();
        ScrabbleTile tempTile1 = new ScrabbleTile('a', 5);
        ScrabbleTile tempTile2 = new ScrabbleTile('b', 5);
        ScrabbleTile tempTile3 = new ScrabbleTile('c', 5);
        tempTile1.setLocation(0,0);
        tempTile2.setLocation(0,1);
        tempTile3.setLocation(0,2);
        newBoardTiles.add(tempTile1);
        newBoardTiles.add(tempTile2);
        newBoardTiles.add(tempTile3);
        board.setBoard(newBoardTiles);

        String word = board.findVerticalWords(tempTile1);

        assertEquals("abc", word);

    }
}