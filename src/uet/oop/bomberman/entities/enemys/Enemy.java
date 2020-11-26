package uet.oop.bomberman.entities.enemys;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.players.Bomber;
import uet.oop.bomberman.scenes.MainGame;
import uet.oop.bomberman.entities.players.Bomb;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tiles.Brick;
import uet.oop.bomberman.entities.tiles.Portal;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Enemy extends Character {
    protected int timeBetweenChangeDirection = 200;
    protected int timeBeforeRemove = 60;
    private boolean scored = false;

    public Enemy(int x, int y, Image img) {
        super(x, y, img);
        posX = x * Sprite.SCALED_SIZE;
        posY = y * Sprite.SCALED_SIZE;
        timeAfterDead = 120;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored() {
        this.scored = true;
    }

    @Override
    public void update() {
        x = (int) posX / Sprite.SCALED_SIZE;
        y = (int) posY / Sprite.SCALED_SIZE;
        if (MainGame.bombers.size() > 0) collideWithFlame(MainGame.bombers.get(0).getBombs());
        animate();
        if (alive) {
            moving();
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        if (alive) {
            chooseImg();
        } else {
            afterDead();
            if (timeAfterDead <= 0) {
                --timeBeforeRemove;
            }
        }
        if (timeAfterDead > 0) {
            gc.drawImage(img, posX + MainGame.camX, posY + MainGame.camY);
        }
    }

    @Override
    public void moving() {
        timeBetweenChangeDirection = 0;
        switch (direction) {
            case 1: //left
                if (!collide(MainGame.map, posX - speed, posY)) {
                    posX -= speed;
                    isMoving = true;
                } else {
                    direction = (int) Math.round(Math.random() * 100) % 4;
                }
                break;
            case 3: //right
                if (!collide(MainGame.map, posX + speed, posY)) {
                    posX += speed;
                    isMoving = true;
                } else {
                    direction = (int) Math.round(Math.random() * 100) % 4;
                }
                break;
            case 0: //up
                if (!collide(MainGame.map, posX, posY - speed)) {
                    posY -= speed;
                    isMoving = true;
                } else {
                    direction = (int) Math.round(Math.random() * 100) % 4;
                }
                break;
            case 2: //down
                if (!collide(MainGame.map, posX, posY + speed)) {
                    posY += speed;
                    isMoving = true;
                } else {
                    direction = (int) Math.round(Math.random() * 100) % 4;
                }
                break;
            default:
                isMoving = false;
                break;
        }
    }

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
        if (MainGame.bombers.size() > 0 && collideWithBombs(posX, posY, MainGame.bombers.get(0).getBombs())) collide = true;
        return collide;
    }

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
            if (e.get(1) instanceof Brick) {
                return checkCollideInMap(objX, objY, posX, posY);
            } else if (e.get(1) instanceof Portal) {
                return checkCollideInMap(objX, objY, posX, posY);
            }
        } else if (e.size() == 3) {
            double objX = e.get(2).getX() * Sprite.SCALED_SIZE;
            double objY = e.get(2).getY() * Sprite.SCALED_SIZE;
            return checkCollideInMap(objX, objY, posX, posY);
        }
        return false;
    }

    protected boolean checkCollideInMap(double objX, double objY, double posX, double posY) {
        return posX < objX + Sprite.SCALED_SIZE &&
                posX + Sprite.SCALED_SIZE > objX &&
                posY < objY + Sprite.SCALED_SIZE &&
                posY + Sprite.SCALED_SIZE > objY;
    }

    protected boolean collideWithBombs(double posX, double posY, ArrayList<Bomb> bombs) {
        boolean collide = false;
        for (Bomb bomb : bombs) {
            double objX = bomb.getX() * Sprite.SCALED_SIZE;
            double objY = bomb.getY() * Sprite.SCALED_SIZE;
            if (checkCollideInMap(objX, objY, posX, posY)) {
                collide = true;
            };
        }
        return collide;
    }

    protected void collideWithFlame(ArrayList<Bomb> bombs) {
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

    @Override
    protected void afterDead() {
        img = Sprite.removeAnimation(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, timeAfterDead, 90);
    }

    public int getTimeBeforeRemove() {
        return timeBeforeRemove;
    }
}
