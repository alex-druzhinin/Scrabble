package com.example.administrator.scrabble.Scrabble.Extras;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.example.administrator.scrabble.R;


/**
 * Displays the scrabble board on the android device
 *
 * Authors: Alexa Carr, Morgan Webber, Nalani (Megan Chun)
 * Last Modified: 11/8/2015
 */
public class board_surfaceview extends SurfaceView {
    public board_surfaceview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false); //let us draw please
    }

    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);

        //Create board tile objects so we can draw them
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        Paint paint = new Paint();
        Bitmap pieceGeneric = BitmapFactory.decodeResource(getResources(), R.drawable.boardtile, opts);
        Bitmap pieceDoubleWord = BitmapFactory.decodeResource(getResources(), R.drawable.boardtile_dw, opts);
        Bitmap pieceTripleWord = BitmapFactory.decodeResource(getResources(), R.drawable.boardtile_tw, opts);
        Bitmap pieceDoubleLetter = BitmapFactory.decodeResource(getResources(), R.drawable.boardtile_dl, opts);
        Bitmap pieceTripleLetter = BitmapFactory.decodeResource(getResources(), R.drawable.boardtile_tl, opts);
        Bitmap pieceStart = BitmapFactory.decodeResource(getResources(), R.drawable.boardtile_start, opts);
        paint.setColor(Color.WHITE);



        //Create our board like a 2D Array
        for (int row = 0; row < 15; row++){
            int xPos = 10 + (row * 75); //x_position for each tile

            for (int col = 0; col < 15; col++){
                int yPos = 10 + col * 75; //y_position for each tile

                //Draw each square depending on where it is on the board
                if (row == 7 && col == 7){
                    //Starting Square
                    c.drawBitmap(pieceStart, xPos, yPos, paint);
                }
                else if ((row == 0 || row == 7 || row == 14) && (col == 0 || col == 7 || col == 14)){
                    //Triple Word Squares
                    c.drawBitmap(pieceTripleWord, xPos, yPos, paint);
                }
                else if(((row == 0 || row == 14) && (col == 3 || col == 11)) || ((row == 2 || row == 12) && (col == 6 || col == 8)) || ((row == 3 || row == 11) && (col == 0 || col == 7 || col == 14)) ||
                        ((row == 6 || row == 8) && (col == 2 || col == 6 || col == 8 || col == 12)) || (row == 7 && (col == 3 || col == 11)))
                {
                    //Double Letter Squares
                    c.drawBitmap(pieceDoubleLetter, xPos, yPos, paint);
                }
                else if(((row == col || row == 14 - col) && ((row > 0 && row < 5) || row > 9 && row < 14) )){
                    //Double Word Squares
                    c.drawBitmap(pieceDoubleWord, xPos, yPos,paint);
                }
                else if(((row == 5 || row == 9) && (col == 1 || col == 5 || col == 9 || col == 13)) ||
                        (row == 1 || row == 13) && (col == 5 || col == 9)){
                    //Triple Letter Squares
                    c.drawBitmap(pieceTripleLetter, xPos, yPos, paint);
                }
                else{
                    //Generic Squares
                    c.drawBitmap(pieceGeneric, xPos, yPos ,paint);
                }
            }

        }


    }
}