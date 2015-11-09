package com.example.administrator.scrabble.Scrabble;

import android.graphics.Point;

import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.LocalGame;
import com.example.administrator.scrabble.game.actionMsg.GameAction;

/**
 * Authors: Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * Last Modified: 11/8/2015
 */
public class ScrabbleLocalGame extends LocalGame{
    ScrabbleState masterState;

    public ScrabbleLocalGame(){
        masterState = new ScrabbleState();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }

    private ScrabbleTile isTileThere(int x, int y){

        Point tileLocation;
        //For each tile in the board, check if it's coordinates are the ones we are
        //looking for
        for (ScrabbleTile tile : masterState.getBoardTiles()){
            tileLocation = tile.location;

            if (tileLocation.x == x && tileLocation.y == y){
                //This is the tile we're looking for
                return tile;
            }
        }

        //If we get to this point then there are no tiles on the board with
        //the given coordinates
        return null;
    }
}
