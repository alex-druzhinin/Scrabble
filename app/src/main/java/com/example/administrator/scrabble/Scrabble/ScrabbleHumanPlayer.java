package com.example.administrator.scrabble.Scrabble;

import android.view.DragEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.scrabble.game.GameHumanPlayer;
import com.example.administrator.scrabble.game.GameMainActivity;
import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameInfo;

import java.util.ArrayList;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the Human players playing the game.
 */
public class ScrabbleHumanPlayer extends GameHumanPlayer implements SurfaceView.OnClickListener, SurfaceView.OnDragListener {

    // ----- Instance variables ----- //

    //The buttons used to submit game actions
    Button endTurnButton,
            redoButton;

    //The SurfaceView we'll be drawing on
    ScrabbleSurfaceView screenView;

    //The array that will hold each of the player's score views
    ArrayList<TextView> playerScoreViews;

    //A "lock" for other actions, used to indicate that the player is currently
    //exchanging tiles
    Boolean isExchanging;

    /**
     * constructor
     *
     * @param name
     *      The name of the player
     */
    public ScrabbleHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

    }


    /**
     * Used to indicate that the player is now currently exchanging tiles
     */
    public void exchangeTilePress(){
        this.isExchanging = true;
    }

    // ----- Events ----- //


    /**
     * Called when the user pressed anywhere on the screen. If the player is exchanging and
     *  they press on a tile, that tile is marked to be exchange.
     *
     * @param v
     *      The view that was tapped on
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * Called when the user drags anywhere on the screeen. If the initial tap was on
     * a scrabble tile, then the scrabble tile will follow the finger's movements.
     *
     * @param v
     *      The view that is being dragged on
     * @param event
     *      The DragEvent that occured when the user dragged
     * @return
     *      True if the drag event was handled successfully, or false if the drag event was
     *      not handled.
     */
    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }

    // ----- Game Actions ----- //

    /**
     * Method to indicate that the player wants to exchange tiles
     *
     *
     * @return
     *      An ExchangeTileAction containing this player
     */
    public GameAction exchangeTiles(){
        return new ExchangeTileAction(this);
    }

    /**
     * Method to indicate that the player wants to end the game
     *
     * @return
     *      An EndGameAction containing this player
     */
    public GameAction endGame(){
        return new EndGameAction(this);
    }

    /**
     * Method to indicate that the player wants to end his/her turn
     *
     * @return
     *      An EndTurnAction containing this player
     */
    public GameAction endTurn(){
        return new EndTurnAction(this);
    }


}
