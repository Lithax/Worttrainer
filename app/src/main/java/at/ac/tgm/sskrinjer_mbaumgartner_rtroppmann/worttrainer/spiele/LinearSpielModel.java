package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public abstract class LinearSpielModel extends SpielModel {

	protected int fehlerAnzahl = 0;
	protected int gesamtAnzahl = 0;

	@Override
	public Statistik getStatistik() 
	{
		Statistik s = super.getStatistik();
		if(gesamtAnzahl!=0)
			s.getStatFields().put("Fehlerquote", String.valueOf((fehlerAnzahl / gesamtAnzahl) * 100));
		return s;
	}
}//end LinearSpielModel