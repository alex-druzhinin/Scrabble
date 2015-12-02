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
        ScrabbleBoard testBoard = new ScrabbleBoard();
        ArrayList<ScrabbleTile> tempBoardTiles = new ArrayList<>();
        ArrayList<ScrabbleTile> prospectiveWord = new ArrayList<>();

        //Generate a temp board
        tempBoardTiles.add(new ScrabbleTile('a', 0, 0, 0));
        tempBoardTiles.add(new ScrabbleTile('b', 0, 0, 1));
        tempBoardTiles.add(new ScrabbleTile('c', 0, 0, 2));
        tempBoardTiles.add(new ScrabbleTile('d', 0, 0, 3));
        testBoard.setBoard(tempBoardTiles);

        //Test some words against it
        prospectiveWord.add(new ScrabbleTile('1', 0, 0, 4));
        prospectiveWord.add(new ScrabbleTile('2', 0, 1, 4));
        for (ScrabbleTile tile : prospectiveWord){
            tempBoardTiles.add(tile);
        }
        testBoard.setBoard(tempBoardTiles);
        ArrayList<String> wordsMade = testBoard.getWords(prospectiveWord); //should contain "a12"
        System.out.println("Words made after placing first tiles:");
        for (String word : wordsMade){
            System.out.println(word);
        }

        assertTrue(wordsMade.contains("abcd1"));
        assertTrue(wordsMade.contains("12"));

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

        String word = board.findVerticalWords(new ScrabbleTile('d',5,0,3));

        assertEquals("abcd", word);

    }

    public void testFindHorizontalWords() throws Exception {
        ScrabbleBoard board = new ScrabbleBoard();
        ArrayList<ScrabbleTile> newBoardTiles = new ArrayList<>();
        ScrabbleTile tempTile1 = new ScrabbleTile('d', 1);
        ScrabbleTile tempTile2 = new ScrabbleTile('o', 1);
        ScrabbleTile tempTile3 = new ScrabbleTile('g', 1);
        tempTile1.setLocation(5,4);
        tempTile2.setLocation(6,4);
        tempTile3.setLocation(7,4);
        newBoardTiles.add(tempTile1);
        newBoardTiles.add(tempTile2);
        newBoardTiles.add(tempTile3);
        board.setBoard(newBoardTiles);

        String word = board.findHorizontalWords(tempTile1);

        assertEquals("dog", word);
    }

    public void testCheckWord() throws Exception {
        ScrabbleBoard board = new ScrabbleBoard();
        assertTrue(board.checkWord("abase"));
    }

}