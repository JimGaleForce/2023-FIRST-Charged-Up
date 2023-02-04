package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {

    private Joystick joystick;

    public Controller() { this(0); }

    public Controller(int usbPort) {
        joystick = new Joystick(usbPort);
    }

    public double getLateralVelocity() {
        // Y-axes on the logitech controller are inverted by default.
        return -1*Constants.LATERAL_SPEED_FACTOR*joystick.getRawAxis(Constants.AXIS_LY);
    }

    public double getAngularVelocity() {
        return Constants.ANGULAR_SPEED_FACTOR*joystick.getRawAxis(Constants.AXIS_RX);
    }

    public boolean getConeTrackingButton() {
        return joystick.getRawButton(Constants.BUTTON_RT);
    }
}
