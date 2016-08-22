package com.adruzh.scrabble.Scrabble;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adruzh.scrabble.R;
import com.adruzh.scrabble.animation.AnimationSurface;
import com.adruzh.scrabble.animation.Animator;
import com.adruzh.scrabble.game.GameHumanPlayer;
import com.adruzh.scrabble.game.GameMainActivity;
import com.adruzh.scrabble.game.actionMsg.GameAction;
import com.adruzh.scrabble.game.infoMsg.GameInfo;
import com.adruzh.scrabble.game.infoMsg.IllegalMoveInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the Human players playing the game.
 */
@TargetApi(21)
public class ScrabbleHumanPlayer extends GameHumanPlayer implements Animator {
    public static final int HAND_LEFT = 50;
    public static final int HAND_TOP = 150;
    public static final int HAND_CELL_SIZE = 35;

    // ----- Instance variables ----- //

    //The buttons used to submit game actions


    //The current state of the game as we know it
    ScrabbleState gameState;

    //The SurfaceView we'll be drawing on
    ScrabbleSurfaceView screenView;

    ArrayList<TextView> playerScoreViews; //each player's score views
    ArrayList<ScrabbleTile> tilesToExchange; //tiles a player wants to exchange
    String wordToPlace;

    //The current activity we are looking at
    private Activity currentActivity;

    //The surface we are going to draw on
    private AnimationSurface animationSurface;

    //Used to tell the player which index he/she is,
    private int playerID = -1;

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
            redoButton,
            playerScoreBorder,
            currentPlayerIcon;

    //The context of the game
    Context context;

    //The paint we'll be using to draw on our canvas
    Paint boardPaint;

    //The current tile marked ready to move
    int tileTouched;

    //The images of each com.adruzh.scrabble tile
    private Bitmap[] alphabetImages;

    //Paint used to paint player scores
    private Paint playerScorePaint;

    //A flag to tell us to stop listening to touch events
    private boolean ignoreTouchEvents = false;
    public static final int CELL_SIZE = 30;
    private Rect endTurnRect;
    private Rect redoRect;
    private Rect handRect;
    private Rect boardRect;


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

        //Initialize our alphabet array
        alphabetImages = new Bitmap[26];

        //Tell our player that no tiles have been touched
        tileTouched = -1;

        //Initialize our playerscorepaint
        playerScorePaint = new Paint(Color.BLUE);
        playerScorePaint.setTextSize(20);
        playerScorePaint.setFakeBoldText(false);

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

        if (info instanceof IllegalMoveInfo){

            if (getTilesToPlace().size() == 0){

            }
            else{
                Toast.makeText(currentActivity.getApplicationContext(),
                        "Sorry, that word is not a valid word.", Toast.LENGTH_SHORT).show();
            }


        }

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
        context = currentActivity.getApplicationContext();
        Resources currentResources = context.getResources();
        normalSqr = BitmapFactory.decodeResource(currentResources, R.drawable.boardtile, opts);
        doubleWordSqr = BitmapFactory.decodeResource(currentResources, R.drawable.boardtile_dw, opts);
        tripleWordSqr = BitmapFactory.decodeResource(currentResources, R.drawable.boardtile_tw, opts);
        doubleLetterSqr = BitmapFactory.decodeResource(currentResources, R.drawable.boardtile_dl, opts);
        tripleLetterSqr = BitmapFactory.decodeResource(currentResources, R.drawable.boardtile_tl, opts);
        centerSqr = BitmapFactory.decodeResource(currentResources, R.drawable.boardtile_start, opts);
        endTurnButton = BitmapFactory.decodeResource(currentResources, R.drawable.endturnbutton);
        handTileBorderMoving = BitmapFactory.decodeResource(currentResources, R.drawable.handtileborder_moving, opts);
        redoButton = BitmapFactory.decodeResource(currentResources, R.drawable.redobutton);
        playerScoreBorder = BitmapFactory.decodeResource(currentResources, R.drawable.playerscoreborder);
        currentPlayerIcon = BitmapFactory.decodeResource(currentResources, R.drawable.curr_player_icon);

