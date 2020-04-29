package graphics;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.ImageLoader;

public class SwingEndscreen extends JPanel {

	private JButton playAgain;
	private JButton showHighscores;
	private JButton signOut;
	private JLabel score;
	private JLabel hScore;
	private BufferedImage background;
	private SwingView sv;

	public SwingEndscreen(SwingView sv, int endScore, int highscore) {

		this.sv = sv;
		setLayout(new GridBagLayout());
		background = ImageLoader.loadImageFromRes("bg.png");

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 20, 40, 20);

		// Score
		c.gridx = 0;
		c.gridy = 0;
		score = new JLabel("Score: " + endScore);
		score.setFont(SwingView.LABEL_FONT);
		add(score, c);

		// HighScore
		c.gridx = 0;
		c.gridy = 1;
		hScore = new JLabel("HighScore: " + highscore);
		hScore.setFont(SwingView.LABEL_FONT);
		add(hScore, c);

		// Play again
		c.gridx = 0;
		c.gridy = 2;
		playAgain = new JButton("Play again");
		playAgain.setFont(SwingView.LABEL_FONT);
		playAgain.setPreferredSize(SwingView.BUTTON_DIMENSION);

		add(playAgain, c);
		playAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sv.changeBackToGameScreen();
			}
		});

		// Show Highscores
		showHighscores = new JButton("Show Highscores");
		c.gridy = 3;
		showHighscores.setPreferredSize(new Dimension(200, 60));

		add(showHighscores, c);
		showHighscores.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println(sv.getHighscores());
				JPanel hPanel = new JPanel();
				hPanel.setLayout(new BoxLayout(hPanel, BoxLayout.Y_AXIS));
				createAndAddLabels(sv.getHighscores(), hPanel);
				JOptionPane.showMessageDialog(null, hPanel, "Highscores", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Sign Out
		signOut = new JButton("Sign Out");
		c.gridy = 4;
		signOut.setPreferredSize(new Dimension(200, 60));
		add(signOut, c);
		signOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sv.changeToStartScreen();
			}
		});
	}

	private void createAndAddLabels(HashMap<String, Integer> hScores, JPanel p) {

		for (String name : hScores.keySet()) {
			JLabel lbl = new JLabel(name + ": " + hScores.get(name));
			p.add(lbl);
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, null, 0, 0);
	}
}
