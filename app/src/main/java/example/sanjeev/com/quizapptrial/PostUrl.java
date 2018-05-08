package example.sanjeev.com.quizapptrial;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostUrl {
    private Interactor interactor;
    public interface Interactor {
        public void urlConnected();
    }

    public PostUrl(Context context, String url, final HashMap<String, String> params){
       // interactor = (Interactor) context;
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                //interactor.urlConnected();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Log.e("params", params.toString());


                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

}
