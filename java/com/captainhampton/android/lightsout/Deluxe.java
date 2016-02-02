package com.captainhampton.android.lightsout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Deluxe extends AppCompatActivity implements View.OnClickListener {

    private static final int NUM_ROWS = 2;
    private static final int NUM_COLS = 3;

    private static final int[][] LEVELS = {
            {7,11,12,13,17},
            {5,9,10,11,13,14,15,19},
            {3,4,7,9,11,12,13,15,17,20,21},
            {0,1,3,4,5,9,15,19,20,21,23,24},
            {0,1,3,4,5,7,9,11,12,13,15,17,19,20,21,23,24},
            {10,12,14},
            {0,2,4,5,7,9,15,17,19,20,22,24}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deluxe);

        populateLevelButtons();
    }

    private void populateLevelButtons() {
        TableLayout table = (TableLayout)findViewById(R.id.tbLevelListing);

        for (int row = 0; row < NUM_ROWS; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++) {
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked();
                    }
                });

                tableRow.addView(button);
            }
        }
    }

    private void gridButtonClicked() {
        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
    }



}
