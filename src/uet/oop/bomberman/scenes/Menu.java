package uet.oop.bomberman.scenes;

import javafx.animation.*;
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

public class Menu {
    private static Timeline timeline = new Timeline();
    private static int chooseX = 212;
    private static int chooseY;
    private static Image choose;
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static Group root;
    private static Scene scene;

    public Menu() {
        initialize();
        play();
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
         gc.drawImage(MenuTextures.background.getImg(), 0, 0);
         if (choose != null) {
             gc.drawImage(choose, chooseX, chooseY);
         }
     }

    public static Scene getScene() {
        return scene;
    }

    public static void play() {
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 202 && event.getY() <= 265) {
                    choose = MenuTextures.pve.getImg();
                    chooseY = 202;
                } else if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 277 && event.getY() <= 340) {
                    choose = MenuTextures.pvp.getImg();
                    chooseY = 277;
                } else if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 354 && event.getY() <= 417) {
                    choose = MenuTextures.highScore.getImg();
                    chooseY = 354;
                } else if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 432 && event.getY() <= 495) {
                    choose = MenuTextures.tutorial.getImg();
                    chooseY = 432;
                } else {
                    choose = null;
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
                    SceneLib.switchToPveGameMode();
                } else if (event.getX() >= 212 && event.getX() <= 478 &&
                        event.getY() >= 277 && event.getY() <= 340) {
                    timeline.stop();
                    if (SoundLib.menu.isPlaying()) {
                        SoundLib.menu.stop();
                    }
                    SceneLib.switchToPvpGameMode();
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

    public static void stop() {
        timeline.stop();
    }

    public static void setChoose() {
        Menu.choose = null;
    }
}
