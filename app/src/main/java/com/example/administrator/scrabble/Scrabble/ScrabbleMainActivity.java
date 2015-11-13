package com.example.administrator.scrabble.Scrabble;

import com.example.administrator.scrabble.game.GameMainActivity;
import com.example.administrator.scrabble.game.LocalGame;
import com.example.administrator.scrabble.game.config.GameConfig;

/**
 * Created by webber18 on 11/13/2015.
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
