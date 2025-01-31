package uet.oop.bomberman.graphics;

import javafx.scene.image.*;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class Sprite {

	public static final int DEFAULT_SIZE = 16;
	public static final int SCALED_SIZE = DEFAULT_SIZE * 2;
    private static final int TRANSPARENT_COLOR = 0xffff00ff;
	public final int SIZE;
	private int _x, _y;
	public int[] _pixels;
	protected int _realWidth;
	protected int _realHeight;
	private SpriteSheet _sheet;

	/*
	|--------------------------------------------------------------------------
	| Board sprites
	|--------------------------------------------------------------------------
	 */
	public static Image grass = new Sprite(DEFAULT_SIZE, 6, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image brick = new Sprite(DEFAULT_SIZE, 7, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image wall = new Sprite(DEFAULT_SIZE, 5, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image portal = new Sprite(DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 14, 14).getFxImage();

	/*
	|--------------------------------------------------------------------------
	| Bomber Sprites
	|--------------------------------------------------------------------------
	 */
	public static Image player_up = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player_down = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player_left = new Sprite(DEFAULT_SIZE, 3, 0, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player_right = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.tiles, 12, 15).getFxImage();

	public static Image player_up_1 = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player_up_2 = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.tiles, 12, 15).getFxImage();

	public static Image player_down_1 = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player_down_2 = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.tiles, 12, 15).getFxImage();

	public static Image player_left_1 = new Sprite(DEFAULT_SIZE, 3, 1, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player_left_2 = new Sprite(DEFAULT_SIZE, 3, 2, SpriteSheet.tiles, 12 ,15).getFxImage();

	public static Image player_right_1 = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player_right_2 = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.tiles, 12, 15).getFxImage();

	public static Image player_dead1 = new Sprite(DEFAULT_SIZE, 4, 2, SpriteSheet.tiles, 14, 16).getFxImage();
	public static Image player_dead2 = new Sprite(DEFAULT_SIZE, 5, 2, SpriteSheet.tiles, 13, 15).getFxImage();
	public static Image player_dead3 = new Sprite(DEFAULT_SIZE, 6, 2, SpriteSheet.tiles, 16, 16).getFxImage();

	//Bomber 2
	public static Image player2_up = new Sprite(DEFAULT_SIZE, 8, 9, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player2_down = new Sprite(DEFAULT_SIZE, 10, 9, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player2_left = new Sprite(DEFAULT_SIZE, 11, 9, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player2_right = new Sprite(DEFAULT_SIZE, 9, 9, SpriteSheet.tiles, 12, 15).getFxImage();

	public static Image player2_up_1 = new Sprite(DEFAULT_SIZE, 8, 10, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player2_up_2 = new Sprite(DEFAULT_SIZE, 8, 11, SpriteSheet.tiles, 12, 15).getFxImage();

	public static Image player2_down_1 = new Sprite(DEFAULT_SIZE, 10, 10, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player2_down_2 = new Sprite(DEFAULT_SIZE, 10, 11, SpriteSheet.tiles, 12, 15).getFxImage();

	public static Image player2_left_1 = new Sprite(DEFAULT_SIZE, 11, 10, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player2_left_2 = new Sprite(DEFAULT_SIZE, 11, 11, SpriteSheet.tiles, 12 ,15).getFxImage();

	public static Image player2_right_1 = new Sprite(DEFAULT_SIZE, 9, 10, SpriteSheet.tiles, 12, 15).getFxImage();
	public static Image player2_right_2 = new Sprite(DEFAULT_SIZE, 9, 11, SpriteSheet.tiles, 12, 15).getFxImage();

	public static Image player2_dead1 = new Sprite(DEFAULT_SIZE, 12, 11, SpriteSheet.tiles, 14, 16).getFxImage();
	public static Image player2_dead2 = new Sprite(DEFAULT_SIZE, 13, 11, SpriteSheet.tiles, 13, 15).getFxImage();
	public static Image player2_dead3 = new Sprite(DEFAULT_SIZE, 14, 11, SpriteSheet.tiles, 16, 16).getFxImage();

	/*
	|--------------------------------------------------------------------------
	| Character
	|--------------------------------------------------------------------------
	 */
	//BALLOM
	public static Image balloom_left1 = new Sprite(DEFAULT_SIZE, 9, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image balloom_left2 = new Sprite(DEFAULT_SIZE, 9, 1, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image balloom_left3 = new Sprite(DEFAULT_SIZE, 9, 2, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image balloom_right1 = new Sprite(DEFAULT_SIZE, 10, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image balloom_right2 = new Sprite(DEFAULT_SIZE, 10, 1, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image balloom_right3 = new Sprite(DEFAULT_SIZE, 10, 2, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image balloom_dead = new Sprite(DEFAULT_SIZE, 9, 3, SpriteSheet.tiles, 16, 16).getFxImage();

	//ONEAL
	public static Image oneal_left1 = new Sprite(DEFAULT_SIZE, 11, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image oneal_left2 = new Sprite(DEFAULT_SIZE, 11, 1, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image oneal_left3 = new Sprite(DEFAULT_SIZE, 11, 2, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image oneal_right1 = new Sprite(DEFAULT_SIZE, 12, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image oneal_right2 = new Sprite(DEFAULT_SIZE, 12, 1, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image oneal_right3 = new Sprite(DEFAULT_SIZE, 12, 2, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image oneal_dead = new Sprite(DEFAULT_SIZE, 11, 3, SpriteSheet.tiles, 16, 16).getFxImage();

	//Doll
	public static Image doll_left1 = new Sprite(DEFAULT_SIZE, 13, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image doll_left2 = new Sprite(DEFAULT_SIZE, 13, 1, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image doll_left3 = new Sprite(DEFAULT_SIZE, 13, 2, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image doll_right1 = new Sprite(DEFAULT_SIZE, 14, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image doll_right2 = new Sprite(DEFAULT_SIZE, 14, 1, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image doll_right3 = new Sprite(DEFAULT_SIZE, 14, 2, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image doll_dead = new Sprite(DEFAULT_SIZE, 13, 3, SpriteSheet.tiles, 16, 16).getFxImage();

	//Minvo
	public static Image minvo_left1 = new Sprite(DEFAULT_SIZE, 8, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image minvo_left2 = new Sprite(DEFAULT_SIZE, 8, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image minvo_left3 = new Sprite(DEFAULT_SIZE, 8, 7, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image minvo_right1 = new Sprite(DEFAULT_SIZE, 9, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image minvo_right2 = new Sprite(DEFAULT_SIZE, 9, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image minvo_right3 = new Sprite(DEFAULT_SIZE, 9, 7, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image minvo_dead = new Sprite(DEFAULT_SIZE, 8, 8, SpriteSheet.tiles, 16, 16).getFxImage();

	//Ovape
	public static Image ovape_left1 = new Sprite(DEFAULT_SIZE, 6, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image ovape_left2 = new Sprite(DEFAULT_SIZE, 6, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image ovape_left3 = new Sprite(DEFAULT_SIZE, 6, 7, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image ovape_right1 = new Sprite(DEFAULT_SIZE, 7, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image ovape_right2 = new Sprite(DEFAULT_SIZE, 7, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image ovape_right3 = new Sprite(DEFAULT_SIZE, 7, 7, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image ovape_dead = new Sprite(DEFAULT_SIZE, 6, 8, SpriteSheet.tiles, 16, 16).getFxImage();

	//Kondoria
	public static Image kondoria_left1 = new Sprite(DEFAULT_SIZE, 10, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image kondoria_left2 = new Sprite(DEFAULT_SIZE, 10, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image kondoria_left3 = new Sprite(DEFAULT_SIZE, 10, 7, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image kondoria_right1 = new Sprite(DEFAULT_SIZE, 11, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image kondoria_right2 = new Sprite(DEFAULT_SIZE, 11, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image kondoria_right3 = new Sprite(DEFAULT_SIZE, 11, 7, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image kondoria_dead = new Sprite(DEFAULT_SIZE, 10, 8, SpriteSheet.tiles, 16, 16).getFxImage();

	//Pontan
	public static Image pontan_left1 = new Sprite(DEFAULT_SIZE, 14, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_left2 = new Sprite(DEFAULT_SIZE, 14, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_left3 = new Sprite(DEFAULT_SIZE, 14, 7, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_right1 = new Sprite(DEFAULT_SIZE, 15, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_right2 = new Sprite(DEFAULT_SIZE, 15, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_right3 = new Sprite(DEFAULT_SIZE, 15, 7, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image pontan_dead = new Sprite(DEFAULT_SIZE, 14, 8, SpriteSheet.tiles, 16, 16).getFxImage();

	//Pontan Angry
	public static Image pontan_angry_left1 = new Sprite(DEFAULT_SIZE, 12, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_angry_left2 = new Sprite(DEFAULT_SIZE, 12, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_angry_left3 = new Sprite(DEFAULT_SIZE, 12, 7, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_angry_right1 = new Sprite(DEFAULT_SIZE, 13, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_angry_right2 = new Sprite(DEFAULT_SIZE, 13, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image pontan_angry_right3 = new Sprite(DEFAULT_SIZE, 13, 7, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image pontan_angry_dead = new Sprite(DEFAULT_SIZE, 12, 8, SpriteSheet.tiles, 16, 16).getFxImage();

	//ALL
	public static Image mob_dead1 = new Sprite(DEFAULT_SIZE, 15, 0, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image mob_dead2 = new Sprite(DEFAULT_SIZE, 15, 1, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image mob_dead3 = new Sprite(DEFAULT_SIZE, 15, 2, SpriteSheet.tiles, 16, 16).getFxImage();

	/*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */
	public static Image bomb = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.tiles, 15, 15).getFxImage();
	public static Image bomb_1 = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.tiles, 13, 15).getFxImage();
	public static Image bomb_2 = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.tiles, 12, 14).getFxImage();

	/*
	|--------------------------------------------------------------------------
	| FlameSegment Sprites
	|--------------------------------------------------------------------------
	 */
	public static Image bomb_exploded = new Sprite(DEFAULT_SIZE, 0, 4, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image bomb_exploded1 = new Sprite(DEFAULT_SIZE, 0, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image bomb_exploded2 = new Sprite(DEFAULT_SIZE, 0, 6, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image explosion_vertical = new Sprite(DEFAULT_SIZE, 1, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_vertical1 = new Sprite(DEFAULT_SIZE, 2, 5, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_vertical2 = new Sprite(DEFAULT_SIZE, 3, 5, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image explosion_horizontal = new Sprite(DEFAULT_SIZE, 1, 7, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_horizontal1 = new Sprite(DEFAULT_SIZE, 1, 8, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_horizontal2 = new Sprite(DEFAULT_SIZE, 1, 9, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image explosion_horizontal_left_last = new Sprite(DEFAULT_SIZE, 0, 7, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_horizontal_left_last1 = new Sprite(DEFAULT_SIZE, 0, 8, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_horizontal_left_last2 = new Sprite(DEFAULT_SIZE, 0, 9, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image explosion_horizontal_right_last = new Sprite(DEFAULT_SIZE, 2, 7, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_horizontal_right_last1 = new Sprite(DEFAULT_SIZE, 2, 8, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_horizontal_right_last2 = new Sprite(DEFAULT_SIZE, 2, 9, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image explosion_vertical_top_last = new Sprite(DEFAULT_SIZE, 1, 4, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_vertical_top_last1 = new Sprite(DEFAULT_SIZE, 2, 4, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_vertical_top_last2 = new Sprite(DEFAULT_SIZE, 3, 4, SpriteSheet.tiles, 16, 16).getFxImage();

	public static Image explosion_vertical_down_last = new Sprite(DEFAULT_SIZE, 1, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_vertical_down_last1 = new Sprite(DEFAULT_SIZE, 2, 6, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image explosion_vertical_down_last2 = new Sprite(DEFAULT_SIZE, 3, 6, SpriteSheet.tiles, 16, 16).getFxImage();

	/*
	|--------------------------------------------------------------------------
	| Brick FlameSegment
	|--------------------------------------------------------------------------
	 */
	public static Image brick_exploded = new Sprite(DEFAULT_SIZE, 7, 1, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image brick_exploded1 = new Sprite(DEFAULT_SIZE, 7, 2, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image brick_exploded2 = new Sprite(DEFAULT_SIZE, 7, 3, SpriteSheet.tiles, 16, 16).getFxImage();

	/*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */
	public static Image powerup_bombs = new Sprite(DEFAULT_SIZE, 0, 10, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image powerup_flames = new Sprite(DEFAULT_SIZE, 1, 10, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image powerup_speed = new Sprite(DEFAULT_SIZE, 2, 10, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image powerup_wallpass = new Sprite(DEFAULT_SIZE, 3, 10, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image powerup_detonator = new Sprite(DEFAULT_SIZE, 4, 10, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image powerup_bombpass = new Sprite(DEFAULT_SIZE, 5, 10, SpriteSheet.tiles, 16, 16).getFxImage();
	public static Image powerup_flamepass = new Sprite(DEFAULT_SIZE, 6, 10, SpriteSheet.tiles, 16, 16).getFxImage();

	public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		_x = x * SIZE;
		_y = y * SIZE;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
	}

	public Sprite(int size, int color) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				_pixels[x + y * SIZE] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.SIZE];
			}
		}
	}

	public static Image movingSprite(Image normal, Image x1, Image x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;

		if(calc < diff) {
			return normal;
		}

		if(calc < diff * 2) {
			return x1;
		}

		return x2;
	}

	public static Image movingSprite(Image x1, Image x2, int animate, int time) {
		int diff = time / 2;
		return (animate % time > diff) ? x1 : x2;
	}

	public static Image removeAnimation(Image normal, Image x1, Image x2, int animate, int time) {
		if (animate < time && animate >= time * 2 / 3) {
			return normal;
		} else if (animate < time * 2 / 3 && animate >= time/ 3) {
			return x1;
		} else return x2;
	}

	public int getSize() {
		return SIZE;
	}

	public int getPixel(int i) {
		return _pixels[i];
	}

	public Image getFxImage() {
        WritableImage wr = new WritableImage(SIZE, SIZE);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if ( _pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, _pixels[x + y * SIZE]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return resample(input, SCALED_SIZE / DEFAULT_SIZE);
    }

	private Image resample(Image input, int scaleFactor) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(
				W * S,
				H * S
		);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}
		return output;
	}

	public int get_realHeight() {
		return _realHeight;
	}

	public int get_realWidth() {
		return _realWidth;
	}
}
