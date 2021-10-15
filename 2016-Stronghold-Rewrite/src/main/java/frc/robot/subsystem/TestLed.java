package frc.robot.subsystem;

import frc.robot.io.hdw_io.IO;

public class TestLed {

    public static void update(){
        IO.led1.set(IO.ledBtn1.get());
        IO.led2.set(IO.ledBtn2.get());
        IO.led3.set(IO.ledBtn3.get());
    }
    
}
