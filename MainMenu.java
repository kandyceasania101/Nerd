/**
 * Nerd 03/11/2017
 * Deep Patel
 * MainMenu class that is used to display the menu on game start.
 * TODO:
 *  - Add Shop
 */

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;

public class MainMenu extends Menu implements ChangeListener {
	private JPanel mainScreen;
	private JPanel shopScreen;
	private JPanel settingsScreen;
	private String selectedCharacter;
	private HoverButton selectedCharacterButton;
	
	public MainMenu() {
		super();

		selectedCharacter = "selectWeaboo";
		selectedCharacterButton = null;

		setupMainScreen(NerdGame.windowSize);

		this.add(mainScreen);
	}

	private void setupMainScreen(Dimension windowSize) {
		mainScreen = createScreen(windowSize, new FlowLayout(FlowLayout.LEFT));

		Font titleFont = new Font(Font.SERIF, Font.PLAIN, 128);
		Font buttonFont = new Font(Font.SERIF, Font.PLAIN, 48);

		mainScreen.add(Box.createRigidArea(new Dimension(windowSize.width * 5 / 7, 0)));

		JLabel title = createLabel("Nerd", titleFont);
		mainScreen.add(title);

		String buttonTitles[] = {"Start", "Shop", "Settings", "Exit"};
		String buttonCommands[] = {"startGame", "shopMenu", "settingsMenu", "closeGame"};
		String regularButtonPath = "./resources/menus/mainButton.png";
		String destructiveButtonPath = "./resources/menus/mainDestructiveButton.png";
		String hoverButtonPath = "./resources/menus/hoverMainButton.png";
		String destructiveHoverButtonPath = "./resources/menus/hoverDestructiveButton.png";

		for (int i = 0; i < buttonTitles.length; i++) {
			mainScreen.add(Box.createRigidArea(new Dimension(windowSize.width, 20)));

			String path = regularButtonPath;
			String hoverPath = hoverButtonPath;
			if (buttonTitles[i].equals("Exit")) {
				path = destructiveButtonPath;
				hoverPath = destructiveHoverButtonPath;
			}

			HoverButton hb = createHoverButton(buttonTitles[i], buttonCommands[i], buttonFont, path, hoverPath);
			hb.setPreferredSize(new Dimension(windowSize.width / 4, hb.getPreferredSize().height));
			hb.setShouldCenter(false);
			mainScreen.add(hb);
		}
	}

	private void setupShopScreen(Dimension windowSize) {
		shopScreen = createScreen(windowSize, new FlowLayout(FlowLayout.LEFT));

		Font characterFont = new Font(Font.SERIF, Font.PLAIN, 24);

		addHeader(shopScreen, "Nerd Shop", "mainFromShop", windowSize);

		// TODO: Add more characters
		BufferedImage characters[] = {Assets.player01};
		String buttonCommands[] = {"selectWeaboo"};
		String names[] = {"Weaboo"};

		for (int i = 0; i < characters.length; i++) {
			mainScreen.add(Box.createRigidArea(new Dimension(20, 20)));

			String title = names[i];
			if (selectedCharacter.equals(buttonCommands[i])) {
				title = names[i] + " selected";
			}

			HoverButton characterButton = new HoverButton(title);

			if (selectedCharacter.equals(buttonCommands[i])) {
				selectedCharacterButton = characterButton;
			}

			characterButton.addActionListener(this);
			characterButton.setActionCommand(buttonCommands[i]);
			characterButton.setFont(characterFont);
			characterButton.setImage(characters[i]);
			characterButton.setHoverImage(characters[i]);
			characterButton.setPressedImage(characters[i]);
			characterButton.setShouldCenter(true);
			characterButton.setPreferredSize(new Dimension(windowSize.width / 6, characters[i].getHeight()));

			shopScreen.add(characterButton);
		}
	}

	private void setupSettingsScreen(Dimension windowSize) {
		settingsScreen = createScreen(windowSize, new FlowLayout(FlowLayout.LEFT));

		addHeader(settingsScreen, "Settings", "mainFromSet", windowSize);

		addSubHeader(settingsScreen, "Volume", windowSize);

		settingsScreen.add(Box.createRigidArea(new Dimension(40, 20)));

		JSlider volumeControlSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 25);
		volumeControlSlider.addChangeListener(this);
		volumeControlSlider.setPreferredSize(new Dimension(windowSize.width - 100, 40));
		volumeControlSlider.setBackground(Color.black);
		settingsScreen.add(volumeControlSlider);

		settingsScreen.add(Box.createRigidArea(new Dimension(windowSize.width, 20)));

		// TODO: Add more settings
	}

	private void addHeader(JPanel panel, String displayTitle, String backButtonCommand, Dimension windowSize) {
		Font titleFont = new Font(Font.SERIF, Font.PLAIN, 80);
		Font buttonFont = new Font(Font.SERIF, Font.PLAIN, 32);
		Font backFont = new Font(Font.SERIF, Font.PLAIN, 16);

		panel.add(Box.createRigidArea(new Dimension(20, 20)));

		HoverButton backButton = createHoverButton("", backButtonCommand, backFont, "./resources/menus/backButton.png", "./resources/menus/hoverBackButton.png");
		backButton.setPreferredSize(new Dimension(64, 64));
		panel.add(backButton);

		JLabel title = createLabel(displayTitle, titleFont);

		panel.add(Box.createRigidArea(new Dimension(windowSize.width / 2 - (int)(title.getPreferredSize().getWidth() / 2) - (int)(backButton.getPreferredSize().getWidth()) - 20, 20)));

		panel.add(title);

		panel.add(Box.createRigidArea(new Dimension(windowSize.width / 2 - (int)(title.getPreferredSize().getWidth() / 2) - (int)(backButton.getPreferredSize().getWidth()), 20)));
	}

	private void addSubHeader(JPanel panel, String displayTitle, Dimension windowSize) {
		Font titleFont = new Font(Font.SERIF, Font.PLAIN, 56);

		JLabel title = createLabel(displayTitle, titleFont);

		panel.add(Box.createRigidArea(new Dimension(40, 20)));

		panel.add(title);

		panel.add(Box.createRigidArea(new Dimension(windowSize.width, 20)));
	}

	public void update() {
		// Nothing additional to update as of yet...
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("startGame")) {
			GameState.getInstance().start();
			DisplayState.getInstance().setCurrentDisplayStatus(DisplayStatus.INGAME);
			super.removeCurrentCanvasIfNeeded();
		} else if (event.getActionCommand().equals("shopMenu")) {
			setupShopScreen(NerdGame.windowSize);
			changeScreen(mainScreen, shopScreen);
		} else if (event.getActionCommand().equals("settingsMenu")) {
			if (settingsScreen == null) {
				setupSettingsScreen(NerdGame.windowSize);
			}
			changeScreen(mainScreen, settingsScreen);
		} else if (event.getActionCommand().equals("closeGame")) {
			NerdGame.getInstance().close();
		} else if (event.getActionCommand().equals("mainFromShop")) {
			changeScreen(shopScreen, mainScreen);
		} else if (event.getActionCommand().equals("mainFromSet")) {
			changeScreen(settingsScreen, mainScreen);
		} else if (event.getActionCommand().contains("select")) {
			if (selectedCharacter != event.getActionCommand()) {
				selectedCharacterButton.setText(selectedCharacterButton.getText().replace(" selected", ""));
				selectedCharacterButton.repaint();
				selectedCharacter = event.getActionCommand();
				selectedCharacterButton = (HoverButton)event.getSource();
				selectedCharacterButton.setText(selectedCharacterButton.getText() + " selected");
				selectedCharacterButton.repaint();
			}
		}
	}

	public void stateChanged(ChangeEvent event) {
		DisplayState.getInstance().setBackgroundMusicVolume(((JSlider)event.getSource()).getValue() / 100.0f);
	}

	private void changeScreen(JPanel fromScreen, JPanel toScreen) {
		this.remove(fromScreen);
		clear(Color.black);
		this.add(toScreen);
		this.paintComponents(this.getGraphics());
	}
}