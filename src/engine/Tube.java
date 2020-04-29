package engine;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import util.ImageLoader;
import util.ImageManipulation;

public class Tube implements Drawable{
	
	public static final int GAP_SIZE = 280;
	public static final int TUBE_WIDTH = 76;
	public static final int TUBES_DISTANCE = 300; // distance between the tubes in x direction
	public static final int GAP_SPACE_TO_BORDER = 80;
	public static final float TUBES_VELOCTIY = 2.0f;
	public static final float VELOCITY_PER_SCORE = 0.03f;
	
	public float xPos = Game.WINDOW_WIDTH;
	public boolean passed = false;
	private int yGap = getRndGapY();
	private int yTop;
	private Rectangle rectTop;
	private Rectangle rectBot;
	
	private static BufferedImage img = makeTwoTubes(Game.TUBE_IMG);
	
	public Tube() {
		rectTop = new Rectangle((int) xPos, 0, TUBE_WIDTH, yGap - 1);
		rectBot = new Rectangle((int) xPos, yGap + GAP_SIZE, TUBE_WIDTH, Game.WINDOW_HEIGHT - yGap - GAP_SIZE);
		if (img != null) {
			yTop = yGap + (int) (GAP_SIZE  / 2) - img.getHeight() / 2;
		}
	}
	
	private int getRndGapY() {
		int possible = Game.WINDOW_HEIGHT - 2 * GAP_SPACE_TO_BORDER - GAP_SIZE;
		int y = ( int ) ( Math.random() * possible + GAP_SPACE_TO_BORDER);
		return y;
	}
	
	
	private static BufferedImage makeTwoTubes(BufferedImage source) {
		BufferedImage bi = new BufferedImage(source.getWidth(), source.getHeight() * 2 + GAP_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = bi.getGraphics();
		g.drawImage(ImageManipulation.mirrowOnXAxis(source), 0, 0, null);
		g.drawImage(source, 0, source.getHeight() + GAP_SIZE, null);
		g.dispose();
		return bi;
	}
	
	
	public void move(int score) {
		xPos = xPos - TUBES_VELOCTIY - score * VELOCITY_PER_SCORE;
		rectTop.x = (int) xPos;
		rectBot.x = (int) xPos;
	}
	
	public Rectangle[] getRects() {
		Rectangle[] rects = new Rectangle[2];
		rects[0] = rectTop;
		rects[1] = rectBot;
		return rects;
	}

	@Override
	public BufferedImage getBufferedImage() {
		return img;
	}

	@Override
	public Point getPoint() {
		return new Point( (int) xPos, yTop);
	}
}