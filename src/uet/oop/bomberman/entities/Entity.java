package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.scenes.MainGame;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected int x;
    protected int y;
    protected Image img;

    public Entity(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x * Sprite.SCALED_SIZE + MainGame.camX,
                y * Sprite.SCALED_SIZE + MainGame.camY);
    }
    public abstract void update();

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
