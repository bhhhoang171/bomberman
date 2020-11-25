package uet.oop.bomberman.entities.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


public class Ovape extends Kondoria {
    public static final int point = 200;
    public int life;

    public Ovape(int x, int y, Image img) {
        super(x, y, img);
        isMoving = false;
        direction = 1;
        speed = 0.25f;
    }

    public Ovape(int x, int y, Image img, int life) {
        super(x, y, img);
        this.life = life;
        isMoving = false;
        direction = 1;
        speed = 0.25f;
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
                img = Sprite.movingSprite(Sprite.ovape_left1, Sprite.ovape_left2, Sprite.ovape_left3, animate, 30);
                break;
            default:
                img = Sprite.movingSprite(Sprite.ovape_right1, Sprite.ovape_right2, Sprite.ovape_right3, animate, 30);
                break;
        }
    }

    @Override
    protected void afterDead() {
        if (timeAfterDead < 120 && timeAfterDead > 90) {
            img = Sprite.ovape_dead;
        } else {
            super.afterDead();
        }
        --timeAfterDead;
    }

}
