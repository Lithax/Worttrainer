package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


import com.formdev.flatlaf.FlatClientProperties;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class SpielPanel extends JPanel {
    private final AtomicReference<? extends SpieleListener> listenerRef;
    private static final int CARD_WIDTH = 220;
    private static final int IMAGE_HEIGHT = 120;

    /**
     * @param name         The title of the game
     * @param beschreibung The description
     * @param iconStream   The input stream for the game icon
     * @param l            Reference to the listener
     */
    public SpielPanel(String name, String beschreibung, InputStream iconStream, AtomicReference<? extends SpieleListener> l) {
        this.listenerRef = l;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(CARD_WIDTH, 200));
        setBackground(UIManager.getColor("Panel.background"));
        
        putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 20;" +
                "border: 1,1,1,1, $Component.borderColor, 1, 20");

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setIcon(processIcon(iconStream));

        imageLabel.setBorder(new EmptyBorder(10, 10, 5, 10));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(new EmptyBorder(0, 12, 12, 12));

        JLabel titleLabel = new JLabel(name);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +2");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String htmlDesc = "<html><body style='width: " + (CARD_WIDTH - 40) + "px'>" + 
                          beschreibung + "</body></html>";
        JLabel descLabel = new JLabel(htmlDesc);
        descLabel.putClientProperty(FlatClientProperties.STYLE, "foreground: $Label.disabledForeground; font: 90%");
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descLabel);

        add(imageLabel, BorderLayout.CENTER);
        add(textPanel, BorderLayout.SOUTH);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SpieleListener listener = listenerRef.get();
                if (listener != null) listener.onSpielSelected(name);
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

    private ImageIcon processIcon(InputStream stream) {
        if (stream == null) return null;
        try {
            BufferedImage img = ImageIO.read(stream);
            if (img == null) return null;

            Image scaled = img.getScaledInstance(CARD_WIDTH - 20, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}//end SpielPanel