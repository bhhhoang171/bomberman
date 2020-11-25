package uet.oop.bomberman.entities.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.players.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.MainGame;

import java.util.ArrayList;

public class Pontan extends Oneal {
    public static final int point = 500;
    private boolean angry = false;
    private int timeBeforeChangeForm = 80;

    public Pontan(int x, int y, Image img) {
        super(x, y, img);
        isMoving = false;
        direction = 1;
        speed = 1f;
    }

    @Override
    public void moving() {
        if (angry && timeBeforeChangeForm <= 0) {
            super.moving();
        }
        super.moving();
    }

    @Override
    public void chooseImg() {
        if (!angry) {
            switch (direction) {
                case 0:
                case 1:
                    img = Sprite.movingSprite(Sprite.pontan_left1, Sprite.pontan_left2, Sprite.pontan_left3, animate, 30);
                    break;
                default:
                    img = Sprite.movingSprite(Sprite.pontan_right1, Sprite.pontan_right2, Sprite.pontan_right3, animate, 30);
                    break;
            }
        } else if (timeBeforeChangeForm <= 0){
            switch (direction) {
                case 0:
                case 1:
                    img = Sprite.movingSprite(Sprite.pontan_angry_left1, Sprite.pontan_angry_left2, Sprite.pontan_angry_left3, animate, 30);
                    break;
                default:
                    img = Sprite.movingSprite(Sprite.pontan_angry_right1, Sprite.pontan_angry_right2, Sprite.pontan_angry_right3, animate, 30);
                    break;
            }
        }
    }

    @Override
    protected void afterDead() {
        if (timeAfterDead < 120 && timeAfterDead > 90) {
            img = Sprite.pontan_angry_dead;
        } else {
            super.afterDead();
        }
        --timeAfterDead;
    }

    @Override
    protected void collideWithFlame(ArrayList<Bomb> bombs) {
        if (angry && timeBeforeChangeForm <= 0) {
            super.collideWithFlame(bombs);
        } else if (!angry) {
            for (Bomb bomb : bombs) {
                if (bomb.isExploding()) {
                    for (int k = 1; k <= bomb.getFlameWidth(); ++k) {
                        double objX = (bomb.getX() - k) * Sprite.SCALED_SIZE;
                        double objY = (bomb.getY() - k) * Sprite.SCALED_SIZE;
                        double objX0 = (bomb.getX() + k) * Sprite.SCALED_SIZE;
                        double objY0 = (bomb.getY() + k) * Sprite.SCALED_SIZE;
                        double objX1 = bomb.getX() * Sprite.SCALED_SIZE;
                        double objY1 = bomb.getY() * Sprite.SCALED_SIZE;
                        if (checkCollideInMap(objX, objY1, posX, posY)) {
                            angry = true;
                            speed = 2f;
                        }
                        if (checkCollideInMap(objX1, objY, posX, posY)) {
                            angry = true;
                            speed = 2f;
                        }
                        if (checkCollideInMap(objX1, objY0, posX, posY)) {
                            angry = true;
                            speed = 2f;
                        }
                        if (checkCollideInMap(objX0, objY1, posX, posY)) {
                            angry = true;
                            speed = 2f;
                        }
                        if (checkCollideInMap(objX1, objY1, posX, posY)) {
                            angry = true;
                            speed = 2f;
                        }
                    }
                }
            }
        }
    }

    private void changeForm() {
        if (angry && timeBeforeChangeForm > 0) {
            img = Sprite.pontan_dead;
            --timeBeforeChangeForm;
            speed = 0;
        } else if (angry) {
            if (posX % 2 != 0 || posY % 2 != 0) {
                speed = 1f;
            }
            else speed = 2f;
        }
    }

    @Override
    public void update() {
        super.update();
        changeForm();
    }
}
