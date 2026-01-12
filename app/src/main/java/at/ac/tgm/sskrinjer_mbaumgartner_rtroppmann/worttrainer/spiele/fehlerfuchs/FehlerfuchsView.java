package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SwingSpielView;

public class FehlerfuchsView
        extends SwingSpielView<FehlerfuchsController, FehlerfuchsView> {

    // UI Komponenten
    private final JLabel wordLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
    private final JTextField inputField = new JTextField();
    private final JButton submitBtn = new JButton("PRÜFEN"); // Großschreibung sieht oft besser aus auf Buttons
    private final JLabel instructionLabel = new JLabel("Korrigiere das Wort:", SwingConstants.CENTER);

    public FehlerfuchsView(FehlerfuchsController c) {
        super(c);
        initLayout();
        initStyling();
        initListeners(c);
    }

    private void initLayout() {
        mainPanel.setLayout(new GridBagLayout());
        // Padding zum Rand hin
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        // WICHTIG: fill = NONE verhindert, dass sich die Elemente aufblähen
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; // Alles bleibt schön in der Mitte

        // 1. Anweisung
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(instructionLabel, gbc);

        // 2. Das fehlerhafte Wort
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 30, 0); // Viel Platz nach unten zum Input
        mainPanel.add(wordLabel, gbc);

        // 3. Eingabefeld
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        // Wir setzen eine feste, große Größe.
        // 400px Breite ist auf Laptops gut und auf großen Screens nicht zu klein.
        inputField.setPreferredSize(new Dimension(400, 50));
        mainPanel.add(inputField, gbc);

        // 4. Button
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        // Button auch schön groß
        submitBtn.setPreferredSize(new Dimension(200, 50));
        mainPanel.add(submitBtn, gbc);

        // 5. Feedback
        gbc.gridy++;
        mainPanel.add(feedbackLabel, gbc);
    }

    private void initStyling() {
        // --- Schriftgrößen massiv erhöhen für bessere Lesbarkeit im Vollbild ---

        // Wort: Sehr groß (42pt)
        wordLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1");
        wordLabel.setFont(wordLabel.getFont().deriveFont(Font.BOLD, 42f));

        // Input: Groß (24pt) für angenehmes Tippen
        inputField.setFont(inputField.getFont().deriveFont(24f));
        inputField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Korrektur hier eingeben...");
        inputField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        // Runde Ecken für modernes Feeling
        inputField.putClientProperty(FlatClientProperties.COMPONENT_ROUND_RECT, true);

        // Button: Groß und fett (18pt)
        submitBtn.setFont(submitBtn.getFont().deriveFont(Font.BOLD, 18f));
        submitBtn.putClientProperty(FlatClientProperties.STYLE_CLASS, "accent");
        submitBtn.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        // Anweisung: Etwas dezenter, aber lesbar (16pt)
        instructionLabel.setFont(instructionLabel.getFont().deriveFont(16f));
        instructionLabel.setForeground(Color.GRAY);

        // Feedback: Auch gut lesbar (18pt)
        feedbackLabel.setFont(feedbackLabel.getFont().deriveFont(Font.BOLD, 18f));
    }

    private void initListeners(FehlerfuchsController c) {
        ActionListener submitAction = e -> c.onWordSubmitted(inputField.getText());
        submitBtn.addActionListener(submitAction);
        inputField.addActionListener(submitAction);
    }

    public void renderFromModel(FehlerfuchsModel model) {
        wordLabel.setText(model.getFehlerhaftesWort());
        feedbackLabel.setText(" ");
        inputField.setText("");
        inputField.requestFocusInWindow();
        feedbackLabel.setForeground(null);
    }

    public void showFeedback(FehlerfuchsModel.Feedback feedback) {
        feedbackLabel.setText(feedback.message());

        // Simpler Farb-Check (Logik ggf. anpassen)
        if (feedback.toString().toLowerCase().contains("richtig")) {
            feedbackLabel.setForeground(new Color(40, 167, 69));
        } else {
            feedbackLabel.setForeground(new Color(220, 53, 69));
        }
    }

    public void setInputEnabled(boolean enabled) {
        inputField.setEnabled(enabled);
        submitBtn.setEnabled(enabled);
    }
}