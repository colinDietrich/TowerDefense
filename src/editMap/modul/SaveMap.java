package editMap.modul;

import editMap.View.EditCell;
import menu.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveMap {

    // nom du fichier dans laquelle la map est enregistree
    private String fileName;

    // objets de java.io permettant d'ecrire dans des fichiers textes
    private FileWriter myWriter;
    private BufferedWriter bufferedWriter;

    // la matrice de EditCells que le joueur a cree dans editMap
    private EditCell[][] cells;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public SaveMap(EditCell[][] cells) {
        this.cells = cells;
        // on cree un fichier texte pour stocker la map
        createFile();

        // on enregistre la map dans le fichier texte en ecrivant la matrice de cells dedans
        writeFile();

        // on reactualise la liste de level et de map contenu dans le main
        Main.getFiles();
    }




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode cree un nouveau fichier texte
     */
    public void createFile() {
        fileName = "LEVEL" + String.valueOf(Main.getListLevel().size() + 1);
        try {
            File folder = new File("src/game/ressources/map");
            String absolutePath = folder.getAbsolutePath();
            File file = new File(absolutePath+"/" + fileName + ".txt");
            System.out.println("first path : " + absolutePath+"/" + fileName + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     * cette methode enregistre la matrice d'EditCells en
     * ecrivant dans un fichier texte
     */
    public void writeFile() {

        try {
            int index1 = 0;
            for(EditCell[] listcell : cells) {
                int index2 = 0;
                for(EditCell cell : listcell) {
                    bufferedWriter.write(cell.getCode());
                    if(index2 < listcell.length - 1) {
                        bufferedWriter.write("   ");
                    } else if (index1 < cells.length - 1) {
                        bufferedWriter.newLine();
                    }
                    index2++;
                }
                index1++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            try{
                if(bufferedWriter != null)
                    bufferedWriter.close();
            }catch(Exception ex){
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }
    }


}