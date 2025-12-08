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

	private JTextField anzahlRunden;
	private JComboBox<Integer> schwierigkeit;
	private JTextField theme;

	public EinstellungsViewImpl(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public int getAnzahlRunden(){
		return 0;
	}

	public int getSchwierigkeit(){
		return 0;
	}

	public String getTheme(){
		return "";
	}

	/**
	 * 
	 * @param anzahlRunden
	 */
	public void setAnzahlRunden(int anzahlRunden){

	}

	/**
	 * 
	 * @param schwierigkeit
	 */
	public void setSchwierigkeit(int schwierigkeit){

	}

	/**
	 * 
	 * @param theme
	 */
	public void setTheme(String theme){

	}
}//end EinstellungsViewImpl