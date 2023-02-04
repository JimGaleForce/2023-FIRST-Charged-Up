package frc.robot;

public class Constants {
 
    // BUTTONS
    public static final int BUTTON_X = 1;
    public static final int BUTTON_A = 2;
    public static final int BUTTON_B = 3;
    public static final int BUTTON_Y = 4;
    public static final int BUTTON_LB = 5;
    public static final int BUTTON_RB = 6;
    public static final int BUTTON_LT = 7;
    public static final int BUTTON_RT = 8;
    public static final int BUTTON_BACK = 9;
    public static final int BUTTON_START = 10;
    public static final int BUTTON_L_STICK = 11;
    public static final int BUTTON_R_STICK = 12;

    // AXES
    public static final int AXIS_LX = 0;
    public static final int AXIS_LY = 1;
    public static final int AXIS_RX = 2;
    public static final int AXIS_RY = 3;

    // PWMs
    public static final int PWM_FR_MOTOR = 0;
    public static final int PWM_RR_MOTOR = 1;

    // DIOs
    public static final int DIO_LIDAR = 0;

    // CAN IDs
    public static final int CAN_FL_MOTOR = 0;
    public static final int CAN_RL_MOTOR = 1;

    // MISCELLANEOUS
    /// These are not constant so we can adjust them dynamically from the dashboard.
    public static double LATERAL_SPEED_FACTOR = 1.0;
    public static double ANGULAR_SPEED_FACTOR = 0.7;

}
