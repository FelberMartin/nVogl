package engine;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import database.Connection;
import graphics.SwingView;
import util.ImageLoader;

public class Game {

	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	public static final BufferedImage BIRD_IMG =  ImageLoader.loadImageFromRes("bird.png");
	public static final BufferedImage TUBE_IMG =  ImageLoader.loadImageFromRes("pipe.png");
	
	public static final float TUBES_VELOCITY = 2.0f;
	
	private Connection connection;
	private Bird bird;
	private ArrayList<Tube> tubes;
	private SwingView sView;
	
	private boolean running = true;
	private boolean crash = false;
	
	private String playername = "Empty";
	private int highscore = 0;
	private int score = 0;
	

	public Game() {
		connection = new Connection();
		sView = new SwingView();
		sView.addGameAsListener(this);
		
	}
	
	private void addAllDrawables() {
		sView.addDrawable(bird);
		for ( int i = 0; i < tubes.size(); i++) {
			sView.addDrawable(tubes.get(i));
		}
	}
	
	private void startGame() {
		crash = false;
		score = 0;
		bird = new Bird();
		tubes = new ArrayList<Tube>();
		addAllDrawables();
		
			startGameLoop();
		
	}
	
	
	public void endGame() {
		running = false;
		if ( highscore < score ) {
			highscore = score;
			connection.setHighscore(playername, highscore);
		}
		sView.changeToEndScreen(  playername, score ,highscore );
	}
	
	private void startGameLoop() {
		running = true;
		Thread gThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				gameloop();
			}
		});
		gThread.start();
	}
	
	private void gameloop() {

		long lastFpsTime = 0;
		int fps = 0;
		   long lastLoopTime = System.nanoTime();
		   final int TARGET_FPS = 60;
		   final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
		   // keep looping round until the game ends
		   while (running) {
		      // work out how long its been since the last update, this
		      // will be used to calculate how far the entities should
		      // move this loop

		      long now = System.nanoTime();
		      long updateLength = now - lastLoopTime;
		      lastLoopTime = now;
		      double delta = updateLength / ((double)OPTIMAL_TIME);

		      // update the frame counter
		      lastFpsTime += updateLength;
		      fps++;
		      
		      // update our FPS counter if a second has passed since
		      // we last recorded
		      if (lastFpsTime >= 1000000000)
		      {
//		         System.out.println("(FPS: "+fps+")");
		         lastFpsTime = 0;
		         fps = 0;
		      }
		      
		      // update the game logic
		      doGameUpdates();
		      
		      // draw everyting
		      sView.requestRepaint();
		      // we want each frame to take 10 milliseconds, to do this
		      // we've recorded when we started the frame. We add 10 milliseconds
		      // to this and then factor in the current time to give 
		      // us our final value to wait for
		      // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
		      try{
		     	 long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
		     	 if (timeout < 0) timeout = 0;
		     	 Thread.sleep(timeout);
		      } catch (Exception e) {
				e.printStackTrace();
		      } 	
		}
		   
	}
	
	private void doGameUpdates() {
		bird.applyG();
		moveTubes();
		checkCrash();
	}
	
	private void moveTubes() {
		for (Iterator<Tube> it = tubes.iterator(); it.hasNext();) {
			Tube t = it.next();
			t.move(score);
			
			if ( t.xPos + Tube.TUBE_WIDTH < Bird.X) {	// Sets Score if Tube passed by
				if ( t.passed == false && !crash ) {
					score++;
					sView.setScore(score);
					t.passed = true;
				}
			}
			
			if ( t.xPos + Tube.TUBE_WIDTH < 0) {	// Destroys Tubes outside the screen
				sView.removeDrawable(t);
				it.remove();				
			}
		}
		
		if ( tubes.size() == 0 || tubes.get(tubes.size() - 1).xPos < WINDOW_WIDTH - Tube.TUBES_DISTANCE) {	// Generates new Tube if there is no one OR the last one is to deep in the screen
//			System.out.println("Tube added, Size = " + tubes.size() );
//			if ( tubes.size() != 0) System.out.println( "xPos = " + tubes.get(0).xPos);
			Tube t = new Tube();
			tubes.add(t);		
			sView.addDrawable(t);
		}
	}
	
	
	private void checkCrash() {
		if ( bird.getY() + Bird.RADIUS > WINDOW_HEIGHT || bird.getY() - Bird.RADIUS < 0) {
			setCrash ( true );
			if ( bird.getY() - Bird.RADIUS > WINDOW_HEIGHT) {
				endGame();
			}
		}
		if (!crash ) {
			for (Tube t : tubes) {
				Rectangle[] rects = t.getRects();
				if (Physics.checkCollision(bird, rects[0]) || Physics.checkCollision(bird, rects[1]) ) {
					System.out.println("Crash with Tubes: " + rects[0] + ", " + rects[1]);
					setCrash ( true );
				}
			}
		}
	}
	
	
	private void setCrash ( boolean b) {
		if (b && !crash ) {
			System.out.println("CRASH");
			bird.crash();
		}
		crash = b;
	}
	

	public void jump() {

		if (bird != null && !crash)	bird.jump();
	}
	
	public void startGame( String username) { 
		//connection.createTable();
		if ( !playername.equals(username)) {
			playername = username;
			highscore = connection.signUp(username);
		}
		startGame();
	}
	public void startGameWithLastName() {
		 startGame(playername);
	}
	public HashMap<String, Integer> getHighscores () {
		HashMap<String, Integer> highscores = connection.getHighscoreList();
		return highscores;
	} 
	public void signOut () {
		playername = "";
		highscore = 0;
		score = 0;
		//sView.changeToStartScreen();
	}
	public void restartGame() {
		sView.changeBackToGameScreen();
	}
	
	
}
	
	
