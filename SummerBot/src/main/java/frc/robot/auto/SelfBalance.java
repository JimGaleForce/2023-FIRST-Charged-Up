package frc.robot.auto;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Chassis;
import frc.robot.auto.commands.BrakeCmd;

public class SelfBalance implements AutoRoutine {

  Chassis c_Drive;
  int timer;

  ADIS16470_IMU c_Gyro;

  public SelfBalance(Chassis c_drive, ADIS16470_IMU c_Gyro) {
    this.c_Drive = c_drive;
    this.c_Gyro = c_Gyro;
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
        // new DriveTimeCmd(this.c_Drive, 0.6,0,3750)
        ));

    // Cancel any previous commands, in case there was a false start.
    CommandScheduler.getInstance().cancelAll();

    // Run the new commands.
    CommandScheduler.getInstance().schedule(m_sequentialCommands);
  }

  public void periodic() {
   System.out.println(c_Gyro.getYComplementaryAngle());
    // gyro-based engaging
    if (c_Gyro.getYComplementaryAngle() < -5){
      c_Drive.arcadeDrive(-0.3,0);
      System.out.println("Tilted backwards");
     } else if (c_Gyro.getYComplementaryAngle() > 5){
      c_Drive.arcadeDrive(0.3,0);
      System.out.println("Tilted forwards");
     } else{
      c_Drive.arcadeDrive(0,0);
      new BrakeCmd(this.c_Drive, 0,0,0);
    }
    // Run the commands (which executes the periodic on all the current commands).
    CommandScheduler.getInstance().run();
  }
}