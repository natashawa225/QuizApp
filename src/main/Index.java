package main;

import DataModel.UserAuth;
import Pages.LoginPage;
import Pages.RegisterPage;
import components.ButtonStyle;
import components.ColorPalette;

import javax.swing.*;
import java.awt.*;

public class Index extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private UserAuth userAuth;

    public Index(CardLayout cardLayout, JPanel cardPanel, UserAuth userAuth) {
        // Set up the JFrame
        this.userAuth = userAuth;  // Set userAuth
        setTitle("Quiz Application");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        // Initialize the main panel (main.Index), login panel, and register panel
        cardPanel.add(createMainPanel(), "main.Index");
        cardPanel.add(new LoginPage(cardLayout, cardPanel, userAuth).getLoginPanel(), "LoginPage");
        cardPanel.add(new RegisterPage(cardLayout, cardPanel, userAuth).getRegisterPanel(), "RegisterPage");

        cardLayout.show(cardPanel, "main.Index"); // sets the first page to be main.Index.java

        add(cardPanel);
        setVisible(true);
    }

    // Create the main panel with buttons for Login and Signup
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ColorPalette.BACKGROUND);

        // Title label
        JLabel titleLabel = new JLabel("Quiz Application");
        titleLabel.setFont(new Font("Calibre", Font.BOLD, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(ColorPalette.TEXT_COLOR);
        JLabel titleLabel2 = new JLabel("Test your knowledge!");
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel2.setForeground(ColorPalette.TEXT_COLOR);
        titleLabel2.setFont(new Font("Calibre", Font.BOLD, 20));

        // Login button
        JButton loginButton = new JButton("Login");
        ButtonStyle.styleButton(loginButton, 200);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> cardLayout.show(cardPanel, "LoginPage"));

        // Signup button
        JButton signupButton = new JButton("Signup");
        ButtonStyle.styleButton(signupButton, 200);
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setPreferredSize(new Dimension(200, 40));
        signupButton.addActionListener(e -> cardLayout.show(cardPanel, "RegisterPage"));

        // Add components to the main panel with spacing
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(titleLabel2);
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(signupButton);

        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserAuth userAuth = new UserAuth();  // instance of dataModel.UserAuthentication

            CardLayout cardLayout = new CardLayout();
            JPanel cardPanel = new JPanel(cardLayout); // creates to hold different panels

            new Index(cardLayout, cardPanel, userAuth); // initialize
        });
    }
}
