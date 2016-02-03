package com.captainhampton.android.lightsout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];

    public static final int[][] LEVELS = {
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
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                button.setText("" + col + "," + row);
                // make text not clip on small buttons
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int col, int row) {

        //Classic classic = new Classic(0);
        //Intent intent = new Intent(this, Classic.class);
        //startActivity(intent);


        Toast.makeText(this, "Button clicked: " + col + "," + row, Toast.LENGTH_SHORT).show();
        Button button = buttons[row][col];

        // Lock button sizes.
        lockButtonSizes();

        button.setBackgroundResource(R.drawable.lock);

        // Only can be used in Jellybean or above.
        // Scale image to button:
//        int newWidth = button.getWidth();
 //       int newHeight = button.getHeight();
  //      Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock);
   //     Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
    //    Resources resource = getResources();
     //   button.setBackground(new BitmapDrawable(resource, scaledBitmap));

    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    @Override
    public void onClick(View v) {
    }



}
