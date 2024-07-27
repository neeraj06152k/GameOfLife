package dev.neeraj;

/**
 * The LSB of the byte represents the state of the cell.
 * The rest of the bits are used to store the number of live neighbors.
 *
 */

public class CellularGrid {
    private final int dimension;
    private final byte[][] grid;

    public CellularGrid() {
        this(20);
    }

    public CellularGrid(int dimension) {
        this.dimension = dimension;
        grid = new byte[dimension][dimension];
        populateGridRandomly();
        //singleDemonoid();
    }

    public void populateGridRandomly() {
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                if(Math.random() < 0.25){
                    setCell(i, j, grid);
                }
            }
        }
    }
    public void singleDemonoid(){
        clearGrid();
        int center = dimension/2;
        setCell(center, center, grid);
        setCell(center, center+1, grid);
        setCell(center, center+2, grid);
        setCell(center+1, center+2, grid);
        setCell(center+2, center+1, grid);
    }

    public void nextGeneration(){
        byte[][] newGrid = new byte[dimension][dimension];
        for(int i=0; i<dimension; i++){
            int j=0;
            while(j<dimension){
                while(j<dimension && grid[i][j]==0) j++;
                if(j==dimension) break;

                int neighborCount = getNeighborCount(i,j,grid);
                if((grid[i][j]&1)==1){
                    if(neighborCount==2 || neighborCount==3){
                        setCell(i, j, newGrid);
                    }
                }
                else {
                    if(neighborCount == 3){
                        setCell(i, j, newGrid);
                    }
                }
                j++;
            }
        }
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                grid[i][j] = newGrid[i][j];
            }
        }
    }

    public void setCell(int x, int y, byte[][] grid){
        if(x < 0 || x >= dimension || y < 0 || y >= dimension || (grid[x][y]&1) == 1){
            return;
        }
        grid[x][y]++;
        for(int i=x-1; i<=x+1; i++){
            for(int j=y-1; j<=y+1; j++){
                if(i==x && j==y) continue;
                //if(i < 0 || i >= dimension || j < 0 || j >= dimension) continue;

                incrementNeighborCount(i, j, grid);
            }
        }

    }
    public void clearCell(int x, int y, byte[][] grid){
        if(x < 0 || x >= dimension || y < 0 || y >= dimension || (grid[x][y]&1) == 0){
            return;
        }
        grid[x][y]--;
        for(int i=x-1; i<=x+1; i++){
            for(int j=y-1; j<=y+1; j++){
                if(i==x && j==y) continue;
                //if(i < 0 || i >= dimension || j < 0 || j >= dimension) continue;

                decrementNeighborCount(i, j, grid);
            }
        }
    }

    private void incrementNeighborCount(int x, int y, byte[][] grid) {
        x = modIndex(x);
        y = modIndex(y);
        grid[x][y]+=2;
    }
    private void decrementNeighborCount(int x, int y, byte[][] grid) {
        x = modIndex(x);
        y = modIndex(y);
        grid[x][y]-=2;
    }

    public int getNeighborCount(int x, int y, byte[][] grid) {
        if (x < 0 || x >= dimension || y < 0 || y >= dimension) {
            return 0;
        }

        return grid[x][y] >> 1;
    }

    private int modIndex(int i){
        return (i+dimension)%dimension;
    }

    public void clearGrid(){
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                grid[i][j] = 0;
            }
        }
    }

    public void printGrid(){

        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                System.out.print(((grid[i][j]&1) == 1 ? "@" : ".") + " ");
            }
            System.out.println();
        }
    }
}
