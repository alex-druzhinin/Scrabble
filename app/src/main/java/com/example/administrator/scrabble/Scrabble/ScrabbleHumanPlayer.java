package com.example.administrator.scrabble.Scrabble;

import android.graphics.Canvas;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.scrabble.animation.Animator;
import com.example.administrator.scrabble.game.GameHumanPlayer;
import com.example.administrator.scrabble.game.GameMainActivity;
import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameInfo;

import java.util.ArrayList;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the Human players playing the game.
 */
public class ScrabbleHumanPlayer extends GameHumanPlayer implements Animator {

    // ----- Instance variables ----- //

    //The buttons used to submit game actions
    Button  endTurnButton,
            redoButton;

    //The SurfaceView we'll be drawing on
    ScrabbleSurfaceView screenView;

    ArrayList<TextView> playerScoreViews; //each player's score views
    ArrayList<ScrabbleTile> tilesToExchange; //tiles a player wants to exchange
    String word;

    //A "lock" for other actions, used to indicate that the player is currently
    //exchanging tiles
    Boolean isExchanging;
    Boolean hasExchanged;

    /**
     * constructor
     *
     * @param name
     *      The name of the player
     */
    public ScrabbleHumanPlayer(String name) {
        super(name);
        playerScoreViews = new ArrayList<>();
        tilesToExchange = new ArrayList<>();
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

    public ArrayList<ScrabbleTile> getTilesToExchange() { return tilesToExchange; }


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
        return new EndTurnAction(this, word);
    }

    public Boolean getHasExchanged() {
        return hasExchanged;
    }

    public void setHasExchanged(Boolean hasExchanged) {
        this.hasExchanged = hasExchanged;
    }



    @Override
    public int interval() {
        return 0;
    }

    @Override
    public int backgroundColor() {
        return 0;
    }

    @Override
    public boolean doPause() {
        return false;
    }

    @Override
    public boolean doQuit() {
        return false;
    }

    @Override
    public void tick(Canvas canvas) {

    }

    @Override
    public void onTouch(MotionEvent event) {

    }
}
