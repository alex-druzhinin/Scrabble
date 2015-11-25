package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.infoMsg.GameInfo;
import com.example.administrator.scrabble.game.infoMsg.NotYourTurnInfo;

import java.util.ArrayList;

/**
 * Computer player has a normal hand like the human player and sets the best word that can be
 * made from the tiles in its hand.
 *
 * Created by chunm18 on 11/25/2015.
 */
public class ScrabbleComputerPlayerEasy extends ScrabbleComputerPlayer{
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public ScrabbleComputerPlayerEasy(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if( !(info instanceof ScrabbleState)) return; //not computer's turn if not scrabblestate

        currentState = (ScrabbleState) info; //cast to scrabblestate

        //get computer's hand
        hand = currentState.getPlayerHand(currentState.getCurrentPlayer());

        word = findWord(hand); //find word from computer's hand

        sleep(MIN_TIME); //delay for minimum time so changes do not look instantaneous to player

        game.sendAction(new EndTurnAction(this, word)); //end turn
    }
}
