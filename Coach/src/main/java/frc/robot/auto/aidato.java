package frc.robot.auto;


import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.CustomDrive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class aidato implements AutoRoutine {

    CustomDrive c_Drive;
    int timer;
  
    ADIS16470_IMU c_Gyro = new ADIS16470_IMU();
  
    public aidato(CustomDrive c_drive) {
      this.c_Drive = c_drive;
    }
  
    public void init() {
      c_Gyro.reset();

      Command m_sequentialCommands = new SequentialCommandGroup(
          new SequentialCommandGroup(
        
              new DriveDistanceCmd(this.c_Drive, 0.5, 4, 0, 4000)

              new DriveTimeCmd(this.c_Drive, 0.5, 0, 1000),

              new TurnCmd(this.c_Drive, this.c_Gyro, 90, 4000),
  
              new DriveTimeCmd(this.c_Drive, 0.5, 0, 2000),

              new TurnCmd(this.c_Drive, this.c_Gyro, -90, 4000),

              new DriveTimeCmd(this.c_Drive, 0.6, 0, 2000),
  
              new DriveTimeCmd(this.c_Drive, 0, 0, 0)

          ));
  
      CommandScheduler.getInstance().cancelAll();
  
      CommandScheduler.getInstance().schedule(m_sequentialCommands);
    }
  
    public void periodic() {

      CommandScheduler.getInstance().run();

    }
  }