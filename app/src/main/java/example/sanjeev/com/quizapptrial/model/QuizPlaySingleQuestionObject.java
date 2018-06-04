package example.sanjeev.com.quizapptrial.model;

import java.util.ArrayList;

public class QuizPlaySingleQuestionObject {
    private String questionString;
    private ArrayList<GeneralQuestionOptionObj> optionList = new ArrayList<>();


    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public ArrayList<GeneralQuestionOptionObj> getOptionList() {
        return optionList;
    }

    public void setOptionList(ArrayList<GeneralQuestionOptionObj> optionList) {
        this.optionList = optionList;
    }

    public void addOption(GeneralQuestionOptionObj singleOptionObj){
        optionList.add(singleOptionObj);
    }
}
