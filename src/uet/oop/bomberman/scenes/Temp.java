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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.omg.CORBA.MARSHAL;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.MenuTextures;
import uet.oop.bomberman.graphics.Sprite;

public class Temp {
    private static Timeline timeline = new Timeline();
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static Group root;
    private static Scene scene;
    public static types type;
    private static int timeBeforeStart = 150;

    private static Image chooseImg;
    private static double chooseImgX;
    private static double chooseImgY;

    public static boolean win = false;

    public enum types {
        LEVEL,
        ENDING_PVE,
        STAGE_COMPLETE,
        ENDING_PVP
    }

    public Temp() {
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
        if (type == types.LEVEL) {
            gc.drawImage(MenuTextures.level.getImg(), 0, 0);
            gc.setFont(new Font("Comic Sans MS", 100));
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(MainGame.level + 1), 435, 297);
        } else if (type == types.STAGE_COMPLETE) {
            gc.drawImage(MenuTextures.stageComplete.getImg(), 0, 0);
            gc.setFont(new Font("Comic Sans MS", 36));
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(MainGame.getScore()), 323, 285);
            if (chooseImg != null) {
                gc.drawImage(chooseImg, chooseImgX, chooseImgY);
            }
        } else if (type == types.ENDING_PVE) {
            gc.drawImage(MenuTextures.endGame.getImg(), 0, 0);
            gc.setFont(new Font("Comic Sans MS", 100));
            if (win) {
                gc.setFill(Color.GREEN);
                gc.fillText("You Win", 130, 193);
            } else {
                gc.setFill(Color.RED);
                gc.fillText("You Lose", 130, 193);
            }
            gc.setFont(new Font("Comic Sans MS", 36));
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(MainGame.getScore()), 323, 285);
            if (chooseImg != null) {
                gc.drawImage(chooseImg, chooseImgX, chooseImgY);
            }
        } else if (type == types.ENDING_PVP) {
            gc.drawImage(MenuTextures.endGamePvp.getImg(), 0, 0);
            gc.setFont(new Font("Comic Sans MS", 100));
            if (MainGame.bombers.get(0).playerID == 0) {
                gc.setFill(Color.GREEN);
                gc.fillText("WIN", 47, 100);
                gc.setFill(Color.RED);
                gc.fillText("LOSE", 401, 100);
            } else {
                gc.setFill(Color.RED);
                gc.fillText("LOSE", 47, 100);
                gc.setFill(Color.GREEN);
                gc.fillText("WIN", 401, 100);
            }
            if (chooseImg != null) {
                gc.drawImage(chooseImg, chooseImgX, chooseImgY);
            }
        }
    }

    public static void play() {
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (type == types.STAGE_COMPLETE) {
                    if (event.getX() >= 432 && event.getX() <= 494 &&
                            event.getY() >= 420 && event.getY() <= 467) {
                        chooseImg = MenuTextures.nextLevel.getImg();
                        chooseImgX = 432;
                        chooseImgY = 420;
                    } else if (event.getX() >= 157 && event.getX() <= 220 &&
                            event.getY() >= 417 && event.getY() <= 468) {
                        chooseImg = MenuTextures.homex.getImg();
                        chooseImgX = 157;
                        chooseImgY = 417;
                    } else {
                        chooseImg = null;
                    }
                } else if (type == types.ENDING_PVE || type == types.ENDING_PVP) {
                    if (event.getX() >= 319 && event.getX() <= 383 &&
                            event.getY() >= 417 && event.getY() <= 468) {
                        chooseImg = MenuTextures.homex.getImg();
                        chooseImgX = 319;
                        chooseImgY = 417;
                    } else {
                        chooseImg = null;
                    }
                }
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (type == types.STAGE_COMPLETE) {
                    if (event.getX() >= 432 && event.getX() <= 494 &&
                            event.getY() >= 420 && event.getY() <= 467) {
                        timeline.stop();
                        SceneLib.switchToMainGame();
                        MainGame.nextLevel();
                    } else if (event.getX() >= 157 && event.getX() <= 220 &&
                            event.getY() >= 417 && event.getY() <= 468) {
                        timeline.stop();
                        SceneLib.switchToMenu();
                        MainGame.nextLevel();
                    }
                } else if (type == types.ENDING_PVE || type == types.ENDING_PVP) {
                    if (event.getX() >= 319 && event.getX() <= 383 &&
                            event.getY() >= 417 && event.getY() <= 468) {
                        timeline.stop();
                        SceneLib.switchToMenu();
                    }
                    if (type == types.ENDING_PVE) {
                        GameMode.con = false;
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
                if (type == types.LEVEL) {
                    if (timeBeforeStart <= 0) {
                        timeBeforeStart = 150;
                        MainGame.chooseGamePlay = MainGame.Gameplay.PVE;
                        timeline.stop();
                        SceneLib.switchToMainGame();
                    } else {
                        --timeBeforeStart;
                    }
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

    public static void stop() {
        timeline.stop();
    }

    public static void setChooseImg() {
        Temp.chooseImg = null;
    }
}
