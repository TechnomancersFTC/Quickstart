package org.firstinspires.ftc.teamcode.technomancers.subsystems;

import org.firstinspires.ftc.teamcode.technomancers.customs.DualSetPower;
import org.firstinspires.ftc.teamcode.technomancers.customs.PowerE;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;

public class movimento implements Subsystem {

    public static final movimento INSTANCE = new movimento();



    private MotorEx rightFront = new MotorEx("rightFront").brakeMode();
    private MotorEx rightBack = new MotorEx("rightBack").brakeMode();
    private MotorEx leftBack = new MotorEx("leftBack").brakeMode().reversed();
    private MotorEx leftFront = new MotorEx("leftFront").brakeMode().reversed();
    private MotorGroup rBlF = new MotorGroup(rightBack, leftFront);
    private MotorGroup lBrF = new MotorGroup(leftBack, rightFront);

    public Command frente = new DualSetPower(rBlF, lBrF, 1);
    public Command tras = new DualSetPower(rBlF, lBrF, -1);
    public Command parar = new DualSetPower(rBlF, lBrF, 0);
    public Command esquerda = new PowerE(rBlF, lBrF,-1, 1);
    public Command direita = new PowerE(rBlF, lBrF,-1, 1);


}
