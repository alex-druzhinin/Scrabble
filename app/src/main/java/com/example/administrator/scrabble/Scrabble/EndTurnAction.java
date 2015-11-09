package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

/**
 * Authors: Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * Last Modified: 11/8/2015
 */
public class EndTurnAction extends GameAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public EndTurnAction(GamePlayer player) {
        super(player);
    }


}
