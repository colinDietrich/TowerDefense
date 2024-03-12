package editMap.modul;

public enum BlockStyle {
    MapNormal("mainRessources/0AN0_mapNormal.png", "0AN0", "MAP NORMAL"),
    MapEdge("mainRessources/0BN0_mapEdge.png", "0BN0", "MAP EDGE"),
    MapBorder("mainRessources/0FN0_mapBorder.png", "0FN0", "MAP BORDER"),
    MapBorderPath("mainRessources/0CN0_mapBorderPath.png", "0CN0", "PATH BORDER"),
    MapCornerPath("mainRessources/0DN0_mapCornerPath.png", "0DN0", "PATH CORNER 1"),
    MapCornerPath2("mainRessources/0XN0mapCornerPath2.png", "0XN0", "PATH CORNER 2"),
    Path("mainRessources/1EH0_path.png", "1EU0", "PATH"),
    Start("mainRessources/2GH0_start.png", "2GU0", "START"),
    End("mainRessources/3JH0_end.png", "3JU0", "END");


    // nom du fichier contenant l'image du bloc
    private String url;

    // code permettant d'identifier le bloc
    private String codeName;

    // nom du bloc a afficher dans editMap
    private String name;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    private BlockStyle(String url, String codeName, String name) {
        this.url = url;
        this.codeName = codeName;
        this.name = name;
    }


    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public String getUrl() { return url; }
    public String getCodeName() { return codeName; }
    public String getName() { return name; }


}
