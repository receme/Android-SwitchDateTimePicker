package com.kunzisoft.switchdatetimesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Sample class for an example of using the API SwitchDateTimePicker
 * @author JJamet
 */
public class Sample extends AppCompatActivity {

    private static final String TAG = "Sample";

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final String TAG_DATETIME_FRAGMENT2 = "TAG_DATETIME_FRAGMENT2";

    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    private static final String STATE_TEXTVIEW2= "STATE_TEXTVIEW2";
    private TextView textView;
    private TextView textView2;

    private SwitchDateTimeDialogFragment dateTimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        if (savedInstanceState != null) {
            // Restore value from saved state
            textView.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
        }

        // Construct SwitchDateTimePicker
        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(R.string.positive_button_datetime_picker),
                    getString(R.string.negative_button_datetime_picker)
            );
        }

        // Assign values we want
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        dateTimeFragment.startAtCalendarView();
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeFragment.setDefaultDateTime(new GregorianCalendar(2017, Calendar.MARCH, 4, 15, 20).getTime());
        // Or assign each element, default element is the current moment
        // dateTimeFragment.setDefaultHourOfDay(15);
        // dateTimeFragment.setDefaultMinute(20);
        // dateTimeFragment.setDefaultDay(4);
        // dateTimeFragment.setDefaultMonth(Calendar.MARCH);
        // dateTimeFragment.setDefaultYear(2017);

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        // Set listener for date
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                if(dateTimeFragment.getTag().equals(TAG_DATETIME_FRAGMENT)){
                    textView.setText(myDateFormat.format(date));
                }
                else{
                    textView2.setText(myDateFormat.format(date));
                }
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }
        });

        Button buttonView = (Button) findViewById(R.id.button);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dateStr = textView.getText().toString();

                if(dateStr!=null && !dateStr.isEmpty()){
                    try {
                        Date date = myDateFormat.parse(dateStr);
                        dateTimeFragment.setCurrentDateTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);

            }
        });

        Button buttonView2 = (Button) findViewById(R.id.button2);
        buttonView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateStr = textView2.getText().toString();

                if(dateStr!=null && !dateStr.isEmpty()){
                    try {
                        Date date = myDateFormat.parse(dateStr);
                        dateTimeFragment.setCurrentDateTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT2);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current textView
        savedInstanceState.putCharSequence(STATE_TEXTVIEW, textView.getText());
        savedInstanceState.putCharSequence(STATE_TEXTVIEW2, textView2.getText());
        super.onSaveInstanceState(savedInstanceState);
    }
}
