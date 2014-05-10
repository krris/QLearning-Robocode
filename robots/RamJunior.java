/******************************************************************************
 * Copyright (c) 2013 Dept. of Computer Science, City University of Hong Kong
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 ******************************************************************************/
import robocode.CSJuniorRobot;

public class RamJunior extends CSJuniorRobot
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

	public void ram(int angle, int distance)
	{
		// turn to target and ram
		// when target is close enough
		// shoot with power 3 to gain energy
		turnTo(angle);
		bearGunTo(0);

		if (true == gunReady) {
			if (distance < 400) fire(3);
			else if (distance < 600) fire(2);
			else fire(1);
		}
		ahead(distance);
	}

	public void run()
	{
		scanning();
	}

	public void onScannedRobot()
	{
		ram(scannedAngle, scannedDistance);
	}
    
    public void onHitObstacle() {
		turnRight(90);
        ahead(50);
	}
	
	public void onScannedObstacle() {

	}

}
