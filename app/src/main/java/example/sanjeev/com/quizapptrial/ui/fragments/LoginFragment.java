package example.sanjeev.com.quizapptrial.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import example.sanjeev.com.quizapptrial.R;

public class LoginFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        login();
    }

    private void login( ){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        //http://localhost:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/Login.php?email=ss@gg.com&password=1234567
        String url = "http://localhost:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/Login.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();

                Log.e("message", response.toString());

                /*try {
                    JSONObject jsonObject = new JSONObject(response);
                    String statusStr = jsonObject.getString("status");

                    if(statusStr.equals("success")){
                        Toast.makeText(getActivity(), "Post created successfully", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("An","Error :" + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("email", "ss@gg.com");
                params.put("password", "1234567");



                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

}
