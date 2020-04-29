package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage loadImageFromRes( String file ) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File( "./res/" + file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}
}
