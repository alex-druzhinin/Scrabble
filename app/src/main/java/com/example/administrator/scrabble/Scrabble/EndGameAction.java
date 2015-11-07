package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

/**
 * Created by webber18 on 11/7/2015.
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
