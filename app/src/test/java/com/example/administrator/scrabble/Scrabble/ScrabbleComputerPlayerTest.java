package com.example.administrator.scrabble.Scrabble;

import android.app.Activity;

import com.example.administrator.scrabble.game.infoMsg.GameInfo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chunm18 on 12/2/2015.
 */
public class ScrabbleComputerPlayerTest {

    @Test
    public void testComputerPlayer() throws Exception {
        Activity activity = new Activity();
        ScrabbleComputerPlayer player = new ScrabbleComputerPlayer("Hi", false, 1, activity);
        assertNotNull(player);
    }

    @Test
    public void testReceiveInfo() throws Exception {

    }

    @Test
    public void testCheckSurroundingTarget() throws Exception {

    }

    @Test
    public void testGetWord() throws Exception {

    }

    @Test
    public void testFindPlace() throws Exception {

    }

    @Test
    public void testMaxLettersAbove() throws Exception {

    }

    @Test
    public void testMaxLettersBelow() throws Exception {

    }

    @Test
    public void testMaxLettersLeft() throws Exception {

    }

    @Test
    public void testMaxLettersRight() throws Exception {

    }

    @Test
    public void testEndGame() throws Exception {

    }

    @Test
    public void testEndTurn() throws Exception {

    }
}