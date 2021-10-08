package frc.robot.io;

import edu.wpi.first.wpilibj.PWM;

public class IO {
    // Drive System
    public static PWM leftDRV = new PWM(0);     //PWM splitter used to drive two motors
    public static PWM rightDRV = new PWM(1);
}
