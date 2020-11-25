package uet.oop.bomberman.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.MenuTextures;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.SoundLib;

public class GameMode {
    enum Mode {
        PVP,
        PVE
    }
    private static Timeline timeline = new Timeline();
    private static int chooseImgX = 212;
    private static int chooseImgY;
    private static Image chooseImg;
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static Group root;
    private static Scene scene;
    public static Mode mode;
    public static boolean con = false;

    public GameMode() {
        initialize();
    }

    private static void initialize() {
        canvas = new Canvas(Sprite.SCALED_SIZE * BombermanGame.WIDTH, Sprite.SCALED_SIZE * BombermanGame.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
    }

    private static void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(MenuTextures.gameMode.getImg(), 0, 0);
        if (mode == Mode.PVE) {
            if (con) {
                gc.drawImage(MenuTextures.continue0.getImg(), 212, 277);
            }
        } else if (mode == Mode.PVP) {
            gc.drawImage(MenuTextures.offline.getImg(), 212, 202);
            gc.drawImage(MenuTextures.online.getImg(), 212, 277);
        }
        if (chooseImg != null) {
            gc.drawImage(chooseImg, chooseImgX, chooseImgY);
        }
    }

    public static void play() {
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 202 && event.getY() <= 265) {
                    if (mode == Mode.PVE) {
                        chooseImg = MenuTextures.newGame1.getImg();
                    } else if (mode == Mode.PVP) {
                        chooseImg = MenuTextures.offline1.getImg();
                    }
                    chooseImgY = 202;
                } else if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 277 && event.getY() <= 340) {
                    if (!con && mode == Mode.PVE) {
                        return;
                    }
                    if (mode == Mode.PVE) {
                        chooseImg = MenuTextures.continue1.getImg();
                    } else if (mode == Mode.PVP) {
                        chooseImg = MenuTextures.online1.getImg();
                    }
                    chooseImgY = 277;
                } else {
                    chooseImg = null;
                }
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 202 && event.getY() <= 265) {
                    timeline.stop();
                    if (SoundLib.menu.isPlaying()) {
                        SoundLib.menu.stop();
                    }
                    if (mode == Mode.PVE) {
                        MainGame.pveNewGame();
                        SceneLib.switchToLevelScene();
                        con = true;
                    } else if (mode == Mode.PVP) {
                        SceneLib.switchToMainGame();
                        MainGame.pvpOffNewGame();
                    }
                } else if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 277 && event.getY() <= 340) {
                    if (!con && mode == Mode.PVE) {
                        return;
                    }
                    timeline.stop();
                    if (SoundLib.menu.isPlaying()) {
                        SoundLib.menu.stop();
                    }
                    if (mode == Mode.PVE && con) {
                        MainGame.pveContinue();
                        SceneLib.switchToLevelScene();
                    } else if (mode == Mode.PVP) {
                        SceneLib.switchToMainGame();
                        MainGame.pvpOnlNewGame();
                    }
                }
            }
        });
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        Duration oneFrameAmt = Duration.millis(1000.0/30);
        KeyFrame oneFrame = new KeyFrame(oneFrameAmt, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!SoundLib.menu.isPlaying()) {
                    SoundLib.menu.play();
                }
                render();
            }
        });
        timeline.getKeyFrames().add(oneFrame);
        timeline.play();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setChooseImg() {
        GameMode.chooseImg = null;
    }
}
