package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;

public class MenuTextures {
    private final Image img;

    //Menu
    public static MenuTextures background = new MenuTextures("/textures/background.png");
    public static MenuTextures pve = new MenuTextures("/textures/pve.png");
    public static MenuTextures pvp = new MenuTextures("/textures/pvp.png");
    public static MenuTextures tutorial = new MenuTextures("/textures/tutorial.png");
    public static MenuTextures highScore = new MenuTextures("/textures/highscore.png");
    //Gamemode
    public static MenuTextures gameMode = new MenuTextures("/textures/pvegameplay.png");
    public static MenuTextures newGame1 = new MenuTextures("/textures/newgame.png");
    public static MenuTextures continue0 = new MenuTextures("/textures/continue.png");
    public static MenuTextures continue1 = new MenuTextures("/textures/continue1.png");
    public static MenuTextures online = new MenuTextures("/textures/onl.png");
    public static MenuTextures online1 = new MenuTextures("/textures/onl1.png");
    public static MenuTextures offline = new MenuTextures("/textures/off.png");
    public static MenuTextures offline1 = new MenuTextures("/textures/off1.png");
    //Next Level
    public static MenuTextures level = new MenuTextures("/textures/level.png");
    //Stage Complete
    public static MenuTextures stageComplete = new MenuTextures("/textures/stagecomplete.png");
    public static MenuTextures nextLevel = new MenuTextures("/textures/nextlevel.png");
    public static MenuTextures homex = new MenuTextures("/textures/home.png");
    //Ending
    public static MenuTextures endGame = new MenuTextures("/textures/endgame.png");
    public static MenuTextures endGamePvp = new MenuTextures("/textures/endgamepvp.png");
    //Game Board
    public static MenuTextures gameBackground = new MenuTextures("/textures/gamebackground.png");
    public static MenuTextures pvpBackground = new MenuTextures("/textures/pvpbackground.png");
    public static MenuTextures music = new MenuTextures("/textures/music.png");
    public static MenuTextures home = new MenuTextures("/textures/home.png", 32, 26);
    public static MenuTextures pause = new MenuTextures("/textures/pause1.png");
    public static MenuTextures mute = new MenuTextures("/textures/mute.png");
    public static MenuTextures mute1 = new MenuTextures("/textures/mute1.png");
    public static MenuTextures start = new MenuTextures("/textures/start.png");
    public static MenuTextures start1 = new MenuTextures("/textures/start1.png");


    public MenuTextures(String path) {
        img = new Image(path);
    }

    public MenuTextures(String path, int w, int h) {
        img = new Image(path, w, h, false, false);
    }

    public Image getImg() {
        return img;
    }
}
