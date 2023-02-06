package frc.robot.auto;


import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Chassis;
import frc.robot.auto.commands.DriveDistanceCmd;
import frc.robot.auto.commands.DriveTimeCmd;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class aidato implements AutoRoutine {

        Chassis c_Drive;
        int timer;
    
        ADIS16470_IMU c_Gyro;
    
        public aidato(Chassis c_drive, ADIS16470_IMU c_Gyro) {
            this.c_Drive = c_drive;
            this.c_Gyro = c_Gyro;
        }
    
        public void init() {
            c_Gyro.reset();

            Command m_sequentialCommands = new SequentialCommandGroup(
                new DriveDistanceCmd(c_Drive, 0.5, 2, 0, 15000),
                new DriveTimeCmd(this.c_Drive, 0.5, 0, 1000)

                /*new DriveTimeCmd(this.c_Drive, 0.6, 0, 1700),

                new TurnCmd(this.c_Drive, this.c_Gyro, 90, 4000),

                new DriveTimeCmd(this.c_Drive, 0.6 , 0, 2000),

                new TurnCmd(this.c_Drive, this.c_Gyro, -90, 3900),

                new DriveTimeCmd(this.c_Drive, 0.7, 0, 1100),

                new DriveTimeCmd(this.c_Drive, 0, 0, 0)*/

                );
    
            CommandScheduler.getInstance().cancelAll();
    
            CommandScheduler.getInstance().schedule(m_sequentialCommands);
        }
    
        public void periodic() {

            CommandScheduler.getInstance().run();

        }
    }