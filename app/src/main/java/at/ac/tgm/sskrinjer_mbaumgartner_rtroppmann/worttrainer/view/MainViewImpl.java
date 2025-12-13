package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import com.formdev.flatlaf.extras.FlatSVGIcon;

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

		UIManager.installLookAndFeel("FlatLaf Light", FlatLightLaf.class.getName());
        UIManager.installLookAndFeel("FlatLaf Dark", FlatDarkLaf.class.getName());
        
        UIManager.installLookAndFeel("FlatLaf Mac Light", FlatMacLightLaf.class.getName());
        UIManager.installLookAndFeel("FlatLaf Mac Dark", FlatMacDarkLaf.class.getName());

		UIManager.installLookAndFeel("Flat Darcula", FlatDarculaLaf.class.getName());
		
		for (FlatAllIJThemes.FlatIJLookAndFeelInfo themeInfo : FlatAllIJThemes.INFOS) {
        	UIManager.installLookAndFeel(themeInfo.getName(), themeInfo.getClassName());
    	}

		tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Start", new FlatSVGIcon(SvgIconLoader.homeSVGPath.toString(), 28, 28), homeView);
		tabbedPane.addTab("Spiele", new FlatSVGIcon(SvgIconLoader.spieleSVGPath.toString(), 28, 28), spieleView);
		tabbedPane.addTab("Einstellungen", new FlatSVGIcon(SvgIconLoader.settingsSVGPath.toString(), 28, 28), einstellungsView);
		tabbedPane.setFont(tabbedPane.getFont().deriveFont(16));

		tabbedPane.setSelectedIndex(0);

		setContentPane(tabbedPane);

		setVisible(true);
		requestFocus();
	}

	public void setController(MainController controller) {
		this.controller.set(controller);
		einstellungsView.setEinstellungsListener(controller);
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
					//SwingUtilities.updateComponentTreeUI(this);
					FlatLaf.updateUI();
					this.getRootPane().updateUI(); 
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