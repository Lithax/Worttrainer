package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import javax.swing.JPanel;

public abstract class SwingSpielView<
        C extends SpielController<?, C, V>,
        V extends SpielView<C, V>
> extends SpielView<C, V> {
    protected JPanel mainPanel;

    public SwingSpielView(C spielController) {
        super(spielController);
        mainPanel = new JPanel();
    }
    
    @Override
	public Object getAsObject() {
		return mainPanel;
	}
}