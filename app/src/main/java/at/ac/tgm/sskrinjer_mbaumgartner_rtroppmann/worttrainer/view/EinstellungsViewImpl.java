package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class EinstellungsViewImpl extends JPanel implements EinstellungsView {

	private JTextField anzahlRundenField;
	private JComboBox<Integer> schwierigkeitCBox;
	private JComboBox<String> themeCBox;

	public EinstellungsViewImpl(){

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
}//end EinstellungsViewImpl