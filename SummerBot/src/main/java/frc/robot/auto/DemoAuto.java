package frc.robot.auto;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import frc.robot.Chassis;
import frc.robot.Limelight;
import frc.robot.Limelight.*;

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
    }

    public void periodic() {
        timer++;

        if (timer % 40 < 10) {
            limelight.setLED(LED.ON);
        } else {
            limelight.setLED(LED.OFF);
        }

        if (direction) {
            chassis.arcadeDrive(0, 0.3);
            if (gyro.getAngle() < -90) {
                direction = false;
            }
        } else {
            chassis.arcadeDrive(0, -0.4);
            if (gyro.getAngle() > 90) {
                direction = true;
            }
        }

        
    }
}
