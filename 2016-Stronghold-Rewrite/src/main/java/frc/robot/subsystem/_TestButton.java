package frc.robot.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.io.hdw_io.IO;
import frc.robot.io.hdw_io.InvertibleDigitalInput;
import frc.robot.io.hdw_io.InvertibleSolenoid;

public class _TestButton {

    private static InvertibleDigitalInput topBut1 = IO.topClimberES;
    private static InvertibleDigitalInput midBut2 = IO.middleClimberES;
    private static InvertibleDigitalInput lowBut3 = IO.bottomClimberES;

    public static InvertibleSolenoid led1 = IO.frntIsCmbrLED1;
    public static InvertibleSolenoid led2 = IO.frntIsCmbrLED2;
    public static InvertibleSolenoid led3 = IO.frntIsSnrfLED3;

    private static int cmdMux = 0;  //Multiplex of possible button combos
    
    /** Constructor - not needed, just bc. */
    public _TestButton(){

    }

    /**
     * Initialize all LEDs off.  Called from teleopInit.
     */
    public static void init(){
        led1.set(false);
        led2.set(false);
        led3.set(false);
    }

    /** Determines the state to call based on conditions. */
    private static void determ(){
        //Multiplex buttons.  top=1 (lsb), mid=2, low=4
        cmdMux = topBut1.get() ? 1 : 0;
        cmdMux += midBut2.get() ? 2 : 0;
        cmdMux += lowBut3.get() ? 4 : 0;
    }

    /** Update conditions and commands.  Called from teleopPeriodic */
    public static void update(){
        determ();
        sdbUpdate();

        switch( cmdMux ){
        case 0: //cmd  led3,  led2,  led1
            cmdUpdate(false, false, false);
            break;
        case 1:
            cmdUpdate(false, false, true);
            break;
        case 2:
            cmdUpdate(false, true, false);
            break;
        case 3:
            cmdUpdate(false, true, true);
            break;
        case 4:
            cmdUpdate(true, false, false);
            break;
        case 5:
            cmdUpdate(true, false, true);
            break;
        case 6:
            cmdUpdate(true, true, false);
            break;
        case 7:
            cmdUpdate(true, true, true);
            break;
        default:
            cmdUpdate(false, false, false);
            break;
        }
    }

    /**Command update.  Any safeties "should" be here! */
    private static void cmdUpdate(boolean cmd3, boolean cmd2, boolean cmd1){
        led1.set(cmd1);
        led2.set(cmd2);
        led3.set(cmd3);
    }

    private static void sdbUpdate(){
        SmartDashboard.putBoolean("Test/Btn 1, Top", topBut1.get());
        SmartDashboard.putBoolean("Test/Btn 2, Mid", midBut2.get());
        SmartDashboard.putBoolean("Test/Btn 3, Low", lowBut3.get());
        SmartDashboard.putBoolean("Test/LED 1, Cl Left", led1.get());
        SmartDashboard.putBoolean("Test/LED 2, Cl Right", led2.get());
        SmartDashboard.putBoolean("Test/LED 3, Snofrler", led3.get());
        SmartDashboard.putNumber("Test/Cmd Mux", cmdMux);
    }
}
