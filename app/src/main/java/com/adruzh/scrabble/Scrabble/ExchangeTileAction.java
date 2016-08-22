package com.adruzh.scrabble.Scrabble;

import com.adruzh.scrabble.game.GamePlayer;
import com.adruzh.scrabble.game.actionMsg.GameAction;

import java.util.ArrayList;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the action when a player wants to exchange their tiles
 */
public class ExchangeTileAction extends GameAction {

    ArrayList<ScrabbleTile> tilesToExchange;



    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ExchangeTileAction(GamePlayer player) {
        //Initialize super ctor
        super(player);
        //Initialize the array of tiles to exchange
        tilesToExchange = new ArrayList<>();
    }

    /**
     *
     * @param player
 *              The player who created the action
     * @param tiles
     *          The tiles we want to exchange
     */
    public ExchangeTileAction(GamePlayer player, ArrayList<ScrabbleTile> tiles){
        this(player);
        //Set our tile to exchange array
        tilesToExchange = tiles;
    }

    public ArrayList<ScrabbleTile> getTilesToExchange(){ return this.tilesToExchange;}


}
