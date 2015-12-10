package com.example.administrator.scrabble.Scrabble;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.scrabble.R;
import com.example.administrator.scrabble.game.GameMainActivity;
import com.example.administrator.scrabble.game.GamePlayer;
import com.example.administrator.scrabble.game.LocalGame;
import com.example.administrator.scrabble.game.config.GameConfig;
import com.example.administrator.scrabble.game.config.GamePlayerType;

import java.util.ArrayList;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * The main activity that sets up the Scrabble game with all the players and connections
 * neccessary
 */
@TargetApi(21)
public class ScrabbleMainActivity extends GameMainActivity{

    private final int PORT_NUM = 1337;
    private Activity activity = this;

    @Override
    public GameConfig createDefaultConfig() {

        ArrayList<GamePlayerType> availTypes = new ArrayList<>();

        //The human player
        availTypes.add(new GamePlayerType("Human") {
            public GamePlayer createPlayer(String name) {
                return new ScrabbleHumanPlayer(name);
            }
        });

        //The easy AI
        availTypes.add(new GamePlayerType("Computer (Easy)") {
            public GamePlayer createPlayer(String name) {
                return new ScrabbleComputerPlayer(name, false, 1, activity);
            }
        });

        //The hard AI
        availTypes.add(new GamePlayerType("Computer (Hard)") {
            public GamePlayer createPlayer(String name) {
                return new ScrabbleComputerPlayer(name, true, 1, activity);
            }
        });

        //Create our default config
        GameConfig defaultConfig = new GameConfig(availTypes, 2, 2, "Scrabble", PORT_NUM);

        //Add the default players for the game
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 1);


        return defaultConfig;

    }

    @Override
    public LocalGame createLocalGame() {
        return new ScrabbleLocalGame();
    }

    public void onTileInHandTouch(View v){

        //If our color is tinted, untint it
        if (v.getBackgroundTintMode() == PorterDuff.Mode.MULTIPLY){
            v.setBackgroundTintMode(PorterDuff.Mode.SCREEN);
        }
        else{
            v.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
        }

    }

}
