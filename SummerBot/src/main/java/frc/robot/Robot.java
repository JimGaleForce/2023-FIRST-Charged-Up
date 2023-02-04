package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.*;


public class Robot extends TimedRobot {
    Chassis chassis;
    Controller controller;
    Lidar lidar;
    Limelight limelight;
    ADIS16470_IMU gyro;

    AutoRoutine autoRoutine;
    SendableChooser<AutoRoutine> chooser;

    @Override
    public void robotInit() {
        chassis = new Chassis();
        controller = new Controller();
        lidar = new Lidar();
        limelight = new Limelight();
        
        gyro = new ADIS16470_IMU();
        gyro.calibrate();

        chooser = new SendableChooser<AutoRoutine>();
        chooser.setDefaultOption("Aiden 1", new aidato(chassis));
        chooser.addOption("Brady 1", new BradyAuto(chassis.m_drive));
        chooser.addOption("Kellen 1", new ConeConeCone(chassis.m_drive));
        chooser.addOption("Brady/Kellen 1", new customAuto(chassis.m_drive));
        chooser.addOption("Lehua 1", new Loneauto(chassis.m_drive));
        chooser.addOption("Lehua 2", new Ltwoauto(chassis.m_drive));
        chooser.addOption("Chris 1", new DemoAuto(chassis, limelight, gyro));

        SmartDashboard.putNumber("Lateral Speed Factor", Constants.LATERAL_SPEED_FACTOR);
        SmartDashboard.putNumber("Angular Speed Factor", Constants.ANGULAR_SPEED_FACTOR);
        SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
    }

    @Override
    public void robotPeriodic() {
        Constants.ANGULAR_SPEED_FACTOR = SmartDashboard.getNumber("Lateral Speed Factor", 1.0);
        Constants.LATERAL_SPEED_FACTOR = SmartDashboard.getNumber("Angular Speed Factor", 0.7);
    }

    @Override
    public void autonomousInit() {
        autoRoutine = chooser.getSelected();
        autoRoutine.init();
    }

    @Override
    public void autonomousPeriodic() {
        autoRoutine.periodic();
    }

    @Override
    public void teleopPeriodic() {
        chassis.arcadeDrive(controller.getLateralVelocity(), controller.getAngularVelocity());
        System.out.println(lidar.getDistance());

        if (controller.getConeTrackingButton()) {
            System.out.println("This feature is not implemented yet!");
        }
    }
}

