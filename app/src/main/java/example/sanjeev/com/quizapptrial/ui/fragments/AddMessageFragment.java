package example.sanjeev.com.quizapptrial.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import example.sanjeev.com.quizapptrial.R;

public class AddMessageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_message, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        final EditText messageEt = getActivity().findViewById(R.id.message_et);
        Button submitButton  = getActivity().findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPost(messageEt.getText().toString());
            }
        });

        getPostSections();
    }

    private void setSectionSpinner(final JSONArray sectionJsonList){
        AppCompatSpinner spinner = getActivity().findViewById(R.id.sections_spinner);

    }

    private void getPostSections(){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/GetPostSections.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();

                Log.e("message", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Log.e("response", jsonArray.getString("status"));
                    String status = jsonObject.getString("status");

                    if(status.equalsIgnoreCase("success")){
                        JSONArray sectionJsonArray = jsonObject.getJSONArray("content");
                        Log.e("sections" , ""+ sectionJsonArray);
                        setSectionSpinner(sectionJsonArray);
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
        });

        mRequestQueue.add(mStringRequest);
    }

    private void sendPost(final String message){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/AddNewpost.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();

                Log.e("message", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Log.e("response", jsonArray.getString("status"));
                    String name = jsonObject.getString("status");
                    long time = Long.parseLong(jsonObject.getString("time"));
                    long unixSeconds = time;
// convert seconds to milliseconds
                    Date date = new java.util.Date(unixSeconds*1000L);
// the format of your date
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
// give a timezone reference for formatting (see comment at the bottom)
                    sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-5"));
                    String formattedDate = sdf.format(date);
                    System.out.println(formattedDate);

                    // Toast.makeText(getApplicationContext(),"Response :" + name + " "+ formattedDate, Toast.LENGTH_LONG).show();

                    Log.e("date", name + " " +formattedDate);
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
                params.put("userid", "27");
                params.put("message", message);


                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
