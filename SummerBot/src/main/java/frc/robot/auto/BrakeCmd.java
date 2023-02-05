package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.CustomDrive;

/**
 * Forward/Backward (straight) drive Command class that extends the CommandBase
 * class and controls the movement of the robot
 * using the arcadeDrive method of the CustomDrive class.
 */
public class BrakeCmd extends CommandBase {

  /**
   * Object of the CustomDrive class that is used to control the movement of the
   * robot.
   */
  CustomDrive c_Drive;
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
  double lat;
  /**
   * Angular velocity of the robot.
   */
  double rot;
  /**
   * Duration of the command in milliseconds.
   */
  int milliseconds;

  /**
   * Constructor that initializes the object of the CustomDrive class, the linear
   * and angular
   * velocity of the robot, and the duration of the command.
   * 
   * @param c_drive      object of the CustomDrive class
   * @param lat          linear velocity of the robot
   * @param rot          angular velocity of the robot
   * @param milliseconds duration of the command
   */
  public BrakeCmd(CustomDrive c_drive, double lat, double rot, int milliseconds) {
    this.c_Drive = c_drive;
    this.lat = lat;
    this.rot = rot;
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
    c_Drive.arcadeDrive(this.lat, this.rot);
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
    this.isDone = System.currentTimeMillis() >= this.startTime + this.milliseconds;
    if (!this.isDone) {
      c_Drive.arcadeDrive(this.lat, this.rot);
    }else{
      c_Drive.setBrake(true);
    
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
