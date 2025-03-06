package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import DataModel.*;
import components.*;
import xjtlu.cpt111.assignment.quiz.model.Question;

public class Dashboard {
    private JPanel dashboardPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel welcomeLabel;
    private JLabel menuLabel;
    private JButton leaderboardButton;
    private JButton logoutButton;
    private UserAuth userAuth;

    public Dashboard(CardLayout cardLayout, JPanel cardPanel, UserAuth userAuth, String username) {

        // Initialize the main dashboard panel
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(2, 1));
        dashboardPanel.setBackground(ColorPalette.BACKGROUND);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));

        // This panel contains menu buttons
        topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.setBackground(ColorPalette.BACKGROUND);

        // Welcome message
        welcomeLabel = new JLabel("Welcome " + userAuth.getFullname(username) + "!");
        welcomeLabel.setForeground(ColorPalette.TEXT_COLOR);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        topPanel.add(welcomeLabel);

        // Menu label
        menuLabel = new JLabel("Menu");
        menuLabel.setForeground(ColorPalette.TEXT_COLOR);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 20));
        menuLabel.setHorizontalAlignment(SwingConstants.LEFT);
        menuLabel.setVerticalAlignment(SwingConstants.CENTER);
        topPanel.add(menuLabel);

        // Buttons for Leaderboard and Logout
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(ColorPalette.BACKGROUND);

        leaderboardButton = new JButton("Leaderboard");
        ButtonStyle.styleButton(leaderboardButton, 200);

        logoutButton = new JButton("Logout");
        ButtonStyle.styleButton(logoutButton, 200);

        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Leaderboard leaderboardPanel = new Leaderboard(cardLayout, cardPanel, userAuth);
                cardPanel.add(leaderboardPanel, "Leaderboard");
                cardLayout.show(cardPanel, "Leaderboard");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(dashboardPanel, "You have logged out!");
                cardLayout.show(cardPanel, "main.Index"); // Switches back the index page when logged out
            }
        });

        buttonPanel.add(leaderboardButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel);
        dashboardPanel.add(topPanel);

        // Setup the bottom panel
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(ColorPalette.BACKGROUND);

        JPanel labelPanel = new JPanel(); // Separate panel for text
        labelPanel.setBackground(ColorPalette.BACKGROUND);
        JLabel topicLabel = new JLabel("Choose topic");
        topicLabel.setForeground(ColorPalette.TEXT_COLOR);
        topicLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topicLabel.setHorizontalAlignment(SwingConstants.LEFT);
        labelPanel.add(topicLabel);

        JPanel buttonGridPanel = new JPanel(new GridLayout(2, 3, 30, 35)); // Panel for topic buttons
        buttonGridPanel.setBackground(ColorPalette.BACKGROUND);

        // Renders the available topics dynamically on buttons
        File questionPATH = new File("src/topicQuestions");
        for (File file : questionPATH.listFiles()) {
            String fileName = file.getName();
            String topicName = "";
            if (fileName.startsWith("QUES-") && fileName.endsWith(".xml")) {
                // Get the topic name by removing "QUES-" and ".xml"
                topicName = fileName.substring(5, fileName.lastIndexOf("."));
                if(topicName.contains("_")){
                    topicName = topicName.replace("_", " ");
                }

                JButton topicButton = new JButton(topicName); // Use the topic name
                ButtonStyle.styleButton(topicButton, 200);

                // When clicked on the button, it loads the questions and quiz
                String finalTopicName = topicName;
                topicButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Dialog to either start or cancel the quiz
                        int response = JOptionPane.showOptionDialog(
                                dashboardPanel,
                                "Chosen Topic: " + finalTopicName + "\nTotal Question: 10" + "\nDo you wish to proceed?",
                                "Confirm Topic Selection",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                new ImageIcon("src/customization/checkIcon.png"),
                                new String[]{"Start", "Cancel"},
                                "Start"
                        );

                        // Checks if the user has decided to do the quiz
                        if (response == JOptionPane.YES_OPTION) { // User clicked "Start"
                            File topicFile = new File("src/topicQuestions/QUES-" + finalTopicName.replace(" ", "_") + ".xml");
                            List<Question> topicQuestions = QuizLoader.loadQuestions(topicFile);

                            if (topicQuestions.isEmpty()) {
                                JOptionPane.showMessageDialog(dashboardPanel, "No questions available for this topic.");
                                return;
                            }

                            // Load the QuizPanel
                            QuizPage quizPanel = new QuizPage(topicQuestions, cardLayout, cardPanel, userAuth, username);
                            cardPanel.add(quizPanel, "panels.QuizPanel");
                            cardLayout.show(cardPanel, "panels.QuizPanel");
                        }
                    }
                });



                buttonGridPanel.add(topicButton);
            }
        }

        // Integrate all panels to the outer one
        bottomPanel.add(labelPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonGridPanel, BorderLayout.CENTER);
        dashboardPanel.add(bottomPanel);
    }

    public JPanel getDashboardPanel() {
        return dashboardPanel;
    }
}

