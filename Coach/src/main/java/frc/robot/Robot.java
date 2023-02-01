package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import frc.robot.auto.*;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.CvSource;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Robot extends TimedRobot {
  VictorSPX m_frontLeft, m_rearLeft;
  PWMVictorSPX m_frontRight, m_rearRight;
  
  MotorControllerGroup m_right;
  CustomDrive m_drive;
  Joystick m_controller;

  Counter m_counter;

  HybridCone m_auto;

  // CameraServer m_camera;
  Thread m_visionThread;

  /**
   * 
   */
  @Override
  public void robotInit() {
    m_frontLeft = new VictorSPX(0);
    m_rearLeft = new VictorSPX(1);
    m_frontRight = new PWMVictorSPX(0);
    m_rearRight = new PWMVictorSPX(1);

    m_rearLeft.follow(m_frontLeft);
    m_right = new MotorControllerGroup(m_frontRight, m_rearRight);
    m_right.setInverted(true);


    m_drive = new CustomDrive(m_frontLeft, m_right);
    m_controller = new Joystick(0);

    m_counter = new Counter(0);
    m_counter.setMaxPeriod(1);
    m_counter.setSemiPeriodMode(true);
    m_counter.reset();

    m_auto = new aidato(m_drive);

    // CameraServer.startAutomaticCapture("null", 0);

    m_visionThread = new Thread(
      () -> {
        UsbCamera camera = CameraServer.startAutomaticCapture();
        camera.setResolution(640, 480);
        CvSink cvsink = CameraServer.getVideo();
        CvSource outputstream = CameraServer.putVideo("Science", 640, 480);
        Mat mat = new Mat();

        while (!Thread.interrupted()) {
          if (cvsink.grabFrame(mat) == 0) {
            System.out.println("Stream Error");
            continue;
          }
          
          // System.out.println(" " + mat.width() + mat.height());
          // Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400), new Scalar(0, 255, 255), -5);
          Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2HSV);
          Imgproc.medianBlur(mat, mat, 5);
          //Core.inRange(mat, new Scalar(30, 100, 100), new Scalar(80, 200, 200), mat);
          outputstream.putFrame(mat);
        }
      }
    );
    m_visionThread.setDaemon(true);
    m_visionThread.start();

  }

  @Override
  public void autonomousInit() {
    m_auto.init();
  }

  @Override
  public void autonomousPeriodic() {
    m_auto.periodic();
  }

  @Override
  public void teleopPeriodic() {
    m_drive.arcadeDrive(-m_controller.getRawAxis(1), 0.6*m_controller.getRawAxis(0));
    
  // System.out.println(getDistance());
  // if (getDistance() > 20) {
  //   m_drive.arcadeDrive(-0.5, 0);
  // } else {
  //   m_drive.arcadeDrive(0, 0);
  // }
  }

  public double getDistance() {
    return m_counter.getPeriod() * 100000 / 2.54;
  }
}
