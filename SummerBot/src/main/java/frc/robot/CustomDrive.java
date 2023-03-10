package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class CustomDrive extends RobotDriveBase{
    VictorSPX m_left;
    MotorControllerGroup m_right;
    double k_defaultDeadband = 0.02;

    public CustomDrive(VictorSPX m_left, MotorControllerGroup m_right) {
        this.m_left = m_left;
        this.m_right = m_right;
    }

    public void arcadeDrive(double lat, double rot) {
        arcadeDrive(lat, rot, false);
    }

    public void arcadeDrive(double lat, double rot, boolean squared) {
        if (Math.abs(lat) < k_defaultDeadband)
            lat = 0;
        if (Math.abs(rot) < k_defaultDeadband)
        
        if (squared) {
            lat = Math.copySign(lat*lat, lat);
            // lat = 0.5*(lat + Math.copySign(lat*lat, lat));
            rot = Math.copySign(rot*rot, rot);
            // rot = 0.5*(rot + Math.copySign(rot*rot, rot));
        }

        double leftSpeed = lat + rot;
        double rightSpeed = lat - rot;
        double k_normalization = Math.max(1, Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed)));

        leftSpeed /= k_normalization;
        rightSpeed /= k_normalization;

        m_left.set(ControlMode.PercentOutput, leftSpeed);
        m_right.set(rightSpeed);

        feedWatchdog();
    }

    public void stopMotor() {
        m_left.set(ControlMode.PercentOutput, 0);
        m_right.set(0);
    }

    public String getDescription() {
        return "Custom Drive";
    }

    public void setBrake(boolean brakeMode) {
        m_left.setNeutralMode(NeutralMode.Brake);
    }
}
