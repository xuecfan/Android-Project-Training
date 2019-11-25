package com.example.chaofanteaching;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.i("1", "onCreate:123 ");
    }

}
