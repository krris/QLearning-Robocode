import robocode.CSJuniorRobot;

public class DemoRobot extends CSJuniorRobot {

	/**
	 * DemoDuck run method - Seesaw as default
	 */
	public void run() {
		// Set robot colors
		setColors(green, black, blue);

		// Seesaw forever
		while (true) {
			turnGunRight(360);
		}
	}

	/**
	 * When we see a robot, turn the gun towards it and fire
	 */
	public void onScannedRobot() {
        turnTo(scannedAngle);
        ahead(200);
        while(true) {
			fire(3);
		}
	}

	/**
	 * We were hit!  Turn and move perpendicular to the bullet,
	 * so our seesaw might avoid a future shot.
	 */
	public void onHitByBullet() {

	}

	public void onHitObstacle() {

	}
}
