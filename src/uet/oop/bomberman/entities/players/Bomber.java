package uet.oop.bomberman.entities.players;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemys.Doll;
import uet.oop.bomberman.entities.tiles.Grass;
import uet.oop.bomberman.scenes.MainGame;
import uet.oop.bomberman.entities.enemys.Enemy;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Portal;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.scenes.SceneLib;
import uet.oop.bomberman.scenes.Temp;
import uet.oop.bomberman.sounds.SoundLib;

import java.util.ArrayList;
import java.util.LinkedList;


public class Bomber extends Character {
    public int playerID;
    public int bombCount;
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private boolean bombBelowBomber;
    private Bomb bombCollide;
    public int flameWidth;

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean space;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        speed = 1f;
        posX = x * Sprite.SCALED_SIZE;
        posY = y * Sprite.SCALED_SIZE;
        direction = 3;
        bombBelowBomber = false;
        bombCount = 1;
        flameWidth = 1;
    }

    public Bomber(int x, int y, Image img, int playerID) {
        super(x, y, img);
        this.playerID = playerID;
        speed = 1f;
        posX = x * Sprite.SCALED_SIZE;
        posY = y * Sprite.SCALED_SIZE;
        direction = 3;
        bombBelowBomber = false;
        bombCount = 1;
        flameWidth = 1;

    }

    @Override
    public void moving() {
        if (left) {
            if (!collide(MainGame.map, posX - speed, posY)) posX -= speed;
            direction = 1;
            isMoving = true;
        }
        if (right) {
            if (!collide(MainGame.map, posX + speed, posY)) posX += speed;
            direction = 3;
            isMoving = true;
        }
        if (up) {
            if (!collide(MainGame.map, posX, posY - speed)) posY -= speed;
            direction = 0;
            isMoving = true;
        }
        if (down) {
            if (!collide(MainGame.map, posX, posY + speed)) posY += speed;
            direction = 2;
            isMoving = true;
        }
        if (space && bombCount > 0 && !bombBelowBomber) {
            int xa = ((int) posX + Sprite.SCALED_SIZE / 2 - 5) / Sprite.SCALED_SIZE;
            int ya = ((int) posY + Sprite.SCALED_SIZE / 2 - 1) / Sprite.SCALED_SIZE;
            bombCollide = new Bomb(xa, ya, Sprite.bomb, playerID, flameWidth);
            bombs.add(bombCollide);
            bombBelowBomber = true;
            --bombCount;
            space = false;
            SoundLib.setBomb.play();
        }
        if (!down && !left && !right && !up) {
            isMoving = false;
        }
    }

    @Override
    public void update() {
        if (alive) {
            moving();
            chooseImg();
        } else {
            afterDead();
        }
        collideWithFlame();
        collideWithEnemy();
        animate();
        bombs.forEach(Bomb::update);
        bombExplode();
    }

    @Override
    public void chooseImg() {
        if (playerID == 0) {
            switch (direction) {
                case 0:
                    img = Sprite.player_up;
                    if (isMoving) {
                        img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 30);
                    }
                    break;
                case 1:
                    img = Sprite.player_left;
                    if (isMoving) {
                        img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 30);
                    }
                    break;
                case 2:
                    img = Sprite.player_down;
                    if (isMoving) {
                        img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 30);
                    }
                    break;
                default:
                    img = Sprite.player_right;
                    if (isMoving) {
                        img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 30);
                    }
                    break;
            }
        } else if (playerID == 1) {
            switch (direction) {
                case 0:
                    img = Sprite.player2_up;
                    if (isMoving) {
                        img = Sprite.movingSprite(Sprite.player2_up_1, Sprite.player2_up_2, animate, 30);
                    }
                    break;
                case 1:
                    img = Sprite.player2_left;
                    if (isMoving) {
                        img = Sprite.movingSprite(Sprite.player2_left_1, Sprite.player2_left_2, animate, 30);
                    }
                    break;
                case 2:
                    img = Sprite.player2_down;
                    if (isMoving) {
                        img = Sprite.movingSprite(Sprite.player2_down_1, Sprite.player2_down_2, animate, 30);
                    }
                    break;
                default:
                    img = Sprite.player2_right;
                    if (isMoving) {
                        img = Sprite.movingSprite(Sprite.player2_right_1, Sprite.player2_right_2, animate, 30);
                    }
                    break;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (bombCollide != null) {
            if (bombCollide.getTimeExploding() <= 0 && !bombCollide.isExist()) {
                bombCollide = null;
            }
        }
        if (MainGame.chooseGamePlay == MainGame.Gameplay.PVE) {
            if (posX > 1.0 * Sprite.SCALED_SIZE * BombermanGame.WIDTH / 2 &&
                    posX < 1.0 * Sprite.SCALED_SIZE * (MainGame.COLUMN[MainGame.level] - BombermanGame.WIDTH / 2)) {
                gc.drawImage(img, 1.0 * Sprite.SCALED_SIZE * BombermanGame.WIDTH / 2, posY - 4 + MainGame.camY);
                MainGame.camX = 0 - posX + 1.0 * Sprite.SCALED_SIZE * BombermanGame.WIDTH / 2;
            } else if (posX >= 1.0 * Sprite.SCALED_SIZE * (MainGame.COLUMN[MainGame.level] - BombermanGame.WIDTH / 2)) {
                gc.drawImage(img, posX - 1.0 * Sprite.SCALED_SIZE * (MainGame.COLUMN[MainGame.level] - BombermanGame.WIDTH), posY - 4 + MainGame.camY);
                MainGame.camX = 0 - 1.0 * Sprite.SCALED_SIZE * (MainGame.COLUMN[MainGame.level] - BombermanGame.WIDTH);
            } else {
                gc.drawImage(img, posX, posY - 4 + MainGame.camY);
                MainGame.camX = 0;
            }
        } else if (MainGame.chooseGamePlay == MainGame.Gameplay.PVP_OFF ||
                MainGame.chooseGamePlay == MainGame.Gameplay.PVP_ONL) {
            MainGame.camX = 0;
            gc.drawImage(img, posX, posY - 4 + MainGame.camY);
        }
    }

    @Override
    protected void afterDead() {
        if (playerID == 0) {
            img = Sprite.removeAnimation(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, timeAfterDead, 90);
        } else if (playerID == 1) {
            img = Sprite.removeAnimation(Sprite.player2_dead1, Sprite.player2_dead2, Sprite.player2_dead3, timeAfterDead, 90);
        }
        --timeAfterDead;
    }

    private boolean collide(ArrayList<LinkedList<Entity>> map, double posX, double posY) {
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
        if (collideWithBombs(posX, posY)) {
            collide = true;
        }
        return collide;
    }

    private boolean collideInMap(ArrayList<LinkedList<Entity>> map, int i, int j, double posX, double posY) {
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
            if (e.get(1) instanceof Brick) {
                return checkCollideInMap(objX, objY, posX, posY);
            } else if (e.get(1) instanceof Portal) {
                boolean res = checkCollideInMap(objX, objY, posX, posY);
                if (res && MainGame.enemies.isEmpty()) {
                    if (MainGame.level + 1 == MainGame.MAX_LEVEL_PVE) {
                        Temp.win = true;
                        SceneLib.switchToPveEndingScene();
                    }
                    else {
                        SceneLib.switchToStageCompleteScene();
                    }
                    MainGame.paused();
                    MainGame.stop();
                } else {
                    return res;
                }
            } else if (e.get(1) instanceof Item) {
                collideWithItem(e, objX, objY);
            }
        } else if (e.size() == 3) {
            double objX = e.get(2).getX() * Sprite.SCALED_SIZE;
            double objY = e.get(2).getY() * Sprite.SCALED_SIZE;
            return checkCollideInMap(objX, objY, posX, posY);
        }
        return false;
    }

    private boolean checkCollideInMap(double objX, double objY, double posX, double posY) {
        return posX < objX + Sprite.SCALED_SIZE &&
                posX + Sprite.SCALED_SIZE - 10 > objX &&
                posY < objY + Sprite.SCALED_SIZE &&
                posY + Sprite.SCALED_SIZE - 4 > objY;
    }

    private boolean collideWithBombs(double posX, double posY) {
        boolean collide = false;
        double objX;
        double objY;
        if (bombCollide != null) {
            collide = false;
            objX = bombCollide.getX() * Sprite.SCALED_SIZE;
            objY = bombCollide.getY() * Sprite.SCALED_SIZE;
            if (posX < objX + Sprite.SCALED_SIZE &&
                    posX + Sprite.SCALED_SIZE - 10 > objX &&
                    posY < objY + Sprite.SCALED_SIZE &&
                    posY + Sprite.SCALED_SIZE - 4 > objY) {
                if (!bombBelowBomber) {
                    collide = true;
                }
            } else {
                bombBelowBomber = false;
            }
        }
        for (Bomber bomber : MainGame.bombers) {
            ArrayList<Bomb> bombs = bomber.getBombs();
            for (Bomb bomb : bombs) {
                if (bomb != bombCollide) {
                    objX = bomb.getX() * Sprite.SCALED_SIZE;
                    objY = bomb.getY() * Sprite.SCALED_SIZE;
                    if (checkCollideInMap(objX, objY, posX, posY)) {
                        collide = true;
                    }
                }
            }
        }
        return collide;
    }

    private void collideWithFlame() {
        boolean up = true;
        boolean down = true;
        boolean left = true;
        boolean right = true;
        for (Bomber bomber : MainGame.bombers) {
            for (Bomb bomb : bomber.getBombs()) {
                if (bomb.isExploding()) {
                    int x = bomb.getX();
                    int y = bomb.getY();
                    for (int k = 1; k <= bomb.getFlameWidth(); ++k) {
                        double objX = (bomb.getX() - k) * Sprite.SCALED_SIZE;
                        double objY = (bomb.getY() - k) * Sprite.SCALED_SIZE;
                        double objX0 = (bomb.getX() + k) * Sprite.SCALED_SIZE;
                        double objY0 = (bomb.getY() + k) * Sprite.SCALED_SIZE;
                        double objX1 = bomb.getX() * Sprite.SCALED_SIZE;
                        double objY1 = bomb.getY() * Sprite.SCALED_SIZE;
                        LinkedList<Entity> e;
                        if (left) {
                            e =  MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x - k);
                            if (e.size() == 2) {
                                left = false;
                            } else if (e.size() == 1) {
                                if (e.get(0) instanceof Wall) {
                                    left = false;
                                }
                            } else if (e.size() == 3) {
                                if (e.get(2) instanceof Brick) {
                                    left = false;
                                }
                            }
                            if (left) {
                                if (checkCollideInMap(objX, objY1, posX, posY)) {
                                    kill();
                                    return;
                                }
                            }
                        } //left

                        if (right) {
                            e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x + k);
                            if (e.size() == 2) {
                                right = false;
                            } else if (e.size() == 1) {
                                if (e.get(0) instanceof Wall) {
                                    right = false;
                                }
                            } else if (e.size() == 3) {
                                if (e.get(2) instanceof Brick) {
                                    right = false;
                                }
                            }
                            if (checkCollideInMap(objX0, objY1, posX, posY)) {
                                kill();
                                return;
                            }
                        } //right

                        if (up) {
                            e = MainGame.map.get((y - k) * MainGame.COLUMN[MainGame.level] + x);
                            if (e.size() == 2) {
                                up = false;
                            } else if (e.size() == 1) {
                                if (e.get(0) instanceof Wall) {
                                    up = false;
                                }
                            } else if (e.size() == 3) {
                                if (e.get(2) instanceof Brick) {
                                    up = false;
                                }
                            }
                            if (checkCollideInMap(objX1, objY, posX, posY)) {
                                kill();
                                return;
                            }
                        } //up

                        if (down) {
                            e = MainGame.map.get((y + k) * MainGame.COLUMN[MainGame.level] + x);
                            if (e.size() == 2) {
                                down = false;
                            } else if (e.size() == 1) {
                                if (e.get(0) instanceof Wall) {
                                    down = false;
                                }
                            } else if (e.size() == 3) {
                                if (e.get(2) instanceof Brick) {
                                    down = false;
                                }
                            }
                            if (checkCollideInMap(objX1, objY0, posX, posY)) {
                                kill();
                                return;
                            }
                        } //down
                        if (checkCollideInMap(objX1, objY1, posX, posY)) {
                            kill();
                            return;
                        }
                    }
                }
            }
        }
    }

    public void collideWithItem(LinkedList<Entity> e, double objX, double objY) {
        if (e.get(1) instanceof BombItem) {
            if (checkCollideInMap(objX, objY, posX, posY)) {
                if (!SoundLib.item.isPlaying()) {
                    SoundLib.item.play();
                }
                ++bombCount;
                e.remove(1);
            }
        } else if (e.get(1) instanceof SpeedItem) {
            if (checkCollideInMap(objX, objY, posX, posY)) {
                if (!SoundLib.item.isPlaying()) {
                    SoundLib.item.play();
                }
                speed += 0.1;
                e.remove(1);
            }
        } else if (e.get(1) instanceof FlameItem) {
            if (checkCollideInMap(objX, objY, posX, posY)) {
                if (!SoundLib.item.isPlaying()) {
                    SoundLib.item.play();
                }
                ++flameWidth;
                e.remove(1);
            }
        }
    }

    public void collideWithEnemy() {
        for (Enemy enemy : MainGame.enemies) {
            if (enemy.isAlive()) {
                if (checkCollideInMap(enemy.getPosX(), enemy.getPosY(), posX, posY)) {
                    kill();
                    return;
                }
                if (enemy instanceof Doll) {
                    if (enemy.getTimeAfterDead() <= 60) {
                        int k = 1;
                        while (true){
                            double objX = (enemy.getX() - k) * Sprite.SCALED_SIZE;
                            double objY = (enemy.getY() - k) * Sprite.SCALED_SIZE;
                            double objX0 = (enemy.getX() + k) * Sprite.SCALED_SIZE;
                            double objY0 = (enemy.getY() + k) * Sprite.SCALED_SIZE;
                            double objX1 = enemy.getX() * Sprite.SCALED_SIZE;
                            double objY1 = enemy.getY() * Sprite.SCALED_SIZE;
                            LinkedList<Entity> e;
                            if (left) {
                                e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x - k);
                                if (e.size() == 2) {
                                    left = false;
                                } else if (e.size() == 1) {
                                    if (e.get(0) instanceof Wall) {
                                        left = false;
                                    }
                                } else if (e.size() == 3) {
                                    if (e.get(2) instanceof Brick) {
                                        left = false;
                                    }
                                }
                                if (left) {
                                    if (checkCollideInMap(objX, objY1, posX, posY)) {
                                        kill();
                                        return;
                                    }
                                }
                            } //left

                            if (right) {
                                e = MainGame.map.get(y * MainGame.COLUMN[MainGame.level] + x + k);
                                if (e.size() == 2) {
                                    right = false;
                                } else if (e.size() == 1) {
                                    if (e.get(0) instanceof Wall) {
                                        right = false;
                                    }
                                } else if (e.size() == 3) {
                                    if (e.get(2) instanceof Brick) {
                                        right = false;
                                    }
                                }
                                if (checkCollideInMap(objX0, objY1, posX, posY)) {
                                    kill();
                                    return;
                                }
                            } //right

                            if (up) {
                                e = MainGame.map.get((y - k) * MainGame.COLUMN[MainGame.level] + x);
                                if (e.size() == 2) {
                                    up = false;
                                } else if (e.size() == 1) {
                                    if (e.get(0) instanceof Wall) {
                                        up = false;
                                    }
                                } else if (e.size() == 3) {
                                    if (e.get(2) instanceof Brick) {
                                        up = false;
                                    }
                                }
                                if (checkCollideInMap(objX1, objY, posX, posY)) {
                                    kill();
                                    return;
                                }
                            } //up

                            if (down) {
                                e = MainGame.map.get((y + k) * MainGame.COLUMN[MainGame.level] + x);
                                if (e.size() == 2) {
                                    down = false;
                                } else if (e.size() == 1) {
                                    if (e.get(0) instanceof Wall) {
                                        down = false;
                                    }
                                } else if (e.size() == 3) {
                                    if (e.get(2) instanceof Brick) {
                                        down = false;
                                    }
                                }
                                if (checkCollideInMap(objX1, objY0, posX, posY)) {
                                    kill();
                                    return;
                                }
                            } //down
                            if (checkCollideInMap(objX1, objY1, posX, posY)) {
                                kill();
                                return;
                            }
                            if (!left && !right && !up && !down) {
                                break;
                            }
                            ++k;
                        }
                    }
                }
            }
        }
    }

    public void bombExplode() {
        bombs.forEach(bomb -> bomb.collideWithFlame(bombs));
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }
}
