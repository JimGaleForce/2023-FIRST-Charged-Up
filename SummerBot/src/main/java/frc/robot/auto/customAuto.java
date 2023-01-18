package frc.robot.auto;

import frc.robot.CustomDrive;

public class customAuto {

    CustomDrive m_drive;
    int timer;

    public customAuto(CustomDrive drive) {
        this.m_drive = drive;
    }

    public void periodic() {
        timer++;

        if (timer < 50) {
            m_drive.arcadeDrive(0.5, 0);
        } else {
            m_drive.arcadeDrive(0, 0);
        }
    }
}
