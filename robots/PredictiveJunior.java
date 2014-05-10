/******************************************************************************
 * Copyright (c) 2013 Dept. of Computer Science, City University of Hong Kong
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 ******************************************************************************/
import robocode.CSJuniorRobot;

public class PredictiveJunior extends CSJuniorRobot
{
	public int gunAdjustment(
		int self_heading, int self_gunHeading,
		int opp_heading,  int opp_bearing,
		int opp_velocity, int opp_angle, int firepower)
	{
		// reference:
		// http://robowiki.net/wiki/Linear_Targeting

		int bv = 20 - firepower * 3;

		double absBearing = Math.toRadians(self_heading + opp_bearing);
		double lv = opp_velocity
				  * Math.sin(Math.toRadians(opp_heading) - 
					     absBearing);

		double predict = robocode.util.Utils.normalRelativeAngle(
			absBearing - Math.toRadians(self_gunHeading)
			+ Math.asin(lv/ bv)
		);

		if (0 == lv) predict = 0;
		return (int) Math.toDegrees(predict);
	}

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
		int opp_distance = scannedDistance;
		int opp_velocity = scannedVelocity;
		int opp_bearing = scannedBearing;
		int opp_heading = scannedHeading;

		int prepared_fire = 1;
		if (opp_distance < 400) 	 prepared_fire = 3;
		else if (opp_distance < 600) prepared_fire = 2;


		int adjust = gunAdjustment(
						heading, gunHeading,
						opp_heading, opp_bearing,
						opp_velocity, opp_angle, 
						prepared_fire);

		turnGunTo(opp_angle + adjust);
		if (true == gunReady) fire(prepared_fire);

		// just skip 1 turn
		// guarantee firing without affecting the gun direction
		doNothing();

		perpendicular_seesaw(opp_angle + adjust);
	}
    
    public void onHitObstacle() {
		turnTo(90);
	}
	
	public void onScannedObstacle() {
		
	}
}
