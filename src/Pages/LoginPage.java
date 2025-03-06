package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import DataModel.UserAuth;
import components.*;
import DataModel.*;

public class LoginPage {
    private JPanel loginPanel;
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private UserAuth userAuth;
    private JLabel errorLabel;  // Move error label here to manage visibility

    // Modify constructor to accept userAuth
    public LoginPage(CardLayout cardLayout, JPanel cardPanel, UserAuth userAuth) {
        this.userAuth = userAuth;  // Initialize userAuth here
        loginPanel = new JPanel(new BorderLayout());

        // Top panel with back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        ButtonStyle.styleButton(backButton, 80);
        backButton.setMaximumSize(new Dimension(100,30));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "main.Index"));
        topPanel.setBackground(ColorPalette.BACKGROUND);
        topPanel.add(backButton);
        loginPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for login components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(ColorPalette.BACKGROUND);

        // Title label
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(ColorPalette.TEXT_COLOR);

        // Username field
        loginUsernameField = new JTextField("Username");
        FieldStyle(loginUsernameField);
        StyleField.FieldStyle(loginUsernameField);

        // Password field
        loginPasswordField = new JPasswordField("Password");
        FieldStyle(loginPasswordField);
        StyleField.FieldStyle(loginPasswordField);

        // Error message label (Initially hidden)
        errorLabel = new JLabel("Incorrect username or password");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);

        // Login button
        JButton loginButton = new JButton("Login");
        ButtonStyle.styleButton(loginButton, 200);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText().trim();
            String password = new String(loginPasswordField.getPassword()).trim();
            if (userAuth.login(username, password)) {
                JOptionPane.showMessageDialog(this.getLoginPanel(), "Login successful!");

                // Create and add the dashboard with the username
                Dashboard dashboard = new Dashboard(cardLayout, cardPanel, userAuth, username);
                cardPanel.add(dashboard.getDashboardPanel(), "panels.Dashboard");

                // Navigate to the dashboard
                cardLayout.show(cardPanel, "panels.Dashboard");

                errorLabel.setVisible(false); // Hide error label on successful login
            } else {
                JOptionPane.showMessageDialog(this.getLoginPanel(), "Invalid username or password.");
                errorLabel.setVisible(true); // Show error label on failed login
            }
        });

        JLabel forgotPasswordLabel = new JLabel("<html>Forgot Password?</html>", SwingConstants.CENTER);
        forgotPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordLabel.setForeground(ColorPalette.TEXT_COLOR);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the password retrieval dialog
                openForgotPasswordDialog();
            }
        });

        // Register link label below login button
        JLabel registerLinkLabel = new JLabel("<html>Don't have an account? <a href=''>Register Now!</a></html>", SwingConstants.CENTER);
        registerLinkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerLinkLabel.setForeground(ColorPalette.TEXT_COLOR);
        registerLinkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        registerLinkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "RegisterPage");
            }
        });

        // Add components to the center panel
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loginUsernameField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(loginPasswordField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(errorLabel);  // Error label added to the panel
        centerPanel.add(forgotPasswordLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(registerLinkLabel);

        // Add the center panel to the main panel
        loginPanel.add(centerPanel, BorderLayout.CENTER);
    }

    // Method to open the "Forgot Password" dialog
    private void openForgotPasswordDialog() {
        JTextField fullNameField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter your full name:"));
        panel.add(fullNameField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Forgot Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String fullName = fullNameField.getText().trim();
            String password = userAuth.getPasswordByFullName(fullName);

            if (password != null) {
                JOptionPane.showMessageDialog(null, "Your password is: " + password);
            } else {
                JOptionPane.showMessageDialog(null, "No user found with that full name.");
            }
        }
    }

    public static void FieldStyle(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setMaximumSize(new Dimension(200, 30));
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField.setForeground(Color.LIGHT_GRAY); // Set placeholder color
    }
    public static void FieldStyle(JPasswordField passwordField) {
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordField.setHorizontalAlignment(JTextField.LEFT);
        passwordField.setForeground(Color.LIGHT_GRAY);
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }
}

