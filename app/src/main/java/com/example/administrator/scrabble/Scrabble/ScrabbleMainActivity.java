package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GameMainActivity;
import com.example.administrator.scrabble.game.LocalGame;
import com.example.administrator.scrabble.game.config.GameConfig;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * The main activity that sets up the Scrabble game with all the players and connections
 * neccessary
 */
public class ScrabbleMainActivity extends GameMainActivity{

    private final int PORT_NUM = 1337;

    @Override
    public GameConfig createDefaultConfig() {
        return null;
    }

    @Override
    public LocalGame createLocalGame() {
        return null;
    }
}
