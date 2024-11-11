package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Выполняем запрос к API для получения списка комментариев
        RetrofitClient.getApiService().getComments().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Получаем список комментариев
                    List<Comment> comments = response.body();

                    // Устанавливаем адаптер для отображения комментариев в RecyclerView
                    commentAdapter = new CommentAdapter(comments);
                    recyclerView.setAdapter(commentAdapter);

                    Log.d("MainActivity", "Количество комментариев: " + comments.size());
                } else {
                    Log.e("MainActivity", "Ошибка ответа: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("MainActivity", "Ошибка сети: " + t.getMessage());
            }
        });
    }
}
