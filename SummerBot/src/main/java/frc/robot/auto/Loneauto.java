package frc.robot.auto;
import frc.robot.CustomDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;


public class Loneauto {
    CustomDrive m_drive;
    int timer;
    Gyro m_gyro;

    public Loneauto(CustomDrive drive){
        m_drive = drive;
    }
    public void init(){
        timer = 0;
    }
    public void periodic(){
        timer++;
        if (timer < 50) {
            m_drive.arcadeDrive(0.5,0);
        } else if (timer < 200 && m_gyro.getAngle() < 270) {
            m_drive.arcadeDrive(0,1);
        } else if (timer < 250) {
            m_drive.arcadeDrive(1,0);
        } else if (timer < 350 && m_gyro.getAngle() > 0) {
            m_drive.arcadeDrive(0,-1);
        } else if(timer < 450) {
            m_drive.arcadeDrive(1,0);
        } else {
            m_drive.arcadeDrive(0,0);
        }
}
}