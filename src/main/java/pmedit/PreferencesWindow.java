package pmedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.prefs.Preferences;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

public class PreferencesWindow extends JDialog {

	public MetadataEditPane defaultMetadataPane;

	public boolean copyBasicToXmp;
	public boolean copyXmpToBasic;
	public String renameTemplate;
	public String defaultSaveAction;
	MetadataInfo defaultMetadata;
	final Preferences prefs;

	Runnable onSave;

	protected boolean isWindows;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				PreferencesWindow frame = new PreferencesWindow(Preferences.userRoot().node("PDFMetadataEditor"),
						null);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * @wbp.parser.constructor
	 */
	public PreferencesWindow(final Preferences prefs, MetadataInfo defaultMetadata) {
		this(prefs, defaultMetadata, null);
	}

	/**
	 * Create the frame.
	 */
	public PreferencesWindow(final Preferences prefs, MetadataInfo defaultMetadata, final Frame owner) {
		super(owner, true);
		setLocationRelativeTo(owner);

		isWindows = System.getProperty("os.name").startsWith("Windows");
		this.prefs = prefs;
		this.defaultMetadata = Objects.requireNonNullElseGet(defaultMetadata, MetadataInfo::new);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				save();
				if (onSave != null) {
					onSave.run();
				}
			}
		});
		setTitle("Preferences");
		setMinimumSize(new Dimension(640, 480));
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 725, 0 };
		gbl_contentPane.rowHeights = new int[] { 389, 29, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.weighty = 1.0;
		gbc_tabbedPane.weightx = 1.0;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);

		JPanel panelGeneral = new JPanel();
		tabbedPane.addTab("General", null, panelGeneral, null);
		panelGeneral.setLayout(new MigLayout("", "[grow]", "[][]"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "On Save ...",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_1.setLayout(new MigLayout("", "[]", "[][]"));

		onsaveCopyDocumentTo = new JCheckBox("Copy Document To XMP");
		onsaveCopyDocumentTo.addActionListener(arg0 -> {
			if (onsaveCopyDocumentTo.isSelected()) {
				onsaveCopyXmpTo.setSelected(false);
			}
			copyBasicToXmp = onsaveCopyDocumentTo.isSelected();
			copyXmpToBasic = onsaveCopyXmpTo.isSelected();
		});
		panel_1.add(onsaveCopyDocumentTo, "cell 0 0,alignx left,aligny top");
		onsaveCopyDocumentTo.setSelected(false);

		onsaveCopyXmpTo = new JCheckBox("Copy XMP To Document");
		onsaveCopyXmpTo.addActionListener(arg0 -> {
			if (onsaveCopyXmpTo.isSelected()) {
				onsaveCopyDocumentTo.setSelected(false);
			}
			copyBasicToXmp = onsaveCopyDocumentTo.isSelected();
			copyXmpToBasic = onsaveCopyXmpTo.isSelected();
		});
		panel_1.add(onsaveCopyXmpTo, "cell 0 1");
		onsaveCopyXmpTo.setSelected(false);
		panelGeneral.add(panel_1, "flowx,cell 0 0,alignx left,aligny top");

		onsaveCopyXmpTo.setSelected(prefs.getBoolean("onsaveCopyXmpTo", false));
		onsaveCopyDocumentTo.setSelected(prefs.getBoolean("onsaveCopyBasicTo", false));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Rename template",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelGeneral.add(panel, "cell 0 1,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[][][]"));

		lblNewLabel = new JLabel("Preview:");
		panel.add(lblNewLabel, "cell 0 1");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		panel.add(scrollPane, "cell 0 2,grow");

		JTextPane txtpnAaa = new JTextPane();
		txtpnAaa.setBackground(UIManager.getColor("Panel.background"));
		txtpnAaa.setEditable(false);
		scrollPane.setViewportView(txtpnAaa);
		txtpnAaa.setContentType("text/html");
		txtpnAaa.setText(
				"Supported fields:<br>\n<pre>\n<i>" + CommandLine.mdFieldsHelpMessage(60,"  {","}", false) + "</i></pre>");
		txtpnAaa.setFont(UIManager.getFont("TextPane.font"));
		txtpnAaa.setCaretPosition(0);

		comboBox = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"", "{doc.author} - {doc.title}.pdf",
				"{doc.author} - {doc.creationDate}.pdf"}));
		comboBox.addActionListener(arg0 -> showPreview((String) getRenameTemplateCombo().getModel().getSelectedItem()));
		comboBox.setEditable(true);
		//comboBox.setModel(new DefaultComboBoxModel(new String[] { "", "{doc.author} - {doc.title}.pdf",
		//		"{doc.author} - {doc.creationDate}.pdf" }));
		panel.add(comboBox, "cell 0 0,growx");

		JPanel saveActionPanel = new JPanel();
		saveActionPanel.setBorder(
				new TitledBorder(null, "Default save action", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelGeneral.add(saveActionPanel, "cell 0 0");
		saveActionPanel.setLayout(new MigLayout("", "[][]", "[][]"));

		final JRadioButton rdbtnSave = new JRadioButton("Save");

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnSave);
		saveActionPanel.add(rdbtnSave, "flowy,cell 0 0,alignx left,aligny top");

		final JRadioButton rdbtnSaveAndRename = new JRadioButton("Save & rename");
		rdbtnSaveAndRename.addActionListener(e -> {
		});
		buttonGroup.add(rdbtnSaveAndRename);

		final JRadioButton rdbtnSaveAs = new JRadioButton("Save as ...");
		buttonGroup.add(rdbtnSaveAs);

		saveActionPanel.add(rdbtnSaveAndRename, "cell 0 0,alignx left,aligny top");

		saveActionPanel.add(rdbtnSaveAs, "cell 1 0,aligny top");
		final JTextComponent tcA = (JTextComponent) comboBox.getEditor().getEditorComponent();

		JPanel panelDefaults = new JPanel();
		tabbedPane.addTab("Defaults", null, panelDefaults, null);
		GridBagLayout gbl_panelDefaults = new GridBagLayout();
		gbl_panelDefaults.columnWidths = new int[] { 555, 0 };
		gbl_panelDefaults.rowHeights = new int[] {32, 100, 0};
		gbl_panelDefaults.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelDefaults.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelDefaults.setLayout(gbl_panelDefaults);


		JLabel lblDefineHereDefault = new JLabel(
				"Define here default values for the fields you would like prefilled if not set in the PDF document ");
		GridBagConstraints gbc_lblDefineHereDefault = new GridBagConstraints();
		gbc_lblDefineHereDefault.insets = new Insets(5, 5, 0, 0);
		gbc_lblDefineHereDefault.weightx = 1.0;
		gbc_lblDefineHereDefault.anchor = GridBagConstraints.NORTH;
		gbc_lblDefineHereDefault.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDefineHereDefault.gridx = 0;
		gbc_lblDefineHereDefault.gridy = 0;
		panelDefaults.add(lblDefineHereDefault, gbc_lblDefineHereDefault);

		GridBagConstraints gbc_lblDefineHereDefault1 = new GridBagConstraints();
		gbc_lblDefineHereDefault1.weightx = 1.0;
		gbc_lblDefineHereDefault1.weighty = 1.0;
		gbc_lblDefineHereDefault1.anchor = GridBagConstraints.NORTH;
		gbc_lblDefineHereDefault1.fill = GridBagConstraints.BOTH;
		gbc_lblDefineHereDefault1.gridx = 0;
		gbc_lblDefineHereDefault1.gridy = 1;
		defaultMetadataPane = new MetadataEditPane();

		panelDefaults.add(defaultMetadataPane.tabbedaPane, gbc_lblDefineHereDefault1);
/*
		JPanel panelOsIntegration = new JPanel();
		tabbedPane.addTab("Os Integration", null, panelOsIntegration, null);
		panelOsIntegration.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Explorer context menu (Windows only)", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panelOsIntegration.add(panel_2, "cell 0 0,grow");
		panel_2.setLayout(new MigLayout("", "[][]", "[growprio 50,grow][growprio 50,grow]"));

		JButton btnRegister = new JButton("Add to context menu");
		btnRegister.addActionListener(e -> {
			try {
				WindowsRegisterContextMenu.register();
			} catch (Exception e1) {
				// StringWriter sw = new StringWriter();
				// PrintWriter pw = new PrintWriter(sw);
				// e1.printStackTrace(pw);
				// JOptionPane.showMessageDialog(owner,
				// "Failed to register context menu:\n" + e1.toString()
				// +"\n" +sw.toString());
				JOptionPane.showMessageDialog(owner, "Failed to register context menu:\n" + e1.toString());
				e1.printStackTrace();
			}

		});
		panel_2.add(btnRegister, "cell 0 0,growx,aligny center");

		JButton btnUnregister = new JButton("Remove from context menu");
		btnUnregister.addActionListener(e -> WindowsRegisterContextMenu.unregister());

		final JLabel lblNewLabel_1 = new JLabel("");
		panel_2.add(lblNewLabel_1, "cell 1 0 1 2");

		panel_2.add(btnUnregister, "cell 0 1,growx,aligny center");

		btnRegister.setEnabled(isWindows);
		btnUnregister.setEnabled(isWindows);
*/
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("About", null, scrollPane_1, null);

		txtpnDf = new JTextPane();
		txtpnDf.addHyperlinkListener(e -> {
			if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
				return;
			}
			if (!java.awt.Desktop.isDesktopSupported()) {
				return;
			}
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
			if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
				return;
			}

			try {
				java.net.URI uri = e.getURL().toURI();
				desktop.browse(uri);
			} catch (Exception ignored) {

			}
		});
		txtpnDf.setContentType("text/html");
		txtpnDf.setEditable(false);
		txtpnDf.setText(
				aboutMsg= """
						<h1 align=center>Pdf Metadata Editor</h1>
						<p align=center>Based on: <a href="http://broken-by.me/pdf-metadata-editor/">http://broken-by.me/pdf-metadata-editor/</a></p>
						<br>
						<p align=center>Origianl version author: <a href="mailto:zarrro@gmail.com"/>zarrro@gmail.com</a></p>
						<p align=center>Mod author: <a href="mailto:raydudu@gmail.com"/>raydudu@gmail.com</a></p>
						<br>""");
		scrollPane_1.setViewportView(txtpnDf);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 5, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		contentPane.add(panel_3, gbc_panel_3);
				panel_3.setLayout(new BorderLayout(0, 0));
		
				JButton btnClose = new JButton("Close");
				panel_3.add(btnClose, BorderLayout.EAST);
				
				updateStatusLabel = new JLabel("...");
				panel_3.add(updateStatusLabel, BorderLayout.WEST);
				btnClose.addActionListener(e -> {
					setVisible(false);
					save();
				});

		ActionListener onDefaultSaveAction = e -> {
			if (rdbtnSave.isSelected()) {
				defaultSaveAction = "save";
			} else if (rdbtnSaveAndRename.isSelected()) {
				defaultSaveAction = "saveRename";

			} else if (rdbtnSaveAs.isSelected()) {
				defaultSaveAction = "saveAs";
			}
		};
		rdbtnSave.addActionListener(onDefaultSaveAction);
		rdbtnSaveAndRename.addActionListener(onDefaultSaveAction);
		rdbtnSaveAs.addActionListener(onDefaultSaveAction);
		tcA.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				showPreview((String) comboBox.getEditor().getItem());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				showPreview((String) comboBox.getEditor().getItem());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				showPreview((String) comboBox.getEditor().getItem());
			}
		});
		String defaultSaveAction = prefs.get("defaultSaveAction", "save");
		if (defaultSaveAction.equals("saveRename")) {
			rdbtnSaveAndRename.setSelected(true);
		} else if (defaultSaveAction.equals("saveAs")) {
			rdbtnSaveAndRename.setSelected(true);
		} else {
			rdbtnSave.setSelected(true);
		}
/*
		SwingUtilities.invokeLater(() -> lblNewLabel_1
				.setIcon(new ImageIcon(PreferencesWindow.class.getResource("/pmedit/os_integration_hint.png"))));
*/
		load();
		refresh();
		contentPane.doLayout();

		showUpdatesStatus();
	}

	private void showUpdatesStatus() {
		String versionMsg;
		updateStatusLabel.setText("");
		Version.VersionTuple current = Version.get();
		versionMsg = "<h3 align=center>Version " + current.getAsString() + "</h3>";
		txtpnDf.setText(aboutMsg + versionMsg);
	}

	public void save() {
		prefs.putBoolean("onsaveCopyXmpTo", copyXmpToBasic);
		prefs.putBoolean("onsaveCopyBasicTo", copyBasicToXmp);
		if (renameTemplate != null && renameTemplate.length() > 0)
			prefs.put("renameTemplate", renameTemplate);
		else
			prefs.remove("renameTemplate");
		defaultMetadataPane.copyToMetadata(defaultMetadata);
		prefs.put("defaultMetadata", defaultMetadata.toYAML());

		prefs.put("defaultSaveAction", defaultSaveAction);
		if (onSave != null)
			onSave.run();
	}

	public void load() {
		copyBasicToXmp = prefs.getBoolean("onsaveCopyBasicTo", false);
		copyXmpToBasic = prefs.getBoolean("onsaveCopyXmpTo", false);
		renameTemplate = prefs.get("renameTemplate", null);
		String defaultMetadataYAML = prefs.get("defaultMetadata", null);
		if (defaultMetadataYAML != null && defaultMetadataYAML.length() > 0) {
			defaultMetadata.fromYAML(defaultMetadataYAML);
		}
		defaultSaveAction = prefs.get("defaultSaveAction", "save");
	}

	public void refresh() {
		onsaveCopyDocumentTo.setSelected(copyBasicToXmp);
		onsaveCopyXmpTo.setSelected(copyXmpToBasic);

		comboBox.setSelectedItem(renameTemplate);

		defaultMetadataPane.fillFromMetadata(defaultMetadata);
		showPreview(renameTemplate);
	}

	public void showPreview(String template) {
		renameTemplate = template;
		TemplateString ts = new TemplateString(template);

		getPreviewLabel().setText("Preview:" + ts.process(MetadataInfo.getSampleMetadata()));
	}

	public void onSaveAction(Runnable newAction) {
		onSave = newAction;
	}

	private final JLabel lblNewLabel;
	private final JComboBox<String> comboBox;
	private final JCheckBox onsaveCopyDocumentTo;
	private JCheckBox onsaveCopyXmpTo;
	private final String aboutMsg;
	private final JTextPane txtpnDf;
	private final JLabel updateStatusLabel;

	protected JLabel getPreviewLabel() {
		return lblNewLabel;
	}

	protected JComboBox<String> getRenameTemplateCombo() {
		return comboBox;
	}
}
