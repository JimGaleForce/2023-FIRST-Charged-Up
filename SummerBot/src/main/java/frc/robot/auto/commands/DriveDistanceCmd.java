package frc.robot.auto.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Chassis;

/**
 * Forward/Backward (straight) drive Command class that extends the CommandBase
 * class and controls the movement of the robot
 * using the arcadeDrive method of the Chassis class.
 */
public class DriveDistanceCmd extends CommandBase {

  // Constants
  private static final double WHEEL_DIAMETER = 6.0; // inches
  private static final double RPM = 56.0; // assuming 100% power is 1000 RPM
  
  private static final double CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; // inches
  private static final double INCHES_PER_MINUTE = RPM * CIRCUMFERENCE; // inches per minute
  
  /**
   * Object of the Chassis class that is used to control themovement of the
   * robot.
   */
  Chassis c_Drive;
  /**
   * Time in milliseconds when the command is started.
   */
  long startTime;
  /**
   * Flag that indicates if the command is finished or not.
   */
  boolean isDone = false;
  /**
   * Linear velocity of the robot.
   */
  double topSpeed;
  /**
   * Distance to drive in feet
   */
  double feet;
  /**
   * Distance to drive in inches
   */
  double inches;
  /**
   * Duration of the command in milliseconds.
   */
  int milliseconds;
  /**
   * Timeout of the command in milliseconds.
   */
  int timeoutInMilliseconds = 5000;
  /**
  * Represents the ramp-up percent of the motor
  */
  double rampUpPercent;
  
  /**
  * Represents the ramp-down percent of the motor
  */
  double rampDownPercent;
  
  /**
  * Represents the time needed to accelerate the motor
  */
  double timeToAccelerate;
  
  /**
  * Represents the time needed to decelerate the motor
  */
  double timeToDecelerate;
  
  /**
  * Represents the time needed to maintain constant speed of the motor 
  */
  double timeConstantSpeed;

  /**
  * Constructor that initializes the object of the Chassis class, the top speed, the distance, and the timeout in milliseconds.
  * The duration of the command is calculated by converting the distance to milliseconds using the provided top speed.
  * This constructor has no ramp up / ramp down - this drives in a square wave.
  *
  * @param c_drive object of the Chassis class
  * @param topSpeed top speed of the robot (1 = 100%)
  * @param feet distance in feet
  * @param inches distance in inches
  * @param timeoutInMilliseconds timeout in milliseconds
  */
  public DriveDistanceCmd(Chassis c_drive, double topSpeed, double feet, double inches, int timeoutInMilliseconds) {
    this.c_Drive = c_drive;
    this.topSpeed = topSpeed;
    this.feet = feet;
    this.inches = inches;
    this.timeoutInMilliseconds = timeoutInMilliseconds;
    this.milliseconds = (int) distanceToMilliseconds(this.rampUpPercent, this.rampDownPercent, topSpeed, feet, inches);
  }

  /**
  * Constructor that initializes the object of the Chassis class, the top speed, the distance, the timeout in milliseconds, and the ramp up and down percentages.
  * The duration of the command is calculated by converting the distance to milliseconds using the provided top speed and ramp up and down percentages.
  * This constructor drives with a ramp up and ramp down percentage that is identical.
  *
  * @param c_drive object of the Chassis class
  * @param topSpeed top speed of the robot (1 = 100%)
  * @param feet distance in feet
  * @param inches distance in inches
  * @param timeoutInMilliseconds timeout in milliseconds
  * @param rampUpDownPercent ramp up and down percentage (0.25 = 25%)
  */
  public DriveDistanceCmd(Chassis c_drive, double topSpeed, double feet, double inches, int timeoutInMilliseconds, double rampUpDownPercent) {
    this(c_drive, topSpeed, feet, inches, timeoutInMilliseconds);
    this.rampUpPercent = rampUpDownPercent;
    this.rampDownPercent = rampUpDownPercent;
    this.milliseconds = (int) distanceToMilliseconds(this.rampUpPercent, this.rampDownPercent, topSpeed, feet, inches);
  }

  /**
  * Constructor that initializes the object of the Chassis class, the top speed, the distance, the timeout in milliseconds, and the ramp up and ramp down percentages.
  * The duration of the command is calculated by converting the distance to milliseconds using the provided top speed and ramp up and down percentages.
  * This constructor drives with a ramp up and ramp down percentage independently.
  *
  * @param c_drive object of the Chassis class
  * @param topSpeed top speed of the robot (1 = 100%)
  * @param feet distance in feet
  * @param inches distance in inches
  * @param timeoutInMilliseconds timeout in milliseconds
  * @param rampUpPercent ramp up percentage (0.25 = 25%)
  * @param rampDownPercent ramp down percentage (0.25 = 25%)
  */
  public DriveDistanceCmd(Chassis c_drive, double topSpeed, double feet, double inches, int timeoutInMilliseconds, double rampUpPercent, double rampDownPercent) {
    this(c_drive, topSpeed, feet, inches, timeoutInMilliseconds);
    this.rampUpPercent = rampUpPercent;
    this.rampDownPercent = rampDownPercent;
    this.milliseconds = (int) distanceToMilliseconds(this.rampUpPercent, this.rampDownPercent, topSpeed, feet, inches);
  }

