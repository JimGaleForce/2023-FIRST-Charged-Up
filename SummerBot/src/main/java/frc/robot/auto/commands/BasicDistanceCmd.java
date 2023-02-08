package frc.robot.auto.commands;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Basic;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Chassis;

public class BasicDistanceCmd extends CommandBase {
    private Chassis m_chassis;
    private double distanceInches;
    private int timer;
    private final double kConversion = 0.5;

    public BasicDistanceCmd(Chassis m_chassis, double distanceInches) {
        this.m_chassis = m_chassis;
        this.distanceInches = distanceInches;
    }

    @Override
    public void initialize() {
        m_chassis.arcadeDrive(0, 0);
        timer = 0;
    }

    @Override
    public void execute() {
        timer++;
        System.out.println(timer);
        if (timer < distanceInches*kConversion) {
            m_chassis.arcadeDrive(0.5, 0);
        } else {
            m_chassis.arcadeDrive(0, 0);
        }
    }

    @Override
    public boolean isFinished() {
        
        return (timer > distanceInches*kConversion);
    }
    
}