        //Give our tiles their images
        opts.inSampleSize = 2;
        alphabetImages[0] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_a, opts);
        alphabetImages[1] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_b, opts);
        alphabetImages[2] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_c, opts);
        alphabetImages[3] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_d, opts);
        alphabetImages[4] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_e, opts);
        alphabetImages[5] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_f, opts);
        alphabetImages[6] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_g, opts);
        alphabetImages[7] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_h, opts);
        alphabetImages[8] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_i, opts);
        alphabetImages[9] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_j, opts);
        alphabetImages[10] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_k, opts);
        alphabetImages[11] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_l, opts);
        alphabetImages[12] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_m, opts);
        alphabetImages[13] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_n, opts);
        alphabetImages[14] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_o, opts);
        alphabetImages[15] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_p, opts);
        alphabetImages[16] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_q, opts);
        alphabetImages[17] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_r, opts);
        alphabetImages[18] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_s, opts);
        alphabetImages[19] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_t, opts);
        alphabetImages[20] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_u, opts);
        alphabetImages[21] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_v, opts);
        alphabetImages[22] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_w, opts);
        alphabetImages[23] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_x, opts);
        alphabetImages[24] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_y, opts);
        alphabetImages[25] = BitmapFactory.decodeResource(currentResources, R.drawable.scrabbletile_z, opts);

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
     * @param tilesToPlace
     * @param words
     */
    public void endTurn(ArrayList<ScrabbleTile> tilesToPlace, ArrayList<String> words){

        //Send the game the tiles we want to place down
        game.sendAction(new EndTurnAction(this, tilesToPlace, words));

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

        boardRect = new Rect(HAND_LEFT + 50, 10, HAND_LEFT + 50 + (15 * CELL_SIZE), 10 + (15 * CELL_SIZE));
        /**Draw the Board**/
        //Create our board like a 2D Array
        for (int row = 0; row < 15; row++) {
            int xPos = HAND_LEFT + 50 + (row * CELL_SIZE); //x_position for each tile

            for (int col = 0; col < 15; col++) {
                int yPos = 10 + col * CELL_SIZE; //y_position for each tile

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
            synchronized (this) {
                for (ScrabbleTile tile : boardTiles) {
                    if (tile.isOnBoard()) {
                        canvas.drawBitmap(alphabetImages[tile.getLetter() - 97], HAND_LEFT + 49 + (tile.getXLocation() * CELL_SIZE), (tile.getYLocation() * CELL_SIZE) + 9, boardPaint);
                    }
                }
            }
        }

        /**Draw our end turn and redo buttons**/
        endTurnRect = new Rect(HAND_LEFT + 100 + (14 * CELL_SIZE), 250,
                HAND_LEFT + 100 + (14 * CELL_SIZE) + endTurnButton.getWidth(), 250 + endTurnButton.getHeight());
        canvas.drawBitmap(endTurnButton, HAND_LEFT + 100 + (14 * CELL_SIZE), 250, boardPaint);
        redoRect = new Rect(HAND_LEFT + 100 + (14 * CELL_SIZE), 50,
                HAND_LEFT + 100 + (14 * CELL_SIZE) + endTurnButton.getWidth(), 50 + endTurnButton.getHeight());
        canvas.drawBitmap(redoButton, HAND_LEFT + 100 + (14 * CELL_SIZE), 50, boardPaint);

        /**Draw our hand**/
        if (gameState != null) {
            ArrayList<ScrabbleTile> ourHand = gameState.getPlayerHand(playerID);
            handRect = new Rect(HAND_LEFT, 150, HAND_LEFT + alphabetImages[0].getWidth(), HAND_TOP + (ourHand.size() * 35));
            int i = 0;
            for (ScrabbleTile handTile : ourHand) {
                //Get the char of the tile
                char tileChar = handTile.getLetter();
                int charIdx = tileChar - 97; //convert to index

                if (handTile.isOnBoard() == false) {//only draw it if it's still in the hand
                    //Draw a border indicating the tile is ready to move
                    if (handTile.isReadyToMove()) {
                        canvas.drawBitmap(handTileBorderMoving, HAND_LEFT - 5, HAND_TOP + (i * HAND_CELL_SIZE) - 5, boardPaint);

                    }
                    //Draw the tile
                    canvas.drawBitmap(alphabetImages[charIdx], HAND_LEFT, HAND_TOP + (i * HAND_CELL_SIZE), boardPaint);
                }
                i++;
            }

        }

        /** Draw the player scores **/
        canvas.drawBitmap(playerScoreBorder, 250, 50 + 14 * CELL_SIZE, boardPaint);
        canvas.drawBitmap(playerScoreBorder, 450, 50 + 14 * CELL_SIZE, boardPaint);
        if (gameState != null) {
            int[] playerScores = gameState.getPlayerScores();
            canvas.drawText("Player 1: " + playerScores[0], 255, 75 + 14 * CELL_SIZE, playerScorePaint);
            canvas.drawText("Player 2: " + playerScores[1], 455, 75 + 14 * CELL_SIZE, playerScorePaint);
            canvas.drawBitmap(currentPlayerIcon, HAND_LEFT + (gameState.getCurrentPlayer() * 200), 50 + 14 * CELL_SIZE, boardPaint);
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

            if (endTurnRect.contains((int)eventX, (int)eventY)) {
                /** We touched the end turn button **/
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //We can't place 0 tiles
                    if (this.getTilesToPlace().size() == 0) {
                        Toast.makeText(currentActivity.getApplicationContext(),
                                "You must place at least one tile", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    //get player hand
                    gameState.getPlayerHand(this.playerID);

                    //Get the current board and the tiles wanted to be placed this turn
                    ScrabbleBoard board = gameState.getScrabbleBoard();
                    ArrayList<ScrabbleTile> boardTiles = board.getBoardTiles(); //get board tiles
                    ArrayList<ScrabbleTile> tilesToPlace = this.getTilesToPlace();

                    //We can't not make a word
                    ArrayList<String> words = board.getWords(tilesToPlace);

                    //go through all the strings that are made on the board
                    for (String word : words) {
                        //check if word is not valid
                        if (!this.checkWord(word)) {
                            //Reset our hand
                            this.resetHand();

                            //The word was invalid, so let us know and stop doing stuff
                            Toast.makeText(currentActivity.getApplicationContext(),
                                    "Sorry, the word you made is not a valid word",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    this.endTurn(tilesToPlace, words);
                }
            } else if (handRect.contains((int)eventX, (int)eventY)) {
                /** We touched a hand tile **/
                //Find out which tile we touched
                tileTouched = (int) ((eventY - HAND_TOP) / HAND_CELL_SIZE); // should be 0->6
                if (tileTouched > 6) {
                    tileTouched = 6;
                }
                if (tileTouched < 0) {
                    tileTouched = 0;
                }

                ArrayList<ScrabbleTile> ourHand = gameState.getPlayerHand(this.playerID);
                //Unmark every tile that might be marked
                for (ScrabbleTile tile : ourHand) {
                    if (tile.isReadyToMove()) {
                        tile.setReadyToMove(false);
                    }
                }

                //Tell the tile that it's moving
                ourHand.get(tileTouched).setReadyToMove(!ourHand.get(tileTouched).isReadyToMove());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                gameState.setPlayerHand(this.playerID, ourHand);
            } else if (boardRect.contains((int)eventX, (int)eventY)) {
                /** We touched a spot on the board **/
                //Find the (x,y) on the board that we touched, each tile is ~80px wide
                int tileX = (int) ((eventX - HAND_LEFT - 50) / CELL_SIZE);
                int tileY = (int) ((eventY - 10) / CELL_SIZE);
                //Log.i("Location: ", "(" + tileX + ", " + tileY + ")");

                //Set the board x,y of the tile selected if one is selected
                Boolean isTileReadyToMove = false;
                ArrayList<ScrabbleTile> ourHand = gameState.getPlayerHand(this.playerID);
                for (ScrabbleTile tile : ourHand) {
                    if (tile.isReadyToMove()) {
                        isTileReadyToMove = true;
                        break;
                    }
                }

                if (isTileReadyToMove) {
                    if (tileTouched != -1) { //we've marked a tile ready to move
                        //Put the tile on the board and update it's location

                        //Add the tile to the board if there isn't a tile with that location already
                        //on the board
                        synchronized (this) {
                            boolean canPlace = true;
                            for (ScrabbleTile boardTile : gameState.getBoardTiles()) {
                                if (tileX == boardTile.getXLocation() && tileY == boardTile.getYLocation()) {
                                    canPlace = false;
                                    break;
                                }
                            }
                            if (canPlace) {
                                ourHand.get(tileTouched).setLocation(tileX, tileY);
                                ourHand.get(tileTouched).setOnBoard(true);
                                gameState.addBoardTile(ourHand.get(tileTouched));
                            }

                        }
                    }
                }
            } else if (redoRect.contains((int)eventX, (int)eventY)) {
                this.resetHand();
            }

    }

    /**
     * Helper method that resets the user's hand by removeing all the tiles from the board
     * and unmarking every tile
     */
    private void resetHand() {
        /** We touched the redo button **/
        //Push all of the tiles on the board back into the player's hand
        ArrayList<ScrabbleTile> ourHand = gameState.getPlayerHand(this.playerID);
        for (ScrabbleTile tile : ourHand){
            tile.setOnBoard(false);
            tile.setReadyToMove(false);
            gameState.getBoardTiles().remove(tile);
        }
    }

    /**
     * checkWord            Checks if a given word is a com.adruzh.scrabble word.
     * @param word
     * @return True if word is a com.adruzh.scrabble word. False if not.
     */
    public boolean checkWord(String word) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("scrabbleDict.txt")));

            //Read each line, if it matches our word then we're done
            String line;
            while ((line = br.readLine()) != null) {
                //If this is our word, we're done
                if(word.equalsIgnoreCase(line)) {
                    br.close();
                    return true;
                }
            }

            //Close our reader, we're done with it
            br.close();
        }
        catch (FileNotFoundException fnfe) {
            System.out.print("Dictionary not found");
        }
        catch (IOException e) {
            System.out.print("IO exception");
        }


        /*//allows dictionary to be searched
        Scanner scanner = new Scanner("scrabbleDict.txt");

        //go through the com.adruzh.scrabble dictionary
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
            //check if word is a com.adruzh.scrabble word
            if(word.equalsIgnoreCase(scanner.nextLine())) {
                return true; //if word is in com.adruzh.scrabble dictionary
            }
        }

        scanner.close(); //close scanner*/

        //The word is not in the dictionary at this point, so return false
        return false;
    }

    /**
     * Helper method to determine which tiles the player is wanting to place
     * @return
     *      The tiles the player wants to replace
     */
    public ArrayList<ScrabbleTile> getTilesToPlace(){
        wordToPlace = "";
        //Get my hand
        ArrayList<ScrabbleTile> myHand = gameState.getPlayerHand(this.playerID);
        ArrayList<ScrabbleTile> tilesToPlace = new ArrayList<>();
        for (ScrabbleTile handTile : myHand){
            if (handTile.isOnBoard()){
                tilesToPlace.add(handTile);
            }
        }

        return tilesToPlace;
    }


    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }


}
