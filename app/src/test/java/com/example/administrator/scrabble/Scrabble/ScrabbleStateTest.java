package com.example.administrator.scrabble.Scrabble;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by chunm18 on 11/8/2015.
 */
public class ScrabbleStateTest {

    @Test
    public void testGameState() throws Exception {
        ScrabbleState state = new ScrabbleState(); //create game state
        assertNotNull(state); //check if game state instantiated
    }

    @Test
    public void testExchangeTile() throws Exception {
        ScrabbleState state = new ScrabbleState(); //create game state

        ArrayList<ScrabbleTile> exchange = new ArrayList<>(); //create array list for tiles to exchange
        ScrabbleTile tile1 = new ScrabbleTile('A', 1); //tile for bag
        ScrabbleTile tile2 = new ScrabbleTile('B', 3); //tile for bag
        exchange.add(tile1); //add tiles to bag
        exchange.add(tile2); //add tiles to bag

        ArrayList<ScrabbleTile> playerHand = new ArrayList<>();
        ScrabbleTile tile3 = new ScrabbleTile('C', 2);
        playerHand.add(tile1);
        playerHand.add(tile2);
        playerHand.add(tile3);
        playerHand = state.exchangeTile(exchange, playerHand);
        assertFalse(playerHand.contains(tile1));
        assertFalse(playerHand.contains(tile2));
        assertTrue(playerHand.contains(tile3));
    }

    @Test
    public void testDrawTiles() throws Exception {
        ScrabbleState state = new ScrabbleState();
        ArrayList<ScrabbleTile> hand = new ArrayList<>();
        assertTrue(state.drawTiles(hand));
        assertNotEquals(0, hand.size());

        //print off tile letters that were added to the hand
        for(ScrabbleTile tile: hand) {
            System.out.println("Tiles in hand " + tile.getLetter());
        }
    }

    @Test
    public void testIsBagEmpty() throws Exception {
        ScrabbleState state = new ScrabbleState();
        assertFalse(state.isBagEmpty());

        state.emptyBag();
        assertTrue(state.isBagEmpty());
    }

    @Test
    public void testTallyWordScore() throws Exception {
        ScrabbleState state = new ScrabbleState();
        assertEquals(9, state.tallyWordScore("meow"));
        assertEquals(87, state.tallyWordScore("abcdefghijklmnopqrstuvwxyz"));
    }

    @Test
    public void testIsTileThere() throws Exception {

    }

    @Test
    public void testAddBoardTiles() throws Exception {
        ScrabbleState state = new ScrabbleState();

        ArrayList<ScrabbleTile> addTiles = new ArrayList<>();
        ScrabbleTile tile1 = new ScrabbleTile('A', 1); //tile for bag
        ScrabbleTile tile2 = new ScrabbleTile('B', 3); //tile for bag
        addTiles.add(tile1);
        addTiles.add(tile2);
        state.addBoardTiles(addTiles);

        assertTrue(state.getBoardTiles().contains(tile1));
    }
}