import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
	A QuizFrame that inherits from the JFrame class.
*/
public class QuizFrame extends JFrame {
	private static final int FRAME_WIDTH = 640;
	private static final int FRAME_HEIGHT = 480;

	private JPanel headingPanel, questionPanel, mainPanel, supervisorPanel;
	private JPanel homeButtonPanel, homePanel, buttonPanel, savePanel, scoresTablePanel;
	private JPanel addQuestionPanel, addAnswerPanel, addChoiceOnePanel, addChoiceTwoPanel, addChoiceThreePanel;
	private JLabel headingTitle, headingDisplay, welcomeMsg, questionNumLabel, question, bottomLabel, saveLabel;
	private JLabel supervisorTopLabel, addQuestionLabel, addAnswerLabel, addChoiceOneLabel, addChoiceTwoLabel, addChoiceThreeLabel;
	private JRadioButton aButton, bButton, cButton, dButton;
	private JButton studentHomeButton, supervisorHomeButton, studentButton;
	private JButton supervisorButton, submitButton, nextQuestionButton, secondRoundButton, saveScoreButton;
	private JButton addQuestionsButton, viewScoresButton, questionSubmitButton;
	private ButtonGroup buttonGroup;
	private Timer labelTimer;
	private JTextField nameField, addQuestionField, addAnswerField, addChoiceOneField, addChoiceTwoField, addChoiceThreeField;

	private ArrayList<Question> questions, missedQuestions;
	private boolean questionsFound;
	private int round, score, i;

	/**
		Constructs a QuizFrame along with all of the necessary components.
	*/
	public QuizFrame() {
		createHeadingTitle();
		createHeadingDisplay();
		createStudentHomeButton();
		createSupervisorHomeButton();
		createHomeButtonPanel();
		createHeadingPanel();
		createStudentButton();
		createSupervisorButton();
		createWelcomeMsg();
		createHomePanel();
		createMainPanel();
		createLabelTimer();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

	private void createHeadingTitle() {
		headingTitle = new JLabel("Quiz Tutor");
		headingTitle.setFont(new Font("Arial", Font.BOLD, 22));
		headingTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	private void createHeadingDisplay() {
		headingDisplay = new JLabel();
		headingDisplay.setFont(new Font("Arial", Font.BOLD, 20));
		headingDisplay.setHorizontalAlignment(JLabel.CENTER);
		headingDisplay.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 24));
	}

