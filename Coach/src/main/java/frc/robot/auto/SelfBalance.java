package frc.robot.auto;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.CustomDrive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class SelfBalance implements AutoRoutine {

  CustomDrive c_Drive;
  int timer;

  ADIS16470_IMU c_Gyro = new ADIS16470_IMU();

  public SelfBalance(CustomDrive c_drive) {
    this.c_Drive = c_drive;
  }

  public void init() {
    c_Gyro.reset();
    timer = 0;
    // Set up commands, in sequential and parallel order to call when auton is
    // initiated.
    Command m_sequentialCommands = new SequentialCommandGroup(
        new SequentialCommandGroup(
       
          // command-based sucessful enganging 
          /*new DriveTimeCmd(this.c_Drive, 0.6,0,3985),
          new DriveTimeCmd(this.c_Drive, -0.5,0,350),
          new DriveTimeCmd(this.c_Drive, 0,0,0),
          new BrakeCmd(this.c_Drive, 0,0,0)
        */ 
        ));

    // Cancel any previous commands, in case there was a false start.
    CommandScheduler.getInstance().cancelAll();

    // Run the new commands.
    CommandScheduler.getInstance().schedule(m_sequentialCommands);
  }

  public void periodic() {
   System.out.println(c_Gyro.getXComplementaryAngle());
    // gyro-based engaging
    if (c_Gyro.getXComplementaryAngle() > 5){
      c_Drive.arcadeDrive(-0.5,0);
     } else if (c_Gyro.getXComplementaryAngle() < -5){
      c_Drive.arcadeDrive(0.5,0);
     } else{
      c_Drive.arcadeDrive(0,0);
      new BrakeCmd(this.c_Drive, 0,0,0);
    }
    // Run the commands (which executes the periodic on all the current commands).
    CommandScheduler.getInstance().run();
  }
}