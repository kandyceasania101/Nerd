/**
 * Nerd 03/30/2017
 * Deep Patel
 * DisplayState class that handles drawing and updating of scene/display.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

public class GameState extends JPanel implements KeyListener, CollisionListener {
 private static GameState instance = null;
 private LocationArray locations;
 private CollisionEvent collisionChecker;
 private Character player;

 private GameState() {
  locations = LocationArray.getInstance();
  Assets assets = Assets.getInstance(); //needs to be under locationArray (dependent)

  collisionChecker = new CollisionEvent();
  collisionChecker.addListener(this);

  this.setBackground(Color.black);
  this.setPreferredSize(NerdGame.windowSize);
  this.setSize(NerdGame.windowSize);
  this.setFocusable(true);
  this.addKeyListener(this);

  FlowLayout layout = (FlowLayout)getLayout();
  layout.setVgap(0);
 }

 public static synchronized GameState getInstance() {
  if (instance == null) {
   instance = new GameState();
  }
  return instance;
 }

 @Override
 public void keyPressed(KeyEvent event) {
  // Nothing additional to do as of yet...
 }

 @Override
 public void keyReleased(KeyEvent event) {
  int keyCode = event.getKeyCode();
  if (keyCode == KeyEvent.VK_ESCAPE) {
    DisplayState.getInstance().setCurrentDisplayStatus(DisplayStatus.PAUSEMENU);
    removeCurrentCanvasIfNeeded();
  } else if (keyCode == KeyEvent.VK_UP) {
    player.moveUp();
  } else if (keyCode == KeyEvent.VK_DOWN) {
    player.moveDown();
  } else if (keyCode ==KeyEvent.VK_LEFT) {
    player.moveLeft();
  } else if (keyCode == KeyEvent.VK_RIGHT) {
    player.moveRight();
  }
 }

 @Override
 public void keyTyped(KeyEvent event) {
  // Nothing additional to do as of yet...
 }

 @Override
 public void collisionDetected() {
  DisplayState.getInstance().setCurrentDisplayStatus(DisplayStatus.LOSEMENU);
  removeCurrentCanvasIfNeeded();
 }

 public void victory() {
  DisplayState.getInstance().setCurrentDisplayStatus(DisplayStatus.VICTORYMENU);
  removeCurrentCanvasIfNeeded();
 }

 public void start() {
  player = new Weaboo(locations.getCurrentSpawnX(), locations.getCurrentSpawnY());
  collisionChecker.addListener(player);
 }

 public void update() {
  collisionChecker.checkCollision(player, locations.getMovableObstacles());
  locations.updateCurrentLocation();
 }

 public void draw() {
  addCurrentCanvasIfNeeded();
  this.requestFocus();

  locations.drawCurrentLocation();
  player.draw();
 }

 private void addCurrentCanvasIfNeeded() {
  if (this.getParent() == null) {
   DisplayState.getInstance().add(this);
  }
 }

 private void removeCurrentCanvasIfNeeded() {
  DisplayState currentDisplayState = DisplayState.getInstance();
  if (this.getParent() != null && this.getParent() == currentDisplayState) {
   currentDisplayState.remove(this);
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
