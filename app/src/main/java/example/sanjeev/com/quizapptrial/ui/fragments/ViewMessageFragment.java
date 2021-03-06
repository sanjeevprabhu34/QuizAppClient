package example.sanjeev.com.quizapptrial.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import example.sanjeev.com.quizapptrial.R;
import example.sanjeev.com.quizapptrial.model.Message;
import example.sanjeev.com.quizapptrial.ui.adapters.RecyclerAdapter;

public class ViewMessageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_message, container, false);
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

        getPost();
    }

    private void getPost(){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/getPosts.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();

                    Log.e("message", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String messagesList = (String) jsonObject.get("content");
                    JSONArray jsonArray = new JSONArray(messagesList);
                    ArrayList<Message> messageList = new ArrayList<>();

                    for(int i=0;i<jsonArray.length();i++){
                        String messageStr  = (String) jsonArray.get(i);
                        Message message = new Message();
                        message.setMessage(messageStr);
                        messageList.add(message);
                    }

                    RecyclerView recyclerView = getActivity().findViewById(R.id.message_list_rv);
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(messageList);

                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerAdapter.notifyDataSetChanged();

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



                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
