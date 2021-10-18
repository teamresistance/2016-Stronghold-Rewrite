package frc.robot.io.hdw_io;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

public class IO {
    // PDP
    public static PowerDistributionPanel pdp = new PowerDistributionPanel(1);

    // Air
    public static Compressor compressor = new Compressor(0);
    public static Relay compressorRelay = new Relay(0);

    // Drive
    public static VictorSP leftDrv = new VictorSP(0); // Cmds left wheels
    public static VictorSP rightDrv = new VictorSP(1); // Cmds right wheels
    //public static DifferentialDrive diffDrv = new DifferentialDrive(leftDrv, leftDrv);

    public static Solenoid frntLedLift1 = new Solenoid(6);
    public static Solenoid frntLedLift2 = new Solenoid(0);
    public static Solenoid frntLedSnorf = new Solenoid(7);

    // Snorfler
    public static VictorSP snorfMtr = new VictorSP(3);  //Sucks in (+) or spits out (-) the ball
    public static InvertibleSolenoid snorfExtSV = new InvertibleSolenoid(0, 3); // Extends both feeders
    public static InvertibleDigitalInput snorfHasBall = new InvertibleDigitalInput(0, false);   //A ball in the snorfler

    // Catapult (Shooter)
    public static Solenoid catapult = new Solenoid(5);

    // Antlers
    public static Solenoid antlerDn = new Solenoid(1);

    // Flipper
    public static Solenoid flipperDn = new Solenoid(4);
    public static DigitalInput fliperIsUp = new DigitalInput(1);
    public static DigitalInput fliperIsDn = new DigitalInput(2);

    // Lifter (Climber)
    public static Victor liftUP = new Victor(2); // Moves climber hook up
    public static ISolenoid liftExt = new InvertibleSolenoid(0, 2); //Extends Lifter forward

    public static DigitalInput liftTopStop = new DigitalInput(3);
    public static DigitalInput liftMidSnsr = new DigitalInput(4);
    public static DigitalInput liftBotStop = new DigitalInput(5);


    /**Initialize any hardware.  Usually called from robotInit in Robot. */
    public static void init() {
    }

    /**Periodicly update any hardware here.  Usually called from robotPeriodic in Robot. */
    public static void update() {
    }

}
