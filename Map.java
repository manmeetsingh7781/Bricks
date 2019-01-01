import java.awt.*;

class Map {

    public int[][] map;
    int tileHeight, tileWidth;
    Rectangle rect;

    // Creating a value holder for Row and cols
        //  <--x-->   y
    Map(int col, int row){
        map = new int[col][row];
        for(int x = 0; x < col; x++){
            for(int y = 0; y < row; y++){
                map[x][y] = 10;
            }
        }
        tileHeight = 32;
        tileWidth = 48;

    } // Constructor ends here

    void draw(Graphics2D g){
     for(int col = 0; col < map.length; col++){
            for(int row = 0; row < map[0].length;row++){

                // Bricks
                    g.setColor(Color.green);
                    g.fillRect(col*tileWidth + map[col][row], row*tileHeight + map[col][row], tileWidth, tileHeight);

                // Black Border around Bricks
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(col*tileWidth + map[col][row], row*tileHeight + map[col][row], tileWidth, tileHeight);

            }
       }
    }

    public void ifCrashed(int val, int col, int row){
        map[col][row] = val;

    }




}