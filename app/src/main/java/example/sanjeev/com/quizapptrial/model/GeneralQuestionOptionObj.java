package example.sanjeev.com.quizapptrial.model;

public class GeneralQuestionOptionObj {
    private String optionText;
    private Integer optionPriorityInPercentage;
    private boolean IsOptionTheCorrectAnswer;

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public double getOptionPriorityInPercentage() {
        return optionPriorityInPercentage;
    }

    public void setOptionPriorityInPercentage(Integer optionPriorityInPercentage) {
        this.optionPriorityInPercentage = optionPriorityInPercentage;
    }

    public boolean isOptionTheCorrectAnswer() {
        return IsOptionTheCorrectAnswer;
    }

    public void setOptionTheCorrectAnswer(boolean optionTheCorrectAnswer) {
        IsOptionTheCorrectAnswer = optionTheCorrectAnswer;
    }
}
