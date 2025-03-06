package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StyleField {
    public static final Dimension dimension = new Dimension(200, 30);

    public static void FieldStyle(JTextField textField) {
        textField.setPreferredSize(dimension);
        textField.setMaximumSize(dimension);
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField.setForeground(Color.LIGHT_GRAY); // Set placeholder color

        String username = textField.getText();

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (username.equals("Username") || username.equals("Full Name")) {
                    textField.setText(""); // Remove placeholder text
                    textField.setForeground(Color.BLACK); // Reset text color
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(textField.getText()).isEmpty()) {
                    textField.setForeground(Color.LIGHT_GRAY); // Placeholder text color
                    textField.setText(username); // Reset to placeholder text
                }
            }
        });
    }
    public static void FieldStyle(JPasswordField passwordField) {
        passwordField.setPreferredSize(dimension);
        passwordField.setMaximumSize(dimension);
        passwordField.setHorizontalAlignment(JTextField.LEFT);
        passwordField.setForeground(Color.LIGHT_GRAY);

        String password = String.valueOf(passwordField.getPassword());

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (password.equals("Password") || password.equals("Confirm Password")) {
                    passwordField.setText(""); // Remove placeholder text
                    passwordField.setForeground(Color.BLACK); // Reset text color
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.LIGHT_GRAY); // Placeholder text color
                    passwordField.setText(password); // Reset to placeholder text
                }
            }
        });
    }
}
