package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import engine.Bird;
import engine.Drawable;
import engine.Game;
import engine.Tube;
import util.ImageLoader;

public class SwingGamescreen extends JPanel {
	
	private static final Font SCORE_FONT = new Font("Consolas", Font.BOLD, 50);
	private BufferedImage background;
	private ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	private boolean showHitboxes = false;
	private int score = 0;
	
	public SwingGamescreen() {
		super();
		setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));

		background = ImageLoader.loadImageFromRes("bg.png");
	}
	
	public void addDrawables( ArrayList<Drawable> list) {
		for (int i = 0; i < list.size(); i++) {
			drawables.add(list.get(i));
		}
	}
	
	public void addDrawable ( Drawable d ) {
		drawables.add(d);
	}
	public void removeDrawable (Drawable d) {
		drawables.remove(d);
	}
	public void removeAllDrawables () {
		drawables = new ArrayList<Drawable>();
		score = 0;
	}
	
	public void setScore (int score) {
		this.score = score;
	}
	
	
	private synchronized  void drawAllDrawables( Graphics2D g ) {
		Drawable bird = null;
		for (Drawable d : drawables) {
			if ( !d.getClass().getSimpleName().equals("Bird"))  {
				g.drawImage(d.getBufferedImage(), d.getPoint().x, d.getPoint().y, null);
				if ( showHitboxes == true ) {				// Draws Tube Hitboxes
						Tube t = ( Tube ) d;
						g.setColor(Color.RED);
						Rectangle[] rects = t.getRects();
						drawRect ( g, rects[0]);
						drawRect ( g, rects[1]);
				}
			} else {
				bird = d;
			}
		}
		if (drawables.size() != 0 && bird != null) {
			g.drawImage(bird.getBufferedImage(), bird.getPoint().x, bird.getPoint().y, null);
			if ( showHitboxes ) {
				g.setColor(Color.RED);
				g.drawOval(bird.getPoint().x, bird.getPoint().y, Bird.RADIUS * 2, Bird.RADIUS * 2);
			}
		}
	}
	
	private void drawRect( Graphics2D g, Rectangle r) {
		g.drawRect(r.x, r.y, (int) r.getWidth(), (int) r.getHeight());
	}
	
	public void paintComponent ( Graphics gg) {
		Graphics2D g = ( Graphics2D ) gg;
		g.drawImage(background, 0, 0, null);
		drawAllDrawables(g);
		g.setFont(SCORE_FONT);
		g.setColor(Color.BLACK);
		g.drawString(String.format( "%03d",  score), 10, 50);
		g.dispose();
	}
}
