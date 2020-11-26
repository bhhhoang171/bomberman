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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.KeyEvents;
import uet.oop.bomberman.entities.enemys.*;
import uet.oop.bomberman.entities.items.*;
import uet.oop.bomberman.entities.players.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.entities.tiles.Portal;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.MenuTextures;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.net.Client;
import uet.oop.bomberman.sounds.SoundLib;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainGame {
    public static int clientID = 0;

    public static double camX;
    public static double camY = Sprite.SCALED_SIZE * 4;

    public static Gameplay chooseGamePlay;

    public static int[] COLUMN = new int[10];
    public static int[] ROW = new int[10];
    public static String[][] gameMap = new String[10][50];
    public static int level = 0;
    private static boolean paused = true;

    private static Timeline timeline;
    private static GraphicsContext gc;
    private static Canvas canvas;
    private static Group root;
    private static Scene scene;
    public static ArrayList<Bomber> bombers = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<LinkedList<Entity>> map = new ArrayList<>();

    private static ArrayList<LinkedList<Entity>> savedMap;
    private static ArrayList<Bomber> savedBombers;
    private static ArrayList<Enemy> savedEnemies;
    private static int savedLevel;

    private static boolean music = true;
    private static Image chooseImg = null;
    private static double chooseImgX;
    private static double chooseImgY;
    private static int score = 0;

    public static final int MAX_LEVEL_PVE = 2;

    private static KeyEvents input = new KeyEvents();

    public enum Gameplay {
        PVE,
        PVP_OFF,
        PVP_ONL
    }

    public MainGame() {
        loadMapFromFile();
        createMap();
        initialize();
    }

    public void initialize() {
        canvas = new Canvas(Sprite.SCALED_SIZE * BombermanGame.WIDTH, Sprite.SCALED_SIZE * BombermanGame.HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
    }

    public static void play() {
        scene.setOnKeyPressed(input);
        scene.setOnKeyReleased(input);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getX() >= 672 && event.getX() <= 703 &&
                        event.getY() >= 0 && event.getY() <= 25) {
                    timeline.stop();
                    paused = true;
                    SceneLib.switchToMenu();
                    if (SoundLib.gameTheme.isPlaying()) {
                        SoundLib.gameTheme.stop();
                    }
                } else if (event.getX() >= 676 && event.getX() <= 699 &&
                        event.getY() >= 40 && event.getY() <= 68) {
                    if (chooseGamePlay == Gameplay.PVE) {
                        paused = !paused;
                    }
                }
            }
        });
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getX() >= 672 && event.getX() <= 703 &&
                        event.getY() >= 0 && event.getY() <= 25) {
                    chooseImg = MenuTextures.home.getImg();
                    chooseImgX = 672;
                    chooseImgY = 0;
                } else if (event.getX() >= 676 && event.getX() <= 699 &&
                        event.getY() >= 40 && event.getY() <= 68) {
                    if (chooseGamePlay == Gameplay.PVE) {
                        if (!paused) {
                            chooseImg = MenuTextures.pause.getImg();
                        } else {
                            chooseImg = MenuTextures.start1.getImg();
                        }
                        chooseImgX = 676;
                        chooseImgY = 40;
                    }
                } else if (event.getX() >= 672 && event.getX() <= 699 &&
                        event.getY() >= 84 && event.getY() <= 110) {
                    if (music) {
                        chooseImg = MenuTextures.music.getImg();
                    } else {
                        chooseImg = MenuTextures.mute1.getImg();
                    }
                    chooseImgX = 672;
                    chooseImgY = 84;
                } else {
                    chooseImg = null;
                }
            }
        });
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        Duration oneFrameAmt = Duration.millis(1000.0 / 60);
        KeyFrame oneFrame = new KeyFrame(oneFrameAmt, new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!SoundLib.gameTheme.isPlaying()) {
                    SoundLib.gameTheme.play();
                }
                if (!MainGame.paused) {
                    update();
                    render();
                }
                renderBackground();
            }
        });
        timeline.getKeyFrames().add(oneFrame);
        timeline.play();
    }

    private static void loadMapFromFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream("res\\levels\\test.txt"), StandardCharsets.UTF_8));

            String textInALine;
            int k = 0;
            int l = 0;
            while ((textInALine = br.readLine()) != null) {
                if (textInALine.charAt(0) != '#') {
                    k = 0;
                    String[] split = textInALine.split(" ");
                    l = Integer.parseInt(split[0]);
                    ROW[l] = Integer.parseInt(split[1]);
                    COLUMN[l] = Integer.parseInt(split[2]);
                } else {
                    gameMap[l][k++] = textInALine;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createMap() {
        int r = 0;
        int num = 0;
        for (String s : gameMap[level]) {
            if (s == null) {
                break;
            }
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                LinkedList<Entity> a = new LinkedList<>();
                switch (c) {
                    case '#':
                        a.add(new Wall(i, r, Sprite.wall));
                        map.add(a);
                        break;
                    case '*':
                        a.add(new Grass(i, r, Sprite.grass));
                        a.add(new Brick(i, r, Sprite.brick));
                        map.add(a);
                        break;
                    case 'x':
                        a.add(new Grass(i, r, Sprite.grass));
                        a.add(new Portal(i, r, Sprite.portal));
                        a.add(new Brick(i, r, Sprite.brick));
                        map.add(a);
                        break;
                    case 'b':
                        a.add(new Grass(i, r, Sprite.grass));
                        a.add(new BombItem(i, r, Sprite.powerup_bombs));
                        a.add(new Brick(i, r, Sprite.brick));
                        map.add(a);
                        break;
                    case 'f':
                        a.add(new Grass(i, r, Sprite.grass));
                        a.add(new FlameItem(i, r, Sprite.powerup_flames));
                        a.add(new Brick(i, r, Sprite.brick));
                        map.add(a);
                        break;
                    case 's':
                        a.add(new Grass(i, r, Sprite.grass));
                        a.add(new SpeedItem(i, r, Sprite.powerup_speed));
                        a.add(new Brick(i, r, Sprite.brick));
                        map.add(a);
                        break;
                    case '(':
                        a.add(new Grass(i, r, Sprite.grass));
                        a.add(new FlamepassItem(i, r, Sprite.powerup_flamepass));
                        a.add(new Brick(i, r, Sprite.brick));
                        map.add(a);
                        break;
                    case ')':
                        a.add(new Grass(i, r, Sprite.grass));
                        a.add(new BombpassItem(i, r, Sprite.powerup_bombpass));
                        a.add(new Brick(i, r, Sprite.brick));
                        map.add(a);
                        break;
                    default:
                        a.add(new Grass(i, r, Sprite.grass));
                        map.add(a);
                        switch (c) {
                            case 'p':
                                bombers.add(new Bomber(i, r, Sprite.player_right, num++));
                                break;
                            case '1':
                                enemies.add(new Balloom(i, r, Sprite.balloom_left1));
                                break;
                            case '2':
                                enemies.add(new Oneal(i, r, Sprite.oneal_right1));
                                break;
                            case '3':
                                enemies.add(new Kondoria(i, r, Sprite.kondoria_right1));
                                break;
                            case '4':
                                enemies.add(new Doll(i, r, Sprite.doll_right1));
                                break;
                            case '5':
                                enemies.add(new Ovape(i, r, Sprite.ovape_right1, 3));
                                break;
                            case '6':
                                enemies.add(new Pontan(i, r, Sprite.pontan_right1));
                                break;
                        }
                        break;
                }
            }
            ++r;
        }
    }

    private static void update() {
        bombers.removeIf(bomber -> !bomber.isAlive() && bomber.getTimeAfterDead() <= 0);
        int l = enemies.size();
        for (int i = 0; i < l; ++i) {
            if (enemies.get(i) instanceof Ovape) {
                if (enemies.get(i).getTimeAfterDead() <= 0 && ((Ovape) enemies.get(i)).life > 1) {
                    enemies.add(new Ovape(enemies.get(i).getX(), enemies.get(i).getY(), Sprite.ovape_right1, ((Ovape) enemies.get(i)).life - 1));
                    enemies.add(new Ovape(enemies.get(i).getX(), enemies.get(i).getY(), Sprite.ovape_right1, ((Ovape) enemies.get(i)).life - 1));
                }
            }
        }
        enemies.removeIf(enemy -> !enemy.isAlive() && enemy.getTimeBeforeRemove() <= 0);
        bombers.forEach(Bomber::update);
        enemies.forEach(Enemy::update);
        if (chooseGamePlay == Gameplay.PVE) {
            if (bombers.size() == 0) {
                if (SoundLib.gameTheme.isPlaying()) {
                    SoundLib.gameTheme.stop();
                }
                timeline.stop();
                paused = true;
                Temp.win = false;
                SceneLib.switchToPveEndingScene();
            }
        } else {
            if (bombers.size() == 1) {
                if (SoundLib.gameTheme.isPlaying()) {
                    SoundLib.gameTheme.stop();
                }
                timeline.stop();
                paused = true;
                SceneLib.switchToPvpEndingScene();
            }
        }
    }

    private static void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.forEach(g -> g.forEach(a -> a.render(gc)));
        bombers.forEach(bomber -> bomber.getBombs().removeIf(bomb -> bomb.getTimeExploding() <= 0 && !bomb.isExist()));
        bombers.forEach(bomber -> bomber.getBombs().forEach(bomb -> bomb.render(gc)));
        int balloom = 1;
        int oneal = 1;
        int kondoria = 1;
        int pontan = 1;
        int ovape = 1;
        int doll = 1;
        for (Enemy enemy : enemies) {
            if (enemy.getTimeAfterDead() <= 0) {
                if (!SoundLib.enemyDead.isPlaying()) {
                    SoundLib.enemyDead.play();
                }
                gc.setFont(BombermanGame.font1);
                gc.setFill(Color.WHITE);
                if (enemy instanceof Ovape) {
                    gc.fillText("+" + Ovape.point * ovape,
                            enemy.getPosX() + 1.0f * Sprite.SCALED_SIZE / 2 + camX,
                            enemy.getPosY() + 1.0f * Sprite.SCALED_SIZE / 2 + camY);
                    if (!enemy.isScored()) {
                        score += Ovape.point * ovape;
                        enemy.setScored();
                    }
                    ++ovape;
                } else if (enemy instanceof Pontan) {
                    gc.fillText("+" + Pontan.point * pontan,
                            enemy.getPosX() + 1.0f * Sprite.SCALED_SIZE / 2 + camX,
                            enemy.getPosY() + 1.0f * Sprite.SCALED_SIZE / 2 + camY);
                    if (!enemy.isScored()) {
                        score += Pontan.point * pontan;
                        enemy.setScored();
                    }
                    ++pontan;
                } else if (enemy instanceof Doll) {
                    gc.fillText("+" + Doll.point * doll,
                            enemy.getPosX() + 1.0f * Sprite.SCALED_SIZE / 2 + camX,
                            enemy.getPosY() + 1.0f * Sprite.SCALED_SIZE / 2 + camY);
                    if (!enemy.isScored()) {
                        score += Doll.point * doll;
                        enemy.setScored();
                    }
                    ++doll;
                } else if (enemy instanceof Kondoria) {
                    gc.fillText("+" + Kondoria.point * kondoria,
                            enemy.getPosX() + 1.0f * Sprite.SCALED_SIZE / 2 + camX,
                            enemy.getPosY() + 1.0f * Sprite.SCALED_SIZE / 2 + camY);
                    if (!enemy.isScored()) {
                        score += Kondoria.point * kondoria;
                        enemy.setScored();
                    }
                    ++kondoria;
                } else if (enemy instanceof Oneal) {
                    gc.fillText("+" + Oneal.point * oneal,
                            enemy.getPosX() + 1.0f * Sprite.SCALED_SIZE / 2 + camX,
                            enemy.getPosY() + 1.0f * Sprite.SCALED_SIZE / 2 + camY);
                    if (!enemy.isScored()) {
                        score += Oneal.point * oneal;
                        enemy.setScored();
                    }
                    ++oneal;
                } else if (enemy instanceof  Balloom) {
                    gc.fillText("+" + Balloom.point * balloom,
                            enemy.getPosX() + 1.0f * Sprite.SCALED_SIZE / 2 + camX,
                            enemy.getPosY() + 1.0f * Sprite.SCALED_SIZE / 2 + camY);
                    if (!enemy.isScored()) {
                        score += Balloom.point * balloom;
                        enemy.setScored();
                    }
                    ++balloom;
                }
            }
            enemy.render(gc);
        }
        bombers.forEach(g -> g.render(gc));
    }

    private static void renderBackground() {
        gc.clearRect(0, 0, 704, Sprite.SCALED_SIZE * 4);
        if (chooseGamePlay == Gameplay.PVE) {
            gc.drawImage(MenuTextures.gameBackground.getImg(), 0, 0);
            if (!music) {
                gc.drawImage(MenuTextures.mute.getImg(), 672, 84);
            }
            if (paused) {
                gc.drawImage(MenuTextures.start.getImg(), 676, 40);
            }
            gc.setFont(new Font("Comic San MS", 12));
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(1), 40, 31);
            if (bombers.size() == 1) {
                gc.fillText(Integer.toString(bombers.get(0).bombCount), 52, 67);
                gc.fillText(Integer.toString(bombers.get(0).flameWidth), 50, 103);
            }
            gc.setFont(new Font("Comic San MS", 24));
            gc.fillText(Integer.toString(MainGame.score), 323, 74);
        } else {
            gc.drawImage(MenuTextures.pvpBackground.getImg(), 0, 0);
            gc.setFont(new Font("Comic San MS", 12));
            gc.setFill(Color.WHITE);
            if (bombers.size() == 2) {
                gc.fillText(Integer.toString(bombers.get(0).bombCount), 53, 53);
                gc.fillText(Integer.toString(bombers.get(0).flameWidth), 51, 89);
                gc.fillText(Integer.toString(bombers.get(1).bombCount), 477, 53);
                gc.fillText(Integer.toString(bombers.get(1).flameWidth), 475, 89);
            }
        }
        if (chooseImg != null) {
            gc.drawImage(chooseImg, chooseImgX, chooseImgY);
        }
    }

    public static Scene getScene() {
        return scene;
    }

    public static void start() {
        paused = false;
    }

    public static void paused() {
        paused = true;
    }

    public static void nextLevel() {
        ++level;
        savedLevel = level;
        bombers.clear();
        enemies.clear();
        map.clear();
        createMap();
    }

    public static void pvpOffNewGame() {
        chooseGamePlay = Gameplay.PVP_OFF;
        map = new ArrayList<>();
        bombers = new ArrayList<>();
        enemies = new ArrayList<>();
        level = 9;
        createMap();
    }

    public static void pvpOnlNewGame() {
        chooseGamePlay = Gameplay.PVP_ONL;
        map = new ArrayList<>();
        bombers = new ArrayList<>();
        enemies = new ArrayList<>();
        level = 9;
        createMap();
        new Client();
    }

    public static void pveContinue() {
        chooseGamePlay = Gameplay.PVE;
        map = savedMap;
        bombers = savedBombers;
        enemies = savedEnemies;
        level = savedLevel;
    }

    public static void pveNewGame() {
        score = 0;
        chooseGamePlay = Gameplay.PVE;
        savedMap = map;
        savedEnemies = enemies;
        savedBombers = bombers;
        map.clear();
        bombers.clear();
        enemies.clear();
        level = 0;
        createMap();
    }

    public static void stop() {
        timeline.stop();
    }

    public static void setChooseImg() {
        MainGame.chooseImg = null;
    }

    public static int getScore() {
        return score;
    }
}
