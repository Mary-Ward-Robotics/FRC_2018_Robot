package robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.RobotConst;
import robot.commands.drive.*;
import robot.commands.elevator.*;
import robot.commands.intake.*;
import robot.oi.AutoSelector;
import robot.oi.GameData;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {

	public static final char LEFT 				= 'L';
	public static final char RIGHT 				= 'R';
	public static final char CENTER 			= 'C';
	public static final String ROBOT_LEFT   	= "Robot Left";
	public static final String ROBOT_CENTER 	= "Robot Center";
	public static final String ROBOT_RIGHT  	= "Robot Right";
	public static final String SWITCH 			= "Switch";
	public static final String SCALE 			= "Scale";
	public static final String CROSS  			= "Cross";
	public static final String NONE  			= "None";

	public AutonomousCommand() {
		//getting info
		String robotStartPosition 	= AutoSelector.getRobotStartPosition();
		String firstAction 			= AutoSelector.getRobotFirstAction();
		String secondAction 		= AutoSelector.getRobotSecondAction();
		char closeSwitch 			= GameData.getCloseSwitch();
		char scale 					= GameData.getScale();


		// Print out the user selection and Game config for debug later
		System.out.println("Auto Command Configuration");
		System.out.println("--------------------------");
		System.out.println("Robot Position : " + robotStartPosition);
		System.out.println("First Action   : " + firstAction);
		System.out.println("Second Action  : " + secondAction);
		System.out.println("Close Switch   : " + closeSwitch);
		System.out.println("Scale		   : " + scale);

		//overrides
		System.out.println("Starting Overrides");

		if (robotStartPosition.equals(ROBOT_CENTER) && !firstAction.equals(SWITCH)) {
			firstAction = SWITCH;
			System.out.println("Center start must do switch as first action. Overriding first action to SWITCH");
		}
		if (robotStartPosition.equals(ROBOT_RIGHT) && firstAction.equals(SWITCH) && closeSwitch == LEFT) {
			firstAction = CROSS;
			System.out.println("Switch is not on our side.  Overriding first action to CROSS");
		}
		if (robotStartPosition.equals(ROBOT_LEFT) && firstAction.equals(SWITCH) && closeSwitch == RIGHT){
			firstAction = CROSS;
			System.out.println("Switch is not on our side. Overriding first action to CROSS");
		}
		
		//TODO Uncomment me if we don't want to always rush SCALE
//		if (robotStartPosition.equals(ROBOT_RIGHT) && firstAction.equals(SCALE) && scale == LEFT) {
//			firstAction = SWITCH;
//			System.out.println("Scale is not on our side.  Overriding first action to SWTICH");
//		}
//		if (robotStartPosition.equals(ROBOT_LEFT) && firstAction.equals(SCALE) && scale == RIGHT){
//			firstAction = SWITCH;
//			System.out.println("Scale is not on our side. Overriding first action to SWITCH");
//		}

		//run the auto

		switch (robotStartPosition) {
		case ROBOT_LEFT:
			leftAuto(scale, closeSwitch, firstAction, secondAction);
			break;
		case ROBOT_CENTER:
			centerAuto(scale, closeSwitch, firstAction, secondAction);
			break;
		case ROBOT_RIGHT:
			rightAuto(scale, closeSwitch, firstAction, secondAction);
			break;
		}

	}

	private void leftAuto(char scale, char closeSwitch, String firstAction, String secondAction) {
		//first action
		if (firstAction.equals(SCALE)) {
			if (scale == LEFT){
				leftScaleLeft1();
			}
			else{
				leftScaleRight1();
			}
		}
		else{
			crossLine();
		}
		if (secondAction.equals(SWITCH)) {
			if (closeSwitch == LEFT){
				if (scale == LEFT) {
					leftSwitchLeft2();
				}
			}
		}

		else if (secondAction.equals(SCALE)) {
			if (scale == LEFT){
				if (firstAction.equals(SCALE)) {
					leftScaleLeft2();
				}
			}
		}

		else{
			System.out.println("No second action");
		}
	}

	private void rightAuto(char scale, char closeSwitch, String firstAction, String secondAction) {

		//first action
		if (firstAction.equals(SCALE)) {
			if (scale == RIGHT){
				rightScaleRight1();
			}
			else{
				rightScaleLeft1();
			}
		}
		else{
			crossLine();
		}

		if (secondAction.equals(SWITCH)) {
			if (closeSwitch == RIGHT) {
				if (firstAction.equals(SCALE)) {
					if (scale == RIGHT) {
						rightSwitchRight2();
					}
				}
			}
		}
		else if (secondAction.equals(SCALE)) {
			if (scale == RIGHT){
				rightScaleRight2();
			}
		}
	}

	private void centerAuto(char scale, char closeSwitch, String firstAction, String secondAction) {
		if (closeSwitch == LEFT){
			centerSwitchLeft1();
		}
		else{
			centerSwitchRight1();
		}

		if (secondAction.equals(SCALE)) {
			if (closeSwitch == LEFT) {
				leftGetPowerCube();

			}
			else {
				rightGetPowerCube();
			}
		}
	}

	//******************************************
	// first action methods 
	//******************************************
	
	// Pattern methods name = (Start Position)(Destination)(Side)(Action Number)
	//left side start

	private void leftScaleLeft1(){
		System.out.println("scale left");
		addSequential(new DriveDistanceCommand(170, 0, 0.6, 7.0, true));
		System.out.println("turning right and driving forward");
		addSequential(new DriveDistanceCommand(20, 10, 0.4, 7.0, true));
		System.out.println("driving forward and raising elevator");
		addParallel(new SetElevatorHeightCommand(RobotConst.ELEVATOR_SCALE_HEIGHT_COUNT));
		addSequential(new DriveDistanceCommand(30, 10, 0.2, 7.0, true));
		System.out.println("releasing cube");
		addSequential(new AutoCubeReleaseCommand());
	}
	private void leftScaleRight1(){
		crossLine(); //TODO Fix my auto!
	}

	//right side start
	private void rightScaleLeft1(){	
		crossLine(); //TODO Fix my auto!
	}
	private void rightScaleRight1(){	
		System.out.println("scale right");
		addParallel(new IntakeTiltReset());
		addSequential(new DriveDistanceCommand(170, 0, 0.6, 7.0, true));
		System.out.println("turning left and driving forward");
		addSequential(new DriveDistanceCommand(20, 350, 0.4, 7.0, true));
		System.out.println("driving forward and raising elevator");
		addParallel(new SetElevatorHeightCommand(RobotConst.ELEVATOR_SCALE_HEIGHT_COUNT));
		addSequential(new DriveDistanceCommand(30, 350, 0.2, 7.0, true));
		System.out.println("releasing cube");
		addSequential(new AutoCubeReleaseCommand());
	}
	
	//center start
	private void centerSwitchLeft1() {
//		addParallel(new SetElevatorHeightCommand(2));
//		addParallel(new TimedRaiseElevatorCommand(1.2, 0.7));
//		addSequential(new ArcCommand(85, 0, 310, 0.6, false));
//		addParallel(new TimedIntakeTiltCommand(1.4, -0.4));
//		addSequential(new ArcCommand(105, 310, 0, 0.6, false));
//		addSequential(new DriveDistanceCommand(20, 0, 0.8, 7.0, false));
//		addSequential(new AutoCubeReleaseCommand());
		crossLine(); //TODO Fix my auto!
	}
	private void centerSwitchRight1(){
//		addParallel(new SetElevatorHeightCommand(2));
//		addParallel(new TimedRaiseElevatorCommand(1.2, 0.8));
//		addSequential(new ArcCommand(80, 0, 45,0.8, false));
//		addSequential(new DriveDistanceCommand(3, 45, 0.8, 3.0, false));
//		addParallel(new TimedIntakeTiltCommand(1.4, -0.4));
//		addSequential(new ArcCommand(110, 45, 0, 0.8, false));
//		addSequential(new DriveDistanceCommand(20, 0, 0.8, 7.0, false));
//		addSequential(new AutoCubeReleaseCommand());
		crossLine(); //TODO Fix my auto!
	}

	//universal
	private void crossLine(){
		System.out.println("Crossing baseline");
		addSequential(new DriveDistanceCommand(120, 0, 0.4, 5.0, true));
	}

	//******************************************
	// Second action methods 
	//******************************************
	
	// pattern methods name = (Side going into second action*)(Destination*)(Side)(Action Number)
	// * = mandatory name parameter

	//left side
	public void leftSwitchRight2(){
	}
	public void leftSwitchLeft2(){
	}
	public void leftScaleRight2(){
	}
	public void leftScaleLeft2(){
	}

	//right side
	public void rightSwitchRight2(){
	}
	public void rightSwitchLeft2(){
	}
	public void rightScaleRight2(){	
	}
	public void rightScaleLeft2(){
	}

	//Center
	public void leftGetPowerCube() {
	}
	public void rightGetPowerCube() {
	}
}
