package uet.oop.bomberman.entities.tiles;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Tile extends Entity {

    public Tile(int x, int y, Image img) {
        super(x, y, img);
    }

}
