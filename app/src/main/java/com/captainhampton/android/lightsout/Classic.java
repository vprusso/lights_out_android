package com.captainhampton.android.lightsout;

import android.graphics.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ejml.simple.SimpleMatrix;


public class Classic extends AppCompatActivity implements View.OnClickListener {

    public Button bHome, bHint, bReset;
    public TextView tvNumMoves, tvLevelTime;

    private static final int[][][] LEVELS = {
            { {1,2}, {2,1}, {2,2}, {2,3}, {3,2} },
            { {1,0}, {1,4}, {2,0}, {2,1}, {2,3}, {2,4}, {3,0}, {3,4} },
            { {0,3}, {0,4}, {1,2}, {1,4}, {2,1}, {2,2}, {2,3}, {3,0}, {3,2}, {4,0}, {4,1}  },
            { {0,0}, {0,1}, {0,3}, {0,4}, {1,0}, {1,4}, {3,0}, {3,4}, {4,0}, {4,1}, {4,3}, {4,4} },
            { {0,0}, {0,1}, {0,3}, {0,4}, {1,0}, {1,2}, {1,4}, {2,1}, {2,2}, {2,3}, {3,0}, {3,2}, {3,4}, {4,0}, {4,1}, {4,3}, {4,4} },
            { {2,0}, {2,2}, {2,4} },
            { {0,0}, {0,2}, {0,4}, {1,0}, {1,2}, {1,4}, {3,0}, {3,2}, {3,4}, {4,0}, {4,2}, {4,4} },
            { {0,1}, {0,3}, {1,0}, {1,1}, {1,3}, {1,4}, {2,0}, {2,1}, {2,3}, {2,4}, {3,0}, {3,1}, {3,3}, {3,4}, {4,1}, {4,3} },
    };

    private static final int[][] LIGHT_IDS = {
            { R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4 },
            { R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 },
            { R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14 },
            { R.id.button15, R.id.button16, R.id.button17, R.id.button18, R.id.button19 },
            { R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24 },
    };

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 5;

    private Button[][] lights = new Button[NUM_ROWS][NUM_COLS];
    private boolean[][] light_states = new boolean[NUM_ROWS][NUM_COLS];

