package org.firstinspires.ftc.teamcode.technomancers.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class flywheel implements Subsystem {

    public static final flywheel INSTANCE = new flywheel();

    private MotorEx lancador = new MotorEx("lancador").brakeMode().reversed();

    public Command ligar = new SetPower(lancador, 0.85);
    public Command desligar = new SetPower(lancador, 0);

}
