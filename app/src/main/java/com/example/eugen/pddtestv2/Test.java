package com.example.eugen.pddtestv2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Eugen on 05.10.2017.
 */

public class Test {
    private static Test test;
    private DataBaseHelper DBHelper;
    private SQLiteDatabase Db;
    private Context context;
    private List <Question> ListQuestion = null;
    private int AnswerArray[];
    public static Test getObject(Context context){
        if(test==null){
            test = new Test(context);
        }
        return test;
    }
    private Test(Context context){
        this.context = context.getApplicationContext();
        DBHelper = new DataBaseHelper(this.context);
        try{
            DBHelper.updateDataBase();
        }
        catch(IOException ex){
            throw new Error("UnableToUpdateDataBase");
        }
        try{
            Db = DBHelper.getWritableDatabase();
        }
        catch(SQLException ex){
            throw ex;
        }
        ListQuestion = new ArrayList<>();
        AnswerArray = new int[20];
        for(int i = 0;i<AnswerArray.length;i++){
            AnswerArray[i] = 0;
        }
    }
    public void SelectFromBillet(int numbill){
        if(!ListQuestion.isEmpty()){
            ListQuestion.clear();
        }
        clearAnswerArray();
        String query = "Select * from Test where numbull = ?";
        Cursor cursor = Db.rawQuery(query,new String[]{new String(""+numbill)});
        Boolean f = cursor.moveToFirst();

        if(f){
            while(!cursor.isAfterLast()){
                ArrayList<String> variants = new ArrayList<>();
                variants.add(cursor.getString(2));
                variants.add(cursor.getString(3));
                variants.add(cursor.getString(4));
                variants.add(cursor.getString(5));
                int numQuestion = cursor.getInt(0);
                Question quest = new Question(numQuestion,cursor.getString(1),variants,cursor.getInt(6),cursor.getString(7),cursor.getInt(8));
                ListQuestion.add(quest);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }
    public void SelectRandomQuestions(){
        if(!ListQuestion.isEmpty()){
            ListQuestion.clear();
        }
        clearAnswerArray();
        ArrayList<Integer> randQuest = new ArrayList<>();
        int CountQuestion = 0;
        boolean flag;
        while(CountQuestion<20){
            flag = true;
            int k = (int)(Math.random()*81+1);
            for(int j = 0;j < CountQuestion;j++){
                if(randQuest.get(j)==k){
                    flag = false;
                    break;
                }
            }
            if(flag) {
                randQuest.add(k);
                CountQuestion++;
            }
        }
        String query = "Select * from Test where id = ?";
        for(int i = 0;i<CountQuestion;i++){
            Cursor cursor = Db.rawQuery(query,new String[]{new String(""+randQuest.get(i))});
            flag = cursor.moveToFirst();
            if(flag){
                    ArrayList <String> variants = new ArrayList<>();
                    variants.add(cursor.getString(2));
                    variants.add(cursor.getString(3));
                    variants.add(cursor.getString(4));
                    variants.add(cursor.getString(5));
                    int numQuestion = cursor.getInt(0);
                    Question quest = new Question(numQuestion,cursor.getString(1),variants,cursor.getInt(6),cursor.getString(7),cursor.getInt(8));
                    ListQuestion.add(quest);
            }
            cursor.close();
        }
    }
    public List<Question> getListQuestion() {
        return ListQuestion;
    }

    public int[] getAnswerArray() {
        return AnswerArray;
    }

    public void setListQuestion(List<Question> listQuestion) {
        ListQuestion = listQuestion;
    }

    public void setAnswerArray(int[] answerArray) {
        AnswerArray = answerArray;
    }
    public void clearListQuestion(){
        ListQuestion.clear();
    }
    public void clearAnswerArray(){
        for(int i = 0;i<AnswerArray.length;i++){
            AnswerArray[i] = 0;
        }
    }
    public void setAnswerArrItem(int pos,int val){
        if(pos<AnswerArray.length)
          AnswerArray[pos] = val;
    }
}
