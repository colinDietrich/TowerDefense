package menu;

import javafx.application.Application;
import javafx.stage.Stage;
import menu.view.MenuViewManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main extends Application {

    private static List<Integer> listLevel;
    private static List<String> listLevelUrl;
    private static String MENU_FONTH = "src/menu/ressources/Chonkly.otf";
    private static int level = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            MenuViewManager manager = new MenuViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.show();

        } catch(Exception e) {

            System.out.println(e);

        }

    }

    public static void main(String[] args) {
        getFiles();

        launch(args);
    }

    public static void getFiles() {
        listLevel = new ArrayList<>();
        listLevelUrl = new ArrayList<>();

        File folder = new File("src/game/ressources/map");
        File[] listOfFiles = folder.listFiles();
        int index = 0;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getPath());
                index++;
                listLevel.add(index);
                listLevelUrl.add(file.getPath());
            }
        }
        Collections.sort(listLevelUrl);

    }

    public static List<Integer> getListLevel() { return listLevel; }

    public static String getMenuFonth() { return MENU_FONTH; }

    public static int getLevel() { return level; }

    public static List<String> getListLevelUrl() {
        Collections.sort(listLevelUrl);
        for(String url : listLevelUrl) {
            System.out.println(url);
        }
        return listLevelUrl;
    }

    public static void setLevel(int level) {
        Main.level = level;
    System.out.println("levelupdate2 : "+level);
    }
}
