package hw3;

import java.util.ArrayList;
import java.util.Random;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;

/**
 * Class that models a game.
 */
public class ConnectGame {
	/**
	 * Dialogue listener
	 */
	private ShowDialogListener dialogListener;
	/**
	 * score listener
	 */
	private ScoreUpdateListener scoreListener;
	/**
	 * minimum tile level
	 */
	private int minLevel;
	/**
	 * maximum tile level
	 */
	private int maxLevel;
	/**
	 * The game's grid
	 */
	private Grid grid;
	/**
	 * Random
	 */
	private final Random random;
	/**
	 * checks if selection is in progress
	 */
	private boolean selectionInProgress;
	/**
	 * Sets the selected tile
	 */
	private Tile selectedTile;
	/**
	 * arrayList of selected tiles
	 */
	private ArrayList<Tile> selectedTiles = new ArrayList<>();
	/**
	 * game's score
	 */
	private long score;
	/**
	 * The last selected tile's level
	 */
	private int lastTileLevel;
	/**
	 * The last selected tile's x value
	 */
	private int lastSelectedX;
	/**
	 * The last selected tile's y value
	 */
	private int lastSelectedY;
	/**
	 * Checks if the minimum tile level has been dropped
	 */
	private boolean levelDropped;
	private boolean forceSelect;

	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum
	 * and maximum tile levels.
	 * 
	 * @param width  grid width
	 * @param height grid height
	 * @param min    minimum tile level
	 * @param max    maximum tile level
	 * @param rand   random number generator
	 */
	public ConnectGame(int width, int height, int min, int max, Random rand) {
		grid = new Grid(width, height);
		minLevel = min;
		maxLevel = max;
		random = rand;
	}

	/**
	 * Gets a random tile with level between minimum tile level inclusive and
	 * maximum tile level exclusive. For example, if minimum is 1 and maximum is 4,
	 * the random tile can be either 1, 2, or 3.
	 * <p>
	 * DO NOT RETURN TILES WITH MAXIMUM LEVEL
	 * 
	 * @return a tile with random level between minimum inclusive and maximum
	 *         exclusive
	 */
	public Tile getRandomTile() {

		return new Tile(random.nextInt(minLevel, maxLevel));
	}

