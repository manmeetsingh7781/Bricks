package GameBricks;

class Map implements variables{

    public int[][] map;
    int tileHeight, tileWidth;

    // Creating a value holder for Row and cols
        //  <--x-->   y
    Map(int col, int row){
        map = new int[col][row];
        for(int x = 0; x < col; x++){
            for(int y = 0; y < row; y++){
                map[x][y] = screen_width/8;
            }
        }
        tileHeight = screen_width/25;
        tileWidth =  screen_width/10;

    } // Constructor ends here

    public void ifCrashed(int val, int col, int row){
        map[col][row] = val;

    }

}