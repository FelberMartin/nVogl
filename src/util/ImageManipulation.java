package util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageManipulation {

	public static BufferedImage mirrowOnXAxis ( BufferedImage source) {
		BufferedImage bi = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		Graphics g = bi.getGraphics();
		
		for (int i = 0; i < source.getHeight(); i++) {
			g.drawImage(source.getSubimage(0, i, source.getWidth(), 1), 0, source.getHeight() - 1 - i, null);
		}
		
		g.dispose();
		return bi;
	}
}
