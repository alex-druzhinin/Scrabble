package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the action when a player wants to exchange their tiles
 */
public class ExchangeTileAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ExchangeTileAction(GamePlayer player) {
        super(player);
    }

}
