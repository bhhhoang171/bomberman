package uet.oop.bomberman;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.scenes.MainGame;

public class KeyEvents implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        if (MainGame.chooseGamePlay == MainGame.Gameplay.PVE || MainGame.chooseGamePlay == MainGame.Gameplay.PVP_OFF) {
            if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                if (event.getCode() == KeyCode.W) {
                    MainGame.bombers.get(0).up = true;
                }
                if (event.getCode() == KeyCode.S) {
                    MainGame.bombers.get(0).down = true;
                }
                if (event.getCode() == KeyCode.A) {
                    MainGame.bombers.get(0).left = true;
                }
                if (event.getCode() == KeyCode.D) {
                    MainGame.bombers.get(0).right = true;
                }
                if (event.getCode() == KeyCode.SPACE) {
                    MainGame.bombers.get(0).space = true;
                }
            }
            if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
                if (event.getCode() == KeyCode.W) {
                    MainGame.bombers.get(0).up = false;
                }
                if (event.getCode() == KeyCode.S) {
                    MainGame.bombers.get(0).down = false;
                }
                if (event.getCode() == KeyCode.A) {
                    MainGame.bombers.get(0).left = false;
                }
                if (event.getCode() == KeyCode.D) {
                    MainGame.bombers.get(0).right = false;
                }
                if (event.getCode() == KeyCode.SPACE) {
                    MainGame.bombers.get(0).space = false;
                }
            }
        }
        if (MainGame.chooseGamePlay == MainGame.Gameplay.PVP_OFF) {
            if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                if (event.getCode() == KeyCode.UP) {
                    MainGame.bombers.get(1).up = true;
                }
                if (event.getCode() == KeyCode.DOWN) {
                    MainGame.bombers.get(1).down = true;
                }
                if (event.getCode() == KeyCode.LEFT) {
                    MainGame.bombers.get(1).left = true;
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    MainGame.bombers.get(1).right = true;
                }
                if (event.getCode() == KeyCode.ENTER) {
                    MainGame.bombers.get(1).space = true;
                }
            }
            if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
                if (event.getCode() == KeyCode.UP) {
                    MainGame.bombers.get(1).up = false;
                }
                if (event.getCode() == KeyCode.DOWN) {
                    MainGame.bombers.get(1).down = false;
                }
                if (event.getCode() == KeyCode.LEFT) {
                    MainGame.bombers.get(1).left = false;
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    MainGame.bombers.get(1).right = false;
                }
                if (event.getCode() == KeyCode.ENTER) {
                    MainGame.bombers.get(1).space = false;
                }
            }
        }
        if (MainGame.clientID != 99 && MainGame.chooseGamePlay == MainGame.Gameplay.PVP_ONL) {
            if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                if (event.getCode() == KeyCode.UP) {
                    MainGame.bombers.get(MainGame.clientID).up = true;
                }
                if (event.getCode() == KeyCode.DOWN) {
                    MainGame.bombers.get(MainGame.clientID).down = true;
                }
                if (event.getCode() == KeyCode.LEFT) {
                    MainGame.bombers.get(MainGame.clientID).left = true;
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    MainGame.bombers.get(MainGame.clientID).right = true;
                }
                if (event.getCode() == KeyCode.ENTER) {
                    MainGame.bombers.get(MainGame.clientID).space = true;
                }
            }
            if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
                if (event.getCode() == KeyCode.UP) {
                    MainGame.bombers.get(MainGame.clientID).up = false;
                }
                if (event.getCode() == KeyCode.DOWN) {
                    MainGame.bombers.get(MainGame.clientID).down = false;
                }
                if (event.getCode() == KeyCode.LEFT) {
                    MainGame.bombers.get(MainGame.clientID).left = false;
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    MainGame.bombers.get(MainGame.clientID).right = false;
                }
                if (event.getCode() == KeyCode.ENTER) {
                    MainGame.bombers.get(MainGame.clientID).space = false;
                }
            }
        }
    }
}
