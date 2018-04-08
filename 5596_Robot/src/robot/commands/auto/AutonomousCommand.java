package robot.commands.auto;

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
				} else {
					leftSwitchRight2();
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
		System.out.println("driving forward");
		addSequential(new DriveDistanceCommand(175, 0, 0.6, 7.0, false));
		
		System.out.println("turning right and driving forward");
		addSequential(new DriveDistanceCommand(20, 20, 0.4, 7.0, true));
		
		System.out.println("driving forward and raising elevator");
		addParallel(new SetTiltCommand(-27));
		addSequential(new SetElevatorHeightCommand(RobotConst.ELEVATOR_SCALE_HEIGHT_COUNT));
		addSequential(new DriveDistanceCommand(40, 20, 0.4, 7.0, true));
		
		System.out.println("releasing cube");
		addSequential(new AutoCubeReleaseCommand());
		
		System.out.println("Lowering arm");
		addParallel(new SetElevatorHeightCommand(0));
		addSequential(new ReverseDriveCommand(40, 20 , 0.4, 7, true));
		
		System.out.println("Scale 1 complete");
		System.out.println("----------------------------------------");
	}
	private void leftScaleRight1(){
		System.out.println("scale right");
		System.out.println("driving forward");
		addSequential(new DriveDistanceCommand(185,0,0.6,6,false));
		
		System.out.println("Turning right");
		addSequential(new DriveDistanceCommand(50, 90, 0.6, 6, false));
		
		System.out.println("Driving forward");
		addSequential(new DriveDistanceCommand(140, 90, 0.6, 4, false));
		
		System.out.println("Turning to scale");
		addSequential(new RotateToAngleCommand(310,0.6,6));
		
		System.out.println("Drive forward to scale");
		addParallel(new SetTiltCommand(RobotConst.INTAKE_TILT_HALF_ENCODER_COUNT));
		addSequential(new DriveDistanceCommand(40,310,0.6,7.0,true));
		
		System.out.println("Driving forward and raising elevator");
		addParallel(new SetTiltCommand(RobotConst.INTAKE_TILT_HALF_ENCODER_COUNT));
		addSequential(new SetElevatorHeightCommand(RobotConst.ELEVATOR_SCALE_HEIGHT_COUNT));
		addSequential(new DriveDistanceCommand(20, 310, 0.4, 7.0, true));
		
		System.out.println("Releasing cube");
		addSequential(new AutoCubeReleaseCommand());
		
		System.out.println("Reversing");
		addParallel(new SetElevatorHeightCommand(0));
		addSequential(new ReverseDriveCommand(20, 310, 0.5, 7.0, true));

		System.out.println("Scale 1 complete");
		System.out.println("----------------------------------------");
	}

	//right side start
	private void rightScaleLeft1(){	
		crossLine();
	}
	private void rightScaleRight1(){
		crossLine();
	}
	//center start
	private void centerSwitchLeft1() {
		crossLine();
	}
	private void centerSwitchRight1(){
		crossLine();
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
	public void leftSwitchRight2() {
	}
	public void leftSwitchLeft2(){
		//TODO Two Box Auto
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