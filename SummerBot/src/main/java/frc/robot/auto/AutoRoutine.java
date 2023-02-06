package frc.robot.auto;

/**
 * Interface that all autonomous routines must implement to be
 * selectable from the driver station dashboard. An autonomous routine
 * may pass subsystems as constructor parameters. If command-based
 * programming is used, it is the author's responsibility to terminate
 * the command instance appropriately.
 */
public interface AutoRoutine {

    /** Called exactly once during Robot::autonomousInit() */
    default public void init() {
        System.out.println("default init() method");
    }

    /** Called repeatedly during Robot::autonomousPeriodic() */
    default public void periodic() {
        System.out.println("default periodic() method");
    }

    /** Called exactly once after autonomous,
     * during Robot::autonomousExit() and before Robot::disabledInit() */
    default public void exit() {
        System.out.println("default exit() method");
    }
}
