package com.example.administrator.scrabble.Scrabble;

import android.app.Application;
import android.content.Context;

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
public class ScrabbleMainActivity extends GameMainActivity {

    private final int PORT_NUM = 1337;

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
        availTypes.add(new GamePlayerType("Computer (Engineer)") {
            public GamePlayer createPlayer(String name) {
                return new ScrabbleComputerPlayerEasy(name);
            }
        });

        //The hard AI
        availTypes.add(new GamePlayerType("Computer (English Major)") {
            public GamePlayer createPlayer(String name) {
                return new ScrabbleComputerPlayerHard(name);
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
}
