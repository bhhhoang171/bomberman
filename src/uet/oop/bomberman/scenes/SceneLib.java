package uet.oop.bomberman.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.sounds.SoundLib;

public abstract class SceneLib {
    private static final Scene mainGame = (new MainGame()).getScene();
    private static final Scene menu = (new Menu()).getScene();
    private static final Scene temp = (new Temp()).getScene();
    private static final Scene gameMode = (new GameMode()).getScene();
    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneLib.stage = stage;
        SceneLib.stage.setResizable(false);
    }

    public static void switchToMainGame() {
        stage.setScene(mainGame);
        MainGame.play();
        MainGame.start();
        MainGame.setChooseImg();
    }

    public static void switchToMenu() {
        stage.setScene(menu);
        Menu.play();

    }

    public static void switchToLevelScene() {
        Temp.type = Temp.types.LEVEL;
        stage.setScene(temp);
        Temp.play();
        SoundLib.stageStart.play();
        Temp.setChooseImg();
    }

    public static void switchToStageCompleteScene() {
        Temp.type = Temp.types.STAGE_COMPLETE;
        stage.setScene(temp);
        Temp.play();
        SoundLib.stageComplete.play();
        Temp.setChooseImg();
    }

    public static void switchToPvpEndingScene() {
        Temp.type = Temp.types.ENDING_PVP;
        stage.setScene(temp);
        Temp.play();
        SoundLib.gameOver.play();
        Temp.setChooseImg();
    }

    public static void switchToPveEndingScene() {
        Temp.type = Temp.types.ENDING_PVE;
        stage.setScene(temp);
        Temp.play();
        if (Temp.win) {
            SoundLib.stageComplete.play();
        } else {
            SoundLib.lifeLost.play();
        }
        Temp.setChooseImg();
    }

    public static void switchToPvpGameMode() {
        stage.setScene(gameMode);
        GameMode.mode = GameMode.Mode.PVP;
        GameMode.play();
        GameMode.setChooseImg();
    }

    public static void switchToPveGameMode() {
        stage.setScene(gameMode);
        GameMode.mode = GameMode.Mode.PVE;
        GameMode.play();
        GameMode.setChooseImg();
    }

    public static Scene getMenu() {
        return menu;
    }

    public static Scene getMainGame() {
        return mainGame;
    }
}
