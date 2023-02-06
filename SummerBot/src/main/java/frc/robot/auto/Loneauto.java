package frc.robot.auto;
import frc.robot.Chassis;
import edu.wpi.first.wpilibj.ADIS16470_IMU;


public class Loneauto implements AutoRoutine {
    Chassis m_drive;
    int timer;
    ADIS16470_IMU m_gyro;

    public Loneauto(Chassis drive, ADIS16470_IMU gyro){
        m_drive = drive;
        m_gyro = gyro;
    }
    public void init(){
        timer = 0;
    }
    public void periodic(){
        timer++;
        if(timer < 50){
            m_drive.arcadeDrive(0.5,0);
        }else if(timer < 200 && m_gyro.getAngle() < 270){
            m_drive.arcadeDrive(0,1);
        }else if(timer < 250){
            m_drive.arcadeDrive(1,0);
        }else if(timer < 350 && m_gyro.getAngle() > 0){
            m_drive.arcadeDrive(0,-1);
        }else if(timer < 450){
            m_drive.arcadeDrive(1,0);
        }else{
            m_drive.arcadeDrive(0,0);
        }
}
}