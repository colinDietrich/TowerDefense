package game.modul.map;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadMap {

    // nom du fichier texte contenant la map
    private String url;

    // matrice de String recuperee a partir du fichier texte
    private String[][] grid;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public LoadMap(String url) throws IOException {
        this.url = url;

        List<String> list = readFile();
        grid = createGrid(list);
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode renvoit une liste de String
     * correspondant a chacunes des lignes du fichier texte
     */
    private List<String> readFile() throws IOException {
        List<String> list = new ArrayList<>();

        BufferedReader reader;
        try {
            File f = new File(url);
            FileReader fr = new FileReader(f);
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            while(line != null) {
                list.add(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Erreur d'ouverture du fichier : " + url + e);
        }

        return list;
    }


    /**
     * cette methode cree une matrice de String correspondant
     * a la map a partir du fichier texte contenant la map
     */
    private String[][] createGrid(List<String> list) {
        int size = list.size();
        String[][] grid = new String[size][size];
        String code = "";

        int y = 0;
        for(String line : list) {
            int x = 0;
            for(int i = 0; i < line.length(); i++) {
                String c = String.valueOf(line.charAt(i));
                if(!c.equals(" ")) {
                    code = code + c;
                }
                if(code.length() == 4) {
                    grid[y][x] = code;
                    x++;
                    code = "";
                }
            }
            y++;
        }
        return grid;
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public String[][] getGrid() { return grid; }

}
