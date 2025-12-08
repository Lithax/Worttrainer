package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller;


/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:07
 */
public interface GeneralListener {

	public void einstellungenChanged();

	public void openEinstellungenRequest();

	/**
	 * 
	 * @param spielName
	 */
	public void spielStartenRequest(String spielName);

}