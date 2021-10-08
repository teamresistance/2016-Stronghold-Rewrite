/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.io.hdw_io.Encoder;
import frc.robot.io.hdw_io.IO;
import frc.robot.io.joysticks.JS_IO;
import frc.util.PropMath;

/**
 * Add your docs here.
 */
public class Drive {

    public static PWM leftDrv = IO.leftDrv;
    public static PWM rightDrv = IO.rightDrv;
    private static int state;

    private static boolean invToggle; // toggles between inverted and not
    private static boolean scaleToggle;
    private static boolean inverted;
    private static boolean scaled;

    private static double scale = -0.5;

   
    // Assignments used by DiffDrv. Slaves sent same command.
    //private static DifferentialDrive diffDrv_M = IO.diffDrv_M;
    // private static DifferentialDrive diffDrv_S = new DifferentialDrive(IO.drvFollowerVSPX_L, IO.drvFollowerVSPX_R);

    private static Steer steer = new Steer();
    private static double[] strCmd;
    private static double hdgFB;

    private static double dist_Avg;

    private static double hdgOut;

    public static void init() {
        SmartDashboard.putNumber("Drive Scale", -0.5);
        cmdUpdate(0, 0);
        state = 0;
        invToggle = true;
        scaleToggle = true;
        inverted = false;
        scaled = false;
        // IO.drvMasterTSRX_L.set(ControlMode.Disabled, 0);
        // IO.drvMasterTSRX_R.set(ControlMode.Disabled, 0);
    }

    // Determine the drive mode.
    // TODO: How does the driver return to unscaled, normal mode?
    public static void determ() {
        // if (JS_IO.btnInvOrientation.onButtonPressed()) {
        //     if (invToggle) {
        //         state = scaled ? 2 : 5;
        //     } else {
        //         state = scaled ? 1 : 0;
        //     }
        //     invToggle = !invToggle;
        // }

        // if (JS_IO.btnScaledDrive.onButtonPressed()) {
        //     if (scaleToggle) {
        //         state = inverted ? 2 : 1;
        //     } else {
        //         state = inverted ? 5 : 0;
        //     }
        //     scaleToggle = !scaleToggle;
        // }

        if (JS_IO.btnInvOrientation.onButtonPressed()) {
            if (invToggle) {
                if (scaled) {
                    state = 2;
                } else {
                    state = 5;
                }
                invToggle = !invToggle;
            } else {
                if (scaled) {
                    state = 1;
                } else {
                    state = 0;
                }
                invToggle = !invToggle;
            }
        }

        if (JS_IO.btnScaledDrive.onButtonPressed()) {
            if (scaleToggle) {
                if (inverted) {
                    state = 2;
                } else {
                    state = 1;
                }
                scaleToggle = !scaleToggle;
            } else {
                if (inverted) {
                    state = 5;
                } else {
                    state = 0;
                }
                scaleToggle = !scaleToggle;
            }
        }
    }

    // Update Drive mode. Called from Robot.
    public static void update() {
        if(JS_IO.btnRstGyro.onButtonPressed()){     //Testing location
        } 
        determ();
        sdbUpdate();
        switch (state) {
            case 0: // Tank mode, no scaling. JSs to wheels.
                cmdUpdate(-JS_IO.axLeftDrive.get(), -JS_IO.axRightDrive.get());
                scaled = false;
                inverted = false;
                // diffDrv_M.tankDrive(-JS_IO.axLeftDrive.get(), -JS_IO.axRightDrive.get());
                // diffDrv_S.tankDrive(-JS_IO.axLeftDrive.get(), -JS_IO.axRightDrive.get());
                break;
            case 1: // Tank mode, w/ scaling. JSs * scale to wheels.
                cmdUpdate(scale * JS_IO.axLeftDrive.get(), scale * JS_IO.axRightDrive.get());
                scaled = true;
                inverted = false;
                break;
            case 2: // Tank mode, w/ scaling. Reversre direction, front & back. swaps axes
                cmdUpdate(-scale * JS_IO.axRightDrive.get(), -scale * JS_IO.axLeftDrive.get());
                scaled = true;
                inverted = true;
                break;
            case 3: // reverse no scaled, swaps axes
                cmdUpdate(JS_IO.axRightDrive.get(), JS_IO.axLeftDrive.get());
                scaled = false;
                inverted = true;
                break;
            default:
                cmdUpdate(0, 0);
                System.out.println("Invaid Drive State - " + state);
                break;
        }
    }

    public static void sdbUpdate() {
        SmartDashboard.putNumber("Driver State", state);
        scale = SmartDashboard.getNumber("Drive Scale", -0.5);
        SmartDashboard.putBoolean("scaled", scaled);
        SmartDashboard.putBoolean("inverted", inverted);
    }

    public static int getState() {
        return state;
    }

    public static void cmdUpdate(double lSpeed, double rSpeed) {
        leftDrv.setSpeed(lSpeed);
        rightDrv.setSpeed(-rSpeed);
    }
}
