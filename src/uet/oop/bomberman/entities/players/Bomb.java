package uet.oop.bomberman.entities.players;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.scenes.MainGame;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.SoundLib;

import java.util.ArrayList;
import java.util.LinkedList;

public class Bomb extends Entity {
    private boolean exist;
    private int animate;
    private final int MAX_ANIMATE = 10000;
    private int timeBeforeExplode;
    private int timeExploding;
    private boolean exploding;
    private int playerID;
    private int flameWidth;
    private boolean left = true;
    private boolean right = true;
    private boolean top = true;
    private boolean down = true;


    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        exist = true;
        timeBeforeExplode = 240;
        timeExploding = 90;
        exploding = false;
    }

    public Bomb(int x, int y, Image img, int playerID, int flameWidth) {
        super(x, y, img);
        this.flameWidth = flameWidth;
        this.playerID = playerID;
        exist = true;
        timeBeforeExplode = 180;
        timeExploding = 60;
        exploding = false;
    }

    @Override
    public void update() {
        animate();
        if (timeExploding <= 0) {
            for (LinkedList<Entity> l : MainGame.map) {
                l.removeIf(g -> g instanceof Brick && !((Brick) g).isExist());
            }
            for (Bomber bomber : MainGame.bombers) {
                if (bomber.playerID == this.playerID) {
                    ++bomber.bombCount;
                    break;
                }
            }
            exploding = false;
        }
    }

    public void animate() {
        if(animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (exist) {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 100);
            gc.drawImage(img, x * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
            --timeBeforeExplode;
        }
        if (timeBeforeExplode <= 0) {
            if (!SoundLib.explosion.isPlaying()) {
                SoundLib.explosion.play();
            }
            explode(gc);
        }
    }

    public int getFlameWidth() {
        return flameWidth;
    }

    public void explode(GraphicsContext gc) {
        img = Sprite.removeAnimation(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, timeExploding, 60);
        exploding = true;
        left = true;
        right = true;
        top = true;
        down = true;
        exist = false;
        gc.drawImage(img, x * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
        for (int k = 1; k < flameWidth; ++k) {
            LinkedList<Entity> e;
            if (left) {
                e =  MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x - k);
                if (e.size() == 2) {
                    if (e.get(1) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
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
                                Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, timeExploding, 60);
                        gc.drawImage(i, (x - k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                    } else {
                        left = false;
                    }
                } else if (e.size() == 3) {
                    if (e.get(2) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
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
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
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
                                Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, timeExploding, 60);
                        gc.drawImage(i, (x + k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                    } else {
                        right = false;
                    }
                } else if (e.size() == 3) {
                    if (e.get(2) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                        gc.drawImage(i, (x - k) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(2)).destroy();
                        right = false;
                    }
                }
            } //right

            if (top) {
                e = MainGame.map.get((y - k) * MainGame.COLUMN[MainGame.level] + x);
                if (e.size() == 2) {
                    if (e.get(1) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - k) * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(1)).destroy();
                        top = false;
                    } else if (e.get(1) instanceof Item) {
                        e.remove(1);
                    } else {
                        top = false;
                    }
                } else if (e.size() == 1) {
                    if (e.get(0) instanceof Grass) {
                        Image i = Sprite.removeAnimation(Sprite.explosion_vertical,
                                Sprite.explosion_vertical1, Sprite.explosion_vertical2, timeExploding, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - k) * Sprite.SCALED_SIZE + MainGame.camY);
                    } else {
                        top = false;
                    }
                } else if (e.size() == 3) {
                    if (e.get(2) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - k) * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(2)).destroy();
                        top = false;
                    }
                }
            } //top

            if (down) {
                e = MainGame.map.get((y + k) * MainGame.COLUMN[MainGame.level] + x);
                if (e.size() == 2) {
                    if (e.get(1) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
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
                                Sprite.explosion_vertical1, Sprite.explosion_vertical2, timeExploding, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y + k) * Sprite.SCALED_SIZE + MainGame.camY);
                    } else {
                        down = false;
                    }
                } else if (e.size() == 3) {
                    if (e.get(2) instanceof Brick) {
                        Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                        gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y + k) * Sprite.SCALED_SIZE + MainGame.camY);
                        ((Brick) e.get(2)).destroy();
                        down = false;
                    }
                }
            } //down
        }

        //last
        LinkedList<Entity> e;

        if (left) {
            e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x - flameWidth);
            if (e.size() == 2) {
                if (e.get(1) instanceof Brick) {
                    Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                    gc.drawImage(i, (x - flameWidth) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                    ((Brick) e.get(1)).destroy();
                    left = false;
                } else if (e.get(1) instanceof Item) {
                    e.remove(1);
                } else {
                    left = false;
                }
            } else if (e.size() == 1) {
                if (e.get(0) instanceof Grass) {
                    Image i = Sprite.removeAnimation(Sprite.explosion_horizontal_left_last,
                            Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, timeExploding, 60);
                    gc.drawImage(i, (x - flameWidth) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                } else {
                    left = false;
                }
            } else if (e.size() == 3) {
                if (e.get(2) instanceof Brick) {
                    Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                    gc.drawImage(i, (x - flameWidth) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                    ((Brick) e.get(2)).destroy();
                    left = false;
                }
            }
        } //left

        if (right) {
            e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x + flameWidth);
            if (e.size() == 2) {
                if (e.get(1) instanceof Brick) {
                    Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                    gc.drawImage(i, (x + flameWidth) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                    ((Brick) e.get(1)).destroy();
                    right = false;
                } else if (e.get(1) instanceof Item) {
                    e.remove(1);
                } else {
                    right = false;
                }
            } else if (e.size() == 1) {
                if (e.get(0) instanceof Grass) {
                    Image i = Sprite.removeAnimation(Sprite.explosion_horizontal_right_last,
                            Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, timeExploding, 60);
                    gc.drawImage(i, (x + flameWidth) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                } else {
                    right = false;
                }
            } else if (e.size() == 3) {
                if (e.get(2) instanceof Brick) {
                    Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                    gc.drawImage(i, (x - flameWidth) * Sprite.SCALED_SIZE + MainGame.camX, y * Sprite.SCALED_SIZE + MainGame.camY);
                    ((Brick) e.get(2)).destroy();
                    right = false;
                }
            }
        } //right

        if (top) {
            e = MainGame.map.get((y - flameWidth) * MainGame.COLUMN[MainGame.level] + x);
            if (e.size() == 2) {
                if (e.get(1) instanceof Brick) {
                    Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                    gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - flameWidth) * Sprite.SCALED_SIZE + MainGame.camY);
                    ((Brick) e.get(1)).destroy();
                    top = false;
                } else if (e.get(1) instanceof Item) {
                    e.remove(1);
                } else {
                    top = false;
                }
            } else if (e.size() == 1) {
                if (e.get(0) instanceof Grass) {
                    Image i = Sprite.removeAnimation(Sprite.explosion_vertical_top_last,
                            Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, timeExploding, 60);
                    gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - flameWidth) * Sprite.SCALED_SIZE + MainGame.camY);
                } else {
                    top = false;
                }
            } else if (e.size() == 3) {
                if (e.get(2) instanceof Brick) {
                    Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                    gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y - flameWidth) * Sprite.SCALED_SIZE + MainGame.camY);
                    ((Brick) e.get(2)).destroy();
                    top = false;
                }
            }
        } //top

        if (down) {
            e = MainGame.map.get((y + flameWidth) * MainGame.COLUMN[MainGame.level] + x);
            if (e.size() == 2) {
                if (e.get(1) instanceof Brick) {
                    Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                    gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y + flameWidth) * Sprite.SCALED_SIZE + MainGame.camY);
                    ((Brick) e.get(1)).destroy();
                    down = false;
                } else if (e.get(1) instanceof Item) {
                    e.remove(1);
                } else {
                    down = false;
                }
            } else if (e.size() == 1) {
                if (e.get(0) instanceof Grass) {
                    Image i = Sprite.removeAnimation(Sprite.explosion_vertical_down_last,
                            Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, timeExploding, 60);
                    gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y + flameWidth) * Sprite.SCALED_SIZE + MainGame.camY);
                } else {
                    down = false;
                }
            } else if (e.size() == 3) {
                if (e.get(2) instanceof Brick) {
                    Image i = Sprite.removeAnimation(Sprite.brick_exploded,
                                Sprite.brick_exploded1, Sprite.brick_exploded2, timeExploding, 60);
                    gc.drawImage(i, x * Sprite.SCALED_SIZE + MainGame.camX, (y + flameWidth) * Sprite.SCALED_SIZE + MainGame.camY);
                    ((Brick) e.get(2)).destroy();
                    down = false;
                }
            }
        } //down

        --timeExploding;
    }

    public int getTimeExploding() {
        return timeExploding;
    }

    public boolean isExist() {
        return exist;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void collideWithFlame(ArrayList<Bomb> bombs) {
        for (Bomb bomb : bombs) {
            bomb.left = true;
            bomb.right = true;
            bomb.top = true;
            bomb.down = true;
            if (bomb != this && bomb.exploding) {
                if (!exploding) {
                    for (int k = 1; k <= bomb.flameWidth; ++k) {
                        LinkedList<Entity> e;
                        if (bomb.left) {
                            e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x - k);
                            if (e.size() == 2) {
                                if (!(e.get(1) instanceof Item)) {
                                    bomb.left = false;
                                } else {
                                    if (x == bomb.x - k && y == bomb.y) this.timeBeforeExplode = 0;
                                }
                            } else if (e.size() == 1) {
                                if (e.get(0) instanceof Wall) {
                                    bomb.left = false;
                                } else {
                                    if (x == bomb.x - k && y == bomb.y) this.timeBeforeExplode = 0;
                                }
                            } else if (e.size() == 3) {
                                if (e.get(2) instanceof Brick) {
                                    bomb.left = false;
                                } else {
                                    if (x == bomb.x - k && y == bomb.y) this.timeBeforeExplode = 0;
                                }
                            }
                        } //left

                        if (bomb.right) {
                            e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x + k);
                            if (e.size() == 2) {
                                if (!(e.get(1) instanceof Item)) {
                                    bomb.right = false;
                                } else {
                                    if (x == bomb.x + k && y == bomb.y) this.timeBeforeExplode = 0;
                                }
                            } else if (e.size() == 1) {
                                if (e.get(0) instanceof Wall) {
                                    bomb.right = false;
                                } else {
                                    if (x == bomb.x + k && y == bomb.y) this.timeBeforeExplode = 0;
                                }
                            } else if (e.size() == 3) {
                                if (e.get(2) instanceof Brick) {
                                    bomb.right = false;
                                } else {
                                    if (x == bomb.x + k && y == bomb.y) this.timeBeforeExplode = 0;
                                }
                            }
                        } //right

                        if (bomb.top) {
                            e = MainGame.map.get((y - k) * MainGame.COLUMN[MainGame.level] + x);
                            if (e.size() == 2) {
                                if (!(e.get(1) instanceof Item)) {
                                    bomb.top = false;
                                } else {
                                    if (x == bomb.x && y == bomb.y - k) this.timeBeforeExplode = 0;
                                }
                            } else if (e.size() == 1) {
                                if (e.get(0) instanceof Wall) {
                                    bomb.top = false;
                                } else {
                                    if (x == bomb.x && y == bomb.y - k) this.timeBeforeExplode = 0;
                                }
                            } else if (e.size() == 3) {
                                if (e.get(2) instanceof Brick) {
                                    bomb.top = false;
                                } else {
                                    if (x == bomb.x && y == bomb.y - k) this.timeBeforeExplode = 0;
                                }
                            }
                        } //top

                        if (bomb.down) {
                            e = MainGame.map.get((y + k) * MainGame.COLUMN[MainGame.level] + x);
                            if (e.size() == 2) {
                                if (!(e.get(1) instanceof Item)) {
                                    bomb.down = false;
                                } else {
                                    if (x == bomb.x && y == bomb.y + k) this.timeBeforeExplode = 0;
                                }
                            } else if (e.size() == 1) {
                                if (e.get(0) instanceof Wall) {
                                    bomb.down = false;
                                } else {
                                    if (x == bomb.x && y == bomb.y + k) this.timeBeforeExplode = 0;
                                }
                            } else if (e.size() == 3) {
                                if (e.get(2) instanceof Brick) {
                                    bomb.down = false;
                                } else {
                                    if (x == bomb.x && y == bomb.y + k) this.timeBeforeExplode = 0;
                                }
                            }
                        } //down
                    }
                } else {
                    return;
                }
            }
        }
    }
}
