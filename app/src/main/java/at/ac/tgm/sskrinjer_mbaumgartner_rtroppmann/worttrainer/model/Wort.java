package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;


/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:40
 */
public record Wort(String wort, Wortart wortart, String artikel, int fall, int specialType, Wort[] singular, Wort[] plural, Wort[] formen) {
	/**
	 * 
	 * @param wort
	 * @param wortart
	 * @param artikel
	 * @param fall    null=nix, 0=nix, 1=1., 2=2., 3=3., 4=4.
	 * @param specialType    0=nix, 1=singular, 2=plural, 3=comperativ, 4=superlativ
	 * @param singular
	 * @param plural
	 * @param formen
	 */
	//private Wort(String wort, Wortart wortart, String artikel, int fall, int specialType, Wort[] singular, Wort[] plural, Wort[] formen){
	//	if (grundform == null) throw new IllegalArgumentException("Grundform fehlt");
	//	this.formen = (formen == null) ? java.util.Map.of() : java.util.Map.copyOf(formen);
	//	//0=nix, 1=singular, 2=plural, 3=comperativ, 4=superlativ
	//	this.grundform = grundform;
	//	this.artikel = artikel;
	//	this.wortart = wortart;
	//}

	/**
	 * 
	 * @param fallNr
	 */
	public String getNomenFall(int fallNr){
		return "";
	}

	/**
	 * 
	 * @param formNr
	 */
	public String getVerbForm(int formNr){
		return "";
	}
}//end Wort