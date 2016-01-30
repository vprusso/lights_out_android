package com.captainhampton.android.lightsout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends AppCompatActivity implements OnClickListener {


    Button bClassic, bDeluxe, bHowToPlay, bAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setupVariables();
    }

    private void setupVariables() {
        bClassic = (Button)findViewById(R.id.bClassic);
        bClassic.setBackgroundResource(R.drawable.default_button_selector);
        bClassic.setOnClickListener(this);

        bDeluxe = (Button)findViewById(R.id.bDeluxe);
        bDeluxe.setBackgroundResource(R.drawable.default_button_selector);
        bDeluxe.setOnClickListener(this);

        bHowToPlay = (Button)findViewById(R.id.bHowToPlay);
        bHowToPlay.setBackgroundResource(R.drawable.default_button_selector);
        bHowToPlay.setOnClickListener(this);

        bAbout = (Button)findViewById(R.id.bAbout);
        bAbout.setBackgroundResource(R.drawable.default_button_selector);
        bAbout.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {

        if (bClassic.isPressed()) {
            Intent classicIntent = new Intent("android.intent.action.CLASSIC");
            startActivity(classicIntent);
        }

        if (bDeluxe.isPressed()) {
            Intent classicIntent = new Intent("android.intent.action.DELUXE");
            startActivity(classicIntent);
        }

        if (bHowToPlay.isPressed()) {
            Intent classicIntent = new Intent("android.intent.action.HOWTOPLAY");
            startActivity(classicIntent);
        }

        if (bAbout.isPressed()) {
            Intent classicIntent = new Intent("android.intent.action.ABOUT");
            startActivity(classicIntent);
        }

    }
}
