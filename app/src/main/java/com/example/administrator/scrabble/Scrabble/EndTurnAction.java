package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

import java.util.ArrayList;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the action when a player wants to end their turn
 */
public class EndTurnAction extends GameAction {
    private ArrayList<ScrabbleTile> tilesToPlace;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public EndTurnAction(GamePlayer player, ArrayList<ScrabbleTile> tilesToPlace) {
        super(player);
        this.tilesToPlace = tilesToPlace;
    }

    public ArrayList<ScrabbleTile> getTilesToPlace() {
        return tilesToPlace;
    }
}
