/******************************************************************************
 * Copyright (c) 2013 Dept. of Computer Science, City University of Hong Kong
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 ******************************************************************************/
import robocode.CSJuniorRobot;

public class PerpendicularJunior extends CSJuniorRobot
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

	public void seesaw(int distance)
	{
		ahead(distance);
		back(distance);
	}

	public void perpendicular_seesaw(int angle)
	{
		// make sure turning at most 359 deg
		int my_heading = (angle + 90) % 360;

		// turn perpendicular to target
		turnTo(my_heading);

		// seesaw
		seesaw(150);
	}

	public void run()
	{
		scanning();
	}

	public void onScannedRobot()
	{
		int opp_angle = scannedAngle;

		turnGunTo(opp_angle);
		if (true == gunReady) fire();

		// just skip 1 turn
		// guarantee firing without affecting the gun direction
		doNothing();

		perpendicular_seesaw(opp_angle);
	}
    
    public void onHitObstacle() {
		turnTo(-90);
	}
	
	public void onScannedObstacle() {
		
	}
}
