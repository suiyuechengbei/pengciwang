package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.MyAdapter;
import com.example.myapplication.domain.Word;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class WrongWordsListActivity extends AppCompatActivity {

    private List<String> words = new ArrayList<String>();
    private ListView listView;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_words_list);

        Intent intent = getIntent();
        words = intent.getStringArrayListExtra("words");
        adapter = new MyAdapter(WrongWordsListActivity.this, words);
        listView = (ListView)findViewById(R.id.alistview);
        listView.setAdapter(adapter);
        //ListView item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(WrongWordsListActivity.this, "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });
        //ListView item 中的删除按钮的点击事件
        adapter.setOnItemDeleteClickListener(new MyAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                String word=words.get(i);
                words.remove(i);
                LitePal.initialize(getApplicationContext());
                List<Word> words=LitePal.where("word=?",word+"").find(Word.class);
                if(!words.isEmpty()){
                    words.get(0).setIsCollected(0);
                    words.get(0).save();
                }
                Toast.makeText(WrongWordsListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });

    }
}