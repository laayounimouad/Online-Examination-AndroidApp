package com.laayouni.onlineexamination;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laayouni.onlineexamination.api.RestServiceApi;
import com.laayouni.onlineexamination.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment {
    private EditText et_name, et_username, et_email,et_password,et_repassword;
    private Button btn_register;
    private User user=new User();
    //UtilService utilService;
    //SharedPreferenceClass sharedPreferenceClass;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_name=getView().findViewById(R.id.et_name);
        et_email=getView().findViewById(R.id.et_email);
        et_password=getView().findViewById(R.id.et_password);
        et_repassword=getView().findViewById(R.id.et_repassword);
        et_username=getView().findViewById(R.id.et_username);
        btn_register=getView().findViewById(R.id.btn_register);

        btn_register.setOnClickListener(view1 -> {
            user.setEmail(et_email.getText().toString());
            user.setFullname(et_name.getText().toString());
            user.setUsername(et_username.getText().toString());
            user.setPassword(et_password.getText().toString());
            if(user.getUsername()!=null){
                registerUser(user);
            }
        });
    }
    private void registerUser(User user){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.41.158:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        RestServiceApi serviceAPI = retrofit.create(RestServiceApi.class);
        Call<User> callUsers = serviceAPI.createUser(user);
        callUsers.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User userResponse=response.body();
                    Log.i("info","sucess::: "+userResponse.getId());
                    Toast.makeText(getView().getContext(),"account created!\n You can login now",Toast.LENGTH_LONG).show();
                }
                else {
                    Log.e("error","error in connection");
                    Toast.makeText(getView().getContext(),"account created!\n You can login now",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error","error in connection"+t);
                Toast.makeText(getView().getContext(),"account created!\n You can login now",Toast.LENGTH_LONG);
            }
        });
    }
}