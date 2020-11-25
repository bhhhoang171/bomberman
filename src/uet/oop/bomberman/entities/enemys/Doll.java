package uet.oop.bomberman.entities.enemys;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.players.Bomber;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.MainGame;

import java.util.LinkedList;

public class Doll extends Enemy {
    public static final int point = 200;

    public Doll(int x, int y, Image img) {
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
                img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 30);
                break;
            default:
                img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 30);
                break;
        }
    }

    @Override
    protected void afterDead() {
        if (timeAfterDead < 120 && timeAfterDead > 90) {
            img = Sprite.doll_dead;
        } else {
            super.afterDead();
        }
        --timeAfterDead;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (alive) {
            chooseImg();
        } else {
            afterDead();
            if (timeAfterDead <= 60 && timeAfterDead > 0) {
                explode(gc);
            }
            if (timeAfterDead <= 0) {
                --timeBeforeRemove;
                for (LinkedList<Entity> l : MainGame.map) {
                    l.removeIf(g -> g instanceof Brick && !((Brick) g).isExist());
                }
            }
        }
        if (timeAfterDead > 0) {
            gc.drawImage(img, posX + MainGame.camX, posY + MainGame.camY);
        }
    }

    private void explode(GraphicsContext gc) {
        boolean up = true;
        boolean down = true;
        boolean left = true;
        boolean right = true;
        int k = 1;
        Image ig = Sprite.removeAnimation(Sprite.bomb_exploded,
                Sprite.bomb_exploded1, Sprite.bomb_exploded2, timeAfterDead, 60);
        gc.drawImage(ig, x * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
        while (true) {
            LinkedList<Entity> e;
            if (left) {
                e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x - k);
                if (e.size() == 2) {
                    if (e.get(1) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeAfterDead, 60);
                        gc.drawImage(i, (x - k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(1)).destroy();
                        left = false;
                    } else if (e.get(1) instanceof Item) {
                        e.remove(1);
                    } else {
                        left = false;
                    }
                } else if (e.size() == 1) {
                    if (e.get(0) instanceof Grass) {
                        Image i = Sprite.removeAnimation(Sprite.explosion_horizontal,
                                Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, timeAfterDead, 60);
                        gc.drawImage(i, (x - k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                    } else {
                        left = false;
                    }
                } else if (e.size() == 3) {
                    if (e.get(2) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeAfterDead, 60);
                        gc.drawImage(i, (x - k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(2)).destroy();
                        left = false;
                    }
                }
            } //left

            if (right) {
                e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x + k);
                if (e.size() == 2) {
                    if (e.get(1) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeAfterDead, 60);
                        gc.drawImage(i, (x + k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(1)).destroy();
                        right = false;
                    } else if (e.get(1) instanceof Item) {
                        e.remove(1);
                    } else {
                        right = false;
                    }
                } else if (e.size() == 1) {
                    if (e.get(0) instanceof Grass) {
                        Image i = Sprite.removeAnimation(Sprite.explosion_horizontal,
                                Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, timeAfterDead, 60);
                        gc.drawImage(i, (x + k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                    } else {
                        right = false;
                    }
                } else if (e.size() == 3) {
                    if (e.get(2) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeAfterDead, 60);
                        gc.drawImage(i, (x - k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(2)).destroy();
                        right = false;
                    }
                }
            } //right

            if (up) {
                e = MainGame.map.get((y - k) * MainGame.COLUMN[MainGame.level] + x);
                if (e.size() == 2) {
                    if (e.get(1) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeAfterDead, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - k) * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(1)).destroy();
                        up = false;
                    } else if (e.get(1) instanceof Item) {
                        e.remove(1);
                    } else {
                        up = false;
                    }
                } else if (e.size() == 1) {
                    if (e.get(0) instanceof Grass) {
                        Image i = Sprite.removeAnimation(Sprite.explosion_vertical,
                                Sprite.explosion_vertical1, Sprite.explosion_vertical2, timeAfterDead, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - k) * Sprite.SCALED_SIZE + MainGame.camY);
                    } else {
                        up = false;
                    }
                } else if (e.size() == 3) {
                    if (e.get(2) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeAfterDead, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - k) * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(2)).destroy();
                        up = false;
                    }
                }
            } //top

            if (down) {
                e = MainGame.map.get((y + k) * MainGame.COLUMN[MainGame.level] + x);
                if (e.size() == 2) {
                    if (e.get(1) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeAfterDead, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y + k) * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(1)).destroy();
                        down = false;
                    } else if (e.get(1) instanceof Item) {
                        e.remove(1);
                    } else {
                        down = false;
                    }
                } else if (e.size() == 1) {
                    if (e.get(0) instanceof Grass) {
                        Image i = Sprite.removeAnimation(Sprite.explosion_vertical,
                                Sprite.explosion_vertical1, Sprite.explosion_vertical2, timeAfterDead, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y + k) * Sprite.SCALED_SIZE + MainGame.camY);
                    } else {
                        down = false;
                    }
                } else if (e.size() == 3) {
                    if (e.get(2) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeAfterDead, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y + k) * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(2)).destroy();
                        down = false;
                    }
                }
            } //down
            if (!left && !right && !up && !down) {
                break;
            }
            ++k;
        }
    }
    private void collideWithBomber() {
        if (MainGame.bombers.size() > 0) {
            Bomber bomber = MainGame.bombers.get(0);
        }
    }
}
