package example.sanjeev.com.quizapptrial.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import example.sanjeev.com.quizapptrial.PostUrl;
import example.sanjeev.com.quizapptrial.R;

public class RegisterFragment extends Fragment implements PostUrl.Interactor{
    private HashMap<String, String> map;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
       final  EditText userNameTv = view.findViewById(R.id.username_et);
        final EditText nameTv = view.findViewById(R.id.name_et);
        final EditText passEt = view.findViewById(R.id.password_et);
        final EditText emailEt = view.findViewById(R.id.email_et);
        final Button submitBtn = view.findViewById(R.id.submitBtn);
        final String url = "http://localhost:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/Register.php";
        map = new HashMap<>();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("username",userNameTv.getText().toString() );
                map.put("password",passEt.getText().toString() );
                map.put("name",nameTv.getText().toString() );
                map.put("email",emailEt.getText().toString() );
                PostUrl postUrl = new PostUrl(getActivity(), url, map);
            }
        });
    }


    @Override
    public void urlConnected() {
        Log.e("url", "connected");
    }
}
