package com.example.administrator.scrabble.Scrabble;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.scrabble.R;
import com.example.administrator.scrabble.animation.AnimationSurface;
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

    //The current state of the game as we know it
    ScrabbleState gameState;

    //The SurfaceView we'll be drawing on
    ScrabbleSurfaceView screenView;

    ArrayList<TextView> playerScoreViews; //each player's score views
    ArrayList<ScrabbleTile> tilesToExchange; //tiles a player wants to exchange
    String word;

    //The current activity we are looking at
    private Activity currentActivity;

    //The surface we are going to draw on
    private AnimationSurface animationSurface;

    //A "lock" for other actions, used to indicate that the player is currently
    //exchanging tiles
    Boolean isExchanging;
    Boolean hasExchanged;

    // The Bitmaps representing basic board squares for drawing the board
    private Bitmap tripleWordSqr,
            tripleLetterSqr,
            doubleWordSqr,
            doubleLetterSqr,
            centerSqr,
            normalSqr;

    //The paint we'll be using to draw on our canvas
    Paint boardPaint;

    /**
     * constructor
     *
     * @param name
     *      The name of the player
     */
    public ScrabbleHumanPlayer(String name) {
        super(name);

        //Initialize our array lists
        playerScoreViews = new ArrayList<>();
        tilesToExchange = new ArrayList<>();

        //Initialize our paint so we can draw
        boardPaint = new Paint(Color.BLACK);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

        if (info instanceof ScrabbleState){
            this.gameState = (ScrabbleState) info;
        }
        //We can receive all sorts of other information as well


    }

    /**
     * Tells the game to set this player as the activity's GUI
     *
     * ----------
     * Portions of this method were taken from Steven R. Vegdahl's
     * version of this same method in his Tick Tack Toe game
     * ----------
     *
     * @param activity
     *      The activity we are communicating with
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        //Grab our activity
        currentActivity = activity;

        //Set our layout
        currentActivity.setContentView(R.layout.active_game);

        //Set us as the animator for our surface view
        animationSurface = (AnimationSurface) currentActivity.findViewById(R.id.board_surfaceview);
        animationSurface.setAnimator(this);

        //Update our game state if we have it
        if (gameState != null){
            receiveInfo(gameState);
        }

        //Initialize our bitmaps for drawing the board
        Context context = currentActivity.getApplicationContext();
        normalSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile);
        doubleWordSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_dw);
        tripleWordSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_tw);
        doubleLetterSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_dl);
        tripleLetterSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_tl);
        centerSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_start);

    }


    /**
     * Used to indicate that the player is now currently exchanging tiles
     */
    public void exchangeTilePress(){
        this.isExchanging = true;
    }

    // ----- Events ----- //

    public ArrayList<ScrabbleTile> getTilesToExchange() { return this.tilesToExchange; }


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
        //20 times per second
        return 50;
    }

    @Override
    public int backgroundColor() {
        //Our background will be black
        return Color.BLACK;
    }

    @Override
    public boolean doPause() {
        //Never pause
        return false;
    }

    @Override
    public boolean doQuit() {
        //Never quit
        return false;
    }

    /**
     * This will draw the gui and board for the player
     *
     * @param canvas
     */
    @Override
    public void tick(Canvas canvas) {

        /**Draw the Board**/
        //Create our board like a 2D Array
        for (int row = 0; row < 15; row++){
            int xPos = 10 + (row * 75); //x_position for each tile

            for (int col = 0; col < 15; col++){
                int yPos = 10 + col * 75; //y_position for each tile

                //Draw each square depending on where it is on the board
                if (row == 7 && col == 7){
                    //Starting Square
                    canvas.drawBitmap(centerSqr, xPos, yPos, boardPaint);
                }
                else if ((row == 0 || row == 7 || row == 14) && (col == 0 || col == 7 || col == 14)){
                    //Triple Word Squares
                    canvas.drawBitmap(tripleWordSqr, xPos, yPos, boardPaint);
                }
                else if(((row == 0 || row == 14) && (col == 3 || col == 11)) || ((row == 2 || row == 12) && (col == 6 || col == 8)) || ((row == 3 || row == 11) && (col == 0 || col == 7 || col == 14)) ||
                        ((row == 6 || row == 8) && (col == 2 || col == 6 || col == 8 || col == 12)) || (row == 7 && (col == 3 || col == 11)))
                {
                    //Double Letter Squares
                    canvas.drawBitmap(doubleLetterSqr, xPos, yPos, boardPaint);
                }
                else if(((row == col || row == 14 - col) && ((row > 0 && row < 5) || row > 9 && row < 14) )){
                    //Double Word Squares
                    canvas.drawBitmap(doubleWordSqr, xPos, yPos,boardPaint);
                }
                else if(((row == 5 || row == 9) && (col == 1 || col == 5 || col == 9 || col == 13)) ||
                        (row == 1 || row == 13) && (col == 5 || col == 9)){
                    //Triple Letter Squares
                    canvas.drawBitmap(tripleLetterSqr, xPos, yPos, boardPaint);
                }
                else{
                    //Generic Squares
                    canvas.drawBitmap(normalSqr, xPos, yPos ,boardPaint);
                }
            }

        }

        /** Draw tiles on top of board **/
        //If we have a game state, let's draw the tiles on top of the board
        if (gameState != null) {
            ArrayList<ScrabbleTile> boardTiles = gameState.getBoardTiles();
            for (ScrabbleTile tile : boardTiles) {
                canvas.drawBitmap(tile.getTileImage(), (tile.getXLocation() * 75) + 10, (tile.getYLocation() * 75) + 10, boardPaint);
            }
        }

    }

    /**
     * Will be used to detect when the player has tapped on certain aspects of the screen
     *      and we should act accordingly
     *
     * @param event a MotionEvent describing the touch
     */
    @Override
    public void onTouch(MotionEvent event) {



    }
}
