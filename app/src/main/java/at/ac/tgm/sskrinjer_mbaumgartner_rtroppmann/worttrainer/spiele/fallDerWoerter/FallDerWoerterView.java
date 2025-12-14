package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SwingSpielView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter.FallDerWoerterModel.Feedback;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter.FallDerWoerterModel.FeedbackType;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortart;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;

public class FallDerWoerterView extends SwingSpielView<FallDerWoerterController, FallDerWoerterView> {

    private GamePanel gamePanel;
    private FallDerWoerterModel model;

    public FallDerWoerterView(FallDerWoerterController c) {
        super(c);
        this.model = c.getModel();
        
        gamePanel = new GamePanel();
        mainPanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        
        InputMap im = gamePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        
        ActionMap am = gamePanel.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "startLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "startLeft");
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "stopLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "stopLeft");
        
        am.put("startLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { c.setMovingLeft(true); }
        });
        am.put("stopLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { c.setMovingLeft(false); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "startRight");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "startRight");
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "stopRight");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "stopRight");

        am.put("startRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { c.setMovingRight(true); }
        });
        am.put("stopRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { c.setMovingRight(false); }
        });

        mainPanel.setLayout(new java.awt.BorderLayout());
        mainPanel.add(gamePanel, java.awt.BorderLayout.CENTER);
    }
    
    public void requestFocus() {
        gamePanel.requestFocusInWindow();
    }
    
    public void repaintGame() {
        gamePanel.repaint();
    }

    private class GamePanel extends JPanel {
        
        public GamePanel() {
            setBackground(UIManager.getColor("Panel.background"));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            int bucketHeight = 80;
            int bucketY = h - bucketHeight;
            
            int count = model.buckets.length;
            int bucketW = w / count;

            for (int i = 0; i < count; i++) {
                Wortart type = model.buckets[i];
                int x = i * bucketW;
                
                // 1. Determine Bucket Background Color
                Color bucketColor;
                if (i % 2 == 0) {
                    bucketColor = UIManager.getColor("Button.background");
                } else {
                    // Use a slightly different shade for alternating buckets
                    // Instead of "borderColor", we darken or lighten the button background slightly
                    // to ensure it looks good but distinct.
                    Color btnBg = UIManager.getColor("Button.background");
                    bucketColor = isDark(btnBg) ? btnBg.brighter() : btnBg.darker();
                }
                
                // 2. Fill Bucket
                g2.setColor(bucketColor);
                g2.fillRect(x, bucketY, bucketW, bucketHeight);
                
                // 3. Draw Border
                g2.setColor(UIManager.getColor("Component.borderColor")); // Better than Color.GRAY
                g2.drawRect(x, bucketY, bucketW, bucketHeight);
                
                // 4. Calculate Text Color dynamically (FIX FOR READABILITY)
                g2.setColor(getReadableTextColor(bucketColor));
                
                int fontSize = count > 5 ? 12 : 14; 
                g2.setFont(new Font("SansSerif", Font.BOLD, fontSize));
                drawCenteredString(g2, type.getName(), x + bucketW/2, bucketY + bucketHeight/2);
            }

            // --- Draw Word ---
            double wordXRatio = model.getX() / 600.0;
            double wordYRatio = model.getY() / 400.0;
            
            int screenX = (int) (wordXRatio * w);
            int screenY = (int) (wordYRatio * (h - bucketHeight));

            String wordText = model.getWordText();
            g2.setFont(new Font("SansSerif", Font.BOLD, 24));
            FontMetrics fm = g2.getFontMetrics();
            int txtW = fm.stringWidth(wordText);
            
            // Draw Word Bubble
            g2.setColor(UIManager.getColor("Component.accentColor"));
            g2.fillRoundRect(screenX - txtW/2 - 10, screenY - 25, txtW + 20, 40, 20, 20);
            
            // Draw Word Text (White text on Accent color is usually safe in FlatLaf)
            g2.setColor(Color.WHITE); 
            g2.drawString(wordText, screenX - txtW/2, screenY);

            // --- Draw HUD ---
            g2.setColor(UIManager.getColor("Label.foreground"));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g2.drawString("Streak: " + model.getStreak(), 20, 30);
            
            Feedback feedback = model.getFeedback();
            if (feedback != null && !feedback.message().isEmpty()) {
                g2.setColor(feedback.type() == FeedbackType.PRAISE ? new Color(0, 150, 0) : Color.RED);
                drawCenteredString(g2, feedback.message(), w/2, 50);
            }
        }
        
        /**
         * Calculates whether black or white text is more readable on the given background.
         */
        private Color getReadableTextColor(Color background) {
            // Calculate luminance (perceived brightness)
            double luminance = (0.299 * background.getRed() + 
                                0.587 * background.getGreen() + 
                                0.114 * background.getBlue()) / 255;

            // If bright > 0.5, use Black text. If dark < 0.5, use White text.
            return luminance > 0.5 ? Color.BLACK : Color.WHITE;
        }

        private boolean isDark(Color c) {
            double luminance = (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue()) / 255;
            return luminance < 0.5;
        }

        private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
            FontMetrics fm = g2.getFontMetrics();
            int len = fm.stringWidth(text);
            g2.drawString(text, x - len / 2, y + fm.getAscent() / 2 - 5);
        }
    }
}