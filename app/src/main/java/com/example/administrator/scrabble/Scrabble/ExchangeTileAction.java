package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

/**
 * Created by webber18 on 11/8/2015.
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