	private void createStudentHomeButton() {
		studentHomeButton = new JButton("Home");
		studentHomeButton.setVisible(false);
		studentHomeButton.setFont(new Font("Arial", Font.PLAIN, 20));
		studentHomeButton.setMargin(new Insets(1, 0, 1, 0));
		studentHomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				headingDisplay.setText("");
				mainPanel.remove(questionPanel);
				mainPanel.revalidate();
				mainPanel.repaint();
				createHomePanel();
				mainPanel.add(homePanel, BorderLayout.CENTER);
				homePanel.revalidate();
				mainPanel.repaint();
				studentHomeButton.setVisible(false);
			}
		});
	}

	private void createSupervisorHomeButton() {
		supervisorHomeButton = new JButton("Home");
		supervisorHomeButton.setVisible(false);
		supervisorHomeButton.setFont(new Font("Arial", Font.PLAIN, 20));
		supervisorHomeButton.setMargin(new Insets(1, 0, 1, 0));
		supervisorHomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				headingDisplay.setText("");
				mainPanel.remove(supervisorPanel);
				mainPanel.revalidate();
				mainPanel.repaint();
				createHomePanel();
				mainPanel.add(homePanel, BorderLayout.CENTER);
				homePanel.revalidate();
				mainPanel.repaint();
				supervisorHomeButton.setVisible(false);
			}
		});
	}

	private void createHomeButtonPanel() {
		homeButtonPanel = new JPanel();
		homeButtonPanel.add(studentHomeButton);
		homeButtonPanel.add(supervisorHomeButton);
	}

	private void createHeadingPanel() {
		headingPanel = new JPanel();
		headingPanel.setLayout(new BorderLayout());
		headingPanel.add(headingTitle, BorderLayout.WEST);
		headingPanel.add(headingDisplay, BorderLayout.CENTER);
		headingPanel.add(homeButtonPanel, BorderLayout.EAST);
	}

	private void createStudentButton() {
		studentButton = new JButton("Student");
		studentButton.setFont(new Font("Arial", Font.PLAIN, 20));
		studentButton.setMargin(new Insets(1, 0, 1, 0));
		studentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		studentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainPanel.remove(homePanel);
				mainPanel.revalidate();
				mainPanel.repaint();
				createQuestionPanel();
				questionPanel.revalidate();
				mainPanel.repaint();
				studentHomeButton.setVisible(true);
				startQuiz();
			}
		});
	}

	private void createSupervisorButton() {
		supervisorButton = new JButton("Supervisor");
		supervisorButton.setFont(new Font("Arial", Font.PLAIN, 20));
		supervisorButton.setMargin(new Insets(1, 0, 1, 0));
		supervisorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		supervisorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainPanel.remove(homePanel);
				mainPanel.revalidate();
				mainPanel.repaint();
				createSupervisorPanel();
				supervisorPanel.revalidate();
				mainPanel.repaint();
				supervisorHomeButton.setVisible(true);
				setUpSupervisorComponents();
			}
		});
	}

	private void createWelcomeMsg() {
		welcomeMsg = new JLabel("Welcome! Please select a user type:");
		welcomeMsg.setFont(new Font("Arial", Font.PLAIN, 20));
		welcomeMsg.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		welcomeMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	private void createHomePanel() {
		homePanel = new JPanel();
		homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
		homePanel.setBackground(Color.WHITE);
		homePanel.add(Box.createVerticalGlue());
		homePanel.add(welcomeMsg);
		homePanel.add(studentButton);
		homePanel.add(Box.createVerticalStrut(10));
		homePanel.add(supervisorButton);
		homePanel.add(Box.createVerticalGlue());
	}

	private void createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(headingPanel, BorderLayout.NORTH);
		mainPanel.add(homePanel, BorderLayout.CENTER);
		add(mainPanel);
	}

	private void createLabelTimer() {
		labelTimer = new Timer(3000, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				headingDisplay.setText("");
			}
		});
	}

	private void createQuestionPanel() {
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		questionPanel.setBackground(Color.WHITE);
		mainPanel.add(questionPanel, BorderLayout.CENTER);
	}

	private void createSupervisorPanel() {
		supervisorPanel = new JPanel();
		supervisorPanel.setLayout(new BoxLayout(supervisorPanel, BoxLayout.Y_AXIS));
		supervisorPanel.setBackground(Color.WHITE);
		mainPanel.add(supervisorPanel, BorderLayout.CENTER);
	}

	private void startQuiz() {
		questionsFound = false;
		try {
			loadQuestions();
		}
		catch (FileNotFoundException exception) {
			headingDisplay.setForeground(Color.BLACK);
			headingDisplay.setText("Questions file not found.");
			labelTimer.setRepeats(false);
			labelTimer.restart();
		}
		catch (IOException exception) {
			headingDisplay.setForeground(Color.BLACK);
			headingDisplay.setText("Error reading from questions file.");
			labelTimer.setRepeats(false);
			labelTimer.restart();
		}
		catch (Exception except) {
			System.out.println(except.getMessage());
		}
		if (questionsFound) {
			round = 1;
			score = 0;
			i = 0;
			setUpQuizComponents();
			nextQuestion();
		}
	}

	private void loadQuestions() throws FileNotFoundException, IOException {
		questions = new ArrayList<Question>();
		missedQuestions = new ArrayList<Question>();
		// Construct the Scanner object for reading
		File inputFile = new File("questions.txt");
		Scanner in = new Scanner(inputFile);
		try {
			if (in.hasNextLine()) {
				while (in.hasNextLine()) {
					String question = in.nextLine();
					String answer = in.nextLine();
					String c1 = in.nextLine();
					String c2 = in.nextLine();
					String c3 = in.nextLine();

					questions.add(new Question(question, answer, c1, c2, c3));	
				}
				if (questions.size() > 0) {
					questionsFound = true;
				}
			}
			else {
				headingDisplay.setForeground(Color.BLACK);
				headingDisplay.setText("No data found in questions file.");
				labelTimer.setRepeats(false);
				labelTimer.restart();
			}			
		}
		finally {
			// Close the Scanner
			in.close();
		}
	}

	private void setUpQuizComponents() {
		createQuestionNumLabel();
		createQuestion();
		createRadioButtons();
		createSubmitButton();
		createNextQuestionButton();
		createButtonPanel();
		createBottomLabel();
		createSavePanel();
		addComponentsToQuestionPanel();
	}

	private void createQuestionNumLabel() {
		questionNumLabel = new JLabel();
		questionNumLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		questionNumLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		questionNumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	private void createQuestion() {
		question = new JLabel();
		question.setFont(new Font("Arial", Font.BOLD, 20));
		question.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		question.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	private void createRadioButtons() {
		aButton = new JRadioButton("A");
		aButton.setFont(new Font("Arial", Font.PLAIN, 20));
		bButton = new JRadioButton("B");
		bButton.setFont(new Font("Arial", Font.PLAIN, 20));
		cButton = new JRadioButton("C");
		cButton.setFont(new Font("Arial", Font.PLAIN, 20));
		dButton = new JRadioButton("D");
		dButton.setFont(new Font("Arial", Font.PLAIN, 20));
		buttonGroup = new ButtonGroup();
		buttonGroup.add(aButton);
		buttonGroup.add(bButton);
		buttonGroup.add(cButton);
		buttonGroup.add(dButton);
	}

	private void createSubmitButton() {
		submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
		submitButton.setMargin(new Insets(1, 0, 1, 0));
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String selectedOption = "";
				if (aButton.isSelected()) {
					selectedOption = aButton.getText();
				} 
				else if (bButton.isSelected()) {
					selectedOption = bButton.getText();
				} 
				else if (cButton.isSelected()) {
					selectedOption = cButton.getText();
				} 
				else if (dButton.isSelected()) {
					selectedOption = dButton.getText();
				}
				if (selectedOption == questions.get(i).getAnswer()) {
					headingDisplay.setForeground((new Color(0, 200, 00)));
					headingDisplay.setText("Correct!");
					labelTimer.setRepeats(false);
					labelTimer.restart();
					score++;
					afterAnswer();
				}
				else if (selectedOption == "") {
					headingDisplay.setForeground(Color.BLACK);
					headingDisplay.setText("No answer selected!");
					labelTimer.setRepeats(false);
					labelTimer.restart();
				}
				else {
					headingDisplay.setForeground(Color.RED);
					headingDisplay.setText("Sorry, that's not correct.");
					labelTimer.setRepeats(false);
					labelTimer.restart();
					if (round < 2) {
						missedQuestions.add(questions.get(i));
					}
					afterAnswer();
					if (round > 1 && i > questions.size() - missedQuestions.size()) {
						if (aButton.getText() == questions.get(i - 1).getAnswer()) {
							aButton.setEnabled(true);
							aButton.setForeground(Color.RED);
							aButton.setText(aButton.getText() + "   <---- Correct Answer");
						} 
						else if (bButton.getText() == questions.get(i - 1).getAnswer()) {
							bButton.setEnabled(true);
							bButton.setForeground(Color.RED);
							bButton.setText(bButton.getText() + "   <---- Correct Answer");
						} 
						else if (cButton.getText() == questions.get(i - 1).getAnswer()) {
							cButton.setEnabled(true);
							cButton.setForeground(Color.RED);
							cButton.setText(cButton.getText() + "   <---- Correct Answer");
						} 
						else if (dButton.getText() == questions.get(i - 1).getAnswer()) {
							dButton.setEnabled(true);
							dButton.setForeground(Color.RED);
							dButton.setText(dButton.getText() + "   <---- Correct Answer");
						}
					}	
				}
			}
		});
	}

	private void createNextQuestionButton() {
		nextQuestionButton = new JButton("Next Question");
		nextQuestionButton.setVisible(false);
		nextQuestionButton.setFont(new Font("Arial", Font.PLAIN, 20));
		nextQuestionButton.setMargin(new Insets(1, 0, 1, 0));
		nextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		nextQuestionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				nextQuestionButton.setVisible(false);
				submitButton.setVisible(true);
				enableButtons();
				if (round > 1) {
					nextQuestionButton.setText("Next Question");
					aButton.setForeground(Color.BLACK);
					bButton.setForeground(Color.BLACK);
					cButton.setForeground(Color.BLACK);
					dButton.setForeground(Color.BLACK);
				}
				nextQuestion();
			}
		});
	}

	private void createButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(aButton);
		buttonPanel.add(bButton);
		buttonPanel.add(cButton);
		buttonPanel.add(dButton);
	}

	private void createBottomLabel() {
		bottomLabel = new JLabel();
		bottomLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		bottomLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		bottomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	private void createSavePanel() {
		createSaveLabel();
		createNameField();
		createSaveScoreButton();
		savePanel = new JPanel();
		savePanel.setVisible(false);
		savePanel.setBackground(Color.WHITE);
		savePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		savePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		savePanel.add(saveLabel);
		savePanel.add(nameField);
		savePanel.add(saveScoreButton);
	}

	private void createSaveLabel() {
		saveLabel = new JLabel();
		saveLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createNameField() {
		nameField = new JTextField(10);
		nameField.setText("");
		nameField.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createSaveScoreButton() {
		saveScoreButton = new JButton("Save Score");
		saveScoreButton.setFont(new Font("Arial", Font.PLAIN, 20));
		saveScoreButton.setMargin(new Insets(1, 0, 1, 0));
		saveScoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (nameField.getText().equals("")) {
					headingDisplay.setForeground(Color.BLACK);
					headingDisplay.setText("Please enter your name.");
					labelTimer.setRepeats(false);
					labelTimer.restart();
				}
				else {
					saveScoreButton.setEnabled(false);
					nameField.setEnabled(false);
					try {
						saveScore();
					}
					catch (IOException exception) {
						headingDisplay.setForeground(Color.BLACK);
						headingDisplay.setText("Error saving score.");
						labelTimer.setRepeats(false);
						labelTimer.restart();
					}
					catch (Exception except) {
						System.out.println(except.getMessage());
					}
				}
			}
		});
	}

	private void addComponentsToQuestionPanel() {
		questionPanel.add(questionNumLabel);
		questionPanel.add(question);
		questionPanel.add(buttonPanel);
		questionPanel.add(submitButton);
		questionPanel.add(nextQuestionButton);
		questionPanel.add(bottomLabel);
		questionPanel.add(savePanel);
	}

	private void afterAnswer() {
		submitButton.setVisible(false);
		nextQuestionButton.setVisible(true);
		disableButtons();
		i++;
		if (!(i < questions.size())) {
			if (score == questions.size() || round > 1) {
				nextQuestionButton.setEnabled(false);
				bottomLabel.setText("Final Score: " + score + " out of " + (questions.size() - missedQuestions.size()));
				saveLabel.setText("Enter your name: ");
				savePanel.setVisible(true);
			}
			else {
				for (int i = 0; i < missedQuestions.size(); i++) {
					questions.add(missedQuestions.get(i));
				}
				nextQuestionButton.setText("Start Second Round");
				round++;
			}
		}
	}

	private void disableButtons() {
		aButton.setEnabled(false);
		bButton.setEnabled(false);
		cButton.setEnabled(false);
		dButton.setEnabled(false);
	}

	private void enableButtons() {
		aButton.setEnabled(true);
		bButton.setEnabled(true);
		cButton.setEnabled(true);
		dButton.setEnabled(true);
		buttonGroup.clearSelection();
	}

	private void saveScore() throws IOException {
		// Construct the PrintWriter object for writing
		PrintWriter out = new PrintWriter(new FileOutputStream(new File("scores.txt"), true));
		try {
			String scoreTotal = score + "/" + (questions.size() - missedQuestions.size());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String stringDate = sdf.format(date);
			out.printf("%s\n", nameField.getText());
			out.printf("%s\n", scoreTotal);
			out.printf("%s\n", stringDate);

			headingDisplay.setForeground(Color.BLACK);
			headingDisplay.setText("Name and score saved!");
			labelTimer.setRepeats(false);
			labelTimer.restart();	
		}
		finally {
			// Close the PrintWriter
			out.close();
		}
	}

	private void nextQuestion() {
		if (round < 2) {
			questionNumLabel.setText("Question " + (i + 1) + " of " + questions.size());
		}
		else {
			questionNumLabel.setText("Missed Question " + (i + 1 - questions.size() + missedQuestions.size()) + " of " + missedQuestions.size());
		}
		question.setText(questions.get(i).getQuestion());
		aButton.setText(questions.get(i).getAnswerList().get(0));
		bButton.setText(questions.get(i).getAnswerList().get(1));
		cButton.setText(questions.get(i).getAnswerList().get(2));
		dButton.setText(questions.get(i).getAnswerList().get(3));
	}

	private void setUpSupervisorComponents() {
		createSupervisorTopLabel();
		createAddQuestionsButton();
		createViewScoresButton();
		createAddQuestionPanel();
		createAddAnswerPanel();
		createAddChoiceOnePanel();
		createAddChoiceTwoPanel();
		createAddChoiceThreePanel();
		createQuestionSubmitButton();
		createScoresTablePanel();
		addComponentsToSupervisorPanel();
	}

	private void createSupervisorTopLabel() {
		supervisorTopLabel = new JLabel("What would you like to do?");
		supervisorTopLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		supervisorTopLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		supervisorTopLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	private void createAddQuestionsButton() {
		addQuestionsButton = new JButton("Add Questions");
		addQuestionsButton.setVisible(true);
		addQuestionsButton.setFont(new Font("Arial", Font.PLAIN, 20));
		addQuestionsButton.setMargin(new Insets(1, 0, 1, 0));
		addQuestionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addQuestionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				supervisorTopLabel.setText("Add a question by filling in the following fields");
				addQuestionsButton.setVisible(false);
				viewScoresButton.setVisible(false);
				addQuestionPanel.setVisible(true);
				addAnswerPanel.setVisible(true);
				addChoiceOnePanel.setVisible(true);
				addChoiceTwoPanel.setVisible(true);
				addChoiceThreePanel.setVisible(true);
				questionSubmitButton.setVisible(true);
			}
		});
	}

	private void createViewScoresButton() {
		viewScoresButton = new JButton("View Scores");
		viewScoresButton.setVisible(true);
		viewScoresButton.setFont(new Font("Arial", Font.PLAIN, 20));
		viewScoresButton.setMargin(new Insets(1, 0, 1, 0));
		viewScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewScoresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				supervisorTopLabel.setText("Student Scores");
				addQuestionsButton.setVisible(false);
				viewScoresButton.setVisible(false);
				try {
					viewScores();
				}
				catch (FileNotFoundException exception) {
					headingDisplay.setForeground(Color.BLACK);
					headingDisplay.setText("Scores file not found.");
					labelTimer.setRepeats(false);
					labelTimer.restart();
				}
				catch (IOException exception) {
					headingDisplay.setForeground(Color.BLACK);
					headingDisplay.setText("Error reading from scores file.");
					labelTimer.setRepeats(false);
					labelTimer.restart();
				}
				catch (Exception except) {
					System.out.println(except.getMessage());
				}
			}
		});
	}

	private void createAddQuestionPanel() {
		createAddQuestionLabel();
		createAddQuestionField();
		addQuestionPanel = new JPanel();
		addQuestionPanel.setVisible(false);
		addQuestionPanel.setBackground(Color.WHITE);
		addQuestionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addQuestionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		addQuestionPanel.add(addQuestionLabel);
		addQuestionPanel.add(addQuestionField);
	}

	private void createAddQuestionLabel() {
		addQuestionLabel = new JLabel("Enter a question:");
		addQuestionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddQuestionField() {
		addQuestionField = new JTextField(25);
		addQuestionField.setText("");
		addQuestionField.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddAnswerPanel() {
		createAddAnswerLabel();
		createAddAnswerField();
		addAnswerPanel = new JPanel();
		addAnswerPanel.setVisible(false);
		addAnswerPanel.setBackground(Color.WHITE);
		addAnswerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addAnswerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		addAnswerPanel.add(addAnswerLabel);
		addAnswerPanel.add(addAnswerField);
	}

	private void createAddAnswerLabel() {
		addAnswerLabel = new JLabel("Enter the correct answer:");
		addAnswerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddAnswerField() {
		addAnswerField = new JTextField(15);
		addAnswerField.setText("");
		addAnswerField.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddChoiceOnePanel() {
		createAddChoiceOneLabel();
		createAddChoiceOneField();
		addChoiceOnePanel = new JPanel();
		addChoiceOnePanel.setVisible(false);
		addChoiceOnePanel.setBackground(Color.WHITE);
		addChoiceOnePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addChoiceOnePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		addChoiceOnePanel.add(addChoiceOneLabel);
		addChoiceOnePanel.add(addChoiceOneField);
	}

	private void createAddChoiceOneLabel() {
		addChoiceOneLabel = new JLabel("Enter a choice:");
		addChoiceOneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddChoiceOneField() {
		addChoiceOneField = new JTextField(15);
		addChoiceOneField.setText("");
		addChoiceOneField.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddChoiceTwoPanel() {
		createAddChoiceTwoLabel();
		createAddChoiceTwoField();
		addChoiceTwoPanel = new JPanel();
		addChoiceTwoPanel.setVisible(false);
		addChoiceTwoPanel.setBackground(Color.WHITE);
		addChoiceTwoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addChoiceTwoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		addChoiceTwoPanel.add(addChoiceTwoLabel);
		addChoiceTwoPanel.add(addChoiceTwoField);
	}

	private void createAddChoiceTwoLabel() {
		addChoiceTwoLabel = new JLabel("Enter another choice:");
		addChoiceTwoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddChoiceTwoField() {
		addChoiceTwoField = new JTextField(15);
		addChoiceTwoField.setText("");
		addChoiceTwoField.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddChoiceThreePanel() {
		createAddChoiceThreeLabel();
		createAddChoiceThreeField();
		addChoiceThreePanel = new JPanel();
		addChoiceThreePanel.setVisible(false);
		addChoiceThreePanel.setBackground(Color.WHITE);
		addChoiceThreePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addChoiceThreePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		addChoiceThreePanel.add(addChoiceThreeLabel);
		addChoiceThreePanel.add(addChoiceThreeField);
	}

	private void createAddChoiceThreeLabel() {
		addChoiceThreeLabel = new JLabel("Enter a final choice:");
		addChoiceThreeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createAddChoiceThreeField() {
		addChoiceThreeField = new JTextField(15);
		addChoiceThreeField.setText("");
		addChoiceThreeField.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	private void createQuestionSubmitButton() {
		questionSubmitButton = new JButton("Submit");
		questionSubmitButton.setVisible(false);
		questionSubmitButton.setFont(new Font("Arial", Font.PLAIN, 20));
		questionSubmitButton.setMargin(new Insets(1, 0, 1, 0));
		questionSubmitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		questionSubmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (addQuestionField.getText().equals("") || addAnswerField.getText().equals("") || addChoiceOneField.getText().equals("") || 
					addChoiceTwoField.getText().equals("") || addChoiceThreeField.getText().equals("")) {
					headingDisplay.setForeground(Color.BLACK);
					headingDisplay.setText("Please fill in all the fields.");
					labelTimer.setRepeats(false);
					labelTimer.restart();
				}
				else {
					questionSubmitButton.setEnabled(false);
					addQuestionField.setEnabled(false);
					addAnswerField.setEnabled(false);
					addChoiceOneField.setEnabled(false);
					addChoiceTwoField.setEnabled(false);
					addChoiceThreeField.setEnabled(false);
					try {
						addQuestion();
					}
					catch (IOException exception) {
						headingDisplay.setForeground(Color.BLACK);
						headingDisplay.setText("Error adding question.");
						labelTimer.setRepeats(false);
						labelTimer.restart();
					}
					catch (Exception except) {
						System.out.println(except.getMessage());
					}
				}
			}
		});
	}

	private void createScoresTablePanel() {
		scoresTablePanel = new JPanel();
		scoresTablePanel.setVisible(false);
		scoresTablePanel.setBackground(Color.WHITE);
		scoresTablePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scoresTablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	private void addComponentsToSupervisorPanel() {
		supervisorPanel.add(supervisorTopLabel);
		supervisorPanel.add(addQuestionsButton);
		supervisorPanel.add(Box.createVerticalStrut(10));
		supervisorPanel.add(viewScoresButton);
		supervisorPanel.add(addQuestionPanel);
		supervisorPanel.add(addAnswerPanel);
		supervisorPanel.add(addChoiceOnePanel);
		supervisorPanel.add(addChoiceTwoPanel);
		supervisorPanel.add(addChoiceThreePanel);
		supervisorPanel.add(questionSubmitButton);
		supervisorPanel.add(scoresTablePanel);
		supervisorPanel.add(Box.createVerticalStrut(40));
	}

	private void addQuestion() throws IOException {
		// Construct the PrintWriter object for writing
		PrintWriter out = new PrintWriter(new FileOutputStream(new File("questions.txt"), true));
		try {
			out.printf("%s\n", addQuestionField.getText());
			out.printf("%s\n", addAnswerField.getText());
			out.printf("%s\n", addChoiceOneField.getText());
			out.printf("%s\n", addChoiceTwoField.getText());
			out.printf("%s\n", addChoiceThreeField.getText());

			headingDisplay.setForeground(Color.BLACK);
			headingDisplay.setText("New question saved!");
			labelTimer.setRepeats(false);
			labelTimer.restart();	
		}
		finally {
			// Close the PrintWriter
			out.close();
		}
	}

	private void viewScores() throws FileNotFoundException, IOException {
		// Construct the Scanner object for reading
		File inputFile = new File("scores.txt");
		Scanner in = new Scanner(inputFile);
		try {
			if (in.hasNextLine()) {
				DefaultTableModel model = new DefaultTableModel();
				JTable scoresTable = new JTable(model);
				model.addColumn("Name");
				model.addColumn("Score");
				model.addColumn("Date");
				while (in.hasNextLine()) {
					String name = in.nextLine();
					String score = in.nextLine();
					String date = in.nextLine();

					model.addRow(new Object[]{name, score, date});

				}
				scoresTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
				scoresTable.setFont(new Font("Arial", Font.PLAIN, 16));
				scoresTable.setRowHeight(scoresTable.getRowHeight() + 10);
				JScrollPane sp = new JScrollPane(scoresTable);
				sp.setPreferredSize(new Dimension(600, 300));
				scoresTablePanel.add(sp);
				scoresTablePanel.setVisible(true);
			}
			else {
				headingDisplay.setForeground(Color.BLACK);
				headingDisplay.setText("No data found in scores file.");
				labelTimer.setRepeats(false);
				labelTimer.restart();
			}			
		}
		finally {
			// Close the Scanner
			in.close();
		}
	}
}