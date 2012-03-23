package org.oyrm.kobo.postproc.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.oyrm.kobo.postproc.KoboSurveyDeviceSynchronizer;
import org.oyrm.kobo.postproc.constants.Constants;
import org.oyrm.kobo.postproc.utils.FUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class KoboPostProcPanel extends JPanel implements ActionListener,
PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4312362067715582873L;

	protected static Properties applicationProps = new Properties();
	static {
		try {
			// create and load default properties
			Properties defaultProps = new Properties();
			InputStream in = KoboPostProcFrame.class.getClassLoader().getResourceAsStream(Constants.CONFIG_PROPSRESOURCE);
			defaultProps.load(in);
			in.close();
			
			applicationProps = new Properties(defaultProps);
			File propFile = new File(System.getProperty("user.home")
					+ File.separator + Constants.CONFIG_STORAGEDIR
					+ File.separator + Constants.CONFIG_PROPSFILE);
			
			File propStorage = new File(propFile.getParent());
			if (!propStorage.exists()) {
				propStorage.mkdir();
			}
			
			if (propFile.exists()) {
				FileInputStream fin = new FileInputStream(propFile);
				applicationProps.load(fin);
				fin.close();
			}
			Enumeration<?> pnames = applicationProps.propertyNames();
			while (pnames.hasMoreElements()) {
				Object key = pnames.nextElement();
				System.setProperty(
						(String)key, 
						(String)applicationProps.getProperty((String) key));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static Dimension prefsize = new Dimension(300, 275);

	private static Logger logger = Logger.getLogger("org.oyrm.kobo");  //  @jve:decl-index=0:
	private static Formatter lf;
	private static FileHandler lh;
	static {
		try {
			lh = new FileHandler(System.getProperty("user.home")
					+ File.separator + Constants.CONFIG_STORAGEDIR
					+ File.separator + "kobo.log", true);
			lf = new SimpleFormatter();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lh.setFormatter(lf);
		logger.addHandler(lh);
		logger.setLevel(Level.parse(System.getProperty(Constants.PROPKEY_LOGGING_LEVEL)));
	}


	
	

	protected static String[] appTextEnglish = {
		"Download",
		"Convert to CSV",
		"Ready",
		"Surveys Aggregated : %d",
		"Surveys Synced to Server : %d",
		"Browse",
		"Change CSV Directory",
		"Change Aggregate Storage Directory",
		"Change Survey Source Directory",
		"Csv Transcribe Process Completed",
		"Task completed.\n",
		"Completed %d%%.\n",
		"XML Sync Process Completed",
		"Retry", 
		"Set",
		"The %1$s does not exist. \n"+"The application can recheck the \n" +"directory now if you select \"Retry\". \n" +
			"Otherwise, select \"Set\" to change the \n" + "%1$s location \n"+ "Current Directory %2$s",
		"CSV storage directory",
		"Set : %1$s",
		"Starting",
		"Writing XML to Storage",
		"Convert to CSV Task",
		"Aggregate XML Task",
		"New Directory Preferences Set",
		"Status",
		"Convert to CSV",
		"Download forms from mobile device(s)",
		"Counter",
		"Form instances:",
		"Save CSV To:",
		"Download To:",
		"Device ID:",
		"Sync To Server Task",
		"Bulk Upload",
		"Bulk Upload survey instances from System to odk server",
		"Sync Server",
		"Unmount",
	    "Formhub ID:",
	};
	
	protected static String[] appText = {
		"Télécharger",
		"Convert to CSV",
		"Ready",
		"Surveys Aggregated : %d",
		"Surveys Synced to Server : %d",
		"Parcourir",
		"Change CSV Directory",
		"Change Aggregate Storage Directory",
		"Change Survey Source Directory",
		"Csv Transcribe Process Completed",
		"Task completed.\n",
		"Completed %d%%.\n",
		"XML Sync Process Completed",
		"Retry", 
		"Set",
		"The %1$s does not exist. \n"+"The application can recheck the \n" +"directory now if you select \"Retry\". \n" +
			"Otherwise, select \"Set\" to change the \n" + "%1$s location \n"+ "Current Directory %2$s",
		"CSV storage directory",
		"Set : %1$s",
		"Starting",
		"Writing XML to Storage",
		"Convert to CSV Task",
		"Aggregate XML Task",
		"New Directory Preferences Set",
		"Status",
		"Convert to CSV",
		" Télécharger les formulaires à partir de votre Téléphone",
		"Counter",
		"Emplacement des formulaires:",
		"Save CSV To:",
		"Télécharger dans:",
		"Identifiant de l'equipement:",
		"Sync To Server Task",
		"Bulk Upload",
		"Bulk Upload survey instances from System to odk server",
		"Synchroniser le serveur",
		"Unmount",
		"Identifiant formhub",
	};

	
	
	private static KoboPostProcPanel INSTANCE;
	
	private JButton xmlAggregateButton;
	private JButton UnmountButton;
	private JButton odkAggregateButton;
	private JButton xmlSyncButton;
	
	private JTextArea statusText, syncStatusText, transStatusText;
	private File xmlDir, csvDir, sourceDir,syncsourceDir, DeviceID;

	private KoboSurveyDeviceSynchronizer xmlSyncProcessor;
	private KoboSurveyDeviceSynchronizer ServerSyncProcessor;
	private ProgressMonitor progressMonitor;
	private JPanel statusPanel = null;
	private JLabel FromLabel = null;
	private JLabel DeviceLabel = null;
	private JLabel ImageLabel = null;
	private JLabel AggregateErrorLabel = null;
	private JTextField csvPathText = null;
	private JLabel toLabel = null;
	private JTextField srcPathText = null;
	private JTextField storagePathText = null;
	private JTextField deviceText = null;
	private JTextField syncStoragePathText = null;
	private JTextField syncUserNameText = null;
	private JTextField UserNameText = null;
	private JTextField syncDeviceText = null;
	private JLabel saveToCSVLabel = null;
	private JButton csvTranscribeButton = null;
	private Integer nSynced = 0;  //  @jve:decl-index=0:
	private Integer nTranscribed = 0;  //  @jve:decl-index=0:
	
	private JButton srcDirButton = null;
	private JButton syncsrcDirButton = null;
	private JButton xmlStorageButton = null;
	private JButton csvStorageButton = null;
	
	private JFrame frame = null;

	/**
	 * Sets up the GUI
	 * @throws HeadlessException
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private KoboPostProcPanel(JFrame frame) throws HeadlessException {
		super();

		/*csvDir = new File(KoboPostProcPanel.applicationProps
				.getProperty(Constants.PROPKEY_DIRNAME_CSV));*/
		xmlDir = new File(KoboPostProcPanel.applicationProps
				.getProperty(Constants.PROPKEY_DIRNAME_XML_STORAGE));
		sourceDir = new File(KoboPostProcPanel.applicationProps
				.getProperty(Constants.PROPKEY_DIRNAME_XML_DEV));
		syncsourceDir = new File(KoboPostProcPanel.applicationProps
				.getProperty(Constants.PROPKEY_DIRNAME_XML_DEV));
		DeviceID = new File(KoboPostProcPanel.applicationProps
				.getProperty(Constants.PROPKEY_DEVICE_ID));
		
		
		this.frame = frame;
		init();
	}
	
	
	/**
	 * Needs research, interestingly this doesn't actually prevent
	 * the launching of multiple instances of the KoboPostProcFrame
	 * @return KoboPostProcFrame singleton
	 */
	public synchronized static KoboPostProcPanel getInstance(JFrame frame) {
		if (INSTANCE == null) {
			INSTANCE = new KoboPostProcPanel(frame);
		}
		return INSTANCE;
	}

	/**
	 * Used to set the app text when
	 * internationalizing things
	 * @param index
	 * @param text
	 */
	public static void setAppText(int index, String text)
	{
		appText[index] = text;
		 
	}


	/**
	 * Housekeeping on exit
	 * Saves properties to user.home/Constants.CONFIG_STORAGEDIR
	 */
	public void updatePreferences() {
		FileOutputStream out;
		try {
			File configDir = new File(System.getProperty("user.home")
					+ File.separator + Constants.CONFIG_STORAGEDIR);
			if (!configDir.exists()) {
				configDir.mkdir();
			}
			File propsFile = new File(configDir, Constants.CONFIG_PROPSFILE);
			if (!propsFile.exists()) {
				propsFile.createNewFile();
			}
			out = new FileOutputStream(propsFile);
			applicationProps.setProperty(Constants.PROPKEY_LOGGING_LEVEL,
								System.getProperty(Constants.PROPKEY_LOGGING_LEVEL));
			applicationProps.store(out, "Saved Application Instance");
			out.close();
		} catch (FileNotFoundException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
	}

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		int marginY = 7;
		
		GridBagConstraints gbcImageLabel = new GridBagConstraints();
		gbcImageLabel.gridy = 0;  // Generated
		gbcImageLabel.gridx = 0;  // Generated
		gbcImageLabel.anchor = GridBagConstraints.EAST;
		gbcImageLabel.insets = new Insets(marginY,0,0,marginY);
		ImageIcon imageIcon = new ImageIcon("logo.png");
		ImageLabel = new JLabel(imageIcon);

		GridBagConstraints gbcDeviceFolderLabel = new GridBagConstraints();
		gbcDeviceFolderLabel.gridy = 1;  // Generated
		gbcDeviceFolderLabel.gridx = 0;  // Generated
		gbcDeviceFolderLabel.anchor = GridBagConstraints.WEST;
		gbcDeviceFolderLabel.insets = new Insets(marginY,0,0,marginY);
		FromLabel = new JLabel();
		FromLabel.setText(appText[Constants.SURVEY_INSTANCES_TEXT]);  // Generated
		
		GridBagConstraints gbcAggregateToLabel = new GridBagConstraints();
		gbcAggregateToLabel.gridy = 2;  // Generated
		gbcAggregateToLabel.gridx = 0;  // Generated
		gbcAggregateToLabel.anchor = GridBagConstraints.WEST;
		gbcAggregateToLabel.insets = new Insets(marginY,0,0,marginY);
		toLabel = new JLabel();
		toLabel.setText(appText[Constants.AGGREGATE_TO_TEXT]);  // Generated
		//toLabel.setAlignmentX(RIGHT_ALIGNMENT);
		
		GridBagConstraints gbcDeviceID = new GridBagConstraints();
		gbcDeviceID.gridy = 3;  // Generated
		gbcDeviceID.gridx = 0;  // Generated		
		gbcDeviceID.anchor = GridBagConstraints.WEST;
		gbcDeviceID.insets = new Insets(marginY,0,0,marginY);
		DeviceLabel = new JLabel();
		DeviceLabel.setText(appText[Constants.DEVICE_ID_TEXT]);  // Generated
		
		GridBagConstraints gbcUserLabel = new GridBagConstraints();
		gbcUserLabel.gridy = 4;  // Generated
		gbcUserLabel.gridx = 0;  // Generated
		gbcUserLabel.anchor = GridBagConstraints.WEST;
		gbcUserLabel.insets = new Insets(marginY,0,0,marginY);
		JLabel UserLabel = new JLabel();
		UserLabel.setText(appText[Constants.Formhub_ID]);
		
	
		
		GridBagConstraints gbcAggregateMessage = new GridBagConstraints();
		gbcAggregateMessage.gridy = 5;  // Generated
		gbcAggregateMessage.gridx = 0;  // Generated		
		gbcAggregateMessage.anchor = GridBagConstraints.WEST;
		gbcAggregateMessage.insets = new Insets(marginY,0,0,marginY);
		AggregateErrorLabel = new JLabel();
		AggregateErrorLabel.setForeground(Color.RED);
		
		/*GridBagConstraints gbcCSVPathLabel = new GridBagConstraints();
		gbcCSVPathLabel.gridx = 0;  // Generated
		gbcCSVPathLabel.gridy = 1;  // Generated
		gbcCSVPathLabel.insets = new Insets(marginY,0,0,marginY);
		saveToCSVLabel = new JLabel();
		saveToCSVLabel.setText(appText[Constants.SAVE_TO_CSV_TEXT]);  // Generated*/
		
		/*GridBagConstraints gbcCsvText = new GridBagConstraints();
		gbcCsvText.fill = GridBagConstraints.HORIZONTAL;  // Generated
		gbcCsvText.gridwidth = 3;  // Generated
		gbcCsvText.gridx = 1;  // Generated
		gbcCsvText.gridy = 1;  // Generated
		gbcCsvText.anchor = GridBagConstraints.WEST;  // Generated
		gbcCsvText.weightx = 1.0;  // Generated
		gbcCsvText.insets = new Insets(marginY,0,0,marginY);*/
		
		GridBagConstraints gbcSourceText = new GridBagConstraints();
		gbcSourceText.fill = GridBagConstraints.HORIZONTAL;  // Generated
		gbcSourceText.anchor = GridBagConstraints.WEST;  // Generated
		gbcSourceText.gridwidth = 3;  // Generated
		gbcSourceText.gridx = 1;  // Generated
		gbcSourceText.gridy = 1;  // Generated
		gbcSourceText.weightx = 1.0;  // Generated
		gbcSourceText.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcXmlStoragePathText = new GridBagConstraints();
		gbcXmlStoragePathText.fill = GridBagConstraints.HORIZONTAL;  // Generated
		gbcXmlStoragePathText.gridwidth = 3;  // Generated
		gbcXmlStoragePathText.gridx = 1;  // Generated
		gbcXmlStoragePathText.gridy = 2;  // Generated
		gbcXmlStoragePathText.anchor = GridBagConstraints.WEST;  // Generated
		gbcXmlStoragePathText.weightx = 1.0;  // Generated
		gbcXmlStoragePathText.insets = new Insets(marginY,0,0,marginY);
		
	
		
		GridBagConstraints gbcDeviceText = new GridBagConstraints();
		gbcDeviceText.fill = GridBagConstraints.HORIZONTAL;  // Generated
		gbcDeviceText.anchor = GridBagConstraints.WEST;  // Generated
		gbcDeviceText.gridwidth = 3;  // Generated
		gbcDeviceText.gridx = 1;  // Generated
		gbcDeviceText.gridy = 3;  // Generated
		gbcDeviceText.weightx = 1.0;  // Generated
		gbcDeviceText.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcUserNameText = new GridBagConstraints();
		gbcUserNameText.fill = GridBagConstraints.HORIZONTAL;  // Generated
		gbcUserNameText.anchor = GridBagConstraints.WEST;  // Generated
		gbcUserNameText.gridwidth = 3;  // Generated
		gbcUserNameText.gridx = 1;  // Generated
		gbcUserNameText.gridy = 4;  // Generated
		gbcUserNameText.weightx = 1.0;  // Generated
		gbcUserNameText.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcBrowseSrcButton = new GridBagConstraints();
		gbcBrowseSrcButton.gridx = 4;  // Generated
		gbcBrowseSrcButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcBrowseSrcButton.gridy = 1;  // Generated
		gbcBrowseSrcButton.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcBrowseStorageButton = new GridBagConstraints();
		gbcBrowseStorageButton.gridx = 4;  // Generated
		gbcBrowseStorageButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcBrowseStorageButton.gridy = 2;  // Generated
		gbcBrowseStorageButton.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcBrowseCSVButton = new GridBagConstraints();
		gbcBrowseCSVButton.gridx = 4;  // Generated
		gbcBrowseCSVButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcBrowseCSVButton.gridy = 3;  // Generated
		gbcBrowseCSVButton.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcAggregateButton = new GridBagConstraints();		
		gbcAggregateButton.gridx = 4;  // Generated
		gbcAggregateButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcAggregateButton.gridy = 3;  // Generated
		gbcAggregateButton.insets = new Insets(marginY,0,0,marginY);

		GridBagConstraints gbcOdkSyncButton = new GridBagConstraints();		
		gbcOdkSyncButton.gridx = 4;  // Generated
		gbcOdkSyncButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcOdkSyncButton.gridy = 4;  // Generated
		gbcOdkSyncButton.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcUnmountButton = new GridBagConstraints();		
		gbcUnmountButton.gridx = 4;  // Generated
		gbcUnmountButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcUnmountButton.gridy = 5;  // Generated
		gbcUnmountButton.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcSyncButton = new GridBagConstraints();		
		gbcSyncButton.gridx = 4;  // Generated
		gbcSyncButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcSyncButton.gridy = 6;  // Generated
		gbcSyncButton.insets = new Insets(marginY,0,0,marginY);
		
		/*************************************************************************************/
		/*************************************************************************************/
		/*************************************************************************************/
		
		
		/*GridBagConstraints gbcTranscribeButton = new GridBagConstraints();
		gbcTranscribeButton.gridx = 4;  // Generated
		gbcTranscribeButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcTranscribeButton.gridy = 2;  // Generated
		gbcTranscribeButton.insets = new Insets(marginY,0,0,marginY);*/
		
		GridBagConstraints gbcStatus = new GridBagConstraints();
		gbcStatus.gridheight = 1;  // Generated
		gbcStatus.gridwidth = 1;  // Generated
		gbcStatus.gridx = 0;  // Generated
		gbcStatus.gridy = 3;  // Generated
		gbcStatus.weightx = 1.0D;  // Generated
		gbcStatus.fill = GridBagConstraints.BOTH;
		gbcStatus.anchor = GridBagConstraints.WEST;
		
		/*GridBagConstraints gbcCsv = new GridBagConstraints();
		gbcCsv.gridheight = 1;  // Generated
		gbcCsv.gridwidth = 1;  // Generated
		gbcCsv.gridx = 0;  // Generated
		gbcCsv.gridy = 1;  // Generated
		gbcCsv.weightx = 1.0D;  // Generated
		gbcCsv.fill = GridBagConstraints.BOTH;
		gbcCsv.anchor = GridBagConstraints.WEST;*/
		
		/***************************************************************************************************/
		/***********************************Describing Syncing Files****************************************/
		/***************************************************************************************************/
		
		/*GridBagConstraints gbcSyncFolderLabel = new GridBagConstraints();
		gbcSyncFolderLabel.gridy = 0;  // Generated
		gbcSyncFolderLabel.gridx = 0;  // Generated
		gbcSyncFolderLabel.anchor = GridBagConstraints.WEST;
		gbcSyncFolderLabel.insets = new Insets(marginY,0,0,marginY);
		JLabel folderLabel = new JLabel();
		folderLabel.setText("Survey Instances:");
		//toLabel.setText(appText[Constants.AGGREGATE_TO_TEXT]);  // Generated
		//toLabel.setAlignmentX(RIGHT_ALIGNMENT);
		
		GridBagConstraints gbcSyncUserLabel = new GridBagConstraints();
		gbcSyncUserLabel.gridy = 1;  // Generated
		gbcSyncUserLabel.gridx = 0;  // Generated
		gbcSyncUserLabel.anchor = GridBagConstraints.WEST;
		gbcSyncUserLabel.insets = new Insets(marginY,0,0,marginY);
		JLabel SyncUserLabel = new JLabel();
		SyncUserLabel.setText("UserName:");
		
		GridBagConstraints gbcSyncDeviceIDLabel = new GridBagConstraints();
		gbcSyncDeviceIDLabel.gridy = 2;  // Generated
		gbcSyncDeviceIDLabel.gridx = 0;  // Generated
		gbcSyncDeviceIDLabel.anchor = GridBagConstraints.WEST;
		gbcSyncDeviceIDLabel.insets = new Insets(marginY,0,0,marginY);
		JLabel SyncDeviceLabel = new JLabel();
		SyncDeviceLabel.setText("Device ID:");
		
		GridBagConstraints gbcSyncStoragePathText = new GridBagConstraints();
		gbcSyncStoragePathText.fill = GridBagConstraints.HORIZONTAL;  // Generated
		gbcSyncStoragePathText.gridwidth = 3;  // Generated
		gbcSyncStoragePathText.gridx = 1;  // Generated
		gbcSyncStoragePathText.gridy = 0;  // Generated
		gbcSyncStoragePathText.anchor = GridBagConstraints.WEST;  // Generated
		gbcSyncStoragePathText.weightx = 1.0;  // Generated
		gbcSyncStoragePathText.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcSyncUserNameText = new GridBagConstraints();
		gbcSyncUserNameText.fill = GridBagConstraints.HORIZONTAL;  // Generated
		gbcSyncUserNameText.anchor = GridBagConstraints.WEST;  // Generated
		gbcSyncUserNameText.gridwidth = 3;  // Generated
		gbcSyncUserNameText.gridx = 1;  // Generated
		gbcSyncUserNameText.gridy = 1;  // Generated
		gbcSyncUserNameText.weightx = 1.0;  // Generated
		gbcSyncUserNameText.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcSyncDeviceText = new GridBagConstraints();
		gbcSyncDeviceText.fill = GridBagConstraints.HORIZONTAL;  // Generated
		gbcSyncDeviceText.anchor = GridBagConstraints.WEST;  // Generated
		gbcSyncDeviceText.gridwidth = 3;  // Generated
		gbcSyncDeviceText.gridx = 1;  // Generated
		gbcSyncDeviceText.gridy = 2;  // Generated
		gbcSyncDeviceText.weightx = 1.0;  // Generated
		gbcSyncDeviceText.insets = new Insets(marginY,0,0,marginY);
		
		GridBagConstraints gbcSyncBrowseStorageButton = new GridBagConstraints();
		gbcSyncBrowseStorageButton.gridx = 4;  // Generated
		gbcSyncBrowseStorageButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcSyncBrowseStorageButton.gridy = 0;  // Generated
		gbcSyncBrowseStorageButton.insets = new Insets(marginY,0,0,marginY);*/
		
		/*GridBagConstraints gbcSyncButton = new GridBagConstraints();		
		gbcSyncButton.gridx = 4;  // Generated
		gbcSyncButton.anchor = GridBagConstraints.EAST;  // Generated
		gbcSyncButton.gridy = 1;  // Generated
		gbcSyncButton.insets = new Insets(marginY,0,0,marginY);*/
		/***************************************************************************************************/
		/***************************************************************************************************/
		/***************************************************************************************************/

		
		
		GridBagConstraints gbcXml = new GridBagConstraints();
		gbcXml.gridheight = 1;
		gbcXml.gridwidth = 1;  // Generated
		gbcXml.gridx = 0;  // Generated
		gbcXml.gridy = 1;  // Generated
		gbcXml.weightx = 1.0D;  // Generated
		gbcXml.fill = GridBagConstraints.BOTH;
		gbcXml.anchor = GridBagConstraints.WEST;
		
		GridBagConstraints gbcSync = new GridBagConstraints();
		gbcSync.gridheight = 1;
		gbcSync.gridwidth = 1;  // Generated
		gbcSync.gridx = 0;  // Generated
		gbcSync.gridy = 0;  // Generated
		gbcSync.weightx = 1.0D;  // Generated
		gbcSync.fill = GridBagConstraints.BOTH;
		gbcSync.anchor = GridBagConstraints.WEST;
		
		
		
		/**
		 * Create widgets
		 */
		statusText = new JTextArea();
		statusText.setText(appText[Constants.STATUS_TEXT]);
		statusText.setAlignmentX(Component.CENTER_ALIGNMENT);
		statusText.setEditable(false);

		syncStatusText = new JTextArea();
		syncStatusText.setText(appText[Constants.COUNTER_TEXT]);
		syncStatusText.setAlignmentX(Component.CENTER_ALIGNMENT);
		syncStatusText.setEditable(false);
		
		transStatusText = new JTextArea();
		transStatusText.setText(appText[Constants.COUNTER_TEXT]);
		transStatusText.setAlignmentX(Component.CENTER_ALIGNMENT);
		transStatusText.setEditable(false);

		/**
		 * Set up the contentPane JPanel instance along with the additional
		 * JPanels to create the appropriate layout
		 */
		
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.setPreferredSize(prefsize);  // Generated
		
		JPanel xmlPanel = new JPanel();
		xmlPanel.setLayout(new GridBagLayout());
		xmlPanel.setBorder(BorderFactory.createTitledBorder(null, appText[Constants.AGGREGATE_XML_TEXT], TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));  // Generated
		xmlPanel.add(getSrcDirButton(), gbcBrowseSrcButton);
		xmlPanel.add(getXmlStorageButton(), gbcBrowseStorageButton);
		xmlPanel.add(getXmlSyncButton(), gbcAggregateButton);  // Generated
		xmlPanel.add(getOdkSyncButton(), gbcOdkSyncButton);
		xmlPanel.add(getUnmountButton(),gbcUnmountButton);
		
		
		xmlPanel.add(FromLabel, gbcDeviceFolderLabel);  // Generated
		xmlPanel.add(getSourceText(), gbcSourceText);  // Generated
		xmlPanel.add(toLabel, gbcAggregateToLabel);  // Generated
		xmlPanel.add(getXmlText(), gbcXmlStoragePathText);  // Generated
		xmlPanel.add(DeviceLabel, gbcDeviceID);  // Generated
		xmlPanel.add(AggregateErrorLabel, gbcAggregateMessage);
		xmlPanel.add(getDeviceText(), gbcDeviceText);  // Generated
		xmlPanel.add(getUserNameText(),gbcUserNameText);
		xmlPanel.add(UserLabel, gbcUserLabel);  // Generated
		//xmlPanel.add(getServerSyncButton(), gbcSyncButton);  // Generated
		
		
		JPanel syncPanel = new JPanel();
		syncPanel.setLayout(new GridBagLayout());
		syncPanel.setBorder(BorderFactory.createEmptyBorder());
		//.createTitledBorder(null, appText[Constants.SYNC_TO_ODK_SERVER], TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));  // Generated
		syncPanel.add(ImageLabel, gbcImageLabel);
		/*syncPanel.add(getSyncSrcDirButton(), gbcSyncBrowseStorageButton);
		syncPanel.add(folderLabel, gbcSyncFolderLabel);  // Generated
		syncPanel.add(getSyncStorageText(), gbcSyncStoragePathText);  // Generated
		syncPanel.add(SyncUserLabel, gbcSyncUserLabel);  // Generated
		syncPanel.add(getSyncUserText(), gbcSyncUserNameText);  // Generated
		//syncPanel.add(SyncDeviceLabel, gbcSyncDeviceIDLabel);  // Generated
		//syncPanel.add(getSyncDeviceText(), gbcSyncDeviceText);  // Generated
		//syncPanel.add(getServerSyncButton(), gbcSyncButton);  // Generated
		//syncPanel.add(getServerSyncButton(), gbcSyncButton);  // Generated
		*/
		
		this.add(xmlPanel, gbcXml);  // Generated
		this.add(syncPanel, gbcSync);  // Generated
		//this.add(getStatusPanel(), gbcStatus);  // Generated
		
		

		
		validateDir(xmlDir);
		validateDir(sourceDir);
		
		/**
		 * Make it So Necessities
		 */
		setVisible(true);
		
		
	}

	/**
	 * This method initializes statusPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStatusPanel() {
		if (statusPanel == null) {
			try {
				GridBagConstraints gbcTransStatusText = new GridBagConstraints();
				gbcTransStatusText.fill = GridBagConstraints.BOTH;  // Generated
				gbcTransStatusText.weighty = 1.0D;  // Generated
				gbcTransStatusText.gridx = 0;  // Generated
				gbcTransStatusText.gridy = 2;  // Generated
				gbcTransStatusText.weightx = 1.0;  // Generated
				
				GridBagConstraints gbcStatusText = new GridBagConstraints();
				gbcStatusText.fill = GridBagConstraints.BOTH;  // Generated
				gbcStatusText.gridx = 0;  // Generated
				gbcStatusText.gridy = 0;  // Generated
				gbcStatusText.weightx = 1.0;  // Generated
				gbcStatusText.weighty = 1.0;  // Generated
				
				GridBagConstraints gbcCounterText = new GridBagConstraints();
				gbcCounterText.fill = GridBagConstraints.BOTH;  // Generated
				gbcCounterText.gridx = 0;  // Generated
				gbcCounterText.gridy = 1;  // Generated
				gbcCounterText.weightx = 1.0;  // Generated
				gbcCounterText.weighty = 1.0;  // Generated

				statusText.setText(appText[Constants.STATUS_INIT]);
				statusText.setEditable(false);
				statusText.setBorder(new LineBorder(Color.BLACK, 1, true));
				
				syncStatusText.setText(String.format(appText[Constants.COUNTER_SYNC_TEXT], nSynced));
				syncStatusText.setEditable(false);
				syncStatusText.setBorder(new LineBorder(Color.BLACK, 1, true));
				
				transStatusText.setText(String.format(appText[Constants.COUNTER_TRANS_TEXT], nTranscribed));
				transStatusText.setEditable(false);
				transStatusText.setBorder(new LineBorder(Color.BLACK, 1, true));
				
				statusPanel = new JPanel();
				statusPanel.setLayout(new GridBagLayout());  // Generated
				statusPanel.setBorder(BorderFactory.createTitledBorder(null, appText[Constants.STATUS_TEXT] , TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));  // Generated
				statusPanel.add(statusText, gbcStatusText);  // Generated
				statusPanel.add(transStatusText, gbcTransStatusText);  // Generated
				statusPanel.add(syncStatusText, gbcCounterText);  // Generated
			} catch (java.lang.Throwable e) {
				e.printStackTrace();
			}
		}
		return statusPanel;
	}

	/**
	 * This method initializes sourceText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSourceText() {
		if (csvPathText == null) {
			csvPathText = new JTextField();
			csvPathText.setText(sourceDir.getAbsolutePath());
			csvPathText.setAlignmentX(JTextField.LEADING);
			csvPathText.setEditable(false);
		}
		return csvPathText;
	}

	/**
	 * This method initializes xmlText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getXmlText() {
		if (srcPathText == null) {
				srcPathText = new JTextField();
				srcPathText.setText(xmlDir.getAbsolutePath());
				srcPathText.setAlignmentX(JTextField.LEADING);
				srcPathText.setEditable(false);
		}
		return srcPathText;
	}
	
	private JTextField getDeviceText() {
		//if (srcPathText == null) {
		deviceText = new JTextField();
		//srcPathText.setText(xmlDir.getAbsolutePath());
		deviceText.setAlignmentX(JTextField.LEADING);
		deviceText.setEditable(true);
		//}
		return deviceText;
	}
	
	
	private JTextField getUserNameText() {
		UserNameText = new JTextField();
		//srcPathText.setText(xmlDir.getAbsolutePath());
		UserNameText.setAlignmentX(JTextField.LEADING);
		UserNameText.setEditable(true);
		//}
		return UserNameText;
	}
	
	/**
	 * This method initializes csvText	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getCsvText() {
		if (storagePathText == null) {
				storagePathText = new JTextField();
				storagePathText.setText(csvDir.getAbsolutePath());
				storagePathText.setAlignmentX(JTextField.LEADING);
				storagePathText.setEditable(false);
		}
		return storagePathText;
	}

	private JTextField getSyncStorageText() {
		if (storagePathText == null) {
			syncStoragePathText = new JTextField();
			syncStoragePathText.setText(syncsourceDir.getAbsolutePath());
			syncStoragePathText.setAlignmentX(JTextField.LEADING);
			syncStoragePathText.setEditable(false);
		}
		return syncStoragePathText;
	}

	private JTextField getSyncUserText() {
		if (syncUserNameText == null) {
		syncUserNameText = new JTextField();
		//srcPathText.setText(xmlDir.getAbsolutePath());
		syncUserNameText.setAlignmentX(JTextField.LEADING);
		syncUserNameText.setEditable(true);
		}
		return syncUserNameText;
	}
	
	private JTextField getSyncDeviceText() {
		//if (srcPathText == null) {
		syncDeviceText = new JTextField();
		//srcPathText.setText(xmlDir.getAbsolutePath());
		syncDeviceText.setAlignmentX(JTextField.LEADING);
		syncDeviceText.setEditable(true);
		//}
		return syncDeviceText;
	}

	/**
	 * This method initializes csvTranscribeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getXmlSyncButton() {
		if (xmlAggregateButton == null) {
				xmlAggregateButton = new JButton(appText[Constants.XML_AGGREGATE_COMMAND]);
				xmlAggregateButton.setAlignmentX(Component.RIGHT_ALIGNMENT);  // Generated
				xmlAggregateButton.setHorizontalAlignment(SwingConstants.RIGHT);  // Generated
				xmlAggregateButton.setActionCommand(appText[Constants.XML_AGGREGATE_COMMAND]);  // Generated
				xmlAggregateButton.setMnemonic(KeyEvent.VK_S);  // Generated
				xmlAggregateButton.addActionListener(this);
		}
		return xmlAggregateButton;
	}
	
	private JButton getUnmountButton() {
		if (UnmountButton == null) {
			    UnmountButton = new JButton(appText[Constants.UNMOUNT_COMMAND]);
			    UnmountButton.setAlignmentX(Component.RIGHT_ALIGNMENT);  // Generated
			    UnmountButton.setHorizontalAlignment(SwingConstants.RIGHT);  // Generated
			    UnmountButton.setActionCommand(appText[Constants.UNMOUNT_COMMAND]);  // Generated
			    UnmountButton.setMnemonic(KeyEvent.VK_S);  // Generated
			    UnmountButton.addActionListener(this);
			    UnmountButton.setEnabled(false);
		}
		return UnmountButton;
	}
	
	
	private JButton getOdkSyncButton() {
		if (odkAggregateButton == null) {
			    odkAggregateButton = new JButton(appText[Constants.ODK_AGGREGATE_COMMAND]);
			    odkAggregateButton.setAlignmentX(Component.RIGHT_ALIGNMENT);  // Generated
			    odkAggregateButton.setHorizontalAlignment(SwingConstants.RIGHT);  // Generated
			    odkAggregateButton.setActionCommand(appText[Constants.ODK_AGGREGATE_COMMAND]);  // Generated
			    odkAggregateButton.setMnemonic(KeyEvent.VK_S);  // Generated
			    odkAggregateButton.addActionListener(this);
			    odkAggregateButton.setEnabled(true);
		}
		return odkAggregateButton;
	}
	
	private JButton getServerSyncButton() {
		if (xmlSyncButton == null) {
			    xmlSyncButton = new JButton(appText[Constants.XML_SYNC_COMMAND]);
			    xmlSyncButton.setAlignmentX(Component.RIGHT_ALIGNMENT);  // Generated
			    xmlSyncButton.setHorizontalAlignment(SwingConstants.RIGHT);  // Generated
			    xmlSyncButton.setActionCommand(appText[Constants.XML_SYNC_COMMAND]);  // Generated
			    xmlSyncButton.setMnemonic(KeyEvent.VK_S);  // Generated
			    xmlSyncButton.addActionListener(this);
		}
		return xmlSyncButton;
	}
	
	/**
	 * This method initializes csvTranscribeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCsvTranscribeButton() {
		if (csvTranscribeButton == null) {
				csvTranscribeButton = new JButton(appText[Constants.CSV_CONVERT_COMMAND]);
				csvTranscribeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);  // Generated
				csvTranscribeButton.setHorizontalAlignment(SwingConstants.RIGHT);  // Generated
				csvTranscribeButton.setActionCommand(appText[Constants.CSV_CONVERT_COMMAND]);  // Generated
				csvTranscribeButton.setMnemonic(KeyEvent.VK_T);  // Generated
				csvTranscribeButton.addActionListener(this);
		}
		return csvTranscribeButton;
	}
	

	/**
	 * Update Preferences 
	 */
	protected void update() {
		sourceDir = new File(KoboPostProcPanel.applicationProps.getProperty(
				Constants.PROPKEY_DIRNAME_XML_DEV));
		csvPathText.setText(sourceDir.getAbsolutePath());
		System.setProperty(
				Constants.PROPKEY_DIRNAME_XML_DEV, 
				(String)applicationProps.getProperty(Constants.PROPKEY_DIRNAME_XML_DEV));
		
		xmlDir = new File(applicationProps.getProperty(Constants.PROPKEY_DIRNAME_XML_STORAGE));
		srcPathText.setText(xmlDir.getAbsolutePath());

		System.setProperty(	
				Constants.PROPKEY_DIRNAME_XML_STORAGE, 
				(String)applicationProps.getProperty(
						Constants.PROPKEY_DIRNAME_XML_STORAGE));
		
		DeviceID = new File(KoboPostProcPanel.applicationProps
				.getProperty(Constants.PROPKEY_DEVICE_ID));
		deviceText.setText(DeviceID.toString());
		
		System.setProperty(	
				Constants.PROPKEY_DEVICE_ID, 
				(String)applicationProps.getProperty(
						Constants.PROPKEY_DEVICE_ID));
		
		/*csvDir = new File(applicationProps.getProperty(Constants.PROPKEY_DIRNAME_CSV));
		storagePathText.setText(csvDir.getAbsolutePath());
		System.setProperty(	
				Constants.PROPKEY_DIRNAME_CSV, 
				(String)applicationProps.getProperty(
						Constants.PROPKEY_DIRNAME_CSV));
		*/
		statusText.setText(appText[Constants.DIR_PREF_SET_TEXT]);
		
		updatePreferences();
	}
	
	//check this part
	/**
	 * ActionListener implementation
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == null) {
			logger.finer("null action event");
			return;
		} else if (e.getActionCommand().equals(appText[Constants.XML_AGGREGATE_COMMAND])) {
			logger.fine("ActionEvent " + e.getActionCommand());
			syncXML();
			statusText.setText(appText[Constants.AGGREGATE_XML_TASK_TEXT]);

		}
		else if (e.getActionCommand().equals(appText[Constants.ODK_AGGREGATE_COMMAND])) {
			logger.fine("ActionEvent " + e.getActionCommand());
			try {
				syncServer();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			//statusText.setText(appText[Constants.AGGREGATE_XML_TASK_TEXT]);
		}
		
		
		else if (e.getActionCommand().equals(appText[Constants.UNMOUNT_COMMAND])) {
			logger.fine("ActionEvent " + e.getActionCommand());
			try {
				unmountDevice();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			statusText.setText(appText[Constants.UNMOUNT_COMMAND]);

		}
		 else if (e.getActionCommand().equals(appText[Constants.CSV_CONVERT_COMMAND])) {			
			logger.fine("ActionEvent " + e.getActionCommand());
			//trascribeToCSV();
			statusText.setText(appText[Constants.CONVERT_TO_CSV_TASK_TEXT]);
		}
		
	}

	/**
	 * Synchronize the XML using a KoboSurveyDeviceSyncronizer called with 
	 * the directories specified in the GUI
	 */
	private void syncXML() {
		logger.entering(getClass().getName(), "syncXML()");
		logger.fine("\tDevice Source Directory:" + sourceDir.getAbsolutePath());
		AggregateErrorLabel.setText("");
		validateDir(xmlDir);
		validateDir(sourceDir);
		
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		boolean success = (new File(xmlDir.toString().concat("/Downloaded Files"))).mkdir();
	    //success = (new File(xmlDir.toString().concat("/Downloaded Files/"+simpleDateFormat.format(new Date())))).mkdir();
	    success = (new File(xmlDir.toString().concat("/Synchonized Files"))).mkdir();
		
		/*if(!sourceDir.toString().endsWith("odk"))
		{
			AggregateErrorLabel.setText("Select ODK Folder");
			logger.entering(getClass().getName(), "syncXML()");
			return;
		}*/
		if(deviceText.getText().isEmpty())
		{
			AggregateErrorLabel.setText("Enter Device ID");
			logger.entering(getClass().getName(), "syncXML()");
			return;
		}
		Calendar cal = Calendar.getInstance();
	    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM);
	    
		xmlSyncProcessor = new KoboSurveyDeviceSynchronizer(new File(sourceDir.toString().concat("/odk")), new File(xmlDir.toString().concat("/Downloaded Files/")), deviceText.getText().concat("_"+df.format(cal.getTime())));
		progressMonitor = new ProgressMonitor(this, appText[Constants.WRITING_XML_TO_STORAGE]
				, "", 0, xmlSyncProcessor
				.getLengthOfTask());
		progressMonitor.setMillisToDecideToPopup(10);
		progressMonitor.setMillisToPopup(100);
		progressMonitor.setNote(appText[Constants.STARTING_TEXT]);
		xmlSyncProcessor.addPropertyChangeListener(this);
		xmlSyncProcessor.execute();
		progressMonitor.setNote(appText[Constants.STARTING_TEXT]);
		progressMonitor.setProgress(1);

		odkAggregateButton.setEnabled(true);
		//xmlAggregateButton.setEnabled(false);
		//UnmountButton.setEnabled(true);
		UserNameText.setEditable(true);
		//deviceText.setEditable(false);
		/*try
		  {
			Runtime.getRuntime().exec("cmd /c start unmount.bat");
		  }catch (IOException e){}*/
		
		logger.fine("XML Storage Directory:" + xmlDir.getAbsolutePath());
		logger.entering(getClass().getName(), "syncXML()");

	}
	
	private void syncODKServer() throws MalformedURLException, IOException
	{
		logger.entering(getClass().getName(), "syncODK()");
		logger.fine("\tDevice Source Directory:" + sourceDir.getAbsolutePath());
		AggregateErrorLabel.setText("");
		validateDir(xmlDir);
		validateDir(sourceDir);
		
		/*if(!sourceDir.toString().endsWith("odk"))
		{
			//Error message if Folder is Wrong
			AggregateErrorLabel.setText("Select ODK Folder");
			logger.entering(getClass().getName(), "syncXML()");
			return;
		}*/
		if(deviceText.getText().isEmpty())
		{
			//Error message if Device ID is missing
			AggregateErrorLabel.setText("Enter Device ID");
			logger.entering(getClass().getName(), "syncXML()");
			return;
		}
		if(UserNameText.getText().isEmpty())
		{
			//Error message if Device ID is missing
			AggregateErrorLabel.setText("Enter Valid User");
			logger.entering(getClass().getName(), "syncXML()");
			return;
		}
		ServerSyncProcessor = new KoboSurveyDeviceSynchronizer();
		boolean serverCheck = ServerSyncProcessor.SendFiles(UserNameText.getText() , xmlDir.toString().concat("/Downloaded Files/"+deviceText.getText()) + ".zip");
		if(serverCheck)
		{
			AggregateErrorLabel.setText("File Uploaded");
			odkAggregateButton.setEnabled(false);
			xmlAggregateButton.setEnabled(true);
			UnmountButton.setEnabled(true);
			UserNameText.setEditable(true);
			deviceText.setEditable(true);
			deviceText.setText("");
		}
		else
		{
			AggregateErrorLabel.setText("Error in Syncing ");
			odkAggregateButton.setEnabled(true);
			xmlAggregateButton.setEnabled(true);
			UnmountButton.setEnabled(true);
			UserNameText.setEditable(true);
			deviceText.setEditable(true);
		}
	}
	
	private void unmountDevice() throws IOException
	{
		/*ServerSyncProcessor = new KoboSurveyDeviceSynchronizer();
		boolean unmountCheck = ServerSyncProcessor.Unmount( sourceDir.toString());
		String osName = System.getProperty("os.name");
		AggregateErrorLabel.setText(osName);*/
		/*if(unmountCheck)
			AggregateErrorLabel.setText("umount -f \"" + sourceDir.toString()+ "\"");
		else
			AggregateErrorLabel.setText("Error in Unmunting");
		*/	
		odkAggregateButton.setEnabled(false);
		xmlAggregateButton.setEnabled(true);
		UnmountButton.setEnabled(false);
		UserNameText.setEditable(true);
		UserNameText.setText("");
		deviceText.setEditable(true);
		deviceText.setText("");
	}
	private void syncServer() throws MalformedURLException, IOException {
		logger.entering(getClass().getName(), "syncODK()");
		logger.fine("\tDevice Source Directory:" + sourceDir.getAbsolutePath());
		AggregateErrorLabel.setText("");
		
		validateDir(xmlDir);
		validateDir(sourceDir);
		if(UserNameText.getText().isEmpty())
		{
			//Error message if Device ID is missing
			AggregateErrorLabel.setText("Enter Valid User");
			logger.entering(getClass().getName(), "syncXML()");
			return;
		}
		
		else
		{
			AggregateErrorLabel.setText("Syncing Files");
		}
		ServerSyncProcessor = new KoboSurveyDeviceSynchronizer();
		boolean serverCheck = ServerSyncProcessor.BulkUpload(UserNameText.getText() , xmlDir.toString());
		
		
		
		if(serverCheck)
		{
			AggregateErrorLabel.setText("Surveys Synced");
			//odkAggregateButton.setEnabled(false);
			xmlAggregateButton.setEnabled(true);
			UserNameText.setEditable(true);
			deviceText.setEditable(true);
			deviceText.setText("");
		}
		else
		{
			AggregateErrorLabel.setText("Error in Syncing ");
			odkAggregateButton.setEnabled(true);
			xmlAggregateButton.setEnabled(true);
			UserNameText.setEditable(true);
			deviceText.setEditable(true);
		}
		
		
	}

	/**
	 * Transcribe the locally stored XML into persistent CSV storage
	 * using the KoboBatchTranscriber
	 */
/*	private void trascribeToCSV() {
		logger.entering(getClass().getName(), "transcribeToCSV()");
		logger.fine("transcribeToCsv():\n");
		logger.fine("\tXML Storage Directory:" + xmlDir.getAbsolutePath());

		try {
			validateDir(xmlDir);
			validateDir(csvDir);
			csvTranscribeProcessor = new KoboBatchTranscriber(xmlDir, csvDir);
		} catch (IllegalArgumentException iaex) {
			logger.warning(iaex.getMessage());
			return;
		}
		progressMonitor = new ProgressMonitor(this, "Sample Processor", "", 0,
				csvTranscribeProcessor.getLengthOfTask());
		progressMonitor.setMillisToDecideToPopup(10);
		progressMonitor.setMillisToPopup(100);
		csvTranscribeProcessor.addPropertyChangeListener(this);
		csvTranscribeProcessor.execute();
		progressMonitor.setNote(appText[Constants.STARTING_TEXT]);
		progressMonitor.setProgress(1);
		xmlAggregateButton.setEnabled(false);
		csvTranscribeButton.setEnabled(false);

		logger.fine("\tCSV Storage Directory:" + csvDir.getAbsolutePath());
		logger.exiting(getClass().getName(), "transcribeToCSV()");
	}
	*/
	public void exit() {
		this.updatePreferences();
	}

	protected void validateDir(File dir) {
		if(dir.equals(csvDir)) {
			if(csvDir == null || !csvDir.exists()) {
				String[] options = {appText[Constants.RETRY_TEXT], appText[Constants.SET_TEXT]};
				switch(JOptionPane.showOptionDialog(
						this, 
						String.format(appText[Constants.STRING_NODIR_MESSAGE], 
								appText[Constants.STRING_NODIR_CSV], 
								csvDir.getAbsolutePath()), 
						String.format(appText[Constants.STRING_NODIR_TITLE], appText[Constants.STRING_NODIR_CSV]), 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE,
						null,
						options, 
						options[0])) 
				{
					case JOptionPane.CLOSED_OPTION:
						validateDir(dir);
						break;
					case JOptionPane.YES_OPTION: //Ridiculous Naming Convention, but user clicked option[0]
						validateDir(dir);
						break;
					case JOptionPane.NO_OPTION: //Ridiculous Naming Convention, but user clicked option[1]
						final JFileChooser csvChooser = new JFileChooser();
						csvChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						csvChooser.setDialogTitle(appText[Constants.CHANGE_CSV_DIR_TEXT]);
						csvChooser.setCurrentDirectory(FUtil.getRealParent(csvDir));
						switch (csvChooser.showSaveDialog(this)) {
							case JFileChooser.APPROVE_OPTION:
								KoboPostProcPanel.applicationProps.setProperty(
										Constants.PROPKEY_DIRNAME_CSV, 
										csvChooser.getSelectedFile().getAbsolutePath());
								update();
							case JFileChooser.CANCEL_OPTION:
							case JFileChooser.ERROR_OPTION:
						}
				}
			}
		} else if (dir.equals(xmlDir)) {
			if(xmlDir == null || !xmlDir.exists()) {
				String[] options = {"Retry", "Set"};
				switch(JOptionPane.showOptionDialog(
						this, 
						String.format(appText[Constants.STRING_NODIR_MESSAGE], 
								Constants.STRING_NODIR_XML, 
								xmlDir.getAbsolutePath()), 
						String.format(appText[Constants.STRING_NODIR_TITLE], Constants.STRING_NODIR_XML), 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE,
						null,
						options, 
						options[0])) 
				{
					case JOptionPane.CLOSED_OPTION:
						validateDir(dir);
						break;
					case JOptionPane.YES_OPTION: //Ridiculous Naming Convention, but user clicked option[0]
						validateDir(dir);
						break;
					case JOptionPane.NO_OPTION: //Ridiculous Naming Convention, but user clicked option[1]
						final JFileChooser csvChooser = new JFileChooser();
						csvChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						csvChooser.setDialogTitle(appText[Constants.CHANGE_XML_DIR_TEXT]);
						csvChooser.setCurrentDirectory(FUtil.getRealParent(xmlDir));
						switch (csvChooser.showSaveDialog(this)) {
							case JFileChooser.APPROVE_OPTION:
								KoboPostProcPanel.applicationProps.setProperty(
										Constants.PROPKEY_DIRNAME_XML_STORAGE, 
										csvChooser.getSelectedFile().getAbsolutePath());
								update();
							case JFileChooser.CANCEL_OPTION:
							case JFileChooser.ERROR_OPTION:
						}
				}
			}
		} else if (dir.equals(sourceDir)) {
			if(sourceDir == null || !sourceDir.exists()) {
				String[] options = {"Retry", "Set"};
				switch(JOptionPane.showOptionDialog(
						this,
						String.format(Constants.STRING_NODIR_MESSAGE_SOURCE, 
								Constants.STRING_NODIR_SRC, 
								sourceDir.getAbsolutePath()), 
						String.format(appText[Constants.STRING_NODIR_TITLE], Constants.STRING_NODIR_SRC), 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE,
						null,
						options, 
						options[0])) 
				{
					case JOptionPane.CLOSED_OPTION:
						validateDir(dir);
						break;
					case JOptionPane.YES_OPTION: //Ridiculous Naming Convention, but user clicked option[0]
						validateDir(dir);
						break;
					case JOptionPane.NO_OPTION: //Ridiculous Naming Convention, but user clicked option[1]
						final JFileChooser csvChooser = new JFileChooser();
						csvChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						csvChooser.setDialogTitle(appText[Constants.CHANGE_XML_DIR_TEXT]);
						csvChooser.setCurrentDirectory(FUtil.getRealParent(sourceDir));
						switch (csvChooser.showSaveDialog(this)) {
							case JFileChooser.APPROVE_OPTION:
								KoboPostProcPanel.applicationProps.setProperty(
										Constants.PROPKEY_DIRNAME_XML_DEV, 
										csvChooser.getSelectedFile().getAbsolutePath());
								update();
							case JFileChooser.CANCEL_OPTION:
							case JFileChooser.ERROR_OPTION:
						}
				}
			}
		}
	}

	/**
	 * PropertyChange listener for the progress bars displayed during
	 * the xml synchronization and transcription to CSV
	 * 
	 * The degree of detail conveyed through these progress bars is entirely
	 * dependent upon updates sent from the appropriate classes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource().equals(xmlSyncProcessor)) {
			if (Constants.CHANGEPROP_NAME_PROGRESS == evt.getPropertyName()) {
				int progress = (Integer) evt.getNewValue();
				String message = String.format(appText[Constants.COMPLETED_PERCENT_TEXT], progress);
				progressMonitor.setNote(message);
				progressMonitor.setProgress(progress);
			} else if (Constants.CHANGEPROP_NAME_STATE == evt.getPropertyName()){
				if (xmlSyncProcessor.isDone()) {
					progressMonitor.setNote(appText[Constants.TASK_COMPLETED_TEXT]);
					statusText.setText(appText[Constants.XML_AGGREGATE_COMPLETE_TEXT]);
					syncStatusText.setText(String.format(appText[Constants.COUNTER_SYNC_TEXT], nSynced));
					csvTranscribeButton.setEnabled(true);
					xmlAggregateButton.setEnabled(true);
				}
			} else if (Constants.CHANGEPROP_NAME_NCOMPLETED == evt.getPropertyName()) {
				nSynced = nSynced + (Integer)evt.getNewValue();
			}
		} /*else if (evt.getSource().equals(this.csvTranscribeProcessor)) {
			if (Constants.CHANGEPROP_NAME_PROGRESS == evt.getPropertyName()) {
				int progress = (Integer) evt.getNewValue();
				String message = String.format(appText[Constants.COMPLETED_PERCENT_TEXT], progress);
				progressMonitor.setNote(message);
				progressMonitor.setProgress(progress);
			} else if (Constants.CHANGEPROP_NAME_STATE == evt.getPropertyName()) {
				if (csvTranscribeProcessor.isDone()) {
					progressMonitor.setNote(appText[Constants.TASK_COMPLETED_TEXT]);
					statusText.setText(appText[Constants.CSV_CONVERT_PROC_COMPLETE_TEXT]);
					transStatusText.setText(String.format(appText[Constants.COUNTER_TRANS_TEXT], nTranscribed));
					csvTranscribeButton.setEnabled(true);
					xmlAggregateButton.setEnabled(true);
				}
			} else if (Constants.CHANGEPROP_NAME_NCOMPLETED == evt.getPropertyName()) {
				nTranscribed = nTranscribed + (Integer)evt.getNewValue();
				transStatusText.setText(String.format(appText[Constants.COUNTER_TRANS_TEXT], nTranscribed));
			}
		}*/
	}
	
	
	/**
	 * This method initializes srcDirButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSrcDirButton() {
		if (srcDirButton == null) {
			try {
				srcDirButton = new JButton();
				final JFileChooser xmlSrcChooser = new JFileChooser();
				xmlSrcChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				xmlSrcChooser.setDialogTitle(appText[Constants.CHANGE_XML_DIR_TEXT]);
				//xmlSrcChooser.setCurrentDirectory(srcDir);
				xmlSrcChooser.setCurrentDirectory(sourceDir);
				
				srcDirButton.setText(appText[Constants.BROWSE_TEXT]);  // Generated
				srcDirButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						switch (xmlSrcChooser.showSaveDialog(frame)) {						
							case JFileChooser.APPROVE_OPTION:
								File srcDir = xmlSrcChooser.getSelectedFile();								
								KoboPostProcPanel.applicationProps.setProperty(
										Constants.PROPKEY_DIRNAME_XML_DEV, srcDir.getAbsolutePath());								
								srcPathText.setText(srcDir.getAbsolutePath());
								update();
							case JFileChooser.CANCEL_OPTION:
							case JFileChooser.ERROR_OPTION:
								return;
						}
					}
				});
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return srcDirButton;
	}
	
	/**
	 * This method initializes syncsrcDirButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSyncSrcDirButton() {
		if (syncsrcDirButton == null) {
			try {
				syncsrcDirButton = new JButton();
				final JFileChooser xmlSrcChooser = new JFileChooser();
				xmlSrcChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				xmlSrcChooser.setDialogTitle(appText[Constants.CHANGE_XML_DIR_TEXT]);
				//xmlSrcChooser.setCurrentDirectory(srcDir);
				xmlSrcChooser.setCurrentDirectory(sourceDir);
				
				syncsrcDirButton.setText(appText[Constants.BROWSE_TEXT]);  // Generated
				syncsrcDirButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						switch (xmlSrcChooser.showSaveDialog(frame)) {						
							case JFileChooser.APPROVE_OPTION:
								File srcDir = xmlSrcChooser.getSelectedFile();								
								KoboPostProcPanel.applicationProps.setProperty(
										Constants.PROPKEY_DIRNAME_XML_DEV, srcDir.getAbsolutePath());								
								syncStoragePathText.setText(sourceDir.getAbsolutePath());
								update();
							case JFileChooser.CANCEL_OPTION:
							case JFileChooser.ERROR_OPTION:
								return;
						}
					}
				});
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return syncsrcDirButton;
	}
	
	
	/**
	 * This method initializes srcDirButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getXmlStorageButton() {
		if (xmlStorageButton == null) {
			try {
				xmlStorageButton = new JButton();
				final JFileChooser dirChooser = new JFileChooser();
				dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dirChooser.setDialogTitle(appText[Constants.CHANGE_SRC_DIR_TEXT]);
				dirChooser.setCurrentDirectory(xmlDir);
				
				xmlStorageButton.setText(appText[Constants.BROWSE_TEXT]);  // Generated
				xmlStorageButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						switch (dirChooser.showSaveDialog(frame)) {
							case JFileChooser.APPROVE_OPTION:
								File dir = dirChooser.getSelectedFile();								
								KoboPostProcPanel.applicationProps.setProperty(
										Constants.PROPKEY_DIRNAME_XML_STORAGE, dir.getAbsolutePath());								
								storagePathText.setText(xmlDir.getAbsolutePath());
								update();
							case JFileChooser.CANCEL_OPTION:
							case JFileChooser.ERROR_OPTION:
								return;
						}
					}
				});
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return xmlStorageButton;
	}
	
	
	
	/**
	 * This method initializes srcDirButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCsvStorageButton() {
		if (csvStorageButton == null) {
			try {
				csvStorageButton = new JButton();
				final JFileChooser dirChooser = new JFileChooser();
				dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dirChooser.setDialogTitle(appText[Constants.CHANGE_CSV_DIR_TEXT]);
				dirChooser.setCurrentDirectory(csvDir);
				
				csvStorageButton.setText(appText[Constants.BROWSE_TEXT]);  // Generated
				csvStorageButton.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						switch (dirChooser.showSaveDialog(frame)) {
							case JFileChooser.APPROVE_OPTION:
								File dir = dirChooser.getSelectedFile();								
								KoboPostProcPanel.applicationProps.setProperty(
										Constants.PROPKEY_DIRNAME_CSV, dir.getAbsolutePath());								
								csvPathText.setText(dir.getAbsolutePath());
								update();
							case JFileChooser.CANCEL_OPTION:
							case JFileChooser.ERROR_OPTION:
								return;
						}
					}
				});
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return csvStorageButton;
	}
	
	public void windowClosed(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {
		this.exit();
	}

	public void windowActivated(WindowEvent e) {	}

	public void windowDeactivated(WindowEvent e) {	}

	public void windowDeiconified(WindowEvent e) {	}

	public void windowIconified(WindowEvent e) {	}

	public void windowOpened(WindowEvent e) {	}
}
