/******************************************************************************
 * Copyright (c) 2013 Dept. of Computer Science, City University of Hong Kong
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 ******************************************************************************/
import robocode.CSJuniorRobot;

public class RandomPositionJunior extends CSJuniorRobot {
	public void random_movement() {
		// get a random (X, Y) coordinate to move
		gotoCoordinate(
			robocode.util.Utils.getRandom().nextInt(fieldWidth),
			robocode.util.Utils.getRandom().nextInt(fieldHeight)
		);
	}
    
	public void gotoCoordinate(int x, int y) {
		// using Pythagoras' theorem to get the distance to travel
		int goto_distance = (int) Math.sqrt(
			  (double) (x - robotX) * (x - robotX)
			+ (double) (y - robotY) * (y - robotY)
		);
		// using trigonometric function to get the heading
		int goto_heading = 0;
		if (x > robotX && y > robotY) {
			// goto somewhere top-right
			goto_heading = (int) (Math.atan(
				(double) (x - robotX) / (y - robotY)
			) / Math.PI * 180);
		}
		if (x > robotX && y < robotY) {
			// goto somewhere bottom-right
			goto_heading = 90 + (int) (Math.atan(
				(double) (robotY - y) / (x - robotX)
			) / Math.PI * 180);
		}
		if (x < robotX && y < robotY) {
			// goto somewhere bottom-left
			goto_heading = 180 + (int) (Math.atan(
				(double) (robotX - x) / (robotY - y)
			) / Math.PI * 180);
		}
		if (x < robotX && y > robotY) {
			// goto somewhere top-left
			goto_heading = 270 + (int) (Math.atan(
				(double) (y - robotY) / (robotX - x)
			) / Math.PI * 180);
		}
		turnTo(goto_heading);
		ahead(goto_distance);
	}
    
	public void run() {
		random_movement();
		turnGunRight(360);
	}
    
	public void onScannedRobot() {
		turnGunTo(scannedAngle);
		fire();
	}
    
    
    public void onHitObstacle() {
		ahead(-50);
		turnLeft(90);
	}
	
	public void onScannedObstacle() {

	}
}