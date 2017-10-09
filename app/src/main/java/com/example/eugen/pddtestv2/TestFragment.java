package com.example.eugen.pddtestv2;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eugen on 06.10.2017.
 */

public class TestFragment extends Fragment {
    private TextView QuestTextView;
    private RadioGroup VariantsRadioGroup;
    private ImageView TestImageView;
    private ScrollView ScrollView;
    private static final String INDEX = "index";
    private int index;
    private boolean isTesting;
    public static TestFragment newInstance(int id,Boolean isTesting){
        Bundle args = new Bundle();
        args.putInt(INDEX,id);
        args.putBoolean(TestingActivity.ISTESTING,isTesting);
        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(args);
        return testFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        index = getArguments().getInt(INDEX);
        isTesting = getArguments().getBoolean(TestingActivity.ISTESTING);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test,container,false);
        QuestTextView = (TextView)v.findViewById(R.id.quest_text);
        VariantsRadioGroup = new RadioGroup(getActivity());
        LinearLayout layout = (LinearLayout)v.findViewById(R.id.fragment_layout);
        ViewGroup.LayoutParams viewparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(viewparams);
        //NEW
        LinearLayout layout1 = new LinearLayout(getActivity());
        layout1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout1.setOrientation(LinearLayout.VERTICAL);
        //
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            try {
                ScrollView = new ScrollView(getActivity());
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        List<Question> questionList = Test.getObject(getActivity()).getListQuestion();
        int ListAnswer[] = Test.getObject(getActivity()).getAnswerArray();
        Question question = questionList.get(index);
        String num_quest = getString(R.string.question_text)+" "+(index+1);
        SpannableString quest_string = new SpannableString(num_quest+" "+question.getQuestText());
        quest_string.setSpan(new ForegroundColorSpan(Color.BLUE),0,num_quest.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        QuestTextView.setText(quest_string);
        int q;
        if((q = getResources().getIdentifier("b"+question.getNumBillet()+"_"+((questionList.get(index).getNumber())%20),"drawable","com.example.eugen.pddtestv2"))!=0){
            TestImageView = new ImageView(getActivity());
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                TestImageView.setScaleX(0.8f);
                TestImageView.setScaleY(0.8f);
            }
            TestImageView.setImageResource(q);
            layout.addView(TestImageView);
        }
        if(isTesting){
            for(int i = 0;i<question.getListVar().size();i++){
               if(question.getListVar().get(i)!=null){
                   RadioButton rb = new RadioButton(getActivity());
                   rb.setId(i+1);
                   VariantsRadioGroup.addView(rb, i, viewparams);
                   rb.setText(question.getListVar().get(i));
               }
            }
            //NEW
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                ScrollView.addView(VariantsRadioGroup);
                layout.addView(ScrollView);
            }
            //
          else layout.addView(VariantsRadioGroup);
            VariantsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    Test.getObject(getActivity()).setAnswerArrItem(index,i);
                }
            });
        }
        else{
            boolean f = true;
            for(int i = 0;i<question.getListVar().size();i++){
                if(question.getListVar().get(i)!=null){
                    TextView tv = new TextView(getActivity());
                    tv.setText(question.getListVar().get(i));
                    if(ListAnswer[index]!=question.getTrueVariants()) {
                        if (ListAnswer[index] == i + 1 && ListAnswer[index]!=0) {
                            tv.setBackgroundColor(Color.RED);
                        }
                        f = false;
                    }
                    if(question.getTrueVariants()==(i+1))
                        tv.setBackgroundColor(Color.GREEN);
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                            //ScrollView.addView(tv);
                        layout1.addView(tv);

                    }
                   else
                    layout.addView(tv);
                }
            }
            if(!f) {
                TextView tv1 = new TextView(getActivity());
                tv1.setPadding(8,20,0,0);
                tv1.setText(question.getIllustration());
                tv1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    layout1.addView(tv1);
                    ScrollView.addView(layout1);
                    layout.addView(ScrollView);
                }
                else
                    layout.addView(tv1);
            }
        }
        if(index==19 && isTesting){
            Button EndButton = new Button(getActivity());
            EndButton.setText(getString(R.string.end));
            EndButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),ResultActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            layout.addView(EndButton);
        }
        return v;
    }
}
