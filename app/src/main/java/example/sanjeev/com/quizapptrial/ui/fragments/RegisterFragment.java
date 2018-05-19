package example.sanjeev.com.quizapptrial.ui.fragments;

import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import example.sanjeev.com.quizapptrial.Connectors.PostUrl;
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
        final String url = "http://localhost:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/GetJsonData.php";
        map = new HashMap<>();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostUrl(getActivity(), url, map);
            }
        });
    }

    public void PostUrl(Context context, String url, final HashMap<String, String> params){
        // interactor = (Interactor) context;
        //developer branch push
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //progressBar.setVisibility(View.INVISIBLE);


                        //getting the whole json object from the response


                        Log.e("jsontest", response);


                        


                        //we have the array named hero inside the object
                        //so here we are getting that json array


                        //creating custom adapter object


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                       // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }



    @Override
    public void urlConnected() {
        Log.e("url", "connected");
    }
}
