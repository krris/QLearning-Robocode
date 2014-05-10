/******************************************************************************
 * Copyright (c) 2013 Dept. of Computer Science, City University of Hong Kong
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 ******************************************************************************/
import robocode.CSJuniorRobot;

public class WallJunior extends CSJuniorRobot {

	public boolean atWall() {
		int t_wall = 36; // threshold to indicate wall is reached
		if (robotX < t_wall) 
			return true; // at left wall
		if (robotY < t_wall) 
			return true; // at bottom wall
		if (fieldWidth - robotX < t_wall) 
			return true; // at right wall
		if (fieldHeight - robotY < t_wall) 
			return true; // at top wall
		return false; // not at any wall
	}
    
	public void gotoWall() {
		if (atWall()) return;
		int nearest_x, nearest_y, turn_x, turn_y, gun_x, gun_y;
		if (fieldWidth - robotX > fieldWidth / 2) {
			nearest_x = robotX; // tank at left half of battlefield
			turn_x = 270; gun_x = 90;
		} else { // tank at right half of battlefield
			nearest_x = fieldWidth - robotX;
			turn_x = 90; gun_x = 270;
		}
		if (fieldHeight-robotY > fieldHeight/2) {// tank at lower half
			nearest_y = robotY;
			turn_y = 180; gun_y = 0;
		} else { // tank at upper half of battlefield
			nearest_y = fieldHeight - robotY;
			turn_y = 0; gun_y = 180;
		}
		if (nearest_x < nearest_y) { // goto nearest wall
			turnTo(turn_x);
			ahead(nearest_x);
			turnTo(0); turnGunTo(gun_x);
		} else {
			turnTo(turn_y);
			ahead(nearest_y);
			turnTo(90); turnGunTo(gun_y);
		}
	}
    
	public void seesaw(int distance) {
		ahead(distance);
		back(distance);
	}
    
	public void run() {
		gotoWall(); // if not at wall, goto the nearest wall
		seesaw(2000);
		turnGunLeft(360);
	}
    
	public void onScannedRobot() {
		turnGunTo(scannedAngle);
		fire();
	}
    
    public void onHitObstacle() {
		turnRight(90);
	}
	
	public void onScannedObstacle() {
		
	}    
}