package components;

import javax.swing.*;
import java.awt.*;

public class ButtonStyle {
    public static void styleButton(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 30));
        button.setMinimumSize(new Dimension(width, 30));
        button.setMaximumSize(new Dimension(width, 30));
        button.setBackground(ColorPalette.BUTTON_BACKGROUND);
        button.setForeground(ColorPalette.BUTTON_FOREGROUND);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setHorizontalAlignment(SwingConstants.HORIZONTAL);
    }
}

