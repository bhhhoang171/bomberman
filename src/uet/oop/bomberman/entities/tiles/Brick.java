package uet.oop.bomberman.entities.tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Tile {
    private boolean exist;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        exist = true;
    }

    @Override
    public void update() {

    }

    public void destroy() {
        exist = false;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (exist) {
            super.render(gc);
        }
    }

    public boolean isExist() {
        return exist;
    }
}
