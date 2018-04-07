package robot;

public class RobotConst {
	
	// The TorontoCodingCollective framework was developed to run on different
	// robots.
	// Supported robots are 1311 and 1321.
	public static int robot = 5596;
	
	public static final boolean INVERTED = true;
	public static final boolean NOT_INVERTED = false;
	public static final char LEFT = 'L';
	public static final char RIGHT = 'R';

	//*********************************************************
	// Drive Constants
	//*********************************************************
	public static final boolean RIGHT_MOTOR_ORIENTATION;
	public static final boolean LEFT_MOTOR_ORIENTATION;
	
	public static final boolean RIGHT_ENCODER_ORIENTATION;
	public static final boolean LEFT_ENCODER_ORIENTATION;

	public static final double DRIVE_ENCODER_COUNTS_PER_INCH;
	public static final double DRIVE_SPEED_PID_KP;
	
	public static final double MAX_LOW_GEAR_SPEED;  // Encoder Counts/sec
	public static final double MAX_HIGH_GEAR_SPEED; // Encoder Counts/sec
	
	public static final double DRIVE_GYRO_PID_KP;
	public static final double DRIVE_GYRO_PID_KI;

	public static final boolean INTAKE_ROLLER_ORIENTATION;
	public static final boolean INTAKE_TILT_ORIENTATION;
	public static final double  INTAKE_TILT_UP_ENCODER_COUNT;
	public static final double  INTAKE_TILT_HALF_ENCODER_COUNT;
	public static final double  INTAKE_TILT_DOWN_ENCODER_COUNT;

	public static final boolean ELEVATOR_MOTOR_ORIENTATION;
	public static final boolean ELEVATOR_ENCODER_ORIENTATION;
	public static final double  ELEVATOR_ENCODER_COUNTS_PER_INCH;
	public static final double  ELEVATOR_MAX_HEIGHT_COUNT;
	public static final double  ELEVATOR_SCALE_HEIGHT_COUNT;
	public static final double  ELEVATOR_SWITCH_HEIGHT_COUNT;
	
	public static final boolean CLIMB_MOTOR_ORIENTATION;
	public static final double  CLIMB_MAX_HEIGHT_COUNT;

	
	//*********************************************************
	// For Ultrasonic Calibration
	//*********************************************************
	public static final double ULTRASONIC_VOLTAGE_20IN = 0.191;
	public static final double ULTRASONIC_VOLTAGE_40IN = 0.383;
	public static final double ULTRASONIC_VOLTAGE_80IN = 0.764;
	
	public static enum Direction { FORWARD, BACKWARD };
	
	
	static {
		
		switch (robot) {

		case 5596:
			RIGHT_MOTOR_ORIENTATION = NOT_INVERTED;
			LEFT_MOTOR_ORIENTATION = INVERTED;
			
			RIGHT_ENCODER_ORIENTATION = NOT_INVERTED;
			LEFT_ENCODER_ORIENTATION = NOT_INVERTED;
			
			DRIVE_ENCODER_COUNTS_PER_INCH = 13.05;
			DRIVE_SPEED_PID_KP = 0.3;
			
			MAX_LOW_GEAR_SPEED = 580.0;    // Encoder counts/sec
			MAX_HIGH_GEAR_SPEED = 2000.0;
			
			DRIVE_GYRO_PID_KP = .05;
			DRIVE_GYRO_PID_KI = 0;
			
			INTAKE_ROLLER_ORIENTATION = INVERTED;
			INTAKE_TILT_ORIENTATION = INVERTED;
			INTAKE_TILT_UP_ENCODER_COUNT = 0;
			INTAKE_TILT_HALF_ENCODER_COUNT = -27;
			INTAKE_TILT_DOWN_ENCODER_COUNT = -45; //Code should automatically set the min to 0 encoder counts
			
			ELEVATOR_MOTOR_ORIENTATION = INVERTED;
			ELEVATOR_ENCODER_ORIENTATION = INVERTED;
			ELEVATOR_ENCODER_COUNTS_PER_INCH = 186.67;
			ELEVATOR_MAX_HEIGHT_COUNT = 1660; //TODO get actual max height
			ELEVATOR_SCALE_HEIGHT_COUNT = 1660;
			ELEVATOR_SWITCH_HEIGHT_COUNT = 575;
			
			CLIMB_MOTOR_ORIENTATION = NOT_INVERTED;
			CLIMB_MAX_HEIGHT_COUNT = 1660; //TODO get actual max height
			break;
			
		default:
			RIGHT_MOTOR_ORIENTATION = NOT_INVERTED;
			LEFT_MOTOR_ORIENTATION = INVERTED;
			
			RIGHT_ENCODER_ORIENTATION = NOT_INVERTED;
			LEFT_ENCODER_ORIENTATION = NOT_INVERTED;
			
			DRIVE_ENCODER_COUNTS_PER_INCH = 8.0*Math.PI / 256.0;
			DRIVE_SPEED_PID_KP = 0.3;
			
			MAX_LOW_GEAR_SPEED = 580.0;    // Encoder counts/sec
			MAX_HIGH_GEAR_SPEED = 2000.0;
			
			DRIVE_GYRO_PID_KP = .05;
			DRIVE_GYRO_PID_KI = 0;
			
			INTAKE_ROLLER_ORIENTATION = INVERTED;
			INTAKE_TILT_ORIENTATION = INVERTED;
			INTAKE_TILT_UP_ENCODER_COUNT = 126.5625; //TODO rough estimate, check value on TallBoi before running code!
			INTAKE_TILT_HALF_ENCODER_COUNT = 0;
			INTAKE_TILT_DOWN_ENCODER_COUNT = 0; //Code should automatically set the min to 0 encoder counts

			ELEVATOR_MOTOR_ORIENTATION = INVERTED;
			ELEVATOR_ENCODER_ORIENTATION = INVERTED;
			ELEVATOR_ENCODER_COUNTS_PER_INCH = 186.67;
			ELEVATOR_MAX_HEIGHT_COUNT = 1660;
			ELEVATOR_SCALE_HEIGHT_COUNT = 1660;
			ELEVATOR_SWITCH_HEIGHT_COUNT = 575;
			
			CLIMB_MOTOR_ORIENTATION = INVERTED;
			CLIMB_MAX_HEIGHT_COUNT = 1660; //TODO get actual max height
			break;
		}
	}
}
