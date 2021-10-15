package frc.robot.io.hdw_io;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import com.revrobotics.ColorSensorV3;

/* temp to fill with latest faults */
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;

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
    


    // Snorfler
    public static VictorSP snorfMtr = new VictorSP(3);
    // public static Victor snorfFeedScdy = new Victor(6);
    public static InvertibleSolenoid snorflerExtSV = new InvertibleSolenoid(22, 6); // Extends both feeders
    // public static InvertibleDigitalInput snorfHasBall = new InvertibleDigitalInput(2, false);

    // // Climb
    // public static Victor climberHoist = new Victor(3); // Extends climber
    // public static ISolenoid climberExt = new InvertibleSolenoid(22, 7);

  
    // public static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

    // Initialize any hardware here
    public static void init() {
    }

    public static void update() {
        
    }

}
