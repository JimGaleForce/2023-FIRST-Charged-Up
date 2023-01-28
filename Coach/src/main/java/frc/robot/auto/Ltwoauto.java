package frc.robot.auto;
import frc.robot.CustomDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Ltwoauto {
    CustomDrive m_drive;
    int timer;
    Gyro m_gyro;


    public Ltwoauto(CustomDrive drive){
        m_drive = drive;
    }
    public void init(){
        timer = 0;
    }
    public void periodic(){
        timer++;
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


