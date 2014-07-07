package game.world;
import java.util.Random;

/**
 * @author Robert
 * @date 2014/06/10
 * @version 0.0.1
 */
public class World {
	
	private static final int NORTHWEST = 0;
	private static final int NORTH = 1;
	private static final int NORTHEAST = 2;
	private static final int WEST = 3;
	private static final int EAST = 4;
	private static final int SOUTHWEST = 5;
	private static final int SOUTH = 6;
	private static final int SOUTHEAST = 7;
	
	
	private static final int NUM_OF_STEPS = 5;
	
	private int stepsDone = 0;
	private int size_H;
	private int size_W;
	private Tile[][] gWorld;
	private static Random random = new Random();
	
	public World(int size_H, int size_W){
		//ctor
		//initialize size Variables.
		this.setsize_H(size_H);
		this.setsize_W(size_W);
		gWorld = new Tile[size_H][size_W];
		initializeWorld(gWorld);
		for(stepsDone = 0; stepsDone < NUM_OF_STEPS; stepsDone++){
			gWorld = doSimulationStep(gWorld);
			
		}
		
	}
	
	
	public void initializeWorld(Tile[][] world){
		//initialize a new World of Random Tiles.
		for (int y = 0; y <size_H; y++){
			for(int x = 0; x < size_W; x++){
				//create a new tile.
				Tile tile = new Tile();
				this.gWorld[y][x] = tile.setupTile();
					
			}
		}
	}
	
	private Tile[][] doSimulationStep(Tile[][] world){
        //create a new blank world, so we're not checking new data.
        //then loop over it, smoothing it out as we go.
		Tile[][] newWorld = new Tile[size_H][size_W];
		int passComplete = NUM_OF_STEPS - (NUM_OF_STEPS - stepsDone);
        for (int y = 0; y < size_H; y++){
            for (int x = 0; x < size_W; x++){
            	newWorld[y][x] = new Tile().setupTile(TileType.WATER);
                //fucking check for bounds, only once in this version of code.  Thank GOD!  (y really hate checking bounds.
                //ORACLE: Do us a favor, make a function for arrays called BoundsCheck() or some such, that does this for us,
                //and build it into the array type.  It should return a boolean.  This will make everyone's lives easier.(Yes, y Am
                //That lazy))
                if ((y == 0 
                	|| x == 0
                    || y + 1 >= world.length 
                    || x + 1 >= world[0].length)){
                    newWorld[y][x].setupTile(TileType.WATER);
                    //DEBUG::System.out.println("BOUND DETECTED!\nFlipping Tile: " + y + " : " + x + " to Water!\nOn Pass" + passComplete);
                    	
                }
                    	
                else{
                    //Count Our Neighbors, so we can apply some rules.
                    Tile[] neighbors = countNeighbors(world, y, x);
 
                    //store our neighborTypes as Integers so we can sort them..
                    int neighborTypes[] = new int[world[0][0].getNumOfTypes()];
                    for (int index = 0; index < neighbors.length; index++){
                        neighborTypes[neighbors[index].getIndexOfType()] ++;
                    }
                                       
                    /*Start applying Rules to Tiles.
                     * Rule 1: if all surrounding tiles are water, flip tile to water.
  					 * Rule 2: If Too much grass, spawn dirt before the last step
                     * Rule 3: generate water if there are more than 4 (water|dirt)-tiles around before last step
                     * Rule 4: If any two opposite surrounding tiles are water, flip tile to sand .
                     * Rule 5: otherwise flip to the greatest surrounding tile type.
                     */
 
 
                    //apply rule 1:
                    if((neighborTypes[TileType.WATER.ordinal()] == 8)
                    		&&(stepsDone == NUM_OF_STEPS) && newWorld[y][x].getType()!= TileType.WATER){
                        newWorld[y][x].setupTile(TileType.WATER);
                        //DEBUG::System.out.println("All Neighbors Water! Flipping Tile: " + y + ":" + x +" To Water!\nOn Pass" + passComplete);
                    }
                    
                    //apply rule 2:
                    else if((neighborTypes[TileType.GRASS.ordinal()] > 6)
                    		&& (stepsDone < NUM_OF_STEPS -1)){
                    	//DEBUG::System.out.println("Not Enough Dirt! Flipping Tile: " + y + ":" + x +" To Dirt!\nOn Pass" + passComplete);
                    	newWorld[y][x].setupTile(TileType.DIRT);
                    	
                    }
                   
                    
                    //apply rule 3:
                    else if ((neighborTypes[TileType.GRASS.ordinal()] + neighborTypes[TileType.DIRT.ordinal()]  > 7) 
                    		&& (stepsDone < NUM_OF_STEPS - 1)) {
                    	//DEBUG::System.out.println("Not Enough Internal Water! Flipping Tile: " + y + ":" + x +" To Water!\nOn Pass" + passComplete);
                        newWorld[y][x].setupTile(TileType.WATER);
                        
                    }
                    //apply rule 4:
                    else if ((((neighbors[NORTH].getType() == TileType.WATER) ^ (neighbors[SOUTH].getType() == TileType.WATER))
                    		^ ((neighbors[EAST].getType() == TileType.WATER)^(neighbors[WEST].getType() == TileType.WATER)))
                    		&& stepsDone >= NUM_OF_STEPS - 1) {
                    	//DEBUG::System.out.println("Shore Detected! Flipping Tile: " + y + ":" + x +" To Sand!\nOn Pass" + passComplete);
                    	newWorld[y][x].setupTile(TileType.SAND);
                    }
                    
                    //apply rule 5:
                    else {
                        int greatest = 0;
                        
                    	for (int index = 0; index < neighborTypes.length; index ++){
                            if(neighborTypes[greatest] < neighborTypes[index]){
                                greatest = index;
                            }
                        	newWorld[y][x].setupTile(greatest);
                    	}
                    }
                }
            }
		}            
	return newWorld;
}

	private Tile[] countNeighbors(Tile[][] world, int x, int y){
	
		
		Tile[] neighbors = {world[x - 1][y - 1], world[x - 1][y], world[x - 1][y + 1], 
							world[x][y -1],		 						world[x][y+1], 
							world[x + 1][y-1],  world[x + 1][y],  world[x + 1][y + 1]};
		return neighbors;
	}		
				
				
				
			
	/**
	 * @return the size_H
	 */
	public int getsize_H() {
		return size_H;
	}


	/**
	 * @param size_H the size_H to set
	 */
	public void setsize_H(int size_H) {
		this.size_H = size_H;
	}


	/**
	 * @return the size_W
	 */
	public int getsize_W() {
		return size_W;
	}


	/**
	 * @param size_W the size_W to set
	 */
	public void setsize_W(int size_W) {
		this.size_W = size_W;
	}

	/**
	 return cell at position
	 */
	public Tile getTile(int x, int y){
		return gWorld[x][y];
	}
	public void print(){
		for(int y = 0; y < size_H; y++){
			String mapString = "";
			for(int x = 0; x < size_W; x++){
				mapString += this.gWorld[y][x].getTileChar();
			}
			System.out.println(mapString);
		}
	}
	
}
	
	

//###END WORLD###