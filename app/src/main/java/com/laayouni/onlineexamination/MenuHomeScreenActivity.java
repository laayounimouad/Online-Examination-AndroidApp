package com.laayouni.onlineexamination;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laayouni.onlineexamination.api.RestServiceApi;
import com.laayouni.onlineexamination.entities.Choice;
import com.laayouni.onlineexamination.entities.Question;
import com.laayouni.onlineexamination.entities.Test;
import com.laayouni.onlineexamination.entities.User;
import com.laayouni.onlineexamination.utils.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuHomeScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button navToggler_btn;
    LinearLayout linearLayout;
    Dialog dialog;
    RecyclerView list;
    List<Test> testList;
    TextInputEditText search_field;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Eneter into fullscreen mode

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_qcm);

        sm=new SessionManager(getApplicationContext());
        if (!sm.isLoggedIn()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(getApplicationContext(),"you need to log in first",Toast.LENGTH_LONG).show();
            finish();
        }
        //Toast.makeText(getApplicationContext(),sm.getUserDetails().get(sm.KEY_NAME),Toast.LENGTH_LONG).show();


        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navToggler_btn = findViewById(R.id.action_menu_presenter);
        linearLayout = findViewById(R.id.main_content);
        search_field = findViewById(R.id.search_field);
        list = findViewById(R.id.list);


        ///////////////////////////// diplaying exams /////////////////////////////
        TextInputLayout ie= findViewById(R.id.textField);
        ie.setStartIconOnClickListener(view -> {
            list.setAdapter(new CustomAdapter(
                    testList.stream().filter(test ->
                            test.getName().contains(
                                    (search_field).getText()
                            )).collect(Collectors.toList())
            ));
        });

        getTest("");

        navigationDrawer();

        dialog = new Dialog(this, R.style.AnimateDialog);
          navigationView
                .getMenu()
                .findItem(R.id.userName)
                .setTitle(sm.getUserDetails()
                        .get(sm.KEY_NAME));
    }

    private void getTest(String keyword){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.41.158:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        RestServiceApi serviceAPI = retrofit.create(RestServiceApi.class);
        Call<List<Test>> callUsers = serviceAPI.listTest(keyword);
        callUsers.enqueue(new Callback<List<Test>>() {
            @Override
            public void onResponse(Call<List<Test>> call, Response<List<Test>> response) {
                if(response.isSuccessful()){
                    //User userResponse=response.body();
                    List<Test> listResponse=response.body();
                    testList=listResponse;
                    list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

                    list.setAdapter(new CustomAdapter(testList));

                    //Toast.makeText(getApplicationContext(),"tests received",Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("error","error in connection");
                    Toast.makeText(getApplicationContext(),"an error had accured",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Test>> call, Throwable t) {
                Log.e("error","error in connection"+t);
                Toast.makeText(getApplicationContext(),"request failed",Toast.LENGTH_LONG);
            }
        });
    }

    private void navigationDrawer() {

        //Navigation Drawer

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        navToggler_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    ////////////////////////////////////////////////////////////ANIMATE NAV DRAWER////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.cat_heading));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset*(1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                linearLayout.setScaleX(offsetScale);
                linearLayout.setScaleY(offsetScale);


                final float xOffset = drawerView.getWidth()*slideOffset;
                final float xOffsetDiff = linearLayout.getWidth()*diffScaledOffset/2;
                final float xTranslation = xOffset - xOffsetDiff;
                linearLayout.setTranslationX(xTranslation);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.logout) {
            //Logout
            sm.logoutUser();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        return true;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onSearch(View view) {

    }
    //////////////////////////////
    //

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
