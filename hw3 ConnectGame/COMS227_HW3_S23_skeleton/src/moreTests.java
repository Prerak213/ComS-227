import java.util.Arrays;
import java.util.Random;

import api.Tile;
import hw3.ConnectGame;
import hw3.GameFileUtil;
import hw3.Grid;
import hw3.MockGameConsole;
import ui.GameConsole;
import org.junit.Assert;
import org.junit.Test;
import speccheck.SpecCheck;
import speccheck.SpecCheckTest;
import ui.GameConsole;

public class moreTests {
    @Test
    public void test() {
        ConnectGame game = new ConnectGame(3, 3, 2, 5, new Random(0L));
        MockGameConsole gc = new MockGameConsole();
        game.setListeners(gc, gc);
        game.radomizeTiles();
        Grid grid = game.getGrid();
        Tile t0 = new Tile(6);
        Tile t1 = new Tile(6);
        Tile t2 = new Tile(6);
        Tile t3 = new Tile(7);
        grid.setTile(t0, 1, 0);
        grid.setTile(t1, 2, 0);
        grid.setTile(t2, 1, 1);
        grid.setTile(t3, 2, 2);
        game.tryFirstSelect(1, 0);
        game.tryContinueSelect(1, 1);
        game.tryFinishSelection(1, 1);
        game.tryFirstSelect(2, 0);
        game.tryContinueSelect(1, 1);
        game.tryFinishSelection(1, 1);

        Assert.assertEquals(6L, (long) game.getGrid().getTile(2, 2).getLevel());
    }

        @Test
        public void test2 (){
            ConnectGame game = new ConnectGame(3, 3, 1, 2, new Random(0L));
            MockGameConsole gc = new MockGameConsole();
            game.setListeners(gc, gc);
            game.radomizeTiles();
            game.tryFirstSelect(0,0);
            game.tryContinueSelect(0,0);
            game.tryFinishSelection(0,0);
            game.tryFirstSelect(0,0);


        }

    public static void main(String[] args) {

    }

}
