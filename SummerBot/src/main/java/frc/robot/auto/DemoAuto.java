package frc.robot.auto;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Chassis;
import frc.robot.Limelight;
import frc.robot.Limelight.*;
import frc.robot.auto.commands.BasicDistanceCmd;

/**
 * Basic autonomous routine that flashes the LEDs every 0.8 seconds
 * on a 25% duty cycle. This will also make the robot rotate in a
 * 180 degree path, 0.3 power clockwise and 0.4 counterclockwise.
 */
public class DemoAuto implements AutoRoutine {

    Chassis chassis;
    Limelight limelight;
    ADIS16470_IMU gyro;

    int timer;
    boolean direction;

    public DemoAuto(Chassis chassis, Limelight limelight, ADIS16470_IMU gyro) {
        this.chassis = chassis;
        this.limelight = limelight;
        this.gyro = gyro;
    }

    public void init() {
        this.gyro.reset();
        timer = 0;
        direction = true;
        CommandScheduler.getInstance().schedule(new BasicDistanceCmd(chassis, 60));
    }

    public void periodic() {
        CommandScheduler.getInstance().run();
    }

    public void exit() {
        CommandScheduler.getInstance().cancelAll();
    }

    // public void periodic() {
    //     timer++;

    //     if (timer % 40 < 10) {
    //         limelight.setLED(LED.ON);
    //     } else {
    //         limelight.setLED(LED.OFF);
    //     }

    //     if (direction) {
    //         chassis.arcadeDrive(0, 0.3);
    //         if (gyro.getAngle() < -90) {
    //             direction = false;
    //         }
    //     } else {
    //         chassis.arcadeDrive(0, -0.4);
    //         if (gyro.getAngle() > 90) {
    //             direction = true;
    //         }
    //     }

        
    // }
}
