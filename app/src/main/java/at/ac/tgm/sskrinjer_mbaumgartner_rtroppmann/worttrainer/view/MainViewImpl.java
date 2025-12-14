package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import com.formdev.flatlaf.FlatLaf;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;

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
	private JPanel gamePane;
	private JPanel gameContentPane;
	private JLabel gameTitleLabel;
	private AtomicReference<MainController> controller = new AtomicReference<>(null);

	public MainViewImpl(){
		super("SBT Worttrainer - Skrinjer::Baumgartner::Troppmann");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		try {
			setIconImage(ImageIO.read(this.getClass().getResourceAsStream("/icons/icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		FlatSVGIcon.ColorFilter.getInstance().setMapper(color -> {
			if (color.equals(Color.BLACK)) {
				return UIManager.getColor("Label.foreground");
			}
			return color;
		});

		JPanel headPanel = new JPanel(new BorderLayout());
		headPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

		gameTitleLabel = new JLabel("Laufendes Spiel");
		gameTitleLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +2");
		headPanel.add(gameTitleLabel, BorderLayout.WEST);

		JButton exitButton = new JButton("Ausstieg");
		exitButton.setIcon(new FlatSVGIcon(SvgIconLoader.exitSVGPath.toString()));

		exitButton.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE, "" +
			"buttonType: toolBarButton;" +
			"hoverBackground: #FF4D4D;" +
			"hoverForeground: #FFFFFF;" +
			"arc: 10");

		exitButton.setFocusable(false);
		exitButton.addActionListener(e -> controller.get().onSpielBeenden());

		headPanel.add(exitButton, BorderLayout.EAST);

		JPanel headerWrapper = new JPanel(new BorderLayout());
		headerWrapper.add(headPanel, BorderLayout.CENTER);
		headerWrapper.add(new JSeparator(), BorderLayout.SOUTH);

		gamePane = new JPanel();
		gamePane.setLayout(new BorderLayout());
		gamePane.add(headerWrapper, BorderLayout.NORTH);

		gameContentPane = new JPanel();
		gamePane.add(gameContentPane, BorderLayout.CENTER);

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
		gameContentPane.removeAll();
		setContentPane(tabbedPane);
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
	public void setSpiel(Spiel spiel){
		gamePane.add((Container)spiel.newSpielEngineView().getAsObject(), BorderLayout.CENTER);
		gameTitleLabel.setText(spiel.getName());
		setContentPane(gamePane);
		SwingUtilities.updateComponentTreeUI(gamePane);
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