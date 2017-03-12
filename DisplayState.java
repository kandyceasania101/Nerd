/**
 * Nerd 03/04/2017
 * Deep Patel
 * DisplayState class that handles drawing and updating of scene/display.
 * TODO:
 *  - Add Menus
 */

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class DisplayState extends JPanel {
	private static DisplayState instance = null;
	private DisplayStatus currentDisplayStatus;
	private GameState gameCanvas;
	private ArrayList<Menu> menus = new ArrayList<Menu>();

	private DisplayState() {
		currentDisplayStatus = DisplayStatus.STARTMENU;
		gameCanvas = GameState.getInstance();
		setupMenus();

		this.setBackground(Color.black);
		this.setPreferredSize(NerdGame.windowSize);
		this.setSize(NerdGame.windowSize);

		FlowLayout layout = (FlowLayout)getLayout();
		layout.setVgap(0);
	}
	
	private void setupMenus() {
		menus.add(new MainMenu());
		menus.add(new PauseMenu());
	}

	public static synchronized DisplayState getInstance() {
		if (instance == null) {
			instance = new DisplayState();
		}
		return instance;
	}

	public DisplayStatus getCurrentDisplayStatus() {
		return currentDisplayStatus;
	}

	public void setCurrentDisplayStatus(DisplayStatus newDisplayStatus) {
		currentDisplayStatus = newDisplayStatus;
		clear(Color.black);
	}

	public void updateCurrentDisplayStatus() {
		switch (currentDisplayStatus) {
			case STARTMENU:
				menus.get(0).update();
				break;
			case INGAME:
				gameCanvas.update();
				break;
			case PAUSEMENU:
				menus.get(1).update();
				break;
			case VICTORYMENU:
				break;
			case LOSEMENU:
				break;
			case CLOSE:
				break;
			default:
				break;
		}
	}

	public void drawCurrentDisplayStatus() {
		addCurrentCanvasIfNeeded();
		switch (currentDisplayStatus) {
			case STARTMENU:
				menus.get(0).draw();
				break;
			case INGAME:
				gameCanvas.draw();
				break;
			case PAUSEMENU:
				menus.get(1).draw();
				// TODO: display pause menu overlay on top of the game
				break;
			case VICTORYMENU:
				// TODO: display victory menu
				break;
			case LOSEMENU:
				// TODO: display lose menu
				break;
			case CLOSE:
				break;
			default:
				break;
		}
	}

	private void addCurrentCanvasIfNeeded() {
		if (this.getParent() == null) {
			NerdGame.getInstance().add(this);
		}
	}

	private void clear(Color color) {
		Graphics g = getGraphics();
		Dimension canvasSize = getSize();
		if (g != null) {
			g.setColor(color);
			g.fillRect(0, 0, (int)canvasSize.getWidth(), (int)canvasSize.getHeight());
		}
	}
}