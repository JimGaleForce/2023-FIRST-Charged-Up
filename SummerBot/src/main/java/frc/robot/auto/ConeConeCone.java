package frc.robot.auto;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Chassis;
import frc.robot.auto.commands.DriveTimeCmd;
import frc.robot.auto.commands.TurnCmd;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class ConeConeCone implements AutoRoutine {

  Chassis c_Drive;
  int timer;

  ADIS16470_IMU c_Gyro;

  public ConeConeCone(Chassis c_drive, ADIS16470_IMU c_gyro) {
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
            // drive forward at 50% for 1 second
            new DriveTimeCmd(this.c_Drive, 0.5, 0, 1000),

            // turn 90 degrees, timeout at 4 seconds
            new TurnCmd(this.c_Drive, this.c_Gyro, 90, 4000),

            // drive forward at 50% for 1 second
            new DriveTimeCmd(this.c_Drive, 0.5, 0, 1000),

            // drive backward at 50% for 1 second
            new DriveTimeCmd(this.c_Drive, -0.5, 0, 1000),

            // turn -90 degrees, timeout at 4 seconds
            new TurnCmd(this.c_Drive, this.c_Gyro, -90, 4000),

            // drive backward at 50% for 1 second
            new DriveTimeCmd(this.c_Drive, -0.5, 0, 1000),

            // stop
            new DriveTimeCmd(this.c_Drive, 0, 0, 0)
        // new Sequential2(this.c_Drive)
        // new ParallelCommandGroup(
        // new Parallel1(),
        // new Parallel2()
        // )
        // ),
        // new SequentialCommandGroup(
        // new Sequential3(),
        // new ParallelCommandGroup(
        // new Parallel3()
        // )
        // )
        ));

    // Cancel any previous commands, in case there was a false start.
    CommandScheduler.getInstance().cancelAll();

    // Run the new commands.
    CommandScheduler.getInstance().schedule(m_sequentialCommands);
  }

  public void periodic() {

    // Run the commands (which executes the periodic on all the current commands).
    CommandScheduler.getInstance().run();

    // previous code
    // timer++;
    // if(timer < 100){
    // c_Drive.arcadeDrive(.5, 0);
    // }else if(timer == 101 && c_Gyro.getAngle() < -85){
    // c_Drive.arcadeDrive(0, .5);
    // timer = 100;
    // }else if (timer < 210){
    // c_Drive.arcadeDrive(.5, 0);
    // }else{
    // c_Drive.arcadeDrive(0,0);
    // }
  }
}