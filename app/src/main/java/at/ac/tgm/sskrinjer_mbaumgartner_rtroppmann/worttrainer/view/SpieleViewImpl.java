package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;

/**
 * @author mhbau
 * @version 1.0
 */
public class SpieleViewImpl extends JPanel implements SpieleView {
    private AtomicReference<? extends SpieleListener> l;
    private JPanel gridPanel;

    public SpieleViewImpl(AtomicReference<? extends SpieleListener> l) {
        this.l = l;

        setLayout(new BorderLayout());
        
        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 3, 20, 20)); 
        gridPanel.setOpaque(false);
        
        container.add(gridPanel);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * @param spiele
     * @throws IOException
     */
    public void setSpiele(List<Spiel> spiele) throws IOException {
        gridPanel.removeAll();
        
        for (Spiel s : spiele) 
            gridPanel.add(new SpielPanel(
                s.getName(), 
                s.getDescription(), 
                s.getIcon(), 
                l
            ));
            
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}