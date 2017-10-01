/**
    The three java files in this project, QuizFrame.java, Question.java, and Main.java, 
    implement a GUI-based multiple choice quiz. The program asks the user to select 
    from two user types, student and supervisor. Selecting student will initialize 
    a quiz with questions drawn from the questions.txt file. If any questions are 
    missed, the user will be given a second round. If any questions are missed during 
    the second round, the answer for that question will be displayed in red. At the end 
    of a quiz, the user will be asked to enter their name to save their score. The 
    name, score, and date are saved to the scores.txt file. Selecting supervisor will 
    provide the user with two options, add questions and view scores. The add questions 
    option will append new user-created questions to the questions.txt file, while the 
    view scores option will display student scores drawn from the scores.txt file.
*/
import javax.swing.JFrame;

/**
    This file, Main.java, acts as a driver for the QuizFrame class. The frame
    will run until the user closes it.
*/
public class Main {
    public static void main(String[] args) {
        QuizFrame frame = new QuizFrame();
        frame.setTitle("Quiz Tutor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}