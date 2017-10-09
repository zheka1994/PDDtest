package com.example.eugen.pddtestv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.sql.Ref;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    private Button ExaminationButton;
    private Button ListofBilletsButton;
    private Button ExitButton;
    private Button FinesButton;
    private Button GIBDDButton;
    public static final String KEY_OF_QUERY = "key";
    public static final String URL = "URL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ExaminationButton = (Button)findViewById(R.id.ex_button);
        ListofBilletsButton = (Button)findViewById(R.id.tick_button);
        ExitButton = (Button)findViewById(R.id.exit_app_button);
        FinesButton = (Button) findViewById(R.id.fines_button);
        GIBDDButton = (Button)findViewById(R.id.gibbd_but);
        GIBDDButton.setOnClickListener(this);
        FinesButton.setOnClickListener(this);
        ExitButton.setOnClickListener(this);
        ExaminationButton.setOnClickListener(this);
        ListofBilletsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == ExaminationButton){
            Intent intent = new Intent(MenuActivity.this, TestingActivity.class);
            intent.putExtra(KEY_OF_QUERY,0);
            startActivity(intent);
            finish();
        }
        else if(view == ListofBilletsButton){
            //do another
            Intent intent = new Intent(MenuActivity.this,BilletListActivity.class);
            startActivity(intent);
            finish();
        }
        else if(view == ExitButton){
            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
            builder.setTitle(getString(R.string.exit))
                    .setMessage(getString(R.string.exit_alert))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            }).setNegativeButton(getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                            }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(view == FinesButton){
            Intent i = new Intent(MenuActivity.this,WebViewActivity.class);
            String url = "https://shtrafy-gibdd.ru/blog/shtrafy-gibdd-2017-goda-tablica-shtrafov-za-narushenie-pdd";
            i.putExtra(URL,url);
            startActivity(i);
        }
        else if(view == GIBDDButton){
            Intent i = new Intent(MenuActivity.this,WebViewActivity.class);
            String url = "https://xn--b1aew.xn--p1ai/%D1%81%D0%B5%D1%80%D0%B2%D0%B8%D1%81%D1%8B-%D0%B3%D0%B8%D0%B1%D0%B4%D0%B4";
            i.putExtra(URL,url);
            startActivity(i);
        }

    }
}
