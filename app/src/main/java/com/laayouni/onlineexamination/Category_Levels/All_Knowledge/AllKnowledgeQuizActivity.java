package com.laayouni.onlineexamination.Category_Levels.All_Knowledge;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.laayouni.onlineexamination.MainActivity;
import com.laayouni.onlineexamination.MenuHomeScreenActivity;
import com.laayouni.onlineexamination.R;
import com.laayouni.onlineexamination.entities.Question;
import com.laayouni.onlineexamination.entities.Test;
import com.laayouni.onlineexamination.questionsModelClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllKnowledgeQuizActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button navToggler_btn,  Next_btn;
    LinearLayout linearLayout, linearLayout1;
    TextView txtQuestions, txtnumberIndicator;
    Dialog dialog;
    private int count = 0;
    private int position = 0;
    //private List<questionsModelClass> list;
    List<Question> questionsList;
    private int score = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Eneter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_questions_view);



    Intent intent=getIntent();
    Test test= (Test) intent.getSerializableExtra("test");

        //All Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navToggler_btn = findViewById(R.id.action_menu_presenter);
        linearLayout = findViewById(R.id.main_content);
        linearLayout1 = findViewById(R.id.options_layout);
        txtQuestions = findViewById(R.id.question_view);
        txtnumberIndicator = findViewById(R.id.no_of_questions_view);
        Next_btn = findViewById(R.id.next_btn);



        dialog = new Dialog(this, R.style.AnimateDialog);

/////////Question lists//////////////
        questionsList=test.getQuestions();

        for (int i = 0; i < 4; i++) {
            linearLayout1.getChildAt(i).setOnClickListener(v ->
                    checkAns((Button) v));
        }

        txtnumberIndicator.setText(position + 1 + "/" + questionsList.size());

        playAnim(txtQuestions, 0, questionsList.get(position).getQuestion());
        Next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next_btn.setEnabled(false);
                Next_btn.setAlpha(0.7f);
                enableOptions(true);
                position++;
                if (position == questionsList.size()) {
                    //Score Activities
                        Button try_again, promote_btn;

                        dialog.setContentView(R.layout.activity_score_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        TextView score_result = dialog.findViewById(R.id.score_result);
                        score_result.setText(score+"% Score");
                        promote_btn.setOnClickListener(v1 -> {
                            Intent BG = new Intent(getApplicationContext(), MenuHomeScreenActivity.class); ///If User get 70% let him to next category
                            startActivity(BG);
                            dialog.dismiss();
                            finish();
                        });

                        try_again.setOnClickListener(v12 -> {
                            Intent BG = new Intent(getApplicationContext(), AllKnowledgeQuizActivity.class); //If User get 20% let him or her play again
                            BG.putExtra("test",test);
                            startActivity(BG);
                            finish();
                        });


                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();


                    return;
                }
                count = 0;
                playAnim(txtQuestions, 0, questionsList.get(position).getQuestion());
            }
        });

        navigationDrawer();
    }
    ///////////////////////////////////////////////////////////////////ANIMATING SCREEN/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void playAnim(final View view, final int value, final String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = questionsList.get(position).getChoices().get(0).getChoice();
                    } else if (count == 1) {
                        option = questionsList.get(position).getChoices().get(1).getChoice();
                    } else if (count == 2) {
                        option = questionsList.get(position).getChoices().get(2).getChoice();
                    } else if (count == 3) {
                        option = questionsList.get(position).getChoices().get(3).getChoice();
                    }
                    playAnim(linearLayout1.getChildAt(count), 0, option);
                    count++;
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animation) {

                if (value == 0) {

                    try {
                        ((TextView) view).setText(data);
                        txtnumberIndicator.setText(position + 1 + "/" + questionsList.size());
                    } catch (ClassCastException ex) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);


                    playAnim(view, 1, data);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void checkAns(Button selectedOptions) {
        enableOptions(false);
        Next_btn.setEnabled(true);
        Next_btn.setAlpha(1);
       if (selectedOptions.getText().toString().equals(questionsList.get(position).getCorrectAnswer().getChoice())) {
            //correct Answer
            Log.e("error","Correct error here:"+selectedOptions);
            score+=100/questionsList.size();
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.ding);
            selectedOptions.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));
            mp.start();
        } else {
            //wrong Answer
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong_buzzer);
            selectedOptions.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF2B55")));
            Button correctOption = linearLayout1.findViewWithTag(questionsList.get(position).getCorrectAnswer().getChoice());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));

            mp.start();
        }
    }

    private void enableOptions(boolean enable) {
        for (int i = 0; i < 4; i++) {
            linearLayout1.getChildAt(i).setEnabled(enable);
            if (enable) {
                linearLayout1.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2133A0")));
            }
        }
    }


    ///////////////////////////////////////////////////////////////////ALL ABOUT NAVIGATION DRAWER/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void navigationDrawer() {

        //Navigation Drawer

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

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

        if (menuItem.getItemId() == R.id.home) {
            Intent home = new Intent(getApplicationContext(), MenuHomeScreenActivity.class);
            startActivity(home);
            AllKnowledgeQuizActivity.super.onBackPressed();

        } else if (menuItem.getItemId() == R.id.logout) {
           // FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

