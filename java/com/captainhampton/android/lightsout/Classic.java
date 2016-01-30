package com.captainhampton.android.lightsout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Classic extends AppCompatActivity implements View.OnClickListener {

    private static final int[] BUTTON_IDS = {
      R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
      R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
      R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
      R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20,
      R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25,
    };

    private Button[] buttons = new Button[BUTTON_IDS.length];
    private boolean[] button_states = new boolean[BUTTON_IDS.length];

    private int row_col_length = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic);
        setupVariables();
    }

    private boolean checkVictory(Button[] buttons) {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            if (isButtonActive(i))
                return false;
        }
        return true;
    }

    private void setupBoard() {
        // Setup some game here. Will eventually read games from text file...
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            buttons[i].setBackgroundColor(Color.WHITE);
            button_states[i] = Boolean.FALSE;
        }
        buttons[0].setBackgroundColor(Color.RED);
        button_states[0] = Boolean.TRUE;

        buttons[1].setBackgroundColor(Color.RED);
        button_states[1] = Boolean.TRUE;

        buttons[5].setBackgroundColor(Color.RED);
        button_states[5] = Boolean.TRUE;

    }

    private void activateButton(Button b, int button_id) {
        button_states[button_id] = Boolean.TRUE;
        buttons[button_id].setBackgroundColor(Color.RED);
    }
    private void deactivateButton(Button b, int button_id) {
        button_states[button_id] = Boolean.FALSE;
        buttons[button_id].setBackgroundColor(Color.WHITE);
    }

    private void setButtonRadius(Button[] buttons, int button_id) {

        int top = button_id - row_col_length;
        int bottom = button_id + row_col_length;
        int left = button_id - 1;
        int right = button_id + 1;

        if ( isButtonActive( button_id ) )
            deactivateButton( buttons[button_id], button_id );
        else
            activateButton(buttons[button_id], button_id);

        if (top > 0 && top < BUTTON_IDS.length) {
            if ( isButtonActive( top ) )
                deactivateButton( buttons[top], top );
            else
                activateButton( buttons[top], top );
        }

        if (bottom > 0 && bottom < BUTTON_IDS.length) {
            if ( isButtonActive( bottom ) )
                deactivateButton( buttons[bottom], bottom );
            else
                activateButton( buttons[bottom], bottom );
        }

        if (left > 0 && left < BUTTON_IDS.length) {
            if ( isButtonActive( left ) )
                deactivateButton( buttons[left], left );
            else
                activateButton( buttons[left], left );
        }

        if (right > 0 && right < BUTTON_IDS.length) {
            if ( isButtonActive( right ) )
                    deactivateButton( buttons[right], right );
            else
                activateButton( buttons[right], right );
        }
    }

    private boolean isButtonActive(int button_id) {
        if ( button_states[button_id] == Boolean.TRUE )
            return true;
        return false;
    }

    private void setupVariables() {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            final int button_id = i;
            buttons[button_id] = (Button)findViewById(BUTTON_IDS[button_id]);
            buttons[button_id].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (buttons[button_id].isPressed()) {
                        setButtonRadius( buttons, button_id );
                    }
                    if ( checkVictory(buttons) ) {
                        // TODO : victory dance
                    }

                }
            });
        }

        setupBoard();

    }

    public void onClick(View v) {



    }

}
