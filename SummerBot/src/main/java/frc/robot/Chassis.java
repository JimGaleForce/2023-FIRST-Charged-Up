package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Chassis {
    VictorSPX m_frontLeft, m_rearLeft;
    PWMVictorSPX m_frontRight, m_rearRight;
    MotorControllerGroup m_right;
    CustomDrive m_drive;

    public Chassis() {
        m_frontLeft = new VictorSPX(Constants.CAN_FL_MOTOR);
        m_rearLeft = new VictorSPX(Constants.CAN_RL_MOTOR);
        m_frontRight = new PWMVictorSPX(Constants.PWM_FR_MOTOR);
        m_rearRight = new PWMVictorSPX(Constants.PWM_RR_MOTOR);

        m_rearLeft.follow(m_frontLeft);
        m_right = new MotorControllerGroup(m_frontRight, m_rearRight);
        m_right.setInverted(true);


        m_drive = new CustomDrive(m_frontLeft, m_right);
    }

    public void arcadeDrive(double lat, double rot) {
        m_drive.arcadeDrive(lat, rot);
    }
}
