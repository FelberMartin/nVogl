package graphics;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import util.ImageLoader;

public class SwingStartscreen extends JPanel {
	
	private BufferedImage background;
	private JButton play;
	private JTextField name;
	private JLabel label;
	private SwingView sv;
	
	public SwingStartscreen(SwingView sv) {
		
		this.sv = sv;
		setLayout(new GridBagLayout());
		background = ImageLoader.loadImageFromRes("bg.png");
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 20, 20, 20);

		// Label
		c.gridx = 0;
		c.gridy = 0;
		label = new JLabel("Enter your name!");
		add(label, c);
						
		// EditorPane
		c.gridx = 0;
		c.gridy = 1;
		name = new JTextField();
		name.setPreferredSize(new Dimension( 200, 30));
		name.setText("Dieter");
		name.setEditable(true);
		add(name, c);
					
		// Button
		c.gridx = 0;
		c.gridy = 2;
		play = new JButton();
		play.setText("Play");
		play.setFont(SwingView.LABEL_FONT);
		play.setPreferredSize(SwingView.BUTTON_DIMENSION);
		add(play, c);
		play.addActionListener(new ActionListener( ) {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sv.playButtonPressed();
			}
		});
	}
	
	public String getUsername() {
		return name.getText();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, null, 0, 0);
	}
}
