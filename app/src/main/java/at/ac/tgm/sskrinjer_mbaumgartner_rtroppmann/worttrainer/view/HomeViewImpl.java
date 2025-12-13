package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import com.formdev.flatlaf.FlatClientProperties;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Tip;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 *
 * @author Benutzbiber
 * @version 1.1
 */
public class HomeViewImpl extends JPanel implements HomeView {
    private JLabel datumLabel;
    private JLabel uhrzeitLabel;
    
    private JLabel tipTitle;
    private JTextArea tipContent;
    private JPanel tipCard;

    private JPanel recentCard;
    private JLabel recentTitle;
    private JLabel recentGameName;
    private JLabel recentGameIcon;
    private JTextArea recentGameDesc;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy");

    public HomeViewImpl() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(new BoxLayout(clockPanel, BoxLayout.Y_AXIS));
        clockPanel.setOpaque(false);
        
        uhrzeitLabel = new JLabel("00:00");
        uhrzeitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        uhrzeitLabel.putClientProperty(FlatClientProperties.STYLE, "font: $h00.font");
        
        datumLabel = new JLabel("Montag, 01. Jänner 2025");
        datumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        datumLabel.putClientProperty(FlatClientProperties.STYLE, "font: $h3.font; foreground: $Label.disabledForeground");

        clockPanel.add(uhrzeitLabel);
        clockPanel.add(Box.createVerticalStrut(5));
        clockPanel.add(datumLabel);
        clockPanel.add(Box.createVerticalStrut(40));

        add(clockPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout()); 
        centerWrapper.setOpaque(false);

        JPanel stackPanel = new JPanel();
        stackPanel.setLayout(new BoxLayout(stackPanel, BoxLayout.Y_AXIS));
        stackPanel.setOpaque(false);

        tipCard = createStyledCard();
        tipCard.setPreferredSize(new Dimension(450, 180));
        
        tipTitle = new JLabel("Tipp des Tages");
        tipTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold $h3.font; foreground: $Component.accentColor");
        tipTitle.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));

        tipContent = new JTextArea("Lade Tipp...");
        tipContent.setWrapStyleWord(true);
        tipContent.setLineWrap(true);
        tipContent.setEditable(false);
        tipContent.setFocusable(false);
        tipContent.setOpaque(false);

        tipCard.add(tipTitle, BorderLayout.NORTH);
        tipCard.add(tipContent, BorderLayout.CENTER);

        recentCard = createStyledCard();
        recentCard.setPreferredSize(new Dimension(450, 120));
        recentCard.setVisible(false); 

        recentTitle = new JLabel("Zuletzt gespielt");
        recentTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold $h3.font; foreground: $Component.accentColor");
        recentTitle.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));

        JPanel gameInfoPanel = new JPanel(new BorderLayout(15, 0));
        gameInfoPanel.setOpaque(false);

        recentGameIcon = new JLabel();
        recentGameIcon.setPreferredSize(new Dimension(64, 64));
        recentGameIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));

        JPanel iconWrapper = new JPanel(new BorderLayout());
        iconWrapper.setOpaque(false);
        iconWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));

        iconWrapper.add(recentGameIcon, BorderLayout.CENTER);
        recentGameIcon.setBorder(null);
        iconWrapper.setPreferredSize(new Dimension(64 + 10, 64));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        
        recentGameName = new JLabel("Spiel Name");
        recentGameName.putClientProperty(FlatClientProperties.STYLE, "font: bold $h4.font");
        
        recentGameDesc = new JTextArea("Beschreibung...");
        recentGameDesc.setWrapStyleWord(true);
        recentGameDesc.setLineWrap(true);
        recentGameDesc.setOpaque(false);
        recentGameDesc.setEditable(false);
        recentGameDesc.setForeground(Color.GRAY);

        textPanel.add(recentGameName);
        textPanel.add(recentGameDesc);

        gameInfoPanel.add(iconWrapper, BorderLayout.WEST);
        gameInfoPanel.add(textPanel, BorderLayout.CENTER);

        recentCard.add(recentTitle, BorderLayout.NORTH);
        recentCard.add(gameInfoPanel, BorderLayout.CENTER);

        stackPanel.add(tipCard);
        stackPanel.add(Box.createVerticalStrut(20));
        stackPanel.add(recentCard);

        centerWrapper.add(stackPanel);
        add(centerWrapper, BorderLayout.CENTER);
    }


    @Override
    public void setDatum(LocalDate datum) {
        String wochentag = datum.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMAN);
        String dateStr = datum.format(dateFormatter);
        datumLabel.setText(wochentag + ", " + dateStr);
    }

    @Override
    public void setUhrzeit(LocalTime uhrzeit) {
        uhrzeitLabel.setText(uhrzeit.format(timeFormatter));
    }

    @Override
    public void setTipOfTheDay(Tip tip) {
        if (tip != null) {
			tipTitle.setText(tip.title());
            tipContent.setText(tip.text());
        } else {
            tipContent.setText("Keine Tipps verfügbar.");
        }
    }

    @Override
    public void setRecentlyPlayedSpiel(Spiel spiel) {
        if (spiel == null) {
            recentCard.setVisible(false);
            return;
        }

        recentCard.setVisible(true);
        recentGameName.setText(spiel.getName());
        
        String desc = spiel.getDescription();
        if (desc != null && desc.length() > 60) {
            desc = desc.substring(0, 57) + "...";
        }
        recentGameDesc.setText(desc);

        try (InputStream is = spiel.getIcon()) {
            if (is != null) {
                BufferedImage image = ImageIO.read(is);
                if (image != null) {
                    Image scaledImage = SwingUtil.makeRoundedImage(image, 64, 64, 20);
                    recentGameIcon.setIcon(new ImageIcon(scaledImage));
                } else {
                    recentGameIcon.setIcon(null);
                }
            } else {
                recentGameIcon.setIcon(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            recentGameIcon.setIcon(null);
        }
        
        this.revalidate();
        this.repaint();
    }

    private JPanel createStyledCard() {
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 20;" +
                "background: $Button.background;" +
                "border: 1,1,1,1, $Component.borderColor, 1, 20");
        return card;
    }
}