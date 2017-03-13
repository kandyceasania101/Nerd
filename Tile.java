/**
 * ~ Tile.java
 * Raymond Hruby II
 * 03/13/2017
 * Tile solidifies tileId to a sprite image
 */
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public abstract class Tile
{
  public static Tile[] tiles = new Tile[50];
  public static Tile grass01Tile = new Tile_Grass01(0); 
  public static Tile grass02Tile = new Tile_Grass02(1); 
  public static Tile grass03Tile = new Tile_Grass03(2);
  public static Tile trail01Tile = new Tile_Trail01(3);
  public static Tile trail02Tile = new Tile_Trail02(4);
  public static Tile rock01Tile = new Tile_Rock01(5);
  public static Tile rock02Tile = new Tile_Rock02(6);
  
  public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;
   
  private int id;
  BufferedImage sprite;
  
  public Tile(BufferedImage sprite, int id){
    this.id=id;
    this.sprite = sprite;
    
    tiles[id]= this;
  }
  
  public void update(){}
  
  public void draw(Graphics g, int x, int y){
    g.drawImage(sprite, x, y, TILE_WIDTH, TILE_HEIGHT, null); 
  }

  //getters and setters
  public int getId(){
    return id;
  }
    
}