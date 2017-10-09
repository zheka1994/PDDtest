package com.example.eugen.pddtestv2;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class TestingActivity extends AppCompatActivity {
    //Переменные для представления
    private ViewPager ViewPager;
    private PagerTabStrip PagerTabStrip;
    private Button NextpageButton;
    private Button PrevPageButton;
    private TextView TimeView;
    //Переменные для данных
    private int NumBillet;
    private Test test;
    private List <Question> listQuest;
    public static final String ISTESTING = "istesting";
    private static final String ISENDOFTIME = "isendoftime";
    private static final String TIME = "time";
    private boolean isTesting;
    private boolean isEndOfTime = false;
    private int time = 1200;
    StringBuffer minStr = new StringBuffer();
    StringBuffer secStr = new StringBuffer();
    TimerAsyncTask task;
    boolean Bundlebool = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        if(savedInstanceState!=null){
           isTesting = savedInstanceState.getBoolean(ISTESTING);
            isEndOfTime = savedInstanceState.getBoolean(ISENDOFTIME);
            time = savedInstanceState.getInt(TIME);
            Bundlebool = true;
        }
        NumBillet = getIntent().getIntExtra(MenuActivity.KEY_OF_QUERY,0);
        test = Test.getObject(this);
        NextpageButton = (Button)findViewById(R.id.next);
        PrevPageButton = (Button) findViewById(R.id.prev);
        ViewPager = (ViewPager)findViewById(R.id.test_view_pager);
        PagerTabStrip = (PagerTabStrip)findViewById(R.id.tab_strip);
        isTesting =  getIntent().getBooleanExtra(ISTESTING,true);
        if(isTesting) {
            if (NumBillet == 0) { //Если экзамен
                if(!Bundlebool)
                test.SelectRandomQuestions();
                TimeView = (TextView)findViewById(R.id.time_view_text);
                TimeView.setTextSize(18);
                TimeView.setTextColor(Color.RED);
                //Добавляем время
               //  task = new TimerAsyncTask();
              //   task.execute();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final StringBuffer minStr = new StringBuffer();
                        final StringBuffer secStr = new StringBuffer();
                        while (!isEndOfTime) {
                            int minutes = time / 60;
                            int seconds = time % 60;
                            if ((minutes >= 0 && minutes <= 9)) {
                                minStr.append("0" + minutes);
                            } else {
                                minStr.append(minutes);
                            }
                            if ((seconds >= 0 && seconds <= 9)) {
                                secStr.append("0" + seconds);
                            } else {
                                secStr.append(seconds);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TimeView.setText("" + minStr.toString() + " : " + secStr.toString());
                                    minStr.setLength(0);
                                    secStr.setLength(0);
                                }
                            });
                            try {
                                Thread.currentThread().sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            time--;
                            if(time<=0) {
                                isEndOfTime = true;
                            }
                        }
                        try {
                            Thread.currentThread().sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                            Intent intent2 = new Intent(TestingActivity.this, ResultActivity.class);
                            startActivity(intent2);
                            finish();

                    }
                }).start();

                //
            } else { // Если тренеровка по номерам билетов
                if(!Bundlebool)
                test.SelectFromBillet(NumBillet);
            }
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPager.setAdapter(new MyAdapter(fragmentManager));
        listQuest = test.getListQuestion();
        NextpageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ViewPager.getCurrentItem()<=listQuest.size()-1) {
                   ViewPager.setCurrentItem(ViewPager.getCurrentItem() + 1);
                }
            }
        });
        PrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ViewPager.getCurrentItem()>=0) {
                    ViewPager.setCurrentItem(ViewPager.getCurrentItem() - 1);
                }
            }
        });
    }
    private class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
          return TestFragment.newInstance(position,isTesting);
        }

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return new String(getString(R.string.question_text)+" "+(position+1));
        }
    }
    class TimerAsyncTask extends AsyncTask<Void,Integer,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while(time>0) {
                SystemClock.sleep(1000);
                time--;
                publishProgress(time);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int minutes = time/60;
            int seconds = time%60;
            if((minutes>=0 && minutes<=9)){
                minStr.append("0"+minutes);
            }
            else{
                minStr.append(minutes);
            }
            if((seconds>=0 && seconds<=9)){
                secStr.append("0"+seconds);
            }
            else{
                secStr.append(seconds);
            }
            TimeView.setText(""+minStr.toString()+" : "+secStr.toString());
            minStr.setLength(0);
            secStr.setLength(0);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent2 = new Intent(TestingActivity.this, ResultActivity.class);
            startActivity(intent2);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ISTESTING,isTesting);
        outState.putBoolean(ISENDOFTIME,isEndOfTime);
        outState.putInt(TIME,time);
    }

  /*  @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(ISTESTING,isTesting);
        outState.putBoolean(ISENDOFTIME,isEndOfTime);
        outState.putInt(TIME,time);
       // outState.putInt();
      //  outState.putint(NumBillet)
          private int NumBillet;
    private Test test;
    private List <Question> listQuest;

    }*/
}
