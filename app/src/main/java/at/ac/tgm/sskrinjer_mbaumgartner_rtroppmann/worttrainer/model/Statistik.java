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

	public Statistik(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param flie�text
	 * @param title
	 * @param statfields
	 */
	public Statistik(String fliesstext, String title, HashMap<String, String> statfields){

	}

	public String getFliesstext(){
		return "";
	}

	public HashMap<String, String> getStatFields(){
		return null;
	}

	/**
	 * 
	 * @param String
	 */
	public String getTitle(String title){
		return "";
	}

	/**
	 * 
	 * @param flie�text
	 */
	public void setFliessText(String fliesstext){

	}

	/**
	 * 
	 * @param newTitle
	 */
	public void setTitle(String newTitle){

	}
}//end Statistik