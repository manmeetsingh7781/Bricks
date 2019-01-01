import java.awt.*;

class Map {

    public int[][] map;
    int tileHeight, tileWidth;

    // Creating a value holder for Row and cols
        //  <--x-->   y
    Map(int col, int row){
        map = new int[col][row];
        for(int x = 0; x < col; x++){
            for(int y = 0; y < row; y++){
                map[x][y] = 800/8;
            }
        }
        tileHeight = 32;
        tileWidth =  80;

    } // Constructor ends here
    
    public void ifCrashed(int val, int col, int row){
        map[col][row] = val;

    }
}
}
