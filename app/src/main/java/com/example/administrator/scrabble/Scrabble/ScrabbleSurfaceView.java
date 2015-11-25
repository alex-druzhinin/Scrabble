package com.example.administrator.scrabble.Scrabble;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.example.administrator.scrabble.animation.AnimationSurface;

import java.util.jar.Attributes;

/**
 * @author Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * @version 11/13/2015
 *
 * The surface view that will be drawn on during the runtime of the game.
 */
public class ScrabbleSurfaceView extends AnimationSurface {

    /**
     * Default Ctor, just sets up the SurfaceView
     * @param context
     *      The context on which we are drawing
     */
    public ScrabbleSurfaceView(Context context) {
        super(context);
    }

    public ScrabbleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
