package org.firstinspires.ftc.teamcode.technomancers.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;


public class intake implements Subsystem {

    public static final intake INSTANCE = new intake();


    private MotorEx intake = new MotorEx("intake").brakeMode();
    private MotorEx trava = new MotorEx("trava").brakeMode().reversed();
    private MotorGroup controle = new MotorGroup(intake, trava);

    public Command ligar = new SetPower(controle, 1);
    public Command desligar = new SetPower(controle, 0);
    public Command atirar = sequencia();

    private Command sequencia(){
        controle.setPower(1);
        new Delay(0.15);
        controle.setPower(0);
        new Delay(0.15);
        controle.setPower(1);
        new Delay(0.15);
        controle.setPower(0);
        return null;
    }

}
