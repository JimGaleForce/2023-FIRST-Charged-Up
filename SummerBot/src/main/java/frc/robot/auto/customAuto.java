package frc.robot.auto;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import frc.robot.Chassis;

public class customAuto implements AutoRoutine {

    Chassis m_drive;
    int timer;
    ADIS16470_IMU newGyro;
    
    public customAuto(Chassis drive, ADIS16470_IMU gyro) {
        this.m_drive = drive;
        newGyro = gyro;
    }

    public void init() {
        newGyro.reset();
        timer = 0;
    }

    public void periodic() {
        timer++;
        System.out.println(newGyro.getAngle());
        // if (timer < 50) {
        //     m_drive.arcadeDrive(0.4, 0);
        // }else if (timer == 50 && newGyro.getAngle() > -90){
        //     m_drive.arcadeDrive(0, .4);
        //     timer = 49;
        // }else if(timer < 300) {
        //     m_drive.arcadeDrive(.4, 0);
        // } else {
        //     m_drive.arcadeDrive(0, 0);
        // }

        if (timer == 1 && newGyro.getAngle() > -175) {
            m_drive.arcadeDrive(0, 0.5);
            timer = 0;
        } else if (timer < 10) {
            m_drive.arcadeDrive(0, 0);
        } else if (timer < 280) {
            m_drive.arcadeDrive(0.5, 0);
        } else if (newGyro.getAngle() < -5 && timer == 281) {
            m_drive.arcadeDrive(0, -0.5);
        } else if (timer < 500) {
            m_drive.arcadeDrive(0.5, 0);
        } else if (newGyro.getAngle() < 5){
            m_drive.arcadeDrive(0, -.5);
        }
        else {
            m_drive.arcadeDrive(0, 0);
        }
    }
}
