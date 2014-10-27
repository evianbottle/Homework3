package edu.uta.mavs.jones.evan.explicit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Evan on 10/17/2014.
 */
public class SongTime extends Activity {

    private MediaPlayer rolled;
    private SeekBar seekBar;
    private Handler songHandler;
    private int counter;
    private TextView rolledText;

    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_song_time);

        rolled = MediaPlayer.create(SongTime.this, R.raw.song);
        seekBar = (SeekBar)findViewById(R.id.musicSeek);
        songHandler = new Handler();


        seekBar.setMax(rolled.getDuration());


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (rolled != null && fromUser) {
                    rolled.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        play();

        loadSharedPrefs();
        counter++;
        saveSharedPrefs();
        beenRolled();

    }

    public void play(){


        rolled.start();

        new Thread(new Runnable(){
            @Override
            public void run(){
                int pauseTime = 1000;
                try {
                    while (rolled.getCurrentPosition() < rolled.getDuration()){
                        Thread.sleep(pauseTime);
                    seekBar.setProgress(rolled.getCurrentPosition());
                }
                }catch(Exception e){
                    return;
                }


            }
        }).start();

    }




    public void loadSharedPrefs(){

        SharedPreferences shared = getPreferences(MODE_PRIVATE);
        counter = shared.getInt("Counter", 0);
    }

    public void saveSharedPrefs(){
        SharedPreferences shared = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("Counter", counter);
        editor.commit();
    }

    public void beenRolled() {

        rolledText = (TextView) findViewById(R.id.rolledText);

        if (counter < 10) {

            rolledText.setText("You've been Rick Rolled " + counter + " times");
        }else if(counter >= 10){
            rolledText.setText("You've been Rick Rolled " + counter + " times\n" +
                    "Wait. Do you like this song?");

        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        rolled.release();
        finish();
    }

}
