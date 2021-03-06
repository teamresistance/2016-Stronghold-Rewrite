// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.security.DrbgParameters.Capability;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
import frc.robot.io.hdw_io.IO;
import frc.robot.io.joysticks.JS_IO;
import frc.robot.subsystem.Catapult;
import frc.robot.subsystem.Snorfler;
import frc.robot.subsystem.Snorfler_Jim;
import frc.robot.subsystem.TestLed;
import frc.robot.subsystem.TestLed2;
import frc.robot.subsystem.Test_Hdw;
import frc.robot.subsystem._SolenoidToJSB;
import frc.robot.subsystem.drive.Drive;
//import frc.robot.subsystem.drive.Drive2;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    /*
     * The next 3 are examples of an Instantiations of a 
     * solenoid interlocked to a JS button trigger.
    */
    public _SolenoidToJSB flipperSV = 
        new _SolenoidToJSB("Flipper", IO.flipperDn, JS_IO.btnFlipper);
    public _SolenoidToJSB antlerSV = 
        new _SolenoidToJSB("Antler", IO.antlerDn, JS_IO.btnAntler);
    public _SolenoidToJSB catapultSV = 
        new _SolenoidToJSB("Catapult", IO.catapult, JS_IO.btnFireShooter);

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        JS_IO.init();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        IO.compressorRelay.set(IO.compressor.enabled() ? Value.kForward : Value.kOff);
        JS_IO.update();
    }

    /** This function is called once when autonomous is enabled. */
    @Override
    public void autonomousInit() {
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
    }

    /** This function is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
        Drive.init();
        // Snorfler.init();
        // Catapult.init();
        // TestLed2.init();
        antlerSV.init();
        flipperSV.init();
        catapultSV.init();
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        Drive.update();
        Snorfler.update();        // Snorfler_Jim.update();
        // Catapult.update();
        // TestLed2.update();
        // Test_Hdw.update();   //Other subsystems should be commented out.
        flipperSV.update();
        antlerSV.update();
        catapultSV.update();
    }

    /** This function is called once when the robot is disabled. */
    @Override
    public void disabledInit() {
    }

    /** This function is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {
    }

    /** This function is called once when test mode is enabled. */
    @Override
    public void testInit() {
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }
}
