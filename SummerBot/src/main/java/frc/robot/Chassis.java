package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Ideally, this class would have been combined with CustomDrive,
 * but in order to emulate WPI's library, which has an ArcadeDrive
 * that we access, I've kept these two classes separate.
 */
public class Chassis {
    private VictorSPX m_frontLeft, m_rearLeft;
    private PWMVictorSPX m_frontRight, m_rearRight;
    private MotorControllerGroup m_right;
    CustomDrive m_drive; // left public to avoid rewriting all auto routines

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

    public void arcadeDrive(double lat, double rot, boolean squared) {
        m_drive.arcadeDrive(lat, rot, squared);
    }

    public void setBrake(boolean brakeMode) {
        m_drive.setBrake(brakeMode);
    }
}
