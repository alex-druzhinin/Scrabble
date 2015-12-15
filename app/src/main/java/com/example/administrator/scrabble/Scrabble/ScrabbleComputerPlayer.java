package com.example.administrator.scrabble.Scrabble;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.administrator.scrabble.game.GameComputerPlayer;
import com.example.administrator.scrabble.game.GameMainActivity;
import com.example.administrator.scrabble.game.actionMsg.GameAction;
import com.example.administrator.scrabble.game.infoMsg.GameInfo;
import com.example.administrator.scrabble.game.infoMsg.NotYourTurnInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * Represents the AI for our game. This player may be either hard or easy.
 */
public class ScrabbleComputerPlayer extends GameComputerPlayer {

    // ----- Instance Variables ----- //

    //The current board, will be used to scan for possible words
    public ScrabbleBoard board;
    protected ArrayList<ScrabbleTile> boardTiles;
    protected ArrayList<ScrabbleTile> wordTiles;
    protected ScrabbleState currentState;
    protected String word;
    protected boolean[] surrounding;
    protected int maxAbove;
    protected int maxBelow;
    protected int maxLeft;
    protected int maxRight;
    protected ScrabbleTile target;
    protected int availableSpaces;
    protected int targetIndex;
    protected int wordLength;
    protected boolean hard;
    protected int thresholdLength = 4;
    protected int playerID;
    protected boolean horizontalWord;
    protected Context context;
    protected Activity currentActivity;

    //The minimum amount of time (ms) the AI waits to perform an action
    final int MIN_TIME = 1000;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public ScrabbleComputerPlayer(String name, boolean hard, int playerID, Activity activity) {
        super(name);
        surrounding = new boolean[8];
        this.hard = hard;
        Arrays.fill(surrounding, false); //initialize as false
        this.playerID = playerID;
        wordTiles = new ArrayList<>();
        boardTiles = new ArrayList<>();
        horizontalWord = false;
        currentActivity = activity;

        //assume can't place tiles in any direction
        maxAbove = 0;
        maxBelow = 0;
        maxLeft = 0;
        maxRight = 0;
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if( !(info instanceof ScrabbleState)) return; //return if not scrabblestate

        currentState = (ScrabbleState) info; //cast to scrabblestate

        if(currentState.getCurrentPlayer() != 1) return; //return if not computer's turn

        //get computer's hand
        boardTiles = currentState.getBoardTiles();

        findPlace(); //find place for word

        updateBoardTiles(); //updates tiles on board

        //set computer player's score
        currentState.setPlayerScore(currentState.tallyWordScore(wordTiles)+target.getValue());

        sleep(MIN_TIME); //delay for minimum time so changes do not look instantaneous to player

        game.sendAction(new EndTurnAction(this, wordTiles)); //end turn
    }

