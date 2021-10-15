package frc.robot.subsystem;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.io.hdw_io.*;
import frc.robot.io.joysticks.Button;
import frc.robot.io.joysticks.JS_IO;
import frc.util.Timer;

public class Snorfler {
    private static InvertibleSolenoid snorfExtendV = IO.snorflerExtSV;   // Lowers snorfler
    private static VictorSP snorfFeederV = IO.snorfMtr;    // Collector motor
    private static Button btnForwardSnorfler = JS_IO.btnForwardSnorfler;
    private static Button btnReverseSnorfler = JS_IO.btnReverseSnorfler;
    private static VictorSP snorfMtr = IO.snorfMtr;
    // private static InvertibleDigitalInput snorfHasBall = IO.hasBallSensor; // Banner snsr, ball at top of snorfler

    public static void update()
    {
        if (btnForwardSnorfler.isDown())
    snorfMtr.setSpeed(1);
    else if (btnReverseSnorfler.isDown())
    snorfMtr.setSpeed(-1);
    else
    snorfMtr.setSpeed(0);

    sbdUpdate();
    }

    public static void sbdUpdate()
    {
        SmartDashboard.putBoolean("btnForwardSnorfler", btnForwardSnorfler.isDown());
        SmartDashboard.putBoolean("btnReverseSnorfler", btnReverseSnorfler.isDown());

    }
}