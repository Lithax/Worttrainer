package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import javax.swing.JDialog;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class StatistikDialog extends JDialog {

	/**
	 * 
	 * @param statistik
	 */
	public StatistikDialog(Statistik statistik){
		super();
		setTitle(statistik.getTitle());
		setModal(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// TODO: gui

		pack();
	}
}//end StatistikView