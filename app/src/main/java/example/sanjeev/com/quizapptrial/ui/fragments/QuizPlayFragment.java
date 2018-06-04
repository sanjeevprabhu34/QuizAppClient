package example.sanjeev.com.quizapptrial.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import example.sanjeev.com.quizapptrial.R;
import example.sanjeev.com.quizapptrial.Services.TimerService;
import example.sanjeev.com.quizapptrial.model.GeneralQuestionOptionObj;
import example.sanjeev.com.quizapptrial.model.Message;
import example.sanjeev.com.quizapptrial.model.QuizPlaySingleQuestionObject;
import example.sanjeev.com.quizapptrial.ui.activities.MainActivity;
import example.sanjeev.com.quizapptrial.ui.adapters.RecyclerAdapter;

public class QuizPlayFragment extends Fragment{
    private interactor myInteractor;
    private ArrayList<QuizPlaySingleQuestionObject> questionList;
    private LinearLayout questionContainer;
    private int currQuestion = 0;
    private int maxQuestions;
    private TextView timerTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.quizplay_fragment, container, false);
        return v;
    }

    public interface interactor  {
        public void createNewQuizTimerReceiver();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        myInteractor = (interactor) getActivity();
        questionContainer = getActivity().findViewById(R.id.QuizContainer);
        timerTv = getActivity().findViewById( R.id.timeRemainingTv);

        getPost();
    }

    public void timerComplete(){

     ;
        if(currQuestion < maxQuestions){
            currQuestion++;
            startQuiz();
        }else{
            Log.e("GameComplete", "gameComplete");
        }
        Toast.makeText(getActivity(), "Timer Up " + currQuestion, Toast.LENGTH_LONG).show();
    }

    public void timerInterval(String timeRemaining){

        timerTv.setText(timeRemaining);
    }

    private void getPost(){
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.0.2.2:4466/sanjeev/work/clients/Shankar_Iyer/QuizzApp/GetGeneralQuestions.php";

        //String Request initialized


        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();

                    Log.e("message", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String messagesList = (String) jsonObject.get("content");
                    JSONArray jsonArray = new JSONArray(messagesList);


                    gotQuizData(jsonArray);

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

    private void gotQuizData(JSONArray jsonArray){
        JSONArray questionArray = null;
        try {
            questionArray = (JSONArray) jsonArray.get(0);
            ArrayList<Message> messageList = new ArrayList<>();

            Log.e("questionArray", String.valueOf(questionArray.length()));

            JSONArray optionJsonArray = (JSONArray) jsonArray.get(1);
            //JSONArray startTimeArray = (JSONArray) jsonArray.get(2);
            //JSONArray endTimeArray = (JSONArray) jsonArray.get(3);

            questionList = new ArrayList<>();

            for(int i=0;i<questionArray.length();i++){
                String question  = (String) questionArray.get(i);
                String optionJsonStr = (String) optionJsonArray.get(i);
                JSONArray optionJsonStrList = new JSONArray(optionJsonStr);



               // Log.e("QuestionArray",i + " " +  question);
                //Log.e("optionJsonArray",i + " " +  optionJsonStrList.length());

                //Log.e("optionJsonStrList",i + " " +  optionJsonStrList.length());

                QuizPlaySingleQuestionObject quizPlaySingleQuestionObject = new QuizPlaySingleQuestionObject();
                quizPlaySingleQuestionObject.setQuestionString(question);

                for(int d=0;d<optionJsonStrList.length();d++) {
                    GeneralQuestionOptionObj generalQuestionOptionObj = new GeneralQuestionOptionObj();
                    JSONObject option = (JSONObject) optionJsonStrList.get(d);
                    String optionString = (String) option.get("optionname");
                    Integer optionPriority = (Integer) option.get("priority");
                    boolean isCorrect = (boolean) option.get("iscorrect");

                    generalQuestionOptionObj.setOptionText(optionString);
                    generalQuestionOptionObj.setOptionPriorityInPercentage(optionPriority);
                    generalQuestionOptionObj.setOptionTheCorrectAnswer(isCorrect);

                    quizPlaySingleQuestionObject.addOption(generalQuestionOptionObj);
                    //Log.e("OptionArray", " " + option.get("optionname") + " " + option.get("priority")+ " " + option.get("iscorrect"));
                }
                questionList.add(quizPlaySingleQuestionObject);
            }


 /*
                    RecyclerView recyclerView = getActivity().findViewById(R.id.message_list_rv);
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(messageList);

                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerAdapter.notifyDataSetChanged();*/

        } catch (JSONException e) {
            e.printStackTrace();
        }



        startQuiz();
    }


    private void startQuiz() {
        Log.e("QuestionList", "size is " + questionList.size());
        questionContainer = getActivity().findViewById(R.id.QuizContainer);
        QuizPlaySingleQuestionObject quizPlaySingleQuestionObject = questionList.get(currQuestion);
        View questionView = LayoutInflater.from(getContext()).inflate(R.layout.quiz_play_single_question_layout, questionContainer, false);
        questionContainer.removeAllViews();
        questionContainer.addView(questionView);
        TextView questionTv = questionView.findViewById(R.id.questionTv);
        questionTv.setText(quizPlaySingleQuestionObject.getQuestionString());

        ViewGroup optionContainerVg = questionView.findViewById(R.id.optionContainer);
        ArrayList<GeneralQuestionOptionObj> optionList = quizPlaySingleQuestionObject.getOptionList();

        Log.e("optionList", String.valueOf(optionList.size()));
        maxQuestions = optionList.size();
        for(int i = 0;i<optionList.size();i++){
            GeneralQuestionOptionObj generalQuestionOptionObj = optionList.get(i);
            View optionView = LayoutInflater.from(getContext()).inflate(R.layout.gameplay_single_question_single_option, questionContainer, false);
            TextView optionTv = optionView.findViewById(R.id.optionText);
            optionTv.setText(generalQuestionOptionObj.getOptionText());



            boolean isCorrect = generalQuestionOptionObj.isOptionTheCorrectAnswer();

            if(isCorrect){
                optionTv.setBackgroundColor(Color.GREEN);
            }else{
                optionTv.setBackgroundColor(Color.RED);
            }

            optionContainerVg.addView(optionView);
        }

        startTimerService();


    }

    private void startTimerService(){
        Intent timerIntent = new Intent(getActivity(), TimerService.class);
        getActivity().startService(timerIntent);
        myInteractor.createNewQuizTimerReceiver();
    }
}
