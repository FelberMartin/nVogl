package engine;

import java.awt.Point;
import java.awt.Rectangle;


public class Physics {

	public static boolean checkCollision(Bird b, Rectangle r) {

		Point birdP = new Point(b.X, (int) b.getY());
		Rectangle rNew;

		if (intersects(birdP, r)) return true; 	// Central Rect

		rNew = new Rectangle(r.x - Bird.RADIUS, r.y, r.width, Bird.RADIUS); // West Rect																	
		if (intersects(birdP, rNew))
			return true;

		rNew = new Rectangle(r.x, r.y - Bird.RADIUS, Bird.RADIUS, r.width); // North Rect
																	
		if (intersects(birdP, rNew))
			return true;

		rNew = new Rectangle(r.x + r.width, r.y, Bird.RADIUS, r.height); // East Rect																		
		if (intersects(birdP, rNew))
			return true;

		rNew = new Rectangle(r.x, r.y + r.height, r.width, Bird.RADIUS); 	// South Rect
		if (intersects(birdP, rNew))
			return true;
		
		Point pNew = new Point(r.x, r.y);		// North-West
		if (intersects(b, pNew)) return true;
		
		pNew = new Point(r.x + r.width, r.y);	//North-East
		if (intersects(b, pNew)) return true;
		
		pNew = new Point(r.x + r.width, r.y + r.height);	//South-East
		if (intersects(b, pNew)) return true;
		
		pNew = new Point(r.x, r.y + r.height);	// South-West
		if (intersects(b, pNew)) return true;
		
		return false;

	}

	private static boolean intersects(Point p, Rectangle r) {
		if (p.x >= r.x && p.x <= r.x + r.width) {
			if (p.y >= r.y && p.y <= r.y + r.height) {
				return true;
			}
		}
		return false;
	}

	private static boolean intersects(Bird b, Point p) {
		if (Math.pow(b.X - p.x, 2) + Math.pow(b.getY() - p.y, 2) <= Math.pow(Bird.RADIUS, 2)) {
			return true;
		}
		return false;
	}
}
