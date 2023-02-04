package frc.robot;

import edu.wpi.first.networktables.*;

public class Limelight {

    private NetworkTable table;

    public enum Pipeline {
        CONE(0), CUBE(1), DRIVING(2);

        private int value;
        private Pipeline(int value) { this.value = value; }
    }

    public enum LED {
        DEFAULT(0), OFF(1), BLINK(2), ON(3);
        
        private int value;
        private LED(int value) { this.value = value; }
    }

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double getTargetX() {
        return table.getEntry("tx").getDouble(0);
    }

    public double getTargetY() {
        return table.getEntry("ty").getDouble(0);
    }

    public boolean hasValidTarget() {
        return table.getEntry("tv").getDouble(0) == 1;
    }

    public double getTargetArea() {
        return table.getEntry("ta").getDouble(0);
    }

    public void setPipeline(Pipeline pipeline) {
        table.getEntry("pipeline").setNumber(pipeline.value);
    }

    public void setLED(LED state) {
        table.getEntry("ledMode").setNumber(state.value);
    }

    public LED getLED() {
        switch ((int) table.getEntry("ledMode").getDouble(0)) {
            case 0:
            default:
                return LED.DEFAULT;
            case 1:
                return LED.OFF;
            case 2:
                return LED.BLINK;
            case 3:
                return LED.ON;
        }
    }
}
