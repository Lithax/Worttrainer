package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.util.HashMap;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 13:58:04
 */
public class Statistik {
	private String fliesstext;
	private HashMap<String, String> statfields;
	private String title;

	/**
	 * 
	 * @param flie�text
	 * @param title
	 * @param statfields
	 */
	public Statistik(String fliesstext, String title, HashMap<String, String> statfields){
		this.fliesstext = fliesstext;
		this.title = title;
		this.statfields = statfields;
	}

	public String getFliesstext(){
		return fliesstext;
	}

	public HashMap<String, String> getStatFields(){
		return statfields;
	}

	/**
	 * 
	 * @param String
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * 
	 * @param flie�text
	 */
	public void setFliessText(String fliesstext){
		this.fliesstext = fliesstext;
	}

	/**
	 * 
	 * @param newTitle
	 */
	public void setTitle(String newTitle){
		this.title = newTitle;
	}
}//end Statistik