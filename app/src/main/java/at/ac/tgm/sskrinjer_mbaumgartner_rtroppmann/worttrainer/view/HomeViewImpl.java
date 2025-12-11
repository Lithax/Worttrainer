package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import com.formdev.flatlaf.FlatClientProperties;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Tip;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
        
        tipCard = new JPanel(new BorderLayout(0, 15));
        tipCard.setPreferredSize(new Dimension(400, 200));
        tipCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        tipCard.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 20;" +
                "background: $Button.background;" +
                "border: 1,1,1,1, $Component.borderColor, 1, 20");

        tipTitle = new JLabel("Tipp des Tages");
        tipTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold $h3.font; foreground: $Component.accentColor");
        tipTitle.setHorizontalAlignment(SwingConstants.LEFT);

        tipContent = new JTextArea("Lade Tipp...");
        tipContent.setWrapStyleWord(true);
        tipContent.setLineWrap(true);
        tipContent.setEditable(false);
        tipContent.setFocusable(false);
        tipContent.setOpaque(false);

        tipCard.add(tipTitle, BorderLayout.NORTH);
        tipCard.add(tipContent, BorderLayout.CENTER);

        centerWrapper.add(tipCard);
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
}