package uet.oop.bomberman.entities.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Portal;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.MainGame;

import java.util.ArrayList;
import java.util.LinkedList;

public class Kondoria extends Enemy {
    public static final int point = 200;

    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        isMoving = false;
        direction = 1;
        speed = 1f;
    }

    @Override
    public void moving() {
        super.moving();
    }

    @Override
    public void chooseImg() {
        switch (direction) {
            case 0:
            case 1:
                img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animate, 30);
                break;
            default:
                img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animate, 30);
                break;
        }
    }

    @Override
    protected boolean collide(ArrayList<LinkedList<Entity>> map, double posX, double posY) {
        boolean collide = false;
        x = (int) posX / Sprite.SCALED_SIZE;
        y = (int) posY / Sprite.SCALED_SIZE;
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                if (x + i >= 0 && y + j >= 0 &&
                        x + i < MainGame.COLUMN[MainGame.level] &&
                        y + j < MainGame.ROW[MainGame.level]) {
                    if (collideInMap(map, i, j, posX, posY)) {
                        collide = true;
                    }
                }
            }
        }
        return collide;
    }

    @Override
    protected boolean collideInMap(ArrayList<LinkedList<Entity>> map, int i, int j, double posX, double posY) {
        LinkedList<Entity> e = map.get(MainGame.COLUMN[MainGame.level] * (y + j) + x + i);
        if (e.size() == 1) {
            if (map.get(MainGame.COLUMN[MainGame.level] * (y + j) + x + i).get(0) instanceof Wall) {
                double objX = e.get(0).getX() * Sprite.SCALED_SIZE;
                double objY = e.get(0).getY() * Sprite.SCALED_SIZE;
                return checkCollideInMap(objX, objY, posX, posY);
            }
        } else if (e.size() == 2) {
            double objX = e.get(1).getX() * Sprite.SCALED_SIZE;
            double objY = e.get(1).getY() * Sprite.SCALED_SIZE;
            if (e.get(1) instanceof Portal) {
                return checkCollideInMap(objX, objY, posX, posY);
            }
        }
        return false;
    }

    @Override
    protected void afterDead() {
        if (timeAfterDead < 120 && timeAfterDead > 90) {
            img = Sprite.kondoria_dead;
        } else {
            super.afterDead();
        }
        --timeAfterDead;
    }
}