    private void updateBoardTiles() {

        //add word tiles to the board
        for(ScrabbleTile tile: wordTiles) {
            boardTiles.add(tile);
        }

        //update board tiles
        currentState.setBoardTiles(boardTiles);
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * checkSurroundingTarget           Checks all spaces around the target tile to see if there
     *                                  are tiles there.
     *
     */
    protected void checkSurroundingTarget() {
        int index = 0; //counters for surrounding indexes

        //check all tiles around the target
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i == 0 && j == 0) { continue; } //do not need to check if target is there

                //assign true if there is a tile there and false if there is not
                surrounding[index] = currentState.isTileThere(target.getXLocation()+i, target.getYLocation()+j);
                ++index;
            }
        }
    }

    protected boolean getWord(int targetIndex, int length) {
        ArrayList<ScrabbleTile> bagTiles = currentState.getBagTiles(); //get tiles in bag
        boolean isValid = true; //tells is word found is valid word
        context = currentActivity.getApplicationContext();
        //length = 4;
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("scrabbleDict.txt")));
            String line;

            while ((line = br.readLine())!= null) {
                if(line.length() == length && line.charAt(targetIndex) == target.getLetter()) {
                    boolean[] letterCheck = new boolean[line.length()]; //check which letters are in bag

                    Arrays.fill(letterCheck, false); //assume none of tiles with matching letters are in bag

                    //check if tiles with matching letters are in bag
                    for(int i = 0; i < line.length(); i++) {
                        for (ScrabbleTile bagTile : bagTiles) {
                            if(bagTile.getLetter() == line.charAt(i)) {
                                letterCheck[i] = true; //tile with matching letter exists
                                bagTiles.remove(bagTile); //remove bagTile so do not double count
                                break;
                            }
                        }
                    }

                    for(int i = 0; i < letterCheck.length; i++) {
                        if(letterCheck[i] == false) { isValid = false; }
                    }

                    if(isValid) { //if all the letters in the word are in the bag
                        word = line; //set the word to the line that was found
                        br.close(); //close the buffered reader
                        return true; //return that the word is valid
                    }
                }
            }

            br.close(); //close buffered reader
        }
        catch (FileNotFoundException fnfe) {
            System.out.print("Dictionary not found");
        }
        catch (IOException e) {
            System.out.print("IO exception");
        }

        return false; //word could not be found with given parameters
    }

    protected void findPlace() {
        //for loop check board tiles
        for(ScrabbleTile boardTile: boardTiles) {
            target = boardTile; //make target the boardTile
            checkSurroundingTarget(); //checks for tiles surrounging target tile

            //ifs to see if can place word: use max methods
            //add l&r, t&b

            //if there is at least one tile on either diagonal,
            //current tile cannot be used as anchor
            if ((surrounding[0] && surrounding[7]) || (surrounding[2] && surrounding[5])) {
                continue; //move to next board tile to see if it can be used
            }
            //if there is at least one tile adjacent in hor direction
            // AND one tile adjacent in vert direction
            //this tile cannot be used as anchor
            if ((surrounding[3] || surrounding[4]) && (surrounding[1] || surrounding[6])) {
                continue; //move to next board tile to see if it can be used
            }

            //determine maxes in either vertical direction
            if (!surrounding[0] && !surrounding[3] && !surrounding[5] &&
                    !surrounding[2] && !surrounding[4] && !surrounding[7]) {
                this.maxLettersAbove(); //find the max number of valid spaces above target
                this.maxLettersBelow(); //find the max number of valid spaces below target
            }

            if (maxAbove > 7) { maxAbove = 7; }

            availableSpaces = maxAbove + maxBelow; //get total spaces for vertical word

            getWordLength(); //get length of word

            if(wordLength > 0) {
                //determine targetIndex of vertical word:
                if (maxAbove == 0 && maxBelow != 0) {
                    targetIndex = 0; //target board tile is first in word
                } else if (maxBelow == 0 && maxAbove != 0) {
                    targetIndex = wordLength - 1; //target board tile is last in word
                } else if ((maxAbove != 0) && (maxBelow != 0)) {
                    targetIndex = 1; //use all the valid spaces above the target in the word
                }

                if (getWord(targetIndex, wordLength)) {
                    horizontalWord = false;
                    break; //have word so do not need to keep looking
                }
            }

            //determine maxes in either horizontal direction
            if (!surrounding[0] && !surrounding[1] && !surrounding[2] &&
                    !surrounding[5] && !surrounding[6] && !surrounding[7]) {
                maxLettersLeft();
                maxLettersRight();
            }

            if(maxLeft > thresholdLength) maxLeft = thresholdLength; //limit maximum spaces to left

            availableSpaces = maxLeft + maxRight; //get total spaces available for a word

            getWordLength(); //get length of word

            if(wordLength > 0) {
                //determine targetIndex:
                if (maxLeft == 0) {
                    targetIndex = 0; //make target board tile the first letter
                } else if (maxRight == 0) {
                    targetIndex = wordLength - 1; //make target board tile the last letter
                } else if (maxLeft != 0 && maxRight != 0)
                    targetIndex = 1; //use all open spaces to left of target on board up to max value

                if (getWord(targetIndex, wordLength)) {
                    horizontalWord = true;
                    break;
                }
            }
        }

        placeWord(targetIndex, word);
    }

    protected void getWordLength() {
        Random r = new Random();

        //determine length:
        if (hard == true) {
            if (availableSpaces >= thresholdLength) {
                //random word length greater than 3
                wordLength = r.nextInt(availableSpaces - thresholdLength) + thresholdLength;
            }
        }
        else {
            if (availableSpaces > thresholdLength) availableSpaces = thresholdLength;
            //go to checking horizontally;
            if (availableSpaces > 1) {
                //random word length between or including 2 and 4
                wordLength = r.nextInt(availableSpaces - 2) + 2;
            }
        }
    }

    protected void placeWord(int targetIndex, String word) {
        ArrayList<ScrabbleTile> bagTiles = currentState.getBagTiles(); //get bag tiles

        //remove tiles in word from bag
        for(int i = 0; i < wordLength; i++) {
            //search through all tiles in the bag
            for (ScrabbleTile bagTile : bagTiles) {

                if(i == targetIndex) { break; }

                if(bagTile.getLetter() == word.charAt(i)) {
                    if(horizontalWord) {
                        bagTile.setLocation(target.getXLocation()-targetIndex+i, target.getYLocation());
                    }
                    else {
                        bagTile.setLocation(target.getXLocation(), target.getYLocation()-targetIndex+i);
                    }

                    bagTiles.remove(bagTile);
                    bagTile.setOnBoard(true);
                    wordTiles.add(bagTile);
                    break; //break out of inner loop
                }
            }
        }

        currentState.setBagTiles(bagTiles); //update bag tiles
    }

    /**
     * maxLettersAbove                  Checks how many spaces above the target board tile
     *                                  can be used for a word.
     */
    protected void maxLettersAbove() {
        boolean noSpace = false; //empty spaces to move to
        boolean left = false; //space on top left diagonal to potential space is open
        boolean right = false; //space on top right diagonal to potential space is open
        boolean up = false; //space above potential space
        int x = target.getXLocation(); //target x position
        int y = target.getYLocation(); //target y position
        maxAbove = 0; //init max spaces can move above as 0

        for(int i = 1; !noSpace && i < 8; i++) {
            left = currentState.isTileThere(x-1, y-i-1); //check top left diagonal
            right = currentState.isTileThere(x+1, y-i-1); //check top right diagonal
            up = currentState.isTileThere(x, y-i-1); //check space above potential space

            if(left == false && right == false && up == false) {
                ++maxAbove; //increment number of tiles that can go above the target
            }
            else if(!left && !right && up) {
                ++maxAbove;
                break;
            }
            else { noSpace = true; } //if there is a tile
        }
    }

    /**
     * maxLettersBelow                  Checks how many spaces below the target board tile
     *                                  can be used for a word.
     */
    protected void maxLettersBelow() {
        boolean noSpace = false; //empty spaces to move to
        boolean left = false; //space on bottom left diagonal to potential space is open
        boolean right = false; //space on bottom right diagonal to potential space is open
        boolean down = false; //space below potential space
        int x = target.getXLocation(); //target x position
        int y = target.getYLocation();  //target y position
        maxBelow = 0; //init max spaces can move below as 0

        for(int i = 1; !noSpace && i < 8; i++) {
            left = currentState.isTileThere(x-1, y+i+1); //check bottom left diagonal
            right = currentState.isTileThere(x+1, y+i+1); //check bottom right diagonal
            down = currentState.isTileThere(x, y+i+1); //check space below potential space

            if(left == false && right == false && down == false) {
                ++maxBelow; //increment number of tiles that can go below the target
            }
            else if(!left && !right && down) {
                ++maxBelow;
                break;
            }
            else { noSpace = true; } //if there is a tile
        }
    }

    /**
     * maxLettersLeft                   Checks how many spaces to the left of the target board tile
     *                                  can be used for a word.
     */
    protected void maxLettersLeft() {
        boolean noSpace = false; //empty spaces to move to
        boolean leftTop = false; //space on top left diagonal to potential space is open
        boolean leftBottom = false; //space on bottom left diagonal to potential space is open
        boolean left = false; //space to left of potential space
        int x = target.getXLocation(); //target x position
        int y = target.getYLocation(); //target y position
        maxLeft = 0; //init max spaces can move to left as 0

        for(int i = 1; !noSpace && i < 8; i++) {
            leftTop = currentState.isTileThere(x-i-1, y-1); //check top left diagonal
            leftBottom = currentState.isTileThere(x-i-1, y+1); //check bottom left diagonal
            left = currentState.isTileThere(x-i-1, y); //check spaces to left of potential space

            //check if valid move
            if(left == false && leftBottom == false && leftTop == false) {
                ++maxLeft; //increment number of tiles that can go left of the target
            }
            else if(!leftBottom && !leftTop && left) {
                ++maxLeft;
                break;
            }
            else { noSpace = true; } //if there is a tile
        }
    }

    /**
     * maxLettersRight              Checks how many spaces to the right of the target board tile
     *                              can be used for a word.
     */
    protected void maxLettersRight() {
        boolean noSpace = false; //empty spaces to move to
        boolean rightTop = false; //space on top right diagonal to potential space is open
        boolean rightBottom = false; //space on bottom right diagonal to potential space is open
        boolean right = false; //space to right of potential space
        int x = target.getXLocation(); //target x position
        int y = target.getYLocation(); //target y position
        maxRight = 0; //init max spaces can move to right as 0

        //check for valid tile placement to right until a right movement is not valid
        for(int i = 1; !noSpace && i < 8; i++) {
            rightTop = currentState.isTileThere(x+i+1, y-1); //check top right diagonal
            rightBottom = currentState.isTileThere(x+i+1, y+1); //check bottom right diagonal
            right = currentState.isTileThere(x+i+1, y); //check spaces to right of potential space

            //check if valid tile placement spot
            if(right == false && rightBottom == false && rightTop == false) {
                ++maxRight; //increment number of tiles that can go above the target
            }
            else if(!rightBottom && !rightTop && right) {
                ++maxRight;
                break;
            }
            else { noSpace = true; } //if there is a tile
        }
    }


    // ----- Game Actions ----- //

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
        return new EndTurnAction(this, wordTiles);
    }
}
