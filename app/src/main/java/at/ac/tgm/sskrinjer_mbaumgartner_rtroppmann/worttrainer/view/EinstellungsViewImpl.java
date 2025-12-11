package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.formdev.flatlaf.FlatClientProperties;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class EinstellungsViewImpl extends JPanel implements EinstellungsView {

	private JTextField anzahlRundenField;
	private JComboBox<Integer> schwierigkeitCBox;
	private JComboBox<String> themeCBox;
	private EinstellungenListener l;

	public EinstellungsViewImpl() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UIManager.getColor("Panel.background"));
        formPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "border: 1,1,1,1, $Component.borderColor, 1, 15;" +
                "background: $Panel.background");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel header = new JLabel("Einstellungen");
        header.putClientProperty(FlatClientProperties.STYLE, "font: $h2.font");
        header.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 30, 20);
        formPanel.add(header, gbc);

        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;

        addLabel(formPanel, "Rundenanzahl:", 1, gbc);
        
        anzahlRundenField = new JTextField("10");
        anzahlRundenField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "z.B. 10");
        addComponent(formPanel, anzahlRundenField, 1, gbc);

        addLabel(formPanel, "Schwierigkeit:", 2, gbc);

        schwierigkeitCBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5}); 
        addComponent(formPanel, schwierigkeitCBox, 2, gbc);

        addLabel(formPanel, "Design:", 3, gbc);
        
        themeCBox = new JComboBox<>();
        addComponent(formPanel, themeCBox, 3, gbc);

		JButton saveButton = new JButton("Speichern");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(l!=null)l.onEinstellungenSave();
			}
		});
		addComponent(formPanel, saveButton, 4, gbc);

        add(formPanel);
    }

    private void addLabel(JPanel panel, String text, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.putClientProperty(FlatClientProperties.STYLE, "font: bold");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
    }

    private void addComponent(JPanel panel, JComponent comp, int row, GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        panel.add(comp, gbc);
    }

	public int getAnzahlRunden(){
		return Integer.parseInt(anzahlRundenField.getText());
	}

	public int getSchwierigkeit(){
		return (Integer) schwierigkeitCBox.getSelectedItem();
	}

	public String getTheme(){
		return (String) themeCBox.getSelectedItem();
	}

	/**
	 * 
	 * @param anzahlRunden
	 */
	public void setAnzahlRunden(int anzahlRunden){
		anzahlRundenField.setText(String.valueOf(anzahlRunden));
	}

	/**
	 * 
	 * @param schwierigkeit
	 */
	public void setSchwierigkeit(int schwierigkeit){
		schwierigkeitCBox.setSelectedItem(Integer.valueOf(schwierigkeit));
	}

	/**
	 * 
	 * @param theme
	 */
	public void setTheme(String theme){
		themeCBox.setSelectedItem(theme);
	}

	@Override
	public void setEinstellungsListener(EinstellungenListener l) {
		this.l = l;
	}

	@Override
	public void setThemes(String[] themes) {
		for(String theme : themes)
			themeCBox.addItem(theme);
	}
}//end EinstellungsViewImpl