package com.laayouni.onlineexamination;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laayouni.onlineexamination.api.RestServiceApi;
import com.laayouni.onlineexamination.entities.User;
import com.laayouni.onlineexamination.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private EditText et_username,et_password;
    private Button btn_login;
    private User user=new User();
    SessionManager session;
    TextView errorText;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_password=getView().findViewById(R.id.et_password);
        et_username=getView().findViewById(R.id.et_username);
        btn_login=getView().findViewById(R.id.btn_login);
        errorText=getView().findViewById(R.id.errorText);
        btn_login.setOnClickListener(view1 -> {
            user.setUsername(
                    et_username.getText().length()==0?
                            null:et_username.getText().toString()
            );
            user.setPassword(
                    et_password.getText().length()==0?
                            null:et_password.getText().toString()
            );
            if(user.getUsername()!= null && user.getPassword()!=null){
                loginUser(user);
            }
            else {
                errorText.setText("both username and password are necessary");
            }

        });
    }
    private void loginUser(User newUser){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Toast.makeText(getView().getContext(),"login succeeded",Toast.LENGTH_LONG);
        Intent intent=new Intent(getContext(),MenuHomeScreenActivity.class);
        session = new SessionManager(getContext());
        session.createLoginSession(newUser);
        startActivity(intent);
        /*
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.155.158:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        RestServiceApi serviceAPI = retrofit.create(RestServiceApi.class);
        Call<User> callUsers = serviceAPI.isPassCorrect(newUser);
        callUsers.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User userResponse=response.body();
                Log.i("info","sucess::: "+response.body());
                Toast.makeText(getView().getContext(),"login succeeded",Toast.LENGTH_LONG);
                Intent intent=new Intent(getContext(),Exams.class);
                session = new SessionManager(getContext());
                session.createLoginSession(userResponse.getUsername(),userResponse.getEmail() );
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error","error in connection"+t);
                Toast.makeText(getView().getContext(),"login failed",Toast.LENGTH_LONG);
            }
        });

         */
    }
}