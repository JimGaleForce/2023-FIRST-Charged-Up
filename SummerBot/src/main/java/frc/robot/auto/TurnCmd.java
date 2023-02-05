package frc.robot.auto;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.CustomDrive;

/**
 * Turning Command class that extends the CommandBase
 * class and controls the movement of the robot
 * using the arcadeDrive method of the CustomDrive class.
 */
public class TurnCmd extends CommandBase {

  /** The CustomDrive object used to control the robot movement */
  CustomDrive c_Drive;

  /** The ADIS16470_IMU object used for the robot's gyroscope */
  ADIS16470_IMU c_Gyro;

  /** The time in milliseconds when the command was started */
  long startTime;

  /** Boolean value indicating whether the command is done or not */
  boolean isDone = false;

  /** The angle in degrees the robot should turn */
  double degrees;

  /** The magnitude (positive or negative) of the rotation */
  double rot;

  /** The intended duration of the command in milliseconds */
  int milliseconds;

  /**
   * Constructs a new TurnCmd object
   * 
   * @param c_drive      The CustomDrive object used to control the robot movement
   * @param c_gyro       The ADIS16470_IMU object used for the robot's gyroscope
   * @param degrees      The angle in degrees the robot should turn
   * @param milliseconds The intended duration of the command in milliseconds
   */
  public TurnCmd(CustomDrive c_drive, ADIS16470_IMU c_Gyro, double degrees, int milliseconds) {
    this.c_Drive = c_drive;
    this.c_Gyro = c_Gyro;
    this.degrees = degrees;
    this.rot = this.degrees > 0 ? 0.5 : -0.5;
    this.milliseconds = milliseconds;
  }

  /**
   * Method that is called when the command is started. It sets the arcadeDrive
   * method of the
   * CustomDrive class with the given linear and angular velocity, and sets the
   * start time.
   */
  @Override
  public void initialize() {
    // called when the command is started
    c_Gyro.reset();
    c_Drive.arcadeDrive(0, this.rot);
    this.startTime = System.currentTimeMillis();
  }

  /**
   * Method that is called repeatedly while the command is running. It checks if
   * the command
   * has reached the specified duration and if not, it sets the arcadeDrive method
   * of the
   * CustomDrive class with the given linear and angular velocity.
   */
  @Override
  public void execute() {
    System.out.println(this.c_Gyro.getAngle());
    this.c_Gyro.getYawAxis();
    // called repeatedly while the command is running
    if (this.degrees > 0) {
      this.isDone = -this.c_Gyro.getAngle() >= this.degrees;
    } else {
      this.isDone = -this.c_Gyro.getAngle() <= this.degrees;
    }

    this.isDone = this.isDone || System.currentTimeMillis() >= this.startTime + this.milliseconds;
    if (!this.isDone) {
      c_Drive.arcadeDrive(0, this.rot);
    }
  }

  /**
   * Method that returns true if the command is finished.
   * 
   * @return true if the command is finished
   */
  @Override
  public boolean isFinished() {
    // returns true when the command is finished
    return this.isDone;
  }

  /**
   * Method that is called when the command is finished.
   * 
   * @param interrupted flag that indicates if the command was interrupted
   */
  @Override
  public void end(boolean interrupted) {
    // called when the command is finished
  }
}