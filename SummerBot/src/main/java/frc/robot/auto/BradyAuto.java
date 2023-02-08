package frc.robot.auto;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import frc.robot.Chassis;
import frc.robot.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class BradyAuto implements AutoRoutine {
        ADIS16470_IMU gyro;
        Chassis m_drive;
        int timer;
        Limelight limelight;

        public void init() {
            gyro.reset();
            timer = 0;
        }
    
        public BradyAuto(Chassis drive, ADIS16470_IMU gyro, Limelight limelight) {
            this.m_drive = drive;
            this.gyro = gyro;
            this.limelight = limelight;
        }
    
        public void periodic() {
            timer++;
           // tv    Whether the limelight has any valid targets (0 or 1)
           // tx	Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
           // ty	Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
           // ta	Target Area (0% of image to 100% of image) (a number from 0 to 100, not 0 to 1)

            double x = limelight.getTargetX();
            double y = limelight.getTargetY();
            double area = limelight.getTargetArea();
            boolean tv = limelight.hasValidTarget();

            // if (y>7){
            //     m_drive.arcadeDrive(4,0);
            // }
            //  if (timer < 75) {
            //      m_drive.arcadeDrive(.4, 0);
            //  }
            //  else if (timer<300){ 
            //      m_drive.arcadeDrive(0,.6);

            //     if(gyro.getAngle() <= -74){
            //        m_drive.arcadeDrive(0.5,0);
            //    }
                
            //  }
            //  else if(timer < 400){
            //      m_drive.arcadeDrive(0,-.6);

            //      if(gyro.getAngle() >= 35){
            //          m_drive.arcadeDrive(0.5,0);
            //     }
            //  }
            //  else {
            //      m_drive.arcadeDrive(0, 0);
            //  }

            if (limelight.hasValidTarget()) {
                if (area < 10)
                    m_drive.arcadeDrive(0.3, 0);
            } else {
                m_drive.arcadeDrive(0,0);
            }
        }
}
