package hw3;

import api.Tile;

/**
 * Represents the game's grid.
 */
public class Grid {

	private Tile[][] grid;
	/**
	 * Creates a new grid.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */

	public Grid(int width, int height) {
		grid = new Tile[height][width];
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width
	 */
	public int getWidth() {
		try{
			return grid[0].length;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height
	 */
	public int getHeight() {
		return grid.length;
	}

	/**
	 * Gets the tile for the given column and row.
	 * 
	 * @param x the column
	 * @param y the row
	 * @return tile
	 */
	public Tile getTile(int x, int y) {

		return grid[y][x];
	}

	/**
	 * Sets the tile for the given column and row. Calls tile.setLocation().
	 * 
	 * @param tile the tile to set
	 * @param x    the column
	 * @param y    the row
	 */
	public void setTile(Tile tile, int x, int y) {
		grid[y][x]=tile;
		tile.setLocation(x,y);

	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int y=0; y<getHeight(); y++) {
			if (y > 0) {
				str.append("\n");
			}
			str.append("[");
			for (int x=0; x<getWidth(); x++) {
				if (x > 0) {
					str.append(",");
				}
				str.append(getTile(x, y));
			}
			str.append("]");
		}
		return str.toString();
	}
}
