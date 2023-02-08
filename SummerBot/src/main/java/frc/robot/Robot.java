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
        chooser.setDefaultOption("Aiden 1", new aidato(chassis, gyro));
        chooser.addOption("Brady 1", new BradyAuto(chassis, gyro, limelight));
        chooser.addOption("Kellen 1", new ConeConeCone(chassis, gyro));
        chooser.addOption("Brady/Kellen 1", new customAuto(chassis, gyro));
        chooser.addOption("Lehua 1", new Loneauto(chassis, gyro));
        chooser.addOption("Lehua 2", new Ltwoauto(chassis, gyro));
        chooser.addOption("Chris 1", new DemoAuto(chassis, limelight, gyro));
        chooser.addOption("Lehua 3", new SelfBalance(chassis, gyro));
        SmartDashboard.putData(chooser);

        SmartDashboard.putNumber("Lateral Speed Factor", Constants.LATERAL_SPEED_FACTOR);
        SmartDashboard.putNumber("Angular Speed Factor", Constants.ANGULAR_SPEED_FACTOR);
        updateValues();
    }

    /** To be called in every periodic method, and will update SmartDashboard. */
    public void updateValues() {
        SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
        SmartDashboard.putNumber("Gyro X", gyro.getXComplementaryAngle());
        SmartDashboard.putNumber("Gyro Y", gyro.getYComplementaryAngle());
        SmartDashboard.putNumber("LIDAR Distance", lidar.getDistance());
        SmartDashboard.putNumber("Limelight dX", limelight.getTargetX());
        SmartDashboard.putNumber("Limelight dY", limelight.getTargetY());
        SmartDashboard.putNumber("Limelight Target Area", limelight.getTargetArea());
    }

    @Override
    public void robotPeriodic() {
        Constants.ANGULAR_SPEED_FACTOR = SmartDashboard.getNumber("Lateral Speed Factor", 1.0);
        Constants.LATERAL_SPEED_FACTOR = SmartDashboard.getNumber("Angular Speed Factor", 0.7);

        updateValues();
    }

    @Override
    public void autonomousInit() {
        autoRoutine = chooser.getSelected();
        System.out.println("\n\n\n>>> Running autonomous: " + autoRoutine.getClass().getName() + "\n\n\n");
        autoRoutine.init();
    }

    @Override
    public void autonomousPeriodic() {
        autoRoutine.periodic();
    }

    @Override
    public void autonomousExit() {
        autoRoutine.exit();
        System.out.println("\n\n\n>>> Exiting autonomous: " + autoRoutine.getClass().getName() + "\n\n\n");
        autoRoutine = null;
    }

    @Override
    public void teleopPeriodic() {
        chassis.arcadeDrive(controller.getLateralVelocity(), controller.getAngularVelocity());

        if (controller.getConeTrackingButton()) {
            System.out.println("This feature is not implemented yet!");
        }
    }

    @Override
    public void disabledInit() {
        if (autoRoutine != null) {
            autoRoutine.exit();
            autoRoutine = null;
        }
    }

    @Override
    public void disabledPeriodic() {
        updateValues();
    }
}

