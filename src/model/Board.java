package model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Abstraction of an Omok board, which consists of n x n intersections
 * or places where players can place their stones. The board can be
 * accessed using a pair of 0-based indices (x, y), where x and y
 * denote the column and row number, respectively. The top-left
 * intersection is represented by the indices (0, 0), and the
 * bottom-right intersection is represented by the indices (n-1, n-1).
 */
public class Board {
    private int size = 10;
    private Place[][] board;
    /** Create a new board of the default size. */
    public Board() {
        this.board = new Place[size][size];
        clear();
    }

    /** Create a new board of the specified size. */
    public Board(int size) {
        this.size = size;
        this.board = new Place[size][size];
        clear();
    }

    /** Return the size of this board. */
    public int size() {
        return this.size;
    }

    /** Return the board. */
    public Place[][] board(){return this.board;}

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        for(int i = 0; i < size; i ++){
            for (int j = 0; j < size; j ++){
                board[i][j] = new Place(i, j);
            }
        }
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
        for(Place[] row: board){
            for(Place place: row){
                if(!place.isOccupied()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        board[x][y].owner = player;
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        return !board[x][y].isOccupied();
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
        return board[x][y].isOccupied();
    }

    /**
     * Rreturn a boolean value indicating whether the specified
     * intersection (x, y) on the board is occupied by the given
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupiedBy(int x, int y, Player player) {
        return board[x][y].owner == player;
    }

    /**
     * Return the player who occupies the specified intersection (x, y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(int x, int y) {
        return board[x][y].owner;
    }

    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(Player player) {
        int count = 0;
        //Check for horizontal lines
        for(Place[] row: board){
            count = 0;
            for(Place place: row){
                if(place.owner == player){
                    count ++;
                }
                else{
                    count = 0;
                }
                if(count == 5){
                    return true;
                }
            }
        }
        //Check for vertical lines
        for(int i = 0; i < board.length; i++){
            count = 0;
            for(int j = 0; j < board[0].length; j++){
                if(board[j][i].owner == player){
                    count ++;
                }
                else{
                    count = 0;
                }
                if(count == 5){
                    return true;
                }
            }
        }
        //Check for diagonals going right starting on the left
        for(int i = 0; i < board.length; i++){
            count = 0;
            int j = 0;
            for(int tempi = i; tempi >= 0; tempi--){
                if(board[tempi][j].owner == player){
                    count ++;
                }
                else{
                    count = 0;
                }
                if(count == 5){
                    return true;
                }
                j++;
            }
        }
        //Check for diagonals going right starting on the bottom
        for(int j = 0; j < board[0].length; j++){
            count = 0;
            int i = board.length - 1;
            for(int tempj = j; tempj < board[0].length; tempj++){
                if(board[i][tempj].owner == player){
                    count ++;
                }
                else{
                    count = 0;
                }
                if(count == 5){
                    return true;
                }
                i --;
            }
        }
        //Check for diagonals going left starting on the right
        for(int i = 0; i < board.length; i++){
            count = 0;
            int j = board[0].length - 1;
            for(int tempi = i; tempi >= 0; tempi--){
                if(board[tempi][j].owner == player){
                    count ++;
                }
                else{
                    count = 0;
                }
                if(count == 5){
                    return true;
                }
                j--;
            }
        }
        //Check for diagonals going left starting on the bottom
        for(int j = board[0].length - 1; j >= 0; j--){
            count = 0;
            int i = board.length - 1;
            for(int tempj = j; tempj >= 0; tempj--){
                if(board[i][tempj].owner == player){
                    count ++;
                }
                else{
                    count = 0;
                }
                if(count == 5){
                    return true;
                }
                i --;
            }
        }
        return false;
    }
    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public Iterable<Place> winningRow() {
        Set<Player> set = new HashSet<>();
        for(Place[] row: board){
            for(Place place: row){
                if(!set.contains(place.owner) && place.owner != null){
                    Iterable<Place> winningRow = realWinningRow(place.owner);
                    if (winningRow != null){
                        return winningRow;
                    }
                    set.add(place.owner);
                }
            }
        }
        return null;
    }
    public Iterable<Place> realWinningRow(Player player) {
        ArrayList<Place> places;
        //Check for horizontal lines
        for(Place[] row: board){
            places = new ArrayList<>();
            for(Place place: row){
                if(place.owner == player){
                    places.add(place);
                }
                else{
                    places = new ArrayList<>();
                }
                if(places.size() == 5){
                    return places;
                }
            }
        }
        //Check for vertical lines
        for(int i = 0; i < board.length; i++){
            places = new ArrayList<>();
            for(int j = 0; j < board[0].length; j++){
                if(board[j][i].owner == player){
                    places.add(board[j][i]);
                }
                else{
                    places = new ArrayList<>();
                }
                if(places.size() == 5){
                    return places;
                }
            }
        }
        //Check for diagonals going right starting on the left
        for(int i = 0; i < board.length; i++){
            places = new ArrayList<>();
            int j = 0;
            for(int tempi = i; tempi >= 0; tempi--){
                if(board[tempi][j].owner == player){
                    places.add(board[tempi][j]);
                }
                else{
                    places = new ArrayList<>();
                }
                if(places.size() == 5){
                    return places;
                }
                j++;
            }
        }
        //Check for diagonals going right starting on the bottom
        for(int j = 0; j < board[0].length; j++){
            places = new ArrayList<>();
            int i = board.length - 1;
            for(int tempj = j; j < board[0].length; tempj++){
                if(board[i][tempj].owner == player){
                    places.add(board[i][tempj]);
                }
                else{
                    places = new ArrayList<>();
                }
                if(places.size() == 5){
                    return places;
                }
                i --;
            }
        }
        //Check for diagonals going left starting on the right
        for(int i = 0; i < board.length; i++){
            places = new ArrayList<>();
            int j = board[0].length;
            for(int tempi = i; tempi >= 0; tempi--){
                if(board[tempi][j].owner == player){
                    places.add(board[tempi][j]);
                }
                else{
                    places = new ArrayList<>();
                }
                if(places.size() == 5){
                    return places;
                }
                j--;
            }
        }
        //Check for diagonals going left starting on the bottom
        for(int j = board[0].length; j >= 0; j--){
            places = new ArrayList<>();
            int i = board.length - 1;
            for(int tempj = j; j >= 0; tempj--){
                if(board[i][tempj].owner == player){
                    places.add(board[i][tempj]);
                }
                else{
                    places = new ArrayList<>();
                }
                if(places.size() == 5){
                    return places;
                }
                i --;
            }
        }
        return null;
    }
    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position
     * of a place on the board, with (0, 0) denoting the top-left
     * corner and (n-1, n-1) denoting the bottom-right corner,
     * where n is the size of the board.
     */
    public static class Place {
        /** 0-based column index of this place. */
        public final int x;

        /** 0-based row index of this place. */
        public final int y;

        public Player owner = null;

        /** Create a new place of the given indices.
         *
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place(int x, int y) {
            this.x = x;
            this.y = y;
        }
        /** Returns True if a player owns this tile.*/
        public boolean isOccupied(){
            return owner != null;
        }

    }
}