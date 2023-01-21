package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import frc.robot.auto.customAuto;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.ADIS16448_IMU;

public class Robot extends TimedRobot {
  VictorSPX m_frontLeft, m_rearLeft;
  PWMVictorSPX m_frontRight, m_rearRight;
  
  MotorControllerGroup m_right;
  CustomDrive m_drive;
  Joystick m_controller;

  Counter m_counter;

  customAuto m_auto;

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

    m_auto = new customAuto(m_drive);
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
    m_drive.arcadeDrive(m_controller.getRawAxis(1),.7*m_controller.getRawAxis(0));
  // System.out.println(getDistance());
  // if (getDistance() > 20) {
  //   m_drive.arcadeDrive(-0.5, 0);
  // } else {
  //   m_drive.arcadeDrive(0, 0);
  // }
  }

  public double getDistance() {
    return m_counter.getPeriod() * 10000000 / 2.54;
  }
}
