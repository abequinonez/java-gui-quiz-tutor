import java.util.ArrayList;
import java.util.Collections;

/**
	A Question with a correct answer and multiple choices.
*/
public class Question {
	private String question;
	private String correctAnswer;
	private ArrayList<String> answerList;

	/**
		Constructs a Question and assigns values to the instance variables.
	*/
	public Question(String q, String answer, String c1, String c2, String c3) {
		question = q;
		correctAnswer = answer;
		answerList = new ArrayList<String>();
	    answerList.add(answer);
	    answerList.add(c1);
	    answerList.add(c2);
	    answerList.add(c3);
	    Collections.shuffle(answerList);
	}

	/**
		Gets the question.
		@return the question
	*/
	public String getQuestion() {
		return this.question;
	}

	/**
		Gets the correct answer.
		@return the correct answer
	*/
	public String getAnswer() {
		return this.correctAnswer;
	}

	/**
		Gets the answers array list.
		@return the answers array list
	*/
	public ArrayList<String> getAnswerList() {
		return this.answerList;
	}
}