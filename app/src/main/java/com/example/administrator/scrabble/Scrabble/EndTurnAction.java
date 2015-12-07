package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the action when a player wants to end their turn
 */
public class EndTurnAction extends GameAction {
    private ArrayList<ScrabbleTile> wordTiles;
    private ArrayList<String> wordsMade;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public EndTurnAction(GamePlayer player, ArrayList<ScrabbleTile> wordTiles){
        super(player);
        this.wordTiles = wordTiles;
        this.wordsMade = new ArrayList<>();
    }

    public EndTurnAction(GamePlayer player, ArrayList<ScrabbleTile> wordTiles, ArrayList<String> wordsMade) {
        this(player, wordTiles);
        this.wordsMade = wordsMade;
    }


    public ArrayList<String> getWordsMade() {
        return wordsMade;
    }
    public ArrayList<ScrabbleTile> getWordTiles() { return wordTiles; } //get word tiles
}
