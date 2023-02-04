package frc.robot.auto;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.CustomDrive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class StraitMonkeyCheeseburger {
    CustomDrive c_Drive;
    int timer;
  
    ADIS16470_IMU c_Gyro = new ADIS16470_IMU();
  
    public StraitMonkeyCheeseburger(CustomDrive c_drive) {
      this.c_Drive = c_drive;
    }
  
    public void init() {
      c_Gyro.reset();
      timer = 0;
  

      Command m_sequentialCommands = new SequentialCommandGroup(
          new SequentialCommandGroup(

  
              new DriveTimeCmd(this.c_Drive, .6, 0, 3500)


          ));
  
      CommandScheduler.getInstance().cancelAll();
  
      CommandScheduler.getInstance().schedule(m_sequentialCommands);
    }
  
    public void periodic() {
  
      CommandScheduler.getInstance().run();
  
    }
}
