package robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//******************************************
	// Speed Controllers (CAN addresses)
	//******************************************
	public static int GYRO_CAN_PORT                         = 2;
	
	public static int LEFT_DRIVE_MOTOR_PWM_ADDRESS          = 1;
	public static int RIGHT_DRIVE_MOTOR_PWM_ADDRESS         = 0;

	public static int ELEVATOR_MOTOR_PWM_ADDRESS            = 3;
	public static int CLIMB_MOTOR_PWM_ADDRESS               = 4;
	
	// Roller: 	to suck in the cube
	// Lift: 	to lift the cube
	public static int INTAKE_ROLLER_MOTOR_PWM_ADDRESS      =  2;
	public static int INTAKE_TILT_MOTOR_PWM_ADDRESS        =  5;
	
	
	//******************************************
	// DIO Ports
	//******************************************
	
	//Encoders always use consecutive ports
	//ex. right drive encoder goes to 2 AND 3
	public static int LEFT_ENCODER_DIO_PORT             = 0;
	public static int RIGHT_ENCODER_DIO_PORT            = 2;
	public static int LIFT_ENCODER_DIO_PORT             = 6;
	public static int CLIMB_ENCODER_DIO_PORT            = 4;
	public static int INTAKE_TILT_ENCODER_DIO_PORT      = 8;

//	public static int CLIMB_UP_LIMIT                    = 10;
	public static int ELEVATOR_UP_LIMIT                 = 12;
	public static int ELEVATOR_DOWN_LIMIT               = 11;
//	public static int INTAKE_TILT_UP_LIMIT              = 13;
//	public static int INTAKE_TILT_DOWN_LIMIT            = 14;
	public static int CUBE_DETECT_DIO_PORT              = 10; 
	
	//******************************************
	// Analog Ports
	//******************************************
	
	//******************************************
	// Pneumatics Ports
	//******************************************
	public static int SHIFTER_PNEUMATIC_PORT = 2;
	public static int INTAKE_CLAW_PNEUMATIC_PORT = 0;
	
}
