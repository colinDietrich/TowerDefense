package game.modul.map;

public enum TileType {

    MAP_NORMAL("mainRessources/0AN0_mapNormal.png"),
    MAP_EDGE("mainRessources/0BN0_mapEdge.png"),
    MAP_BORDER_PATH("mainRessources/0CN0_mapBorderPath.png"),
    MAP_CORNER_PATH("mainRessources/0DN0_mapCornerPath.png"),
    MAP_CORNER_PATH_2("mainRessources/0XN0mapCornerPath2.png"),
    MAP_BORDER("mainRessources/0FN0_mapBorder.png"),
    PATH("mainRessources/1EH0_path.png"),
    START("mainRessources/2GH0_start.png"),
    END("mainRessources/3JH0_end.png");

    // contient le nom du fichier contenant l'image de la Tile
    private String url;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    private TileType(String url) {
        this.url = url;
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public String getUrl() {
        return url;
    }





}
