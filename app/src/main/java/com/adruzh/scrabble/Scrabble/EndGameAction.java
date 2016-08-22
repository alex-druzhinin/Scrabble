package com.adruzh.scrabble.Scrabble;

import com.adruzh.scrabble.game.GamePlayer;
import com.adruzh.scrabble.game.actionMsg.GameAction;

/**
 * Authors: Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * Last Modified: 11/8/2015
 *
 * Represents the action when a player wants to end the game
 */
public class EndGameAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public EndGameAction(GamePlayer player) {
        super(player);
    }
}
