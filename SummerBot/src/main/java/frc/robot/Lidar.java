package frc.robot;
import edu.wpi.first.wpilibj.Counter;

public class Lidar {

    private Counter counter;

    private static final double IN_TO_CM = 2.54;
    private static final double CM_PER_CYCLE = 100000;

    // LIDAR sensor with method to get distance in inches
    //  using a running average over 5 measurements
    public Lidar() { this(Constants.DIO_LIDAR); }

    public Lidar(int channel) {
        counter = new Counter(channel);
        counter.setMaxPeriod(1);
        counter.setSemiPeriodMode(true);
        counter.setSamplesToAverage(5);
        counter.reset();
    }

    public double getDistance() {
        return counter.getPeriod() * CM_PER_CYCLE / IN_TO_CM;
    }
}
