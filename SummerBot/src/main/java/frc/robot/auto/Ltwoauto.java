package frc.robot.auto;
import frc.robot.CustomDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Ltwoauto {
    CustomDrive m_drive;
    int timer;
    Gyro m_gyro;

    public Ltwoauto(CustomDrive drive) {
        m_drive = drive;
    }
    
    public void init() {
        timer = 0;
    }
    
    public void periodic() {
        timer++;
        if (timer < 150) {
            m_drive.arcadeDrive(1,0);
        } else if (timer == 151 && m_gyro.getAngle() < 175) {
           m_drive.arcadeDrive(0,1);
            timer = 150;
        } else if (timer < 300) {
            m_drive.arcadeDrive(1,0);
        } else if (timer == 301 && m_gyro.getAngle() < 267) {
            m_drive.arcadeDrive(0,1);
            timer = 300;
        } else if (timer < 350) {
            m_drive.arcadeDrive(1,0);
        } else if (timer == 351 && m_gyro.getAngle() < 356) {
            m_drive.arcadeDrive(0,1);
            timer = 350;
        } else if (timer < 425) {
            m_drive.arcadeDrive(0.5,0);
        } else {
            m_drive.arcadeDrive(0,0);
        }
    }
}


