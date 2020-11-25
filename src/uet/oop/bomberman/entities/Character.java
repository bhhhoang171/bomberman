package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class Character extends Entity {
    protected int direction;
    protected double speed;
    protected boolean alive = true;
    protected double posX;
    protected double posY;
    protected boolean isMoving = false;
    protected int animate = 0;
    protected int MAX_ANIMATE = 10000;
    protected int timeAfterDead = 90;

    public Character(int x, int y, Image img) {
        super(x, y, img);
    }

    public abstract void moving();

    @Override
    public abstract void update();
    public abstract void chooseImg();
    public void animate() {
        if(animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isMoving() {
        return isMoving;
    }

    protected abstract void afterDead();

    public int getTimeAfterDead() {
        return timeAfterDead;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
