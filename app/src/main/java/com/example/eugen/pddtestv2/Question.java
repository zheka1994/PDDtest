package com.example.eugen.pddtestv2;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Eugen on 05.10.2017.
 */

public class Question {
    private int Number;
    private String QuestText;
    private ArrayList<String> ListVar;
    private int TrueVariants;
    private String Illustration;
    private  int NumBillet;

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getQuestText() {
        return QuestText;
    }

    public void setQuestText(String questText) {
        QuestText = questText;
    }

    public int getTrueVariants() {
        return TrueVariants;
    }

    public ArrayList<String> getListVar() {
        return ListVar;
    }

    public void setListQuest(ArrayList<String> listVar) {
        ListVar = listVar;
    }

    public void setTrueVariants(int trueVariants) {
        TrueVariants = trueVariants;

    }

    public String getIllustration() {
        return Illustration;
    }

    public void setIllustration(String illustration) {
        Illustration = illustration;
    }

    public int getNumBillet() {
        return NumBillet;
    }

    public void setNumBillet(int numBillet) {
        NumBillet = numBillet;
    }

    public Question(int number, String questText, ArrayList<String> ListVar, int trueVariants, String illustration, int numBillet) {

        Number = number;
        QuestText = questText;
        this.ListVar = ListVar;
        TrueVariants = trueVariants;
        Illustration = illustration;
        NumBillet = numBillet;
    }
}