	/**
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles() {
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				grid.setTile(getRandomTile(),j,i);
			}
		}
	}

	/**
	 * Determines if two tiles are adjacent to each other. The may be next to each
	 * other horizontally, vertically, or diagonally.
	 * 
	 * @param t1 one of the two tiles
	 * @param t2 one of the two tiles
	 * @return true if they are next to each other horizontally, vertically, or
	 *         diagonally on the grid, false otherwise
	 */
	public boolean isAdjacent(Tile t1, Tile t2) {
		int x1 = t1.getX();
		int y1 = t1.getY();
		int x2 = t2.getX();
		int y2 = t2.getY();

		return (x1 == x2 || x1 == x2 - 1 || x1 == x2 + 1) && (y1 == y2 || y1 == y2 - 1 || y1 == y2 + 1);

	}

	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new
	 * selection of tiles.
	 * <p>
	 * If a selection of tiles is already in progress, the method should do nothing
	 * and return false.
	 * <p>
	 * If a selection is not already in progress (this is the first tile selected),
	 * then start a new selection of tiles and return true.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return true if this is the first tile selected, otherwise false
	 */
	public boolean tryFirstSelect(int x, int y) {
		if(selectionInProgress){
			return false;
		}
		else {
			// sets selection in progress to true and adds the tile to the array of selected tiles
			grid.getTile(x,y).setSelect(true);
			selectedTile = grid.getTile(x, y);
			selectionInProgress = true;
			selectedTiles.add(selectedTile);
			selectedTile.setSelect(true);
			return true;
		}
	}

	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the
	 * selected sequence of tiles. The rules of a sequence of tiles are:
	 * 
	 * <pre>
	 * 1. The first two tiles must have the same level.
	 * 2. After the first two, each tile must have the same level or one greater than the level of the previous tile.
	 * </pre>
	 * 
	 * For example, given the sequence: 1, 1, 2, 2, 2, 3. The next selected tile
	 * could be a 3 or a 4. If the use tries to select an invalid tile, the method
	 * should do nothing. If the user selects a valid tile, the tile should be added
	 * to the list of selected tiles.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 */
	public void tryContinueSelect(int x, int y) {
		//doesn't do anything if selection not in progress
		if(!selectionInProgress || x>= grid.getWidth()|| y>= grid.getHeight() || !isAdjacent(selectedTile,grid.getTile(x,y))){
			return;
		}
		//if size = 1
		else if(selectedTiles.size()==1){
			if(selectedTile.getLevel()!=grid.getTile(x,y).getLevel()){
				return;
			}
		}
		//if you select the previously selected tile
		else if(selectedTiles.size()>1 && x==selectedTiles.get(selectedTiles.size()-2).getX()){
			if(y==selectedTiles.get(selectedTiles.size()-2).getY()) {
				selectedTiles.get(selectedTiles.size()-1).setSelect(false);
				selectedTiles.remove(selectedTiles.size() - 1);
				selectedTile = selectedTiles.get(selectedTiles.size()-1);
				lastTileLevel = selectedTile.getLevel();
				lastSelectedX = selectedTile.getX();
				lastSelectedY = selectedTile.getY();
				return;
			}
		}
		else {
			if (grid.getTile(x,y).getLevel()!=selectedTile.getLevel() && grid.getTile(x,y).getLevel()!=selectedTile.getLevel()+1){
				return;
			}
		}
		// if selecting any previously selected tile
		for (int i = 0; i < selectedTiles.size(); i++) {
			if (x==selectedTiles.get(i).getX() && y==selectedTiles.get(i).getY() && selectedTiles.size()>2){
				return;
			}
		}
		// if selecting the same tile again
		if(selectedTiles.size()==1 && x==selectedTile.getX() && y==selectedTile.getY()){
			grid.getTile(x,y).setSelect(false);
			selectedTiles.remove(0);
			selectionInProgress = false;
			forceSelect = true;
			return;
		}
		//if no errors above
		selectedTile = grid.getTile(x,y);
		lastTileLevel = selectedTile.getLevel();
		lastSelectedX = selectedTile.getX();
		lastSelectedY = selectedTile.getY();
		selectedTile.setSelect(true);
		selectedTiles.add(selectedTile);

	}

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of
	 * tiles. If the method is not called for the last selected tile, it should do
	 * nothing and return false. Otherwise it should do the following:
	 * 
	 * <pre>
	 * 1. When the selection contains only 1 tile reset the selection and make sure all tiles selected is set to false.
	 * 2. When the selection contains more than one block:
	 *     a. Upgrade the last selected tiles with upgradeLastSelectedTile().
	 *     b. Drop all other selected tiles with selected().
	 *     c. Reset the selection and make sure all tiles selected is set to false.
	 * </pre>
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return return false if the tile was not selected, otherwise return true
	 */
	public boolean tryFinishSelection(int x, int y) {
		if (forceSelect){
			forceSelect = false;
			selectedTile.setSelect(false);
			selectedTiles.clear();
			selectionInProgress = false;
			selectedTile= new Tile(0);
			return true;
		}
		//if trying to select first tile
		if(x==selectedTile.getX() && y==selectedTile.getY() && selectedTiles.size()==1){
			selectedTiles.clear();
			selectedTile.setSelect(false);
			selectedTile = new Tile(0);
			selectionInProgress = false;
			return true;
		}
		if(!selectionInProgress){
			return false;
		}
		//if size is 1
		if (selectedTiles.size()==1){
			selectedTile.setSelect(false);
			selectedTiles.clear();
			selectionInProgress = false;
			selectedTile= new Tile(0);
			return true;
		}
		//if stuff above passes
		if(x == selectedTile.getX() && y == selectedTile.getY()) {
			selectedTile = new Tile(0);
			for (Tile tile : selectedTiles) {
				tile.setSelect(false);
				score += tile.getValue();
				scoreListener.updateScore(score);
			}
			upgradeLastSelectedTile();
			selectedTiles.remove(selectedTiles.size()-1);
			if(!levelDropped) {
				dropSelected();
				levelDropped = false;
			}
			selectedTiles.clear();
			selectionInProgress = false;
			for (int i = 0; i < grid.getHeight(); i++) {
				for (int j = 0; j < grid.getWidth(); j++) {
					grid.getTile(j,i).setSelect(false);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from
	 * the list of selected tiles. The tile itself should be set to unselected.
	 * <p>
	 * If the upgrade results in a tile that is greater than the current maximum
	 * tile level, both the minimum and maximum tile level are increased by 1. A
	 * message dialog should also be displayed with the message "New block 32,
	 * removing blocks 2". Not that the message shows tile values and not levels.
	 * Display a message is performed with dialogListener.showDialog("Hello,
	 * World!");
	 */
	public void upgradeLastSelectedTile() {
		//if the level is greater than max level, drops selected and drops level
		grid.getTile(lastSelectedX, lastSelectedY).setLevel(lastTileLevel+1);
		if (grid.getTile(lastSelectedX, lastSelectedY).getLevel()>maxLevel){
			dropSelected();
			levelDropped = true;
			dropLevel(minLevel);
			levelDropped=false;
			grid.getTile(lastSelectedX, lastSelectedY).setLevel(lastTileLevel+1);
			dialogListener.showDialog("New block "+Math.pow(2,lastTileLevel+1)+", removing blocks "+ Math.pow(2,minLevel-1));
		}

	}

	/**
	 * Gets the selected tiles in the form of an array. This does not mean selected
	 * tiles must be stored in this class as an array.
	 * 
	 * @return the selected tiles in the form of an array
	 */
	public Tile[] getSelectedAsArray() {
		try{
			Tile[] selected = new Tile[selectedTiles.size()];
			for (int i = 0; i < selectedTiles.size(); i++){
				selected[i] = selectedTiles.get(i);
			}

				return selected;
		} catch (Exception e) {
			return new Tile[0];
		}

	}

	/**
	 * Removes all tiles of a particular level from the grid. When a tile is
	 * removed, the tiles above it drop down one spot and a new random tile is
	 * placed at the top of the grid.
	 * 
	 * @param level the level of tile to remove
	 */
	public void dropLevel(int level) {
		maxLevel+=1;
		minLevel+=1;
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				if(grid.getTile(j,i).getLevel()==level){
					int k = i;

					// while loop to drop tiles continuously until height = 0
					while(k!=0) {
						grid.setTile(grid.getTile(j, k-1), j, k);
						k-=1;
					}
					if(k==0){
						grid.setTile(getRandomTile(),j,k);
					}
				}
			}
		}

	}

	/**
	 * Removes all selected tiles from the grid. When a tile is removed, the tiles
	 * above it drop down one spot and a new random tile is placed at the top of the
	 * grid.
	 */
	public void dropSelected() {

		//similar logic to drop level
		for (Tile tile : selectedTiles) {
			int k = tile.getY();
			int j = tile.getX();
			while (k != 0) {
				grid.setTile(grid.getTile(j, k - 1), j, k);
				k -= 1;
			}
			grid.setTile(getRandomTile(), j, k);
		}
	}


	/**
	 * Remove the tile from the selected tiles.
	 * 
	 * @param x column of the tile
	 * @param y row of the tile
	 */
	public void unselect(int x, int y) {
		for (int i = 0; i < selectedTiles.size(); i++) {
			if(selectedTiles.get(i).getX()==x && selectedTiles.get(i).getY()==y) {
				selectedTiles.get(i).setSelect(false);
				selectedTiles.remove(i);
			}
		}
	}

	/**
	 * Gets the player's score.
	 * 
	 * @return the score
	 */
	public long getScore() {
		return score;
	}

	/**
	 * Gets the game grid.
	 * 
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Gets the minimum tile level.
	 * 
	 * @return the minimum tile level
	 */
	public int getMinTileLevel() {
		return minLevel;
	}

	/**
	 * Gets the maximum tile level.
	 * 
	 * @return the maximum tile level
	 */
	public int getMaxTileLevel() {
		return maxLevel;
	}

	/**
	 * Sets the player's score.
	 * 
	 * @param score number of points
	 */
	public void setScore(long score) {
		this.score= score;
	}

	/**
	 * Sets the game's grid.
	 * 
	 * @param grid game's grid
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Sets the minimum tile level.
	 * 
	 * @param minTileLevel the lowest level tile
	 */
	public void setMinTileLevel(int minTileLevel) {
		minLevel = minTileLevel;
	}

	/**
	 * Sets the maximum tile level.
	 * 
	 * @param maxTileLevel the highest level tile
	 */
	public void setMaxTileLevel(int maxTileLevel) {
		maxLevel = maxTileLevel;
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Save the game to the given file path.
	 * 
	 * @param filePath location of file to save
	 */
	public void save(String filePath) {
		GameFileUtil.save(filePath, this);
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}
}
