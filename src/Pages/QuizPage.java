package Pages;


import javax.swing.*;
import java.awt.*;
import java.io.*;

import java.util.Collections;
import java.util.List;
import DataModel.*;
import components.*;
import xjtlu.cpt111.assignment.quiz.model.Question;
import xjtlu.cpt111.assignment.quiz.model.Option;

public class QuizPage extends JPanel {
    private final List<Question> quizQuestions;
    private int currentQuestionIndex = 0;
    private int correctAnswerCount = 0;
    private int questionNumber = 1;
    private String topic = "";
    private String currentUser = "";

    private final JTextArea questionArea;
    private final JLabel topicLabel;
    private final JLabel difficultyLabel;
    private final JRadioButton[] optionButtons;
    private final JButton nextButton;
    private final ButtonGroup optionGroup;
    private final UserAuth userAuth;

    public QuizPage(List<Question> selectedQuestions, CardLayout cardLayout, JPanel cardPanel, UserAuth userAuth, String username) {
        this.currentUser = username;
        this.userAuth = userAuth;
        // Shuffle and select questions
        Collections.shuffle(selectedQuestions);
        this.quizQuestions = selectedQuestions;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        setBackground(ColorPalette.BACKGROUND);

        // Top panel for question info
        JPanel questionInfoPanel = new JPanel(new GridLayout(3, 1));
        questionInfoPanel.setBackground(ColorPalette.BACKGROUND);
        topicLabel = new JLabel();
        difficultyLabel = new JLabel();
        questionArea = new JTextArea();
        questionArea.setBackground(ColorPalette.BACKGROUND);
        questionArea.setFont(new Font("Arial", Font.BOLD, 18));
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);

        questionInfoPanel.add(topicLabel);
        questionInfoPanel.add(difficultyLabel);
        questionInfoPanel.add(questionArea);

        // Center panel for options
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setForeground(ColorPalette.BUTTON_FOREGROUND);
            optionButtons[i].setBackground(ColorPalette.BACKGROUND);
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionsPanel.setBackground(ColorPalette.BACKGROUND);
            optionsPanel.add(optionButtons[i]);
            optionGroup.add(optionButtons[i]);

            // Add action listener for options
            int optionIndex = i; // Preserve index for lambda
            optionButtons[i].addActionListener(e -> handleOptionSelection(optionIndex));
        }

        // Bottom panel for navigation (Next and Back Buttons)
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(ColorPalette.BACKGROUND);

        // Quit button
        JButton backButton = new JButton("Quit");
        ButtonStyle.styleButton(backButton, 200); // Custom styling
        backButton.addActionListener(e -> {
            // Confirmation dialog before quitting the quiz
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to quit the quiz and return to the dashboard?",
                    "Quit Quiz",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                cardLayout.show(cardPanel, "panels.Dashboard"); // Navigate back to Dashboard
            }
        });

        // Next button
        nextButton = new JButton("Next");
        ButtonStyle.styleButton(nextButton, 200); // Custom styling
        nextButton.setEnabled(false); // Disabled until an answer is selected
        nextButton.addActionListener(e -> loadNextQuestion(cardLayout, cardPanel));

        // Add buttons to the navigation panel
        navigationPanel.add(backButton);
        navigationPanel.add(nextButton);

        // Add panels to main layout
        add(questionInfoPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);

        // Load the first question
        loadQuestion(cardLayout, cardPanel);
    }


    private void loadQuestion(CardLayout cardLayout, JPanel cardPanel) {
        if (currentQuestionIndex >= quizQuestions.size()) {
            showFinalScore(cardLayout, cardPanel);
            return;
        }

        // Get current question
        Question question = quizQuestions.get(currentQuestionIndex);
        this.topic = question.getTopic();
        topicLabel.setText("Topic: " + question.getTopic());
        topicLabel.setForeground(ColorPalette.TEXT_COLOR);
        topicLabel.setFont(new Font("Arial", Font.BOLD, 18));
        difficultyLabel.setText("Difficulty: " + question.getDifficulty());
        difficultyLabel.setForeground(ColorPalette.TEXT_COLOR);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionArea.setText("Question " + questionNumber +": " + question.getQuestionStatement());
        questionArea.setForeground(ColorPalette.TEXT_COLOR);
        questionArea.setFont(new Font("Arial", Font.BOLD, 18));

        // Set options
        for (int i = 0; i < question.getOptions().length; i++) {
            optionButtons[i].setText(question.getOptions()[i].getAnswer());
            optionButtons[i].setEnabled(true);
        }
        for (int i = question.getOptions().length; i < optionButtons.length; i++) {
            optionButtons[i].setText("");
            optionButtons[i].setEnabled(false);
        }

        optionGroup.clearSelection(); // clears selection for the next question
        nextButton.setEnabled(false); // disabled until an option is selected
    }

    private void handleOptionSelection(int selectedIndex) {
        // Get current question and selected option
        Question question = quizQuestions.get(currentQuestionIndex);
        Option selectedOption = question.getOptions()[selectedIndex];

        // Check if the answer is correct
        if (selectedOption.isCorrectAnswer()) {
            correctAnswerCount++;
            JOptionPane.showMessageDialog(this, "Correct answer!", "Feedback", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Wrong answer!", "Feedback", JOptionPane.ERROR_MESSAGE);
        }

        // Disable all option buttons after selection
        for (JRadioButton button : optionButtons) {
            button.setEnabled(false);
        }

        nextButton.setEnabled(true);
    }

    private void loadNextQuestion(CardLayout cardLayout, JPanel cardPanel) {
        currentQuestionIndex++;
        questionNumber++;
        loadQuestion(cardLayout, cardPanel);
    }

    private void showFinalScore(CardLayout cardLayout, JPanel cardPanel) {
        int response = JOptionPane.showOptionDialog(
                this,
                "Quiz completed!\nCorrect ansewr: " + correctAnswerCount + "\nPercentage: " + correctAnswerCount * 10 + "%",
                "Quiz finished!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                new ImageIcon("src/customization/check.png"),
                new String[]{"Return", "Cancel"},
                "Return" // Default selected button
        );
        if (response == JOptionPane.YES_OPTION) {
            cardLayout.show(cardPanel, "panels.Dashboard"); // Navigate back to Dashboard
        }


        // Save score to file
        saveScoreToFile(correctAnswerCount, topic);

        // Optionally, notify parent component to switch back to dashboard
        Container parent = getParent();
        if (parent instanceof JFrame) {
            ((JFrame) parent).dispose();
        }
    }

    private void saveScoreToFile(int score, String topic) {
        String filename = "src/database/scores.csv";
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(userAuth.getFullname(this.currentUser)+","+topic+"," + score + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving score to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public JPanel getQuizPanel(){
        return this;
    }
}
