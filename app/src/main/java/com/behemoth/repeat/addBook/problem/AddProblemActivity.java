package com.behemoth.repeat.addBook.problem;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.model.Book;

public class AddProblemActivity extends AppCompatActivity implements AddProblemContract.View {

    private AddProblemContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        setToolbar();

        presenter = new AddProblemPresenter(this);

        Bundle data = getIntent().getExtras();
        Book newBook = data.getParcelable("book");

        presenter.setRecyclerView(newBook);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    public void upload() {
        presenter.upload();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onUploadSuccess() {
        Intent i = new Intent(AddProblemActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("dataChanged", 1);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}