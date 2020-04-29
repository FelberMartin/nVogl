package graphics;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import engine.Drawable;
import engine.Game;


public class SwingView implements KeyListener, WindowListener{
	
	public static Dimension BUTTON_DIMENSION = new Dimension(300, 80);
	public static Font LABEL_FONT = new Font( "Arial", Font.BOLD, 18);

	
	private JFrame frame;
	private SwingStartscreen sc;
	private SwingGamescreen gc;
	private SwingEndscreen ec;
	
	private Game game = null;
	
	public SwingView() {
		setup();
	}
	
	public void setup() {
			
		frame = new JFrame("FlappyBird");
		frame.addKeyListener(this);
//		frame.setSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(this);
		
		sc = new SwingStartscreen(this);
		sc.setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
		frame.add(sc);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	public void addGameAsListener(Game g) {
		this.game = g;
	}
	
	public void addDrawable( Drawable d) {
		gc.addDrawable(d);
	}
	public void removeDrawable ( Drawable d) {
		gc.removeDrawable(d);
	}
	public void removeAllDrawables () {
		gc.removeAllDrawables();
	}
	
	public void startGame() {
		frame.remove(sc);
		frame.add(gc);
	}
	
	public void playButtonPressed() {
		gc = new SwingGamescreen();
		game.startGame(sc.getUsername());
		frame.remove(sc);
		frame.add(gc);
//		gc.addKeyListener(this);
		frame.requestFocus();
		frame.setVisible(true);
	}
	
	public void changeToEndScreen ( String playername, int score, int highscore) {
		frame.remove(gc);
		ec = new SwingEndscreen(this, score, highscore);
		frame.add(ec);
		frame.requestFocus();
		frame.setVisible(true);
	}
	
	public void changeToStartScreen () {
	 	removeAllDrawables();
	 	ec.setVisible(false);
		frame.remove(ec);
		frame.add(sc);
		frame.requestFocus();
		frame.setVisible(true);
	}
	
	public void changeBackToGameScreen ( ) {
		frame.remove(ec);
		gc.removeAllDrawables();
		gc = new SwingGamescreen();
		gc.addKeyListener(this);
		frame.add(gc);
		game.startGameWithLastName();
		frame.requestFocus();
		frame.setVisible(true);
	}
	
	public HashMap<String, Integer> getHighscores() {
		return game.getHighscores();
	}
	
	
	public void requestRepaint() {
		frame.repaint();
	}
	
	public void setScore(int score) {
		gc.setScore(score);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if ( arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			game.jump();							// JUMP
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {	 
		if ( arg0.getKeyCode() == KeyEvent.VK_ESCAPE ) {											// ESCAPE
			game.endGame();
			System.exit(1);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(1);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}


	
}
