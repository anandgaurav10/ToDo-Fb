package androidapplication.com.todoplusflogin.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import androidapplication.com.todoplusflogin.util.AppConstant;
import androidapplication.com.todoplusflogin.R;

/**
 * Created by Anand on 23-09-2016.
 */
public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isLoggedIn = sharedPreferences.getBoolean(AppConstant.IS_LOGGED_IN,false);

        if (isLoggedIn){
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
