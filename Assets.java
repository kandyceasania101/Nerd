/**
 * ~ Assets.java
 * Raymond Hruby II
 * 04/18/2017
 * Assets class holds sprite images
 */
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets
{
  private static int WIDTH=64, HEIGHT=64, 
    ROWS,COLS;
  
  private static Assets instance = null;
  
  public static ArrayList<BufferedImage> sprites;
  public static ArrayList<BufferedImage> sprites_cars;
  private String path;        //path to Spritesheet 

  public static Assets getInstance() {
    if (instance == null) {
      instance = new Assets( LocationArray.getInstance().getCurrentLocationIndex() );
    }
    return instance;
  }
  private Assets(int locationId){ //private constructor for Singleton pattern, use getInstance()
    System.out.println("inpath" + this.path);
    this.path = AssetsLoader.getAssetsPath(locationId);
    System.out.println("inpath" + this.path);
    
    updateRowsAndCols(locationId);
    initialize();
  }
  public void initialize(){
    SpriteSheet sheet = new SpriteSheet(this.path);
    sprites = new ArrayList<BufferedImage>(ROWS*COLS);
    
    for (int i = 0; i < ROWS; i++){
      for (int j = 0; j < COLS; j++){
        sprites.add( i*COLS+j, sheet.crop( j*WIDTH, i*HEIGHT, WIDTH, HEIGHT ) );
      }
    }
    
    //CARS
    if(LocationArray.getInstance().getCurrentLocationIndex() == 0){
      sprites_cars = new ArrayList<BufferedImage>(3); //hardcoded, no measure of how many will have
      
      sprites_cars.add( 0, sheet.crop( 0, 5*HEIGHT, WIDTH*2, HEIGHT ) );
      sprites_cars.add( 1, sheet.crop( 2*WIDTH, 5*HEIGHT, WIDTH*2, HEIGHT ) );
      sprites_cars.add( 2, sheet.crop( 0, 6*HEIGHT, WIDTH*2, HEIGHT ) );
    }
  }
  
  //Only call if there is a new locaiton
  public void update(){
    int newLocationId = LocationArray.getInstance().getCurrentLocationIndex();
    
    System.out.println("inpath" + this.path);
    this.path = AssetsLoader.getAssetsPath(newLocationId);
    System.out.println("inpath" + this.path);
    
    updateRowsAndCols(newLocationId);
    initialize();
  }
  public void updateRowsAndCols(int id){
    switch (id){
      case 0:  ROWS = 7; COLS = 5;
      break;
      case 1:  ROWS = 8; COLS = 5;
      break;
      default: ROWS = 5; COLS = 5;
      break;
    }
  }
  
  //GETTERS
  public BufferedImage getSprite(int index){
    return sprites.get(index);
  }
  public BufferedImage getCarSprite(int index){
    return sprites_cars.get(index);
  }
  public int getRows(){
    return ROWS;
  }
  public int getCols(){
    return COLS;
  }

}