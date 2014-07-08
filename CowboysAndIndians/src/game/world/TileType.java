package game.world;

import game.units.Unit;


public enum TileType {
			//Here we're enumerating out our tile types.  This will make it easier to add tiles in the future.   
			/*
			 * Params:
			 * char tileChar: Debug purposes, just to display the map in console.
			 * boolean passable: Whether or not the tile is passable.
			 * double speedMod: Any units moving through this tile multiply their speed by this.
			 * String sType: The String version of the tile type
			 * Unit[5] unitsInMe: Any units that are in the tile.
			 * String sprite: The image file to load for this tile.
			 */
			WATER('~', false, 0.0, "Water", null, "water.png"),
			SAND('§', true, 0.75, "Sand", null, "sand.png"), 
			DIRT('#', true, 1.0, "Dirt", null, "dirt.png"), 
			GRASS('"', true, 1.0, "Grass", null, "grass.png"),
			PEBBLES('*', true, 1.0, "Pebbles", null, "pebbles.png"),
			ROCK('^', false, 0.0, "Rock", null, "rocks.png"),
			TREE('†', true, 0.5, "Tree", null, "tree.png");
			
			//same member variables as our parent class.
			public String tileImage = "./resources/images/world/";
			char tileChar;
			boolean passable;
			double speedmod;
			String sType;		
			Unit[] unitsInMe;
			String sprite;
			
			private TileType(char tileChar, boolean passable, double speedMod, String sType, Unit[] unitsInMe, String sprite){
				//CTOR
				this.tileChar = tileChar;
				this.passable = passable;
				this.speedmod = speedMod;
				this.sType = sType;
				this.tileImage = tileImage + sprite;
				this.unitsInMe = new Unit[5];
			
			}
			
			
			
};
