package Pages;

import components.ColorPalette;
import components.ButtonStyle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import DataModel.*;

public class Leaderboard extends JPanel {

    private static final int LATEST_ATTEMPTS_PER_TOPIC = 3;
    private static final String SCORES_FILE = "src/database/scores.csv";
    private Map<String, List<ScoreAttempt>> latestAttempts = new HashMap<>();
    private Map<String, Integer> topScores = new HashMap<>();
    private UserAuth userAuth;

    public Leaderboard(CardLayout cardLayout, JPanel cardPanel, UserAuth userAuth) {
        this.userAuth = userAuth;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        setBackground(ColorPalette.BACKGROUND);

        // Load scores and process them
        loadScores();

        // Create UI components
        JLabel titleLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(ColorPalette.TEXT_COLOR);
        add(titleLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBackground(ColorPalette.BACKGROUND);

        // Left panel for latest attempts
        JPanel latestAttemptsPanel = createLatestAttemptsPanel();
        contentPanel.add(latestAttemptsPanel);

        // Right panel for top scores
        JPanel topScoresPanel = createTopScoresPanel();
        contentPanel.add(topScoresPanel);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(ColorPalette.BACKGROUND);
        JButton backButton = new JButton("Back");
        ButtonStyle.styleButton(backButton, 200);
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "panels.Dashboard");
        });
        bottomPanel.add(backButton);

        scrollPane.setViewportView(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    processScore(parts);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading scores from file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processScore(String[] parts) {
        String fullName = parts[0];
        String topic = parts[1];
        int score = Integer.parseInt(parts[2]);
//        String attemptIdentifier = (parts.length > 3) ? parts[2] : null; // Could be used to track specific attempts if needed

        latestAttempts.computeIfAbsent(topic, k -> new ArrayList<>()).add(new ScoreAttempt(fullName, score));

        // Update top score for this topic if necessary
        topScores.put(topic, Math.max(topScores.getOrDefault(topic, 0), score));
    }

    private JPanel createLatestAttemptsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorPalette.BACKGROUND);

        for (Map.Entry<String, List<ScoreAttempt>> entry : latestAttempts.entrySet()) {
            String topic = entry.getKey();
            List<ScoreAttempt> attempts = entry.getValue().stream()
                    .sorted((a, b) -> b.getScore() - a.getScore()) // Sort by score descending
                    .limit(LATEST_ATTEMPTS_PER_TOPIC)
                    .collect(Collectors.toList());

            JLabel topicLabel = new JLabel("Latest attempts for " + topic, SwingConstants.LEFT);
            topicLabel.setFont(new Font("Arial", Font.BOLD, 18));
            topicLabel.setForeground(ColorPalette.TEXT_COLOR);
            panel.add(topicLabel);

            for (ScoreAttempt attempt : attempts) {
                JLabel attemptLabel = new JLabel(
                        "Score: " + attempt.getScore() + " (Attempt by " + attempt.getFullName() + ")", SwingConstants.LEFT);
                attemptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                attemptLabel.setForeground(ColorPalette.BUTTON_FOREGROUND);
                panel.add(attemptLabel);
            }

            panel.add(Box.createVerticalStrut(20)); // Add some vertical space between topics
        }

        return panel;
    }


    private JPanel createTopScoresPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorPalette.BACKGROUND);

        for (Map.Entry<String, Integer> entry : topScores.entrySet()) {
            String topic = entry.getKey();
            int score = entry.getValue();

            JLabel topicLabel = new JLabel("Top score for " + topic + ": " + score, SwingConstants.LEFT);
            topicLabel.setFont(new Font("Arial", Font.BOLD, 18));
            topicLabel.setForeground(ColorPalette.TEXT_COLOR);
            panel.add(topicLabel);

            panel.add(Box.createVerticalStrut(10)); // Add some vertical space between entries
        }

        return panel;
    }

    // Inner class to represent a score attempt
    private static class ScoreAttempt {
        private final String fullName;
        private final int score;

        public ScoreAttempt(String fullName, int score) {
            this.fullName = fullName;
            this.score = score;
        }

        public String getFullName() {
            return fullName;
        }

        public int getScore() {
            return score;
        }
    }
}

