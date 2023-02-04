package frc.robot.auto;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import frc.robot.CustomDrive;

public class ConeConeCone implements AutoRoutine {

    CustomDrive c_Drive;
    int timer;

    ADIS16470_IMU c_Gyro = new ADIS16470_IMU();

    public ConeConeCone(CustomDrive c_drive){
        this.c_Drive = c_drive;
    }
    public void init(){
        c_Gyro.reset();
        timer = 0;
    }

    public void periodic(){
        timer++;
        if(timer < 100){
            c_Drive.arcadeDrive(.5, 0);
        }else if(timer == 101 && c_Gyro.getAngle() < -85){
            c_Drive.arcadeDrive(0, .5);
            timer = 100;
        }else if (timer < 210){
            c_Drive.arcadeDrive(.5, 0);
        }else{
            c_Drive.arcadeDrive(0,0);
        }
    }
}











