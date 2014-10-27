package edu.uta.mavs.jones.evan.explicit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;


public class MainActivity extends Activity {


    Calendar c;
    final Context context = this;
    final static int DATE_PICKER_ID = 1111;

    private int year;
    private int month;
    private int day;
    private boolean ofAge = false;

    DatePicker datePicker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    datePicker = (DatePicker)findViewById(R.id.agePicker);

        c = Calendar.getInstance();
    }


    public void onClick(View v) {

        ofAge = checkAge();

        if (ofAge) {
            Intent i = new Intent(this, SongTime.class);
            startActivity(i);
        }else if (!ofAge){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sorry")
                    .setMessage("You are not old enough for this content.")
                    .setNegativeButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    MainActivity.this.finish();
                                }
                            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    public boolean checkAge(){

        boolean oldEnough = false;

        year = datePicker.getYear();
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth();

        if (year < (c.get(Calendar.YEAR) - 18)) {
            oldEnough = true;
        } else if (year == (c.get(Calendar.YEAR) - 18) && month < c.get(Calendar.MONTH)) {
            oldEnough = true;
        } else if (year == (c.get(Calendar.YEAR) - 18) && month == c.get(Calendar.MONTH) &&
                day <= c.get(Calendar.DAY_OF_MONTH)) {
            oldEnough = true;
        }

        return oldEnough;


    }

}