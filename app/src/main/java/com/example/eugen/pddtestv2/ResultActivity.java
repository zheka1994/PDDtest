package com.example.eugen.pddtestv2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView ResultTextView;
    private TextView ErrorTextView;
    private TextView NoAnswertextview;
    private TextView TrueAnswerTextView;
    private Button gotoMainMenuButon;
    private Button showResButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int CountTrue = 0;
        int CountFalse = 0;
        int CountWithoutAnswer = 0;
        int[] AnAr = Test.getObject(this).getAnswerArray();
        for(int i = 0;i < AnAr.length;i++){
            if(AnAr[i]!=0){
                if(AnAr[i]==Test.getObject(this).getListQuestion().get(i).getTrueVariants())
                    CountTrue++;
                else
                  CountFalse++;
            }
            else
                CountWithoutAnswer++;
        }
        int res = CountFalse + CountWithoutAnswer;
      ResultTextView = (TextView)findViewById(R.id.res_string);
        if(res>2){
            ResultTextView.setBackgroundColor(Color.RED);
            ResultTextView.setText(getString(R.string.exs_no_passed));
        }
        else{
            ResultTextView.setBackgroundColor(Color.GREEN);
            ResultTextView.setText(getString(R.string.exs_passed));
        }
        ErrorTextView = (TextView)findViewById(R.id.count_errors);
        ErrorTextView.setText(getString(R.string.count_err_str)+CountFalse);
        NoAnswertextview = (TextView)findViewById(R.id.count_without_answer);
        NoAnswertextview.setText(getString(R.string.count_no_ans_str)+CountWithoutAnswer);
        TrueAnswerTextView = (TextView)findViewById(R.id.count_true_answers);
        TrueAnswerTextView.setText(getString(R.string.count_true_str)+CountTrue);
        gotoMainMenuButon = (Button)findViewById(R.id.main_menu_but);
        gotoMainMenuButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        showResButton = (Button)findViewById(R.id.show_answer_but);
        showResButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,TestingActivity.class);
                boolean t = false;
                intent.putExtra(TestingActivity.ISTESTING,t);
                startActivity(intent);
            }
        });
    }
}
