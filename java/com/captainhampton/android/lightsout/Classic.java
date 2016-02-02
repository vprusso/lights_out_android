package com.captainhampton.android.lightsout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Classic extends AppCompatActivity implements View.OnClickListener {

    public Button bHome, bHint, bReset;
    public TextView tvNumMoves, tvLevelTime;

    private static final int[][] LEVELS = {
            {7,11,12,13,17},
            {5,9,10,11,13,14,15,19},
            {3,4,7,9,11,12,13,15,17,20,21},
            {0,1,3,4,5,9,15,19,20,21,23,24},
            {0,1,3,4,5,7,9,11,12,13,15,17,19,20,21,23,24},
            {10,12,14},
            {0,2,4,5,7,9,15,17,19,20,22,24}
    };

    private static final int[] BUTTON_IDS = {
      R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
      R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
      R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14,
      R.id.button15, R.id.button16, R.id.button17, R.id.button18, R.id.button19,
      R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24,
    };

    private static final int row_col_length = 5;

    private Button[] buttons = new Button[BUTTON_IDS.length];
    private boolean[] button_states = new boolean[BUTTON_IDS.length];

    private int level_num = 0;
    private int num_moves;
    private long level_time; // System.nanoTime()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic);
        setupVariables();
    }

    private void setLevel(int lvl) {
        level_num = lvl;
    }

    private void resetTimer() {
        level_time = 0;
        tvLevelTime.setText(Long.toString(level_time));
    }

    private void resetNumMoves() {
        num_moves = 0;
        tvNumMoves.setText(Integer.toString(num_moves));
    }

    private void activateButton(Button b, int button_id) {
        button_states[button_id] = Boolean.TRUE;
        buttons[button_id].setBackgroundColor(Color.RED);
    }

    private void deactivateButton(Button b, int button_id) {
        button_states[button_id] = Boolean.FALSE;
        buttons[button_id].setBackgroundColor(Color.WHITE);
    }

    private boolean isOffRightScreen(int button_id) {
        return ( (button_id + 1) % row_col_length == 0 );
    }

    private boolean isOffLeftScreen(int button_id) {
        return ( button_id % row_col_length == 0 );
    }

    private boolean isButtonActive(int button_id) {
        return ( button_states[button_id] == Boolean.TRUE );
    }

    private void setButtonRadius(Button[] buttons, int button_id) {

        int top = button_id - row_col_length;
        int bottom = button_id + row_col_length;
        int left = button_id - 1;
        int right = button_id + 1;

        if ( isButtonActive( button_id )  )
            deactivateButton( buttons[button_id], button_id );
        else
            activateButton(buttons[button_id], button_id);

        if (top >= 0 && top < BUTTON_IDS.length) {
            if ( isButtonActive( top ) )
                deactivateButton( buttons[top], top );
            else
                activateButton( buttons[top], top );
        }

        if (bottom >= 0 && bottom < BUTTON_IDS.length) {
            if ( isButtonActive( bottom ) )
                deactivateButton( buttons[bottom], bottom );
            else
                activateButton( buttons[bottom], bottom );
        }

        if (left >= 0 && left < BUTTON_IDS.length && !isOffLeftScreen(button_id) ) {
            if ( isButtonActive( left ) )
                deactivateButton( buttons[left], left );
            else
                activateButton( buttons[left], left );
        }

        if (right >= 0 && right < BUTTON_IDS.length && !isOffRightScreen(button_id) ) {
            if ( isButtonActive( right ) )
                    deactivateButton( buttons[right], right );
            else
                activateButton( buttons[right], right );
        }
    }

    private void initBoard() {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            buttons[i].setBackgroundColor(Color.WHITE);
            button_states[i] = Boolean.FALSE;
        }
        resetTimer();
        resetNumMoves();
    }

    private void setupBoard() {

        initBoard();

        for (int i = 0; i < LEVELS[level_num].length; i++ ) {
            buttons[ LEVELS[level_num][i] ].setBackgroundColor(Color.RED);
            button_states[ LEVELS[level_num][i] ] = Boolean.TRUE;
        }
    }

    private boolean checkVictory(Button[] buttons) {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            if (isButtonActive(i))
                return false;
        }
        return true;
    }

    private void setupVariables() {

        bHome = (Button)findViewById(R.id.bHome);
        bHome.setOnClickListener(this);

        bReset = (Button)findViewById(R.id.bReset);
        bReset.setOnClickListener(this);

        tvNumMoves = (TextView)findViewById(R.id.tvNumMoves);
        tvLevelTime = (TextView)findViewById(R.id.tvLevelTime);

        for (int i = 0; i < BUTTON_IDS.length; i++) {
            final int button_id = i;
            buttons[button_id] = (Button)findViewById(BUTTON_IDS[button_id]);
            buttons[button_id].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (buttons[button_id].isPressed()) {
                        setButtonRadius(buttons, button_id);
                        num_moves++;
                        // Put in timer stuff here
                        tvNumMoves.setText(Integer.toString(num_moves));
                        tvLevelTime.setText(Long.toString(level_time));
                    }

                    if ( checkVictory(buttons) ) {
                        // TODO : victory dance
                        level_num++;
                        if (level_num <= LEVELS.length) {
                            setLevel(level_num);
                            setupBoard();
                        } else {
                            // TODO
                        }
                    } // end checkVictory

                }
            });
        }

        setupBoard();

    }

    @Override
    public void onClick(View v) {

        if (bReset.isPressed()) {
            setupBoard();
        }

    }

}