  /**
  * This method calculates the time in milliseconds it takes to drive a certain distance at a certain speed.
  * @param distance The distance that the robot needs to travel, in inches.
  * @param percentPower The speed of the robot as a percentage of maximum speed.
  */
  public static double convertDistanceToMilliseconds(double distance, double percentPower) {
    double inchesPerMinute = INCHES_PER_MINUTE * (percentPower / 100.0);
    double minutes = distance / inchesPerMinute;
    double seconds = minutes * 60.0;
    return seconds * 1000;
  }

  /**
  * This method calculates the time in milliseconds required to travel a certain distance with a trapezoidal speed profile.
  * @param rampUp The percentage of the distance to be used for ramping up to full speed (0.25 = 25%)
  * @param rampDown The percentage of the distance to be used for ramping down to 0 speed (0.25 = 25%)
  * @param maxSpeed The maximum speed of the robot, expressed as a percentage (1 = 100%)
  * @param distanceFeet The distance to be traveled, in feet
  * @param distanceInches The additional distance to be traveled, in inches
  * @return The time in milliseconds required to travel the distance with the given speed profile
  */
  public double distanceToMilliseconds(double rampUp, double rampDown, double maxSpeed, double distanceFeet, double distanceInches) {
    double wheelDiameter = WHEEL_DIAMETER; // inches
    double inchesPerFoot = 12;
    distanceInches = distanceFeet * inchesPerFoot + distanceInches;
    double fullRotationInches = wheelDiameter * Math.PI;
    double RPM = DriveDistanceCmd.RPM;
    double distanceAcceleration = distanceInches * rampUp;
    double distanceDeceleration = distanceInches * rampDown;
    double distanceConstantSpeed = distanceInches - (distanceAcceleration + distanceDeceleration);
    this.timeToAccelerate = ((distanceAcceleration / (fullRotationInches * RPM * maxSpeed)) * 60 * 1000) * 2;
    this.timeToDecelerate = ((distanceDeceleration / (fullRotationInches * RPM * maxSpeed)) * 60 * 1000) * 2;
    this.timeConstantSpeed = (distanceConstantSpeed / (fullRotationInches * RPM * maxSpeed)) * 60 * 1000;
    return this.timeToAccelerate + this.timeConstantSpeed + this.timeToDecelerate;
  }

  /**
   * Get the speed of the robot at a certain time.
   * 
   * @param currentTime  The current time since the start of the robot's movement
   * @return The robot's current speed at that time
   */
  public double getSpeedFrom(long currentTime) {
    if (currentTime < this.timeToAccelerate && this.timeToAccelerate > 0) {
      return (currentTime/this.timeToAccelerate) * this.topSpeed;
    } 
    
    currentTime -= this.timeToAccelerate;
    if (currentTime < this.timeConstantSpeed) {
      return this.topSpeed;
    }

    currentTime -= this.timeConstantSpeed;
    if (currentTime < this.timeToDecelerate && this.timeToDecelerate > 0) { 
      return ((this.timeToDecelerate-currentTime)/this.timeToDecelerate) * this.topSpeed;
    }

    return 0;
  }

  /**
   * Method that is called when the command is started. It sets the arcadeDrive
   * method of the
   * Chassis class with the given linear and angular velocity, and sets the
   * start time.
   */
  @Override
  public void initialize() {
    c_Drive.arcadeDrive(0, 0);
    this.startTime = System.currentTimeMillis();
  }

  /**
   * Method that is called repeatedly while the command is running. It checks if
   * the command
   * has reached the specified duration and if not, it sets the arcadeDrive method
   * of the
   * Chassis class with the given linear and angular velocity.
   */
  @Override
  public void execute() {
    long currentTime = System.currentTimeMillis();
    this.isDone = currentTime >= this.startTime + this.milliseconds || currentTime > this.startTime + this.timeoutInMilliseconds;
    if (!this.isDone) {
      double currentSpeed = getSpeedFrom(currentTime - this.startTime);
      c_Drive.arcadeDrive(currentSpeed, 0);
    }else{
      c_Drive.arcadeDrive(0, 0); 
    }
  }

  /**
   * Method that returns true if the command is finished.
   * 
   * @return true if the command is finished
   */
  @Override
  public boolean isFinished() {
    return this.isDone;
  }

  /**
   * Method that is called when the command is finished.
   * 
   * @param interrupted flag that indicates if the command was interrupted
   */
  @Override
  public void end(boolean interrupted) {

  }
}
