package uet.oop.bomberman.sounds;

import javafx.scene.media.AudioClip;

import java.io.File;

public abstract class SoundLib {
    public static AudioClip setBomb = new AudioClip(
            new File("/bomberman/res/sounds/setbomb.wav").toURI().toString());
    public static AudioClip explosion = new AudioClip(
            new File("/bomberman/res/sounds/explosion.wav").toURI().toString());
    public static AudioClip item = new AudioClip(
            new File("/bomberman/res/sounds/item.wav").toURI().toString());
    public static AudioClip stageStart = new AudioClip(
            new File("/bomberman/res/sounds/stagestart.mp3").toURI().toString());
    public static AudioClip stageComplete = new AudioClip(
            new File("/bomberman/res/sounds/stagecomplete.mp3").toURI().toString());
    public static AudioClip menu = new AudioClip(
            new File("/bomberman/res/sounds/menu.mp3").toURI().toString());
    public static AudioClip lifeLost = new AudioClip(
            new File("/bomberman/res/sounds/lifelost.mp3").toURI().toString());
    public static AudioClip gameOver = new AudioClip(
            new File("/bomberman/res/sounds/gameover.mp3").toURI().toString());
}
