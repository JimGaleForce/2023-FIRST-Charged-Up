package frc.robot.auto;
import frc.robot.Chassis;
import edu.wpi.first.wpilibj.ADIS16470_IMU;

public class Ltwoauto implements AutoRoutine {
    Chassis m_drive;
    int timer;
    ADIS16470_IMU m_gyro;

    public Ltwoauto(Chassis drive, ADIS16470_IMU gyro) {
        m_drive = drive;
        m_gyro = gyro;
    }

    public void init() {
        timer = 0;
    }

    public void periodic() {
     //   timer++;
        if(timer < 150){
            m_drive.arcadeDrive(1,0);
        }else if(timer > 150 && m_gyro.getAngle() < 180){
           m_drive.arcadeDrive(0,1);
        }else if(timer < 400){
            m_drive.arcadeDrive(1,0);
        }else if(timer > 400 && m_gyro.getAngle() < 270){
            m_drive.arcadeDrive(0,1);
        }else if(timer < 550){
            
            m_drive.arcadeDrive(1,0);
        }else if(timer > 550 && m_gyro.getAngle() < 360){
            m_drive.arcadeDrive(0,1);
        }else if(timer < 625){
            m_drive.arcadeDrive(0.5,0);
        }else{
            m_drive.arcadeDrive(0,0);
        }
    }
}


