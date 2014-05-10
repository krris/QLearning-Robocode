import robocode.CSJuniorRobot;

public class DemoDuck extends CSJuniorRobot {

	/**
	 * DemoDuck run method - Seesaw as default
	 */
	public void run() {
		// Set robot colors
		setColors(red, black, blue);

		// Seesaw forever
		while (true) {
			doNothing(); // Do Nothing
		}
	}

	/**
	 * When we see a robot, turn the gun towards it and fire
	 */
	public void onScannedRobot() {

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
