package uet.oop.bomberman.entities.enemys;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.entities.players.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.tiles.Wall;
import uet.oop.bomberman.scenes.MainGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

public class Oneal extends Enemy {
    public static final int point = 200;
    private ArrayList<Integer> updateDirection = new ArrayList<>();
    private int[][] distances = new int[MainGame.ROW[MainGame.level]][MainGame.COLUMN[MainGame.level]];

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        isMoving = false;
        direction = 1;
        speed = 1f;
    }

    @Override
    public void moving() {
        updateDirection = new ArrayList<>();
        for (int i = 0; i < MainGame.ROW[MainGame.level]; ++i) {
            for (int j = 0; j < MainGame.COLUMN[MainGame.level]; ++j) {
                LinkedList<Entity> map = MainGame.map.get(i * MainGame.COLUMN[MainGame.level] + j);
                if (map.size() == 1) {
                    if (map.get(0) instanceof Wall) {
                        distances[i][j] = -1;
                    } else {
                        distances[i][j] = Integer.MAX_VALUE;
                    }
                } else if (map.size() == 2) {
                    if (!(map.get(0) instanceof Item)) {
                        distances[i][j] = -1;
                    } else {
                        distances[i][j] = Integer.MAX_VALUE;
                    }
                } else {
                    distances[i][j] = -1;
                }
            }
        }
        if (MainGame.bombers.size() > 0) {
            ArrayList<Bomb> bombs = MainGame.bombers.get(0).getBombs();
            for (Bomb bomb : bombs) {
                distances[bomb.getY()][bomb.getX()] = -1;
            }
        }
        x = (int) posX / Sprite.SCALED_SIZE;
        y = (int) posY / Sprite.SCALED_SIZE;
        if (MainGame.bombers.size() > 0 && shortestPath() > 0) {
            if (posX == this.x * Sprite.SCALED_SIZE && posY == this.y * Sprite.SCALED_SIZE) {
                direction = updateDirection.get(updateDirection.size() - 1);
            }
        }
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

    @Override
    public void chooseImg() {
        switch (direction) {
            case 0:
            case 1:
                img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 30);
                break;
            default:
                img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 30);
                break;
        }
    }

    private int shortestPath() {
        int bomberX = (int) MainGame.bombers.get(0).getPosX() / Sprite.SCALED_SIZE;
        int bomberY = (int) MainGame.bombers.get(0).getPosY() / Sprite.SCALED_SIZE;
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        q.add(new Pair<>(x, y));
        distances[y][x] = 0;
        while (!q.isEmpty()) {
            Pair<Integer, Integer> p = q.peek();
            q.remove();
            int xx = p.getKey();
            int yy = p.getValue();
            if (xx == bomberX && yy == bomberY) {
                for (int d = distances[bomberY][bomberX] - 1; d >= 0; --d) {
                    //up
                    if (yy - 1 >= 0 && distances[yy - 1][xx] == d) {
                        updateDirection.add(2);
                        --yy;
                        continue;
                    }
                    //down
                    if (yy + 1 < MainGame.ROW[MainGame.level] && distances[yy + 1][xx] == d) {
                        updateDirection.add(0);
                        ++yy;
                        continue;
                    }
                    //left
                    if (xx - 1 >= 0 && distances[yy][xx - 1] == d) {
                        updateDirection.add(3);
                        --xx;
                        continue;
                    }
                    //right
                    if (xx + 1 < MainGame.COLUMN[MainGame.level] && distances[yy][xx + 1] == d) {
                        updateDirection.add(1);
                        ++xx;
                    }
                }
                return distances[bomberY][bomberX];
            }
            //up
            if (yy - 1 >= 0 && distances[yy - 1][xx] == Integer.MAX_VALUE) {
                q.add(new Pair<>(xx, yy - 1));
                distances[yy - 1][xx] = distances[yy][xx] + 1;
            }
            //down
            if (yy + 1 < MainGame.ROW[MainGame.level] && distances[yy + 1][xx] == Integer.MAX_VALUE) {
                q.add(new Pair<>(xx, yy + 1));
                distances[yy + 1][xx] = distances[yy][xx] + 1;
            }
            //left
            if (xx - 1 >= 0 && distances[yy][xx - 1] == Integer.MAX_VALUE) {
                q.add(new Pair<>(xx - 1, yy));
                distances[yy][xx - 1] = distances[yy][xx] + 1;
            }
            //right
            if (xx + 1 < MainGame.COLUMN[MainGame.level] && distances[yy][xx + 1] == Integer.MAX_VALUE) {
                q.add(new Pair<>(xx + 1, yy));
                distances[yy][xx + 1] = distances[yy][xx] + 1;
            }
        }
        return -1;
    }

    @Override
    protected void afterDead() {
        if (timeAfterDead < 120 && timeAfterDead > 90) {
            img = Sprite.oneal_dead;
        } else {
            super.afterDead();
        }
        --timeAfterDead;
    }
}
