package frc.robot.subsystem;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.io.hdw_io.*;
import frc.robot.io.joysticks.JS_IO;
import frc.util.Timer;

public class Snorfler {
    private static InvertibleSolenoid snorfExtendV = IO.snorflerExtSV;   // Lowers snorfler
    private static VictorSP snorfFeederV = IO.snorflerMotor;    // Collector motor
    private static InvertibleDigitalInput snorfHasBall = IO.hasBallSensor; // Banner snsr, ball at top of snorfler

    private static int state; // Snorfler state machine. 0=Off, 1-6 Snorfle, 10 Spit drvr, 90 Spit Ball jam
    // private static boolean snorfArmDn;
    // private static boolean snorfFeederOn;
    // private static boolean snorfReverse;
    // public static boolean reqsnorfDrvAuto;  //Request to enable the snorfler from Drv Auto system
    private static Timer stateTmr; // Timer used in state machine

    public static double feederSpeed = 0.7;

    public static void init() {

        stateTmr = new Timer(0);    // Timer for state machine
        snorfFeederV.set(0.0);      // Turn off motor
        snorfExtendV.set(false);    //Retract arm

        state = 0;
        // snorfArmDn = false;     //Lower arm & extend feeder
        // snorfFeederOn = false;  //Feed motors on to snorfle
        // snorfReverse = false;   //Reverse motor while button down
        // reqsnorfDrvAuto = false;//Request to enable the snorfler from Drv Auto system
    }

    private static void determ() {
        // toggle arms down and up
        if (JS_IO.btnTglSnorArmDn.onButtonPressed()) {
            if (state != 0) {
                state = 0;
            } else {
                state = 1;
            }
        }

        //Set the in speed of the motor
        if( hasBall() || state == 0 ) {
            feederSpeed = 0.0;  
        }else{
            feederSpeed = 1.0;
        }

        //Override motor speed anytime button pressed
        if (JS_IO.btnReverseSnorfler.onButtonPressed()) {
            feederSpeed = -1.0;
        }
    }

    /*  For discussion
    Normal mode
    Starts Up and motor intake off.
    Initiate by pressing the tglSnorfler down.  Lowers snorfler and starts motor sucking balls
    When ball is detected motor is stopped.
    Then arm is raised.
    OR?
    When tglSnrfler is pressed again, raise snorfler and leave motor off.

    When spitBall is pressed the motor is reversed to spit out ball.

    Thoughts:
    What do we do if we capture 2 balls?  Rule, penalty for more then 1.
    Any Autonomous control needed?

    */
    /** Update Snorfler. Called from teleopPeriodic in Robot.java */
    public static void update() {
        sdbUpdate();
        determ();
        switch (state) { // cmd snorfler and motor
            case 0: // Up (not dn) & Off
                cmdUpdate(false, feederSpeed);
                stateTmr.hasExpired(0.05, state);
                break;
            case 1: // Down & sucking
                cmdUpdate(true, feederSpeed);
                if (snorfHasBall.get()) state++;
                break;
            case 2: // Down & holding
                cmdUpdate(true, feederSpeed);
                if (stateTmr.hasExpired(0.5, state)) state = 0;
                break;
            default:
                cmdUpdate(false, 0.0);
                System.out.println("Snorfler bad state - " + state);
                break;
        }
    }

    /**
     * Send cmds to various parts of snrfling system
     * 
     * @param snorfDn - Activates solenoid valve to lower snorfler arm
     * @param snorfFeederSpd    - Motor speed for Feeder, collector.
     */
    public static void cmdUpdate(boolean snorfDn, double snorfFeederSpd) {
        snorfExtendV.set(snorfDn);
        snorfFeederV.set(snorfFeederSpd);
    }

    /** Update sdb for Snorfler */
    public static void sdbUpdate() {
        SmartDashboard.putNumber("Snorfler/1.state", state);
        SmartDashboard.putBoolean("Snorfler/3.ballBanner", snorfHasBall.get());
        SmartDashboard.putNumber("Snorfler/4.pdp snorf curr", IO.pdp.getCurrent(2));
        SmartDashboard.putBoolean("Snorfler/5.Arm is dn", snorfExtendV.get());
        SmartDashboard.putNumber("Snorfler/6.Feed mtr spd", snorfFeederV.get());
    }

    /**
     * 
     * @return A combined status of all the components of the snorfler
     * <p> Bit 0 (LSB) - snorfler arm down.
     * <p> Bit 1 - snorfler has a ball
     * <p> Bit 2 - snorfler sucking in the ball
     * <p> Bit 3 - snorfler spitting out the ball
     */
    public int status(){
        int tmp = snorfExtendV.get() ? 1 : 0;
        tmp |= snorfHasBall.get() ? 2 : 0;
        tmp |= feederSpeed > 0.0 ? 4 : 0;
        tmp |= feederSpeed < 0.0 ? 8 : 0;
        return tmp;
    }

    /**
     * Probably shouldn't use this bc the states can change. Use statuses.
     * 
     * @return - present state of Shooter state machine.
     */
    public static int getState() {
        return state;
    }

    /**
     * @return - status of banner sensor at top of snofler, has a ball.
     */
    public static boolean hasBall() {
        return snorfHasBall.get();
    }

    /**
     * Returns if the snorfler is currently in an unready state. Change state number
     * if state changes.
     * 
     * @return - returns if the snorfler is in the cmdUpdate(true, 0, 0) state.
     */
    public static boolean snorfNotRdy() {
        return snorfExtendV.get();
    }    
}
