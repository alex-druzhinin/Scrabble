package com.example.administrator.scrabble.Scrabble;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
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
@TargetApi(21)
public class ScrabbleHumanPlayer extends GameHumanPlayer implements Animator {

    // ----- Instance variables ----- //

    //The buttons used to submit game actions


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

    private int playerID;

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
            normalSqr,
            endTurnButton,
            handTileBorderMoving,
            redoButton;

    //The paint we'll be using to draw on our canvas
    Paint boardPaint;

    //The current tile marked ready to move
    int tileTouched;

    private Bitmap[] alphabetImages;


    /**
     * constructor
     *
     * @param name
     *      The name of the player
     */
    public ScrabbleHumanPlayer(String name, int playerID) {
        super(name);

        //Initialize our array lists
        playerScoreViews = new ArrayList<>();
        tilesToExchange = new ArrayList<>();

        //Initialize our paint so we can draw
        boardPaint = new Paint(Color.BLACK);

        //Initialize our alphabet array
        alphabetImages = new Bitmap[26];

        //Tell our player that no tiles have been touched
        tileTouched = -1;

        this.playerID = playerID; //set playerID
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
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        Context context = currentActivity.getApplicationContext();
        normalSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile, opts);
        doubleWordSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_dw, opts);
        tripleWordSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_tw, opts);
        doubleLetterSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_dl, opts);
        tripleLetterSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_tl, opts);
        centerSqr = BitmapFactory.decodeResource(context.getResources(), R.drawable.boardtile_start, opts);
        endTurnButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.endturnbutton);
        handTileBorderMoving = BitmapFactory.decodeResource(context.getResources(), R.drawable.handtileborder_moving);
        redoButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.redobutton);
        //Give our tiles their images
        alphabetImages[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_a);
        alphabetImages[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_b);
        alphabetImages[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_c);
        alphabetImages[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_d);
        alphabetImages[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_e);
        alphabetImages[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_f);
        alphabetImages[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_g);
        alphabetImages[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_h);
        alphabetImages[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_i);
        alphabetImages[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_j);
        alphabetImages[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_k);
        alphabetImages[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_l);
        alphabetImages[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_m);
        alphabetImages[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_n);
        alphabetImages[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_o);
        alphabetImages[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_p);
        alphabetImages[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_q);
        alphabetImages[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_r);
        alphabetImages[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_s);
        alphabetImages[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_t);
        alphabetImages[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_u);
        alphabetImages[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_v);
        alphabetImages[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_w);
        alphabetImages[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_x);
        alphabetImages[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_y);
        alphabetImages[25] = BitmapFactory.decodeResource(context.getResources(), R.drawable.scrabbletile_z);

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
        for (int row = 0; row < 15; row++) {
            int xPos = 450 + (row * 75); //x_position for each tile

            for (int col = 0; col < 15; col++) {
                int yPos = 10 + col * 75; //y_position for each tile

                //Draw each square depending on where it is on the board
                if (row == 7 && col == 7) {
                    //Starting Square
                    canvas.drawBitmap(centerSqr, xPos, yPos, boardPaint);
                } else if ((row == 0 || row == 7 || row == 14) && (col == 0 || col == 7 || col == 14)) {
                    //Triple Word Squares
                    canvas.drawBitmap(tripleWordSqr, xPos, yPos, boardPaint);
                } else if (((row == 0 || row == 14) && (col == 3 || col == 11)) || ((row == 2 || row == 12) && (col == 6 || col == 8)) || ((row == 3 || row == 11) && (col == 0 || col == 7 || col == 14)) ||
                        ((row == 6 || row == 8) && (col == 2 || col == 6 || col == 8 || col == 12)) || (row == 7 && (col == 3 || col == 11))) {
                    //Double Letter Squares
                    canvas.drawBitmap(doubleLetterSqr, xPos, yPos, boardPaint);
                } else if (((row == col || row == 14 - col) && ((row > 0 && row < 5) || row > 9 && row < 14))) {
                    //Double Word Squares
                    canvas.drawBitmap(doubleWordSqr, xPos, yPos, boardPaint);
                } else if (((row == 5 || row == 9) && (col == 1 || col == 5 || col == 9 || col == 13)) ||
                        (row == 1 || row == 13) && (col == 5 || col == 9)) {
                    //Triple Letter Squares
                    canvas.drawBitmap(tripleLetterSqr, xPos, yPos, boardPaint);
                } else {
                    //Generic Squares
                    canvas.drawBitmap(normalSqr, xPos, yPos, boardPaint);
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

        /**Draw our end turn and redo buttons**/
        canvas.drawBitmap(endTurnButton, 1600, 350, boardPaint);
        canvas.drawBitmap(redoButton, 1600, 50, boardPaint);

        /**Draw our hand**/
        if (gameState != null) {
            ArrayList<ScrabbleTile> ourHand = gameState.getPlayerHand(playerID);
            int i = 0;
            for (ScrabbleTile handTile : ourHand) {
                //Get the char of the tile
                char tileChar = handTile.getLetter();
                int charIdx = tileChar - 97; //convert to index

                //Draw a border indicating the tile is ready to move
                if (handTile.isReadyToMove()) {
                    canvas.drawBitmap(handTileBorderMoving, 195, (100 * i) + 150 + (i * 25) - 5, boardPaint);

                }
                //Draw the tile
                canvas.drawBitmap(alphabetImages[charIdx], 200, (100 * i) + 150 + (i * 25), boardPaint);

                i++;
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

        float eventX = event.getX();
        float eventY = event.getY();

        if (eventX > 1600 && eventX < 1800 && eventY < 550 && eventY > 350){
            //We're inside the endturnButton press, so end our turn
            game.sendAction(new EndTurnAction(this, ""));
        }
        else if(eventX > 200 && eventX < 300 && eventY > 200 && eventY < 1300){
            /** We touched a hand tile **/
            //Find out which tile we touched
            tileTouched = (int) ((eventY - 150) / 120); // should be 0->6
            if (tileTouched > 6){
                tileTouched = 6;
            }
            if (tileTouched < 0){
                tileTouched = 0;
            }

            ArrayList<ScrabbleTile> ourHand = gameState.getPlayerHand(0);
            //Unmark every tile that might be marked
            for (ScrabbleTile tile : ourHand){
                if (tile.isReadyToMove()){
                    tile.setReadyToMove(false);
                }
            }

            //Tell the tile that it's moving
            ourHand.get(tileTouched).setReadyToMove(! ourHand.get(tileTouched).isReadyToMove());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            gameState.setPlayerHand(0, ourHand);
        }
        else if (eventX >= 450 && eventX <= 1575 && eventY >= 10 && eventY <= 1100){
            /** We touched a spot on the board **/
            //Find the (x,y) on the board that we touched, each tile is ~80px wide
            int tileX = (int) ((eventX - 450) / 75);
            int tileY = (int) ((eventY - 10) / 75);
            //Sleep so we don't pick up multiple touches
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Log.i("Location: ", "(" + tileX + ", " + tileY + ")");

            //Set the board x,y of the tile selected if one is selected
            Boolean isTileReadyToMove = false;
            ArrayList<ScrabbleTile> ourHand = gameState.getPlayerHand(0);
            for (ScrabbleTile tile : ourHand){
                if (tile.isReadyToMove()){
                    isTileReadyToMove = true;
                    break;
                }
            }

            if (isTileReadyToMove){
                if (tileTouched != -1){ //we've marked a tile ready to move
                    //Put the tile on the board and update it's location
                    ourHand.get(tileTouched).setLocation(tileX, tileY);
                    ourHand.get(tileTouched).setOnBoard(true);
                }
            }


        }


    }

}