    private int level_num = 0;
    private int num_moves;
    private long level_time; // System.nanoTime()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic);
        setupVariables();
    }

    private boolean isConfigSolvable5x5() {
        // TODO
        return true;
    }

    private SimpleMatrix g2rref(SimpleMatrix A) {

        // TODO : In progress - function that computes the reduced row echeleon form of a matrix
        //        over the field GF(2).
        int i = 0;
        int j = 0;

        //double c = Double.longBitsToDouble(
        //        Double.doubleToRawLongBits(a) ^ Double.doubleToRawLongBits(b));

        while ( (i < NUM_ROWS) && (j < NUM_COLS) ) {
            SimpleMatrix C = A.extractMatrix(i,A.numRows(),j,j+1);
            // Find value and index of largest element in the remainder of column j.
            int k = 0;

            for (int n = 0; n < C.numRows(); n++) {
                for (int m = 0; m < C.numCols(); m++) {
                    //Log.i("MYTAG",String.valueOf(C.get(n,m)));
                    if ( C.get(n,m) == 1 ) {
                        k = n;
                        break;
                    }
                }
            }
            k = k + i - 1;
            Log.i("MYTAG",String.valueOf(k));

            i++;
            j++;
        }

        return A;
    }

    private boolean[][] calculateWinningConfig() {
        // Winning configuration boils down to Ax = b
        // Refer to https://www.math.ksu.edu/math551/math551a.f06/lights_out.pdf
        boolean[][] solution = new boolean[NUM_ROWS][NUM_COLS];


        SimpleMatrix A = new SimpleMatrix(new double[][]
                {
                        {1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
                });

        SimpleMatrix b;
        b = calculateLightVector();
        A = A.combine(0, A.numCols(), b.transpose());
        A = g2rref(A);




//
//        b = calculateLightVector();
//
//        Log.i("MYTAG", String.valueOf(b));
//        Log.i("MYTAG", String.valueOf(A));
//        SimpleMatrix x = A.solve(b.transpose());
//
//        Log.i("MYTAG", String.valueOf(x));
//
//        int x_size = 0;
//        for (int i = 0; i < NUM_ROWS; i++) {
//            for (int j = 0; j < NUM_COLS; j++) {
//                Log.i("MYTAG", String.valueOf(x.get(x_size)));
//                if ( x.get(x_size) == 1.0 )
//                    solution[i][j] = Boolean.TRUE;
//                else
//                    solution[i][j] = Boolean.FALSE;
//                x_size++;
//            }
//        }

        return solution;
    }

    private SimpleMatrix calculateLightVector() {
        SimpleMatrix vec = new SimpleMatrix(1,NUM_ROWS*NUM_COLS);

        int vec_size = 0;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (light_states[i][j] == Boolean.TRUE)
                    vec.set(0,vec_size,1);
                else
                    vec.set(0,vec_size,0);

                vec_size++;
            }
        }

        return vec;
    }

    private void clearSolution() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                lights[i][j].setTextColor(Color.BLACK);
            }
        }
    }

    private void showSolution() {
        calculateWinningConfig();
//        boolean[][] solution = calculateWinningConfig();
//
//        for (int i = 0; i < NUM_ROWS; i++) {
//            for (int j = 0; j < NUM_COLS; j++) {
//                Log.i("MYTAG", String.valueOf(solution[i][j]));
//                if (solution[i][j] == Boolean.TRUE)
//                    lights[i][j].setTextColor(Color.YELLOW);
//            }
//        }

    }

    private void setLevel(int lvl) {
        level_num = lvl;
    }

    private void resetTimer() {
        level_time = 0;
        tvLevelTime.setText(String.format("%d", level_time));
    }

    private void resetNumMoves() {
        num_moves = 0;
        tvNumMoves.setText(String.format("%d", num_moves));
    }

    private void activateButton(int x, int y) {
        light_states[x][y] = Boolean.TRUE;
        lights[x][y].setBackgroundColor(Color.RED);
    }

    private void deactivateButton(int x, int y) {
        light_states[x][y] = Boolean.FALSE;
        lights[x][y].setBackgroundColor(Color.WHITE);
    }

    private void flipLight(int x, int y) {
        if (isLightActive(x, y))
            deactivateButton(x,y);
        else
            activateButton(x, y);
    }

    private boolean isLightOutOfBounds(int x, int y) {
        return ( x >= NUM_ROWS || x < 0 || y >= NUM_COLS || y < 0 );
    }

    private boolean isLightActive(int x, int y) {
        return ( light_states[x][y] == Boolean.TRUE );
    }

    private void pressedLights(int x, int y) {
        int top = x - 1;
        int bot = x + 1;
        int left = y - 1;
        int right = y + 1;

        flipLight(x,y);

        if (!isLightOutOfBounds(top, y))
            flipLight(top, y);


        if (!isLightOutOfBounds(bot, y))
            flipLight(bot, y);


        if (!isLightOutOfBounds(x, left))
            flipLight(x, left);


        if (!isLightOutOfBounds(x, right))
            flipLight(x, right);

    }

    private void clearBoard() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                deactivateButton(i, j);
            }
        }
        resetTimer();
        resetNumMoves();
    }

    private void setupBoard() {

        clearBoard();

        for (int i = 0; i < LEVELS[level_num].length; i++) {
            int x = LEVELS[level_num][i][0];
            int y = LEVELS[level_num][i][1];

            activateButton(x, y);
        }
    }

    private boolean checkVictory() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (isLightActive(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setupVariables() {

        bHome = (Button)findViewById(R.id.bHome);
        bHome.setOnClickListener(this);

        bReset = (Button)findViewById(R.id.bReset);
        bReset.setOnClickListener(this);

        bHint = (Button)findViewById(R.id.bHint);
        bHint.setOnClickListener(this);

        tvNumMoves = (TextView) findViewById(R.id.tvNumMoves);
        tvLevelTime = (TextView) findViewById(R.id.tvLevelTime);

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {

                final int x = i;
                final int y = j;
                lights[x][y] = (Button)findViewById(LIGHT_IDS[x][y]);
                lights[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (lights[x][y].isPressed()) {
                            pressedLights(x, y);
                            clearSolution();
                            num_moves++;
                            tvNumMoves.setText(String.format("%d", num_moves));
                            tvLevelTime.setText(String.format("%d", level_time));
                        }

                        if ( checkVictory() ) {
                            // TODO : victory dance
                            level_num++;
                            if (level_num <= LEVELS.length) {
                                setLevel(level_num);
                                setupBoard();
                            } else {
                                // TODO
                            }
                        }

                    }
                });

            }
        }

        setupBoard();

    }

    @Override
    public void onClick(View v) {

        if (bReset.isPressed()) {
            setupBoard();
        }

        if (bHint.isPressed()) {
            showSolution();
        }

    }

}