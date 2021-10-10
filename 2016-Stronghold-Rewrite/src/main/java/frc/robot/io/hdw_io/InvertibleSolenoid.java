package frc.robot.io.hdw_io;

import edu.wpi.first.wpilibj.Solenoid;

// public class InvertibleSolenoid extends Solenoid implements ISolenoid {
public class InvertibleSolenoid extends Solenoid {

    private final boolean isInverted;

    /**
     * Constructor, default Solenoid Constructor, not inverted
     * @param module The CAN ID of the PCM the solenoid is attached to.
     * @param channel The channel on the PCM to control (0..7).
     */
    public InvertibleSolenoid(int module, int channel) {
        this(module, channel, false);
    }

    /**
     * Constructor to Makes Solenoid Object that is invertible.
     * @param module The CAN ID of the PCM the solenoid is attached to.
     * @param channel The channel on the PCM to control (0..7).
     * @param isInverted Invert the command sent to the channel.
     */
    public InvertibleSolenoid(int module, int channel, boolean isInverted) {
        super(module, channel);
        this.isInverted = isInverted;
    }

    //This activates the Solenoid
    @Override
    public void set(boolean state) {
        if (isInverted) {
            super.set(!state);
        } else {
            super.set(state);
        }
    }

    public boolean get(){
        return (isInverted ? !super.get() : super.get());
    }

}
