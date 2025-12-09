package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import com.formdev.flatlaf.FlatLaf;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class MainViewImpl extends JFrame implements MainView {

	private EinstellungsViewImpl einstellungsView;
	private HomeViewImpl homeView;
	private SpieleViewImpl spieleView;
	private JTabbedPane tabbedPane;
	private AtomicReference<MainController> controller = new AtomicReference<>(null);

	public MainViewImpl(){
		super("SBT Worttrainer - Skrinjer::Baumgartner::Troppmann");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		homeView = new HomeViewImpl();
		spieleView = new SpieleViewImpl(controller);
		einstellungsView = new EinstellungsViewImpl();

		tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Start", homeView);
		tabbedPane.addTab("Einstellungen", einstellungsView);
		tabbedPane.addTab("Spiele", spieleView);


		setVisible(true);
		requestFocus();
	}

	public void setController(MainController controller) {
		this.controller = new AtomicReference<>(controller);
	}

	public void disposeSpiel(){

	}

	public EinstellungsView getEinstellungsView(){
		return einstellungsView;
	}

	public HomeView getHomeView(){
		return homeView;
	}

	public SpieleView getSpieleView(){
		return spieleView;
	}

	/**
	 * 
	 * @param spielEngineView
	 */
	public void setSpiel(SpielEngineView spielEngineView){

	}

	/**
	 * 
	 * @param callback
	 */
	public void runOnThread(Runnable callback){
		SwingUtilities.invokeLater(callback);
	}

	@Override
	public void setTheme(String theme) {
		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			if (info.getName().equals(theme)) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
					FlatLaf.updateUI();
				} catch (Exception e) {e.printStackTrace();}
				return;
			}
	}

	@Override
	public String[] getAvailableThemes() {
		return Stream.of(UIManager.getInstalledLookAndFeels())
        .map(UIManager.LookAndFeelInfo::getName)
        .toArray(String[]::new);
	}

	private static final Map<MessageType, Integer> typeMap = Map.of(
		MessageType.INFO, JOptionPane.INFORMATION_MESSAGE,
		MessageType.ERROR, JOptionPane.ERROR_MESSAGE
	);

	@Override
	public void showMessage(String title, String content, MessageType type) {
		JOptionPane.showMessageDialog(this, content, title, typeMap.get(type));
	}
}//end MainViewImpl