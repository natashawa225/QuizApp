package Pages;


import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import DataModel.*;
import components.*;
import components.StyleField;

public class RegisterPage {
    public JPanel registerPanel;
    private UserAuth userAuth;  // Instance of dataModel.UserAuthentication

    public RegisterPage(CardLayout cardLayout, JPanel cardPanel, UserAuth userAuth) {
        this.userAuth = userAuth;
        registerPanel = new JPanel(new BorderLayout());

        // Top panel with back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        ButtonStyle.styleButton(backButton, 80);
        backButton.setMaximumSize(new Dimension(100,30));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "main.Index"));
        topPanel.setBackground(ColorPalette.BACKGROUND);
        topPanel.add(backButton);
        registerPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for registration components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(ColorPalette.BACKGROUND);

        // Title label
        JLabel titleLabel = new JLabel("Create a new account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(ColorPalette.TEXT_COLOR);

        // Username field
        JTextField registerUsernameField = new JTextField("Username");
        StyleField.FieldStyle(registerUsernameField);

        // Full Name field
        JTextField fullNameField = new JTextField("Full Name");
        StyleField.FieldStyle(fullNameField);

        // Password field
        JPasswordField registerPasswordField = new JPasswordField("Password");
        StyleField.FieldStyle(registerPasswordField);

        // Confirm Password field
        JPasswordField confirmPasswordField = new JPasswordField("Confirm Password");
        StyleField.FieldStyle(confirmPasswordField);

        // Register button
        JButton registerButton = new JButton("Register");
        ButtonStyle.styleButton(registerButton, 200);
        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listener for register button
        registerButton.addActionListener(e -> {
            String username = registerUsernameField.getText().trim(); // Correct field name
            String fullName = fullNameField.getText().trim(); // Get the full name
            String password = new String(registerPasswordField.getPassword()).trim(); // Correct field name
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim(); // Correct field name

            boolean nullCheck = username.equals("") || fullName.equals("")
                    || password.equals("") || confirmPassword.equals("");
            boolean placeHolderCheck = username.equals("Username") || fullName.equals("Full Name") ||
                    password.equals("Password") || confirmPassword.equals("Confirm Password");

            // Password checks
            if(password.length() < 5){
                JOptionPane.showMessageDialog(null, "Password must be at least 5 characters");
                return;
            }

            if(nullCheck || placeHolderCheck) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
                return;
            }

            // Call the register method of dataModel.UserAuthentication
            if (userAuth.register(username, fullName, password)) {
                JOptionPane.showMessageDialog(null, "Registration Successful!");
                cardLayout.show(cardPanel, "main.Index");  // Navigate back to index page
            } else {
                JOptionPane.showMessageDialog(null, "Username already taken. Please try again.");
            }
        });

        // Add components to center panel
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(registerUsernameField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(fullNameField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(registerPasswordField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(confirmPasswordField);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(registerButton);

        // Size and alignment to be at the center of the screen
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerPanel.add(centerPanel, BorderLayout.CENTER);
    }

    public JPanel getRegisterPanel() {
        return registerPanel;
    }
}

