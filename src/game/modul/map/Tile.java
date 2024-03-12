package game.modul.map;

public class Tile {

    // indices (x,y) de la Tile dans la matrice grid
    private int x, y;

    // tailles de chaque Tile dans la map
    private final static int TILE_SIZE = Map.getWIDTH()/Map.getCOLUMNS();

    // type de la Tile
    private TileType type;

    // String qui permet d'identifier une Tile et son type
    private String code;

    // angle de rotation de la tile
    private int angle;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Tile(int x, int y, String code) {
        this.x = x;
        this.y = y;
        this.code = code;
        this.type = updateType();
        setAngle(code);
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode permet d'associer le bon type
     * a la tile en fonction de son "code"
     * elle renvoit le type de la Tile "Tiletype"
     */
    public TileType updateType() {
        switch (code.charAt(1)) {
            case 'A':
                type = TileType.MAP_NORMAL;
                break;
            case 'B':
                type = TileType.MAP_EDGE;
                break;
            case 'C':
                type = TileType.MAP_BORDER_PATH;
                break;
            case 'D':
                type = TileType.MAP_CORNER_PATH;
                break;
            case 'F':
                type = TileType.MAP_BORDER;
                break;
            case 'X':
                type = TileType.MAP_CORNER_PATH_2;
                break;
            case 'E':
                type = TileType.PATH;
                break;
            case 'G':
                type = TileType.START;
                break;
            case 'J':
                type = TileType.END;
                break;
        }
        return type;
    }




    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public int getX() { return x; }
    public int getY() { return y; }
    public static int getTileSize() { return TILE_SIZE; }
    public TileType getType() { return type; }
    public String getCode() { return code; }
    public int[] getPosition() { return new int[]{x, y}; }
    public String getDirection() { return String.valueOf(code.charAt(2)); }
    public int getAngle() { return angle; }

    /**
     * renvoit true si la Tile correspond a un bloc "start"
     */
    public boolean isStart() {
        boolean bool = false;
        if(String.valueOf(code.charAt(1)).equals("G")) {
            bool = true;
        }
        return bool;
    }

    /**
     * renvoit true si la Tile correspond a un bloc "end"
     */
    public boolean isEnd() {
        boolean bool = false;
        if(String.valueOf(code.charAt(1)).equals("J")) {
            bool = true;
        }
        return bool;
    }

    /**
     * renvoit true si la Tile correspond a un bloc "path"
     */
    public boolean isPath() {
        boolean bool = false;
        if(Integer.parseInt(String.valueOf(code.charAt(0))) == 1) {
            bool = true;
        }
        return bool;
    }

    /**
     * renvoit true si la Tile correspond a un bloc "normal"
     */
    public boolean isBlock() {
        boolean bool = false;
        if(!isPath() && !isStart() && !isEnd()){
            bool = true;
        }
        return bool;
    }




    // ******************************
    // ********** SETTERS ***********
    // ******************************
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setType(TileType type) { this.type = type; }
    public void setCode(String code) { this.code = code; }
    public void setAngle(String code) { angle = Integer.parseInt(String.valueOf(code.charAt(3)))*90; }

}
