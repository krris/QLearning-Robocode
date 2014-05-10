/******************************************************************************
 * Copyright (c) 2013 Dept. of Computer Science, City University of Hong Kong
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 ******************************************************************************/
import robocode.CSJuniorRobot;

public class SquareJunior extends CSJuniorRobot
{
	public void scanning()
	{
		// Each tick can turn gun for 20 degree
		// if target not find yet,
		// do 360 degree gun turning in total
		for (int i = 0; i < 9; ++i) {
			turnGunLeft(40);
		}
	}

	public void square_movement()
	{
		// keep turning the body left
		// and move for a fixed distance
		// resulted as a square
		for (int i = 0; i < 4; ++i) {
			ahead(150);
			turnLeft(90);
		}
	}

	public void run ()
	{
		scanning();
		square_movement();
	}

	public void onScannedRobot()
	{
		turnGunTo(scannedAngle);
		if (true == gunReady) fire();
	}
    
    public void onHitObstacle() {
		turnLeft(90);
        ahead(150);
	}
	
	public void onScannedObstacle() {
		
	}
}
