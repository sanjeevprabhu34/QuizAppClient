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
import android.widget.Toast;

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

import example.sanjeev.com.quizapptrial.R;

public class JsonTFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_section, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        final EditText messageEt = getActivity().findViewById(R.id.message_et);
        Button submitButton  = getActivity().findViewById(R.id.add_question);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("q", "sss");
                    jsonArray.put(obj);
                    obj.put("a1", "10");
                    jsonArray.put(obj);
                    obj.put("a2", "20");
                    jsonArray.put(obj);
                    obj.put("a3", "30");
                    jsonArray.put(obj);


                    JSONObject question = new JSONObject();
                    question.put("question", jsonArray);
                    addQuestion(question.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void addQuestion( final String question){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/AddQuestionToSurvey.php";

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
                params.put("question", question);



                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }



    private void addSection( final String section_name){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/CreateNewPostSection.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();

                    Log.e("message", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String statusStr = jsonObject.getString("status");

                    if(statusStr.equals("success")){
                        Toast.makeText(getActivity(), "Post created successfully", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                params.put("post_name", section_name);



                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
