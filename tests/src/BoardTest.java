import model.Player;
import org.junit.Before;
import org.junit.Test;

import model.Board;

import static org.junit.Assert.*;

public class BoardTest {
    public Player player;
    public Player player2;
    @Before
    public void setUp(){
        this.player = new Player("Tadeo");
        this.player2 = new Player("OmokFish");
    }
    @Test
    public void testConstructor(){
        Board testBoard = new Board(15);
        assertEquals(15, testBoard.size());
        testBoard = new Board();
        assertEquals(10, testBoard.size());
    }
    @Test
    public void testPlayerAt(){
        Board testBoard = new Board();
        assertEquals(testBoard.playerAt(1,1), null);
        testBoard.placeStone(1,1, player);
        assertEquals(testBoard.playerAt(1,1), player);
    }
    @Test
    public void testIsOcuppiedBy(){
        Board testBoard = new Board();
        assertFalse(testBoard.isOccupiedBy(1,1, player));
        testBoard.placeStone(1,1, player);
        assertTrue(testBoard.isOccupiedBy(1,1, player));
    }
    @Test
    public void testIsOcuppied(){
        Board testBoard = new Board();
        assertFalse(testBoard.isOccupied(1,1));
        testBoard.placeStone(1,1, player);
        assertTrue(testBoard.isOccupied(1,1));
    }
    @Test
    public void testIsEmpty(){
        Board testBoard = new Board();
        assertTrue(testBoard.isEmpty(1,1));
        testBoard.placeStone(1,1, player);
        assertFalse(testBoard.isEmpty(1,1));
    }
    @Test
    public void testPlaceStone(){
        Board testBoard = new Board();
        testBoard.placeStone(1,1,player);
        assertTrue(testBoard.isOccupiedBy(1,1,player));
    }
    @Test
    public void testFull(){
        Board testBoard = new Board(1);
        assertFalse(testBoard.isFull());
        testBoard.placeStone(0,0, player);
        assertTrue(testBoard.isFull());
    }
    @Test
    public void testClear(){
        Board testBoard = new Board();
        testBoard.placeStone(1,1,player);
        testBoard.clear();
        assertFalse(testBoard.isOccupied(1,1));
    }
    @Test
    public void testWinning(){
        Board testBoard = new Board(7);
        int size = 0;
        //Test false
        assertFalse(testBoard.isWonBy(player));
        //Test horizontal case
        testBoard.placeStone(0,1,player);
        testBoard.placeStone(0,2,player);
        testBoard.placeStone(0,3,player);
        testBoard.placeStone(0,4,player);
        testBoard.placeStone(0,5,player);
        assertTrue(testBoard.isWonBy(player));
        Iterable<Board.Place> winningRow = testBoard.winningRow();
        for(Board.Place place: winningRow){
            assertTrue(place.owner == player);
            size ++;
        }
        assertEquals(5, size);
        size = 0;
        testBoard.clear();

        //Test vertical case
        testBoard.placeStone(1,1,player);
        testBoard.placeStone(2,1,player);
        testBoard.placeStone(3,1,player);
        testBoard.placeStone(4,1,player);
        testBoard.placeStone(5,1,player);
        assertTrue(testBoard.isWonBy(player));
        winningRow = testBoard.winningRow();
        for(Board.Place place: winningRow){
            assertTrue(place.owner == player);
            size ++;
        }
        assertEquals(5, size);
        size = 0;
        testBoard.clear();

        //Test diagonal to the right
        testBoard.placeStone(1,4,player);
        testBoard.placeStone(2,3,player);
        testBoard.placeStone(3,2,player);
        testBoard.placeStone(4,1,player);
        testBoard.placeStone(5,0,player);
        assertTrue(testBoard.isWonBy(player));
        winningRow = testBoard.winningRow();
        for(Board.Place place: winningRow){
            assertTrue(place.owner == player);
            size ++;
        }
        assertEquals(5, size);
        size = 0;
        testBoard.clear();

        //Test diagonal to the right
        testBoard.placeStone(4,1,player);
        testBoard.placeStone(3,2,player);
        testBoard.placeStone(2,3,player);
        testBoard.placeStone(1,4,player);
        testBoard.placeStone(0,5,player);
        assertTrue(testBoard.isWonBy(player));
        winningRow = testBoard.winningRow();
        for(Board.Place place: winningRow){
            assertTrue(place.owner == player);
            size ++;
        }
        assertEquals(5, size);
    }
}
