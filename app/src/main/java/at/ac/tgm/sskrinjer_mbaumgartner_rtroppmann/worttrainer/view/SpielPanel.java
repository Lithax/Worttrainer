package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

/**
 * @author mhbau
 * @version 1.2
 */
public class SpielPanel extends JPanel {
    private final AtomicReference<? extends SpieleListener> listenerRef;
    private static final int CARD_WIDTH = 250;
    private static final int CARD_HEIGHT = 280;
    private static final int IMAGE_SIZE = 120;
    private static final int MAX_DESC_LENGTH = 90;

    /**
     * @param name         The title of the game
     * @param beschreibung The description
     * @param iconStream   The input stream for the game icon
     * @param l            Reference to the listener
     */
    public SpielPanel(String name, String beschreibung, InputStream iconStream, AtomicReference<? extends SpieleListener> l) {
        this.listenerRef = l;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setBackground(UIManager.getColor("Panel.background"));
        
        putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 20;" +
                "border: 1,1,1,1, $Component.borderColor, 1, 20");

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setIcon(processIcon(iconStream));
        imageLabel.setBorder(new EmptyBorder(15, 10, 5, 10));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(new EmptyBorder(0, 12, 12, 12));

        JLabel titleLabel = new JLabel(name);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +2");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String displayDesc = beschreibung;
        boolean isTruncated = false;

        if (beschreibung != null && beschreibung.length() > MAX_DESC_LENGTH) {
            int cutOffIndex = beschreibung.lastIndexOf(" ", MAX_DESC_LENGTH);
            if (cutOffIndex == -1) cutOffIndex = MAX_DESC_LENGTH;
            
            displayDesc = beschreibung.substring(0, cutOffIndex) + "...";
            isTruncated = true;
        }

        JTextArea descArea = new JTextArea(displayDesc);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setFocusable(false);
        descArea.setOpaque(false);
        descArea.setBorder(null);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descArea.putClientProperty(FlatClientProperties.STYLE, "foreground: $Label.disabledForeground; font: 90%");
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descArea);

        if (isTruncated) {
            textPanel.add(Box.createVerticalStrut(5));
            JLabel readMoreLabel = new JLabel("<html><u>Mehr lesen</u></html>");
            readMoreLabel.setForeground(UIManager.getColor("Component.accentColor"));
            readMoreLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            readMoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            readMoreLabel.putClientProperty(FlatClientProperties.STYLE, "font: small");

            readMoreLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(
                            SwingUtilities.getWindowAncestor(SpielPanel.this), 
                            createHtmlDescription(beschreibung), 
                            name, 
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    e.consume();
                }
            });
            textPanel.add(readMoreLabel);
        }

        add(imageLabel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!e.isConsumed()) {
                    SpieleListener listener = listenerRef.get();
                    if (listener != null) listener.onSpielSelected(name);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(UIManager.getColor("Button.hoverBackground"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(UIManager.getColor("Panel.background"));
            }
        });
    }

    private String createHtmlDescription(String text) {
        return "<html><body style='width: 300px;'>" + text + "</body></html>";
    }

    private ImageIcon processIcon(InputStream stream) {
        if (stream == null) return null;
        try {
            BufferedImage img = ImageIO.read(stream);
            if (img == null) return null;

            Image scaled = SwingUtil.makeRoundedImage(img, IMAGE_SIZE, IMAGE_SIZE, 20);
            return new ImageIcon(scaled);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}