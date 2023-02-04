package frc.robot.auto;

public interface AutoRoutine {

    default public void init() {
        System.out.println("Warning! This class does not override the default init()!");
    };
    
    default public void periodic() {
        System.out.println("Warning! This class does not override the default periodic()!");
    };
}
