package engine;

import java.awt.Point;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class Bird implements Drawable {
	
	public static final int RADIUS = 50;
	
	public static final float GRAVITY = 0.4f;
	public static final float JUMP_CONST = -8.0f;
	public static final float CRASH_CONST = - 15.0f;
	public static final int X = (int) (Game.WINDOW_WIDTH * 0.3f);
	
	private float yPos = Game.WINDOW_HEIGHT * 0.2f;
	private float velocity = 0;
	
	private static final BufferedImage img = Game.BIRD_IMG;
	
	public Bird() {
	} 
	
	public void applyG() {
		yPos = yPos + velocity;
		velocity += GRAVITY;
		
	}
	
	public void jump() {
		velocity = JUMP_CONST;
	}
	public void crash() {
		velocity = CRASH_CONST;
	}
	
	public float getY() {
		return yPos;
	}

	@Override
	public BufferedImage getBufferedImage() {
		return img;
	}

	@Override
	public Point getPoint() {
		return new Point(X - RADIUS, (int) (yPos - RADIUS));
	}
}
