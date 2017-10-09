package com.example.eugen.pddtestv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BilletListActivity extends AppCompatActivity {

    private RecyclerView ListBulletsRecyclerview;
    private MyAdapter adapter;
    private List<Integer> lst;
    private int CountBillets = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billet_list);
        ListBulletsRecyclerview = (RecyclerView)findViewById(R.id.list_bullets_recycler_view);
        ListBulletsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        lst  = new ArrayList<>();
        for(int i = 1;i<=CountBillets;i++)
            lst.add(i);
        adapter = new MyAdapter(lst);
        ListBulletsRecyclerview.setAdapter(adapter);
    }
    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView NumBillTextView;
        public MyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item,parent,false));
            itemView.setOnClickListener(this);
            NumBillTextView = (TextView)itemView.findViewById(R.id.num_bullet_title);
        }
        public void bind(int num){
            NumBillTextView.setText(getString(R.string.billet_string)+" "+num);
        }
        @Override
        public void onClick(View view) {
            String parseStr = NumBillTextView.getText().toString();
            String subStr = parseStr.substring(6);
            int number = Integer.parseInt(subStr);
            //Открывать новую активность
            Intent intent = new Intent(BilletListActivity.this,TestingActivity.class);
            intent.putExtra(MenuActivity.KEY_OF_QUERY,number);
            startActivity(intent);
            finish();
        }
    }
    private class MyAdapter extends RecyclerView.Adapter<MyHolder>{
        private List<Integer> numBillets;
        public MyAdapter(List<Integer> nb){
            numBillets = nb;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(BilletListActivity.this);
            return new MyHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.bind(position+1);
        }

        @Override
        public int getItemCount() {
            return lst.size();
        }
    }
}
