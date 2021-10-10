package frc.robot.io.hdw_io;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.I2C;
// import edu.wpi.first.wpilibj.PWM;
// import edu.wpi.first.wpilibj.PWMSpeedController;
// import edu.wpi.first.wpilibj.AnalogPotentiometer;
// import edu.wpi.first.wpilibj.Counter;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import com.revrobotics.ColorSensorV3;

/* temp to fill with latest faults */
// import com.ctre.phoenix.motorcontrol.*;
// import com.ctre.phoenix.motorcontrol.can.*;

public class IO {
    // navX
    public static NavX navX = new NavX();

    // PDP
    public static PowerDistributionPanel pdp = new PowerDistributionPanel(21);

    // Air compressor
    public static Compressor compressor = new Compressor(); //Default CAN Adr is 0 for PCM.
    public static Relay compressorRelay = new Relay(0);

    // Drive
    // public static PWM leftDrv = new PWM(0); // Cmds left wheels
    // public static PWM rightDrv = new PWM(1); // Cmds right wheels
    //public static DifferentialDrive diffDrv = new DifferentialDrive(leftDrv, leftDrv);
    
    // Drive, assign snorfler as front.  Front can be swapped.
    public static VictorSP drvMtrAB_L = new VictorSP(0); // Cmds left wheels
    public static VictorSP drvMtrAB_R = new VictorSP(1); // Cmds right wheels
    public static DifferentialDrive diffDrv_M = new DifferentialDrive(drvMtrAB_L, drvMtrAB_R);
    
    public static final double drvMasterTPF_L = 368.4;  // 1024 t/r (0.5' * 3.14)/r 9:60 gr = 385.4  calibrated= 364.63
    public static final double drvMasterTPF_R = -368.4; // 1024 t/r (0.5' * 3.14)/r 9:60 gr = 385.4  calibrated= 364.63
    // public static Encoder drvEnc_L = new Encoder(drvMasterTSRX_L, drvMasterTPF_L);  //Interface for feet, ticks, reset
    // public static Encoder drvEnc_R = new Encoder(drvMasterTSRX_R, drvMasterTPF_R);
    public static Encoder drvEnc_L = new Encoder(10,11);  //Interface for feet, ticks, reset
    public static Encoder drvEnc_R = new Encoder(12,13);  //Interface for feet, ticks, reset
    public static void drvFeetRst() { drvEnc_L.reset(); drvEnc_R.reset(); }
    public static double drvFeet() { return (drvEnc_L.getDistance() + drvEnc_R.getDistance()) / 2.0; }



    // Snorfler
    // public static Victor snorfFeedMain = new Victor(9);
    // public static Victor snorfFeedScdy = new Victor(6);
    // public static ISolenoid snorflerExt = new InvertibleSolenoid(22, 6); // Extends both feeders
    // public static InvertibleDigitalInput snorfHasBall = new InvertibleDigitalInput(2, false);

    // // Climb
    // public static Victor climberHoist = new Victor(3); // Extends climber
    // public static ISolenoid climberExt = new InvertibleSolenoid(22, 7);

  
    // public static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    // public static NavXIMU imu = new NavXIMU();

    // Initialize any hardware here
    public static void init() {
        drvEnc_L.setDistancePerPulse(1/drvMasterTPF_L);
        drvEnc_R.setDistancePerPulse(1/drvMasterTPF_R);
    }

    public static void update() {
        
    }


    //--------------------  XY Coordinates -----------------------------------
    private static double prstDist;     //Present distance traveled since last reset.
    private static double prvDist;      //previous distance traveled since last reset.
    private static double deltaD;       //Distance traveled during this period.
    private static double coorX = 0;    //Calculated X (Left/Right) coordinate on field
    private static double coorY = 0;    //Calculated Y (Fwd/Bkwd) coordinate on field.
    
    /**Calculates the XY coordinates by taken the delta distance and applying the sinh/cosh 
     * of the gyro heading.
     * <p>Initialize by calling resetLoc.
     * <p>Needs to be called periodically from IO.update called in robotPeriodic in Robot.
     */
    public static void coorUpdate(){
        // prstDist = (drvEnc_L.feet() + drvEnc_R.feet())/2;   //Distance since last reset.
        prstDist = drvFeet();   //Distance since last reset.
        deltaD = prstDist - prvDist;                        //Distancce this pass
        prvDist = prstDist;                                 //Save for next pass

        //If encoders are reset by another method, may cause large deltaD.
        //During testing deltaD never exceeded 0.15 on a 20mS update.
        if (Math.abs(deltaD) > 0.2) deltaD = 0.0;       //Skip this update if too large.

        if (Math.abs(deltaD) > 0.0){    //Deadband for encoders if needed (vibration?).  Presently set to 0.0
            coorY += deltaD * Math.cos(Math.toRadians(IO.navX.getAngle())) * 1.0;
            coorX += deltaD * Math.sin(Math.toRadians(IO.navX.getAngle())) * 1.1;
        }
    }

    /**Reset the location on the field to 0.0, 0.0.
     * If needed navX.Reset must be called separtely.
     */
    public static void resetCoor(){
        // IO.navX.reset();
        // encL.reset();
        // encR.reset();
        coorX = 0;
        coorY = 0;
        prstDist = (drvEnc_L.getDistance() + drvEnc_R.getDistance())/2;
        prvDist = prstDist;
        deltaD = 0;
    }

    /**
     * @return an array of the calculated X and Y coordinate on the field since the last reset.
     */
    public static double[] getCoor(){
        double[] coorXY = {coorX, coorY};
        return coorXY;
    }

    /**
     * @return the calculated X (left/right) coordinate on the field since the last reset.
     */
    public static double getCoorX(){
        return coorX;
    }

    /**
     * @return the calculated Y (fwd/bkwd) coordinate on the field since the last reset.
     */
    public static double getCoorY(){
        return coorY;
    }

    /**
     * @return the calculated Y (fwd/bkwd) coordinate on the field since the last reset.
     */
    public static double getDeltaD(){
        return deltaD;
    }
}
