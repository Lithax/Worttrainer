package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class StatistikDialog extends JDialog {
    
    public StatistikDialog(Statistik statistik){
        super();
        setTitle(statistik.getTitle());
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(true);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(10, 20));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(statistik.getTitle());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font: $h1.font");
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        if (statistik.getFliesstext() != null && !statistik.getFliesstext().isEmpty()) {
            JTextArea descArea = new JTextArea(statistik.getFliesstext());
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setFocusable(false);
            descArea.setOpaque(false);
            descArea.setBorder(new EmptyBorder(0, 5, 20, 5));
            
            descArea.setColumns(40); 
            
            descArea.putClientProperty(FlatClientProperties.STYLE, "foreground: $Label.disabledForeground");
            centerPanel.add(descArea);
        }

        if (statistik.getStatFields() != null && !statistik.getStatFields().isEmpty()) {
            JPanel statsGrid = new JPanel();
            statsGrid.setLayout(new GridLayout(0, 2, 20, 10)); 

            for (Map.Entry<String, String> entry : statistik.getStatFields().entrySet()) {
                JLabel keyLabel = new JLabel(entry.getKey() + ":");
                keyLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold");
                
                JLabel valueLabel = new JLabel(entry.getValue()); 
                valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                statsGrid.add(keyLabel);
                statsGrid.add(valueLabel);
            }
            centerPanel.add(statsGrid);
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton closeButton = new JButton("Schließen");
        getRootPane().setDefaultButton(closeButton);
        closeButton.addActionListener(e -> dispose());
        closeButton.putClientProperty(FlatClientProperties.STYLE, "font: +1");

        buttonPanel.add(closeButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        
        pack();
        
        setMinimumSize(new Dimension(400, 300));
        
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        if (getHeight() > screenSize.height - 100) {
            setSize(getWidth(), screenSize.height - 100);
        }
        
        setLocationRelativeTo(null);
    }
}