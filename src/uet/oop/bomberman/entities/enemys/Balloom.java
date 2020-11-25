package uet.oop.bomberman.entities.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.scenes.MainGame;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public static final int point = 100;

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        isMoving = false;
        direction = 1;
        speed = 0.25f;
    }

    @Override
    public void moving() {
        --timeBetweenChangeDirection;
        if (timeBetweenChangeDirection <= 0) {
            direction = (int) Math.round(Math.random() * 100) % 4;
            timeBetweenChangeDirection = 200;
        }
        switch (direction) {
            case 1: //left
                if (!collide(MainGame.map, posX - speed, posY)) {
                    posX -= speed;
                    isMoving = true;
                } else {
                    timeBetweenChangeDirection = 0;
                }
                break;
            case 3: //right
                if (!collide(MainGame.map, posX + speed, posY)) {
                    posX += speed;
                    isMoving = true;
                } else {
                    timeBetweenChangeDirection = 0;
                }
                break;
            case 0: //up
                if (!collide(MainGame.map, posX, posY - speed)) {
                    posY -= speed;
                    isMoving = true;
                } else {
                    timeBetweenChangeDirection = 0;
                }
                break;
            case 2: //down
                if (!collide(MainGame.map, posX, posY + speed)) {
                    posY += speed;
                    isMoving = true;
                } else {
                    timeBetweenChangeDirection = 0;
                }
                break;
            default:
                isMoving = false;
                break;
        }
    }

    @Override
    public void chooseImg() {
        switch (direction) {
            case 0:
            case 1:
                img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 30);
                break;
            default:
                img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 30);
                break;
        }
    }

    @Override
    protected void afterDead() {
        if (timeAfterDead < 120 && timeAfterDead > 90) {
            img = Sprite.balloom_dead;
        } else {
            super.afterDead();
        }
        --timeAfterDead;
    }
}
