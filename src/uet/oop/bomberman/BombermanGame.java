package uet.oop.bomberman;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.scenes.SceneLib;

public class BombermanGame extends Application {
    public static final int WIDTH = 22;
    public static final int HEIGHT = 17;
    public static Font font1 = new Font("Comic Sans MS", 10);
    public static Font font2 = new Font("Comic Sans MS", 3);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  {
        primaryStage.setResizable(false);
        primaryStage.setScene(SceneLib.getMenu());
        SceneLib.setStage(primaryStage);
        primaryStage.show();
    }
}
