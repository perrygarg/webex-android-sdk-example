package com.ciscowebex.androidsdk.kitchensink.launcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ciscowebex.androidsdk.kitchensink.R;

public class PerryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perry);
        setTitle("Dummy Intermediate Activity");
    }

    public void startCall(View view) {
        startActivity(new Intent(this, LauncherActivity.class));
    }
}
