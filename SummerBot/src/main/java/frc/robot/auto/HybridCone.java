package frc.robot.auto;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Chassis;
import frc.robot.auto.commands.BrakeCmd;
import frc.robot.auto.commands.DriveTimeCmd;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class HybridCone implements AutoRoutine {

  Chassis c_Drive;
  int timer;

  ADIS16470_IMU c_Gyro;

  public HybridCone(Chassis c_drive, ADIS16470_IMU c_gyro) {
    this.c_Drive = c_drive;
    this.c_Gyro = c_gyro;
  }

  public void init() {
    c_Gyro.reset();
    timer = 0;

    // Set up commands, in sequential and parallel order to call when auton is
    // initiated.
    Command m_sequentialCommands = new SequentialCommandGroup(
        new SequentialCommandGroup(
          /* new DriveTimeCmd(this.c_Drive, -0.5,0,100),
          new DriveTimeCmd(this.c_Drive, 0.7,0,3050),
          new TurnCmd(this.c_Drive, this.c_Gyro, 30,1500),
          new DriveTimeCmd(this.c_Drive, 0,0,500),
          new TurnCmd(this.c_Drive, this.c_Gyro, 145,5000),
          new DriveTimeCmd(this.c_Drive, 0.7,0,3000),
          new TurnCmd(this.c_Drive, this.c_Gyro, -86,2000),
          new DriveTimeCmd(this.c_Drive, 1,0,500),
          new TurnCmd(this.c_Drive, this.c_Gyro, 86,2000), */

          // testing engaging
          new DriveTimeCmd(this.c_Drive, 0.6,0,3985),
          new DriveTimeCmd(this.c_Drive, -0.5,0,350),
          new DriveTimeCmd(this.c_Drive, 0,0,0),
          new BrakeCmd(this.c_Drive, 0,0,0)
        ));

    // Cancel any previous commands, in case there was a false start.
    CommandScheduler.getInstance().cancelAll();

    // Run the new commands.
    CommandScheduler.getInstance().schedule(m_sequentialCommands);
  }

  public void periodic() {
    
    // Run the commands (which executes the periodic on all the current commands).
    CommandScheduler.getInstance().run();
  }
}