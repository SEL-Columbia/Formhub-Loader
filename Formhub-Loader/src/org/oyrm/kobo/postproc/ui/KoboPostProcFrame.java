/* 
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.oyrm.kobo.postproc.ui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.UnsupportedLookAndFeelException;

import org.oyrm.kobo.postproc.constants.Constants;
import org.oyrm.kobo.postproc.ui.actions.ExitAction;
import org.oyrm.kobo.postproc.ui.actions.ShowSettingsAction;

/**
 * This extension of JFrame handles instantiation, property persistence, user interactivity,
 * and an entry point to the central functionality of the org.oyrm.kobo.postproc package
 * @author Gary Hendrick
 */
@SuppressWarnings( { "serial" })
public class KoboPostProcFrame extends JFrame implements WindowListener{

	private static KoboPostProcFrame INSTANCE;

	private final static String TITLE = new String("formhub Loader");

	private KoboPostProcPanel mainPanel = null;
	/**
	 * Sets up the GUI
	 * @throws HeadlessException
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private KoboPostProcFrame() throws HeadlessException {
		super();
		this.setTitle(KoboPostProcFrame.TITLE);
		addWindowListener(this);
		
		mainPanel = KoboPostProcPanel.getInstance(this);
		init();
	}

	/**
	 * Needs research, interestingly this doesn't actually prevent
	 * the launching of multiple instances of the KoboPostProcFrame
	 * @return KoboPostProcFrame singleton
	 */
	public synchronized static KoboPostProcFrame getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new KoboPostProcFrame();
		}
		return INSTANCE;
	}

	/**
	 * Housekeeping on exit
	 * Saves properties to user.home/Constants.CONFIG_STORAGEDIR
	 */
	private void onExit() {
		mainPanel.updatePreferences();
		dispose();
	}

	private void init() {
		
		JMenuBar mBar = new JMenuBar();
		JMenu settingsMenu = new JMenu(Constants.TEXT_MENUITEM_OPTION);
		settingsMenu.setMnemonic(KeyEvent.VK_O);  // Generated
		settingsMenu.add(new ShowSettingsAction());
		settingsMenu.add(new ExitAction());
		
		mBar.add(settingsMenu);
		//setJMenuBar(mBar);

		
		/**
		 * Make it So Necessities
		 */
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setContentPane(mainPanel);
		setLocation(100, 100);
		this.setPreferredSize(new Dimension(600,300));
		setVisible(true);
		
		pack();

	}

	
	/**
	 * @param args should be empty as settings are stored in user.dir under
	 * the .kobo directory
	 */
	public static void main(String[] args) {
		getInstance();
	}

	/**
	 * Update Preferences 
	 */
	protected void update() {
		
		mainPanel.update();
		pack();
		
	}
	
	

	

	
	
	public void exit() {
		mainPanel.exit();
		this.onExit();
	}

	

	
	public void windowClosing(WindowEvent e) {
		this.exit();
	}

	public void windowActivated(WindowEvent e) {	}

	public void windowDeactivated(WindowEvent e) {	}

	public void windowDeiconified(WindowEvent e) {	}

	public void windowIconified(WindowEvent e) {	}

	public void windowOpened(WindowEvent e) {	}

	public void windowClosed(WindowEvent arg0) {	}
}