package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button btnHenGioi,btnDung;
    TimePicker timePicker;
    TextView txtHienThi;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHenGioi = (Button) findViewById(R.id.btnHenGio);
        btnDung = (Button) findViewById(R.id.btnDung);
        txtHienThi = (TextView) findViewById(R.id.txtHienThi);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(MainActivity.this, AlarmRecever.class);
        btnHenGioi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);
                if(phut < 10){
                    string_phut = "0" +String.valueOf(phut);
                }
                intent.putExtra("extra","on");
                pendingIntent = PendingIntent.getBroadcast(
                    MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT
                );
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                txtHienThi.setText("Giờ bạn đặt là : " + string_gio + ":" + string_phut);
            }
        });
        btnDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHienThi.setText("Dùng lại");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);
            }
        });
    }
}