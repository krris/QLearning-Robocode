/******************************************************************************
 * Copyright (c) 2013 Dept. of Computer Science, City University of Hong Kong
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 ******************************************************************************/
import robocode.CSJuniorRobot;

public class CornerJunior extends CSJuniorRobot {
    int hitObstacleTime = 0;
    
	public boolean atCorner() {
		int t_corner = 36; // threshold to indicate corner is reached
		if (robotX < t_corner && robotY < t_corner)
			return true; // at bottom-left corner
		if (robotX < t_corner && fieldHeight - robotY < t_corner) 
			return true; // at top-left corner
		if (fieldWidth - robotX < t_corner && robotY < t_corner) 
			return true; // at bottom-right corner
		if (fieldWidth - robotX < t_corner && 
		    fieldHeight - robotY < t_corner) 
			return true; // at top-right corner
		return false; // not at any corner
	}
	public void gotoCorner() {
		if (atCorner() || hitObstacleTime == 10) return;
		
		int nearest_x, nearest_y, turn_x, turn_y;
		if (fieldWidth - robotX > fieldWidth / 2) {
			nearest_x = robotX; // tank at left half of battlefield
			turn_x = 270;
		} else { // tank at right half of battlefield
			nearest_x = fieldWidth - robotX;
			turn_x = 90;
		}
		if (fieldHeight - robotY > fieldHeight / 2) {
			nearest_y = robotY; // tank at lower half of battlefield
			turn_y = 180;
		} else { // tank at upper half of battlefield
			nearest_y = fieldHeight - robotY;
			turn_y = 0;
		}
		if (nearest_x < nearest_y) { // goto nearest corner
			turnTo(turn_x);
			ahead(nearest_x);
			turnTo(turn_y);
			ahead(nearest_y);
		} else {
			turnTo(turn_x);
			ahead(nearest_x);
			turnTo(turn_y);
			ahead(nearest_y);
		}
	}
    
	public void run() {
		gotoCorner();
		turnGunLeft(360);
	}
    
	public void onScannedRobot() {
		turnGunTo(scannedAngle);
		if (true == gunReady) fire();
	}
    
    public void onHitObstacle() {		
        if (hitObstacleTime != 10) hitObstacleTime++;
	}
	
	public void onScannedObstacle() {
		
	}    
}