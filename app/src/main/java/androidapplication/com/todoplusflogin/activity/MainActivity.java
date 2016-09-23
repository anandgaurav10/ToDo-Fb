package androidapplication.com.todoplusflogin.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import androidapplication.com.todoplusflogin.R;
import androidapplication.com.todoplusflogin.activity.LoginActivity;
import androidapplication.com.todoplusflogin.fragment.NewFragment;
import androidapplication.com.todoplusflogin.fragment.ProfileFragment;
import androidapplication.com.todoplusflogin.util.AppConstant;

/**
 * Created by Anand on 23-09-2016.
 */
public class MainActivity extends AppCompatActivity {

    private final static String TAG = LoginActivity.class.getSimpleName();

    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    ListView listView;
    ArrayAdapter<String> listAdapter;
    String fragmentArray[] = {"Profile","New Fragment"};
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.fragmentList);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fragmentArray);
        listView.setAdapter(listAdapter);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"onSuccess "+loginResult.toString());

            }

            @Override
            public void onCancel() {

                Log.d(TAG,"onCancel ");
            }

            @Override
            public void onError(FacebookException error) {

                Log.d(TAG,"ERROR " +error.toString());
                Log.d(TAG,"ERROR gaurav " +error.toString());
            }
        });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //write your code here what to do when user logout
                    Log.d(TAG,"anand ");
                    sharedPreferences.edit().putBoolean(AppConstant.IS_LOGGED_IN,false).apply();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();


                }

            }
        };

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment;

                switch (i) {
                    case 0:
                        fragment = new ProfileFragment();
                        break;
                    case 1:
                        fragment = new NewFragment();
                        break;
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.relativeLayout, fragment).commit();
                drawerLayout.closeDrawers();
            }

        });

        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativeLayout, fragment).commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"onActivityResult" );
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
