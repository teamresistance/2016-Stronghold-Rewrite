package frc.robot.subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.io.hdw_io.*;
import frc.robot.io.joysticks.Button;
import frc.robot.io.joysticks.Axis;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.io.joysticks.JS_IO;
import frc.util.Timer;
import jdk.nashorn.api.tree.WhileLoopTree;

/**This is a framework for a TR86 subsystem state machine.
 * <p>Some hardware references have been left as examples.
 */
public class Lifter {
    // Reference or Initialize hardware
    private static Victor liftUp = IO.liftUP;
    private static ISolenoid liftExt = IO.liftExt;
    private static DigitalInput liftTopStop = IO.liftTopStop;
    private static DigitalInput liftMidSnsr = IO.liftMidSnsr;
    private static DigitalInput liftBotStop = IO.liftBotStop;




    // Reference or Initialize Joystick axis, buttons or pov
   private static Axis axClimb = JS_IO.axClimb;   //To move climber up or dn
   private static Button btnClimbTop = JS_IO.btnClimbTop;   //Move climber to top
   private static Button btnClimbBot = JS_IO.btnClimbBot;    //Move climber to bottom
   private static Button btnClimbExt = JS_IO.btnClimbExt;    //Extend climber.  For testing only



    // Create objects for this SM
    private static int state = 0;
    private static Timer timer = new Timer(0);

    /**
     * Initialize objects for this SM. Usually called from autonomousInit or/and
     * teleopInit
     */
    public static void init() {
        sbdInit();
    }

    /**
     * Determine the state to select if some outside condition is detected such as a
     * joystick button press or change of state of a DI.
     */
    private static void determ() {
        /*
        Mode 0: initliaze for climbing = drive motor up to the top swtich so you can extend the lifter
        Mode 1: actual climbing = move the climber down to the middle switch after the driver reaches so that you can climb
        Mode 2: you need to move the climber back up (slowly) so that the robot can safely hit the floor
        Mode 3: after the robot is back on the floor, then you need to unextend the lifter
                and reset the climber to the middle
        
                *make sure to stop the motor in between extending and other movements
        */

        

    }
    /*
    
    /**
     * Periodically Update commands to this subsystem. Normally called from
     * autonomousPeriodic or teleopPeroic in Robot.
     */
    public static void update() {
        determ();   // Check on external conditions
        sbdUpdate();// Update Smartdashboard stuff

        switch (state) {
            case 0: // Off & off. State 0 is normally the default, off.
                cmdUpdate(0.0);
                timer.hasExpired(0.0, state);   //Set timer for next state
                break;
            case 1: // Normally the kickoff. Ex. drop arm but wait 0.5 seconds to start motor
                cmdUpdate(0.0);
                if (timer.hasExpired(0.5, state)) // wait 0.5 seconds to start motor
                    state++;
                break;
            case 2: // Followup state as needed.
                cmdUpdate(1.0);
                break;
            default: // Always have a default, just incase.
                cmdUpdate(0.0);
                System.out.println("Bad state for this SMName - " + state);
        }
    }

    /**
     * Update commands for this subsystem. Commands to an object "should" only be
     * issued from one location.
     * <p>
     * Any safeties, things that if not handled will cause damage, should be here.
     */
    private static void cmdUpdate(double motorSpeed) {
        liftUp.set(motorSpeed);
    }

    /** Initalize Smartdashbord items */
    private static void sbdInit() {
    }

    /** Update Smartdashbord items */
    private static void sbdUpdate() {
        //SmartDashboard.putNumber("Snorfler/Motor", snorfMtr.get());
        //SmartDashboard.putBoolean("Snorfler/Btn Toggle", btnToggleSnorf.isDown());
    }

    /**
     * @return the active state.
     */
    public static int getState(){
        return state;
    }

    /**
     * @return Encoded status of Leds: snorf, lift2, lift1.
     */
    public static int status(){
        return 0;
    }

}