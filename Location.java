/*
 *  ~Location.java
 * Raymond Hruby II
 * 03/13/2017
 * Location - holds tileCodes and dimension of location
 * 
 * TODO: Checkbounds back to location height and width, 
 */
import java.util.ArrayList;
import java.awt.Graphics;

//TODO: - implement string parser to get dimensions from location.txt file 
  
//Location class to store data for locations
public abstract class Location implements Drawable
{
  private int id;                  //index for LocationArray, index used for theme(assetLoader)     
  private int spawnX,spawnY;
  private String path;             //path to location data in .txt
  private LocationDimension dimensions;
  private int[][] tileCodes;       //holds the tile codes from the location.txt
  private ArrayList<MovableObstacle> movableObstacles;
  private ArrayList<StaticObstacle> staticObstacles;
  private OzLocation ozLo;
  
  public Location(int locationId){
    ///grabbing width and height from location.txt files in createLocation()
    this.id = locationId; 
    this.path = AssetsLoader.getLocationPath(locationId);
    this.movableObstacles = new ArrayList<MovableObstacle>();
    this.staticObstacles = new ArrayList<StaticObstacle>();
    initializeLocation(path);
  }
  
  abstract public void update();
  abstract public void draw();
  
  //getters and setters below:
  public String getPath(){
    return this.path;
  }
  public int getId(){
    return this.id;
  }
  
  public int getWidth(){
    return this.dimensions.getWidth();
  } 
  public int getHeight(){
    return this.dimensions.getHeight();
  }
  public int getSpawnX(){
    return this.spawnX;
  }
  public int getSpawnY(){
    return this.spawnY;
  }
  
  //TILES
  public Tile getTile(int x, int y){
    //protection against going outside boundary(glitches)
    if(x<0 || y<0 || x>=dimensions.getWidth() || y>=dimensions.getHeight()){
      return Tile.grass01Tile;
    }
    
    Tile t = Tile.tiles[ tileCodes[x][y] ];
    if(t==null){  //default to grassTile if error occured
      return Tile.grass01Tile;
    }
    return t;
  }
  
  //OBSTACLES
  public void addMovable(MovableObstacle obstacle){
    movableObstacles.add(obstacle);
  }
  public void addStatic(StaticObstacle obstacle){
    staticObstacles.add(obstacle);
  } 
  public MovableObstacle getMovable(int index){
    return movableObstacles.get(index);
  }
  public StaticObstacle getStatic(int index){
    return staticObstacles.get(index);
  }
  //OBSTACLES LISTS
  public ArrayList<MovableObstacle> getMovableObstacles(){
    return movableObstacles;
  }
  public ArrayList<StaticObstacle> getStaticObstacles(){
    return staticObstacles;
  }
  
  public boolean canMoveToPosition(int xPos, int yPos){       
    return checkBounds(xPos, yPos);
  }
  public boolean checkBounds(int xPos, int yPos){
    return (xPos>1 && xPos<1024)&&(yPos>1 && yPos<768);
  }
 
  public void initializeLocation(String path){
    String file = Utilities.loadFileAsString(path);
    //System.out.println("Location | FILE: "+file+"\n");
    String[] tokens = file.split("\\s+");//split on space or newline
    
    int width = Utilities.parseInt(tokens[0]);   
    int height = Utilities.parseInt(tokens[1]);
    dimensions = new LocationDimension(width,height);
    
    spawnX = Utilities.parseInt(tokens[2]);
    spawnY = Utilities.parseInt(tokens[3]);
    
    tileCodes = new int[width][height];
    
    for(int y=0; y<height; y++){
      for(int x=0; x<width; x++){
        //add 4 becuase of previous set values (four values)
        tileCodes[x][y] = Utilities.parseInt( tokens[(x+y*width)+4] );                                           
      }
    }
  }
}
