package org.firstinspires.ftc.teamcode.pedroPathing.Technomancers.Autos;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.technomancers.subsystems.flywheel;
import org.firstinspires.ftc.teamcode.technomancers.subsystems.intake;
import org.firstinspires.ftc.teamcode.technomancers.subsystems.movimento;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous (name = "GolAzul", group = "Autos")
@Configurable
public class GolAzul extends NextFTCOpMode {

    DcMotorEx leftFront, leftBack, rightFront, rightBack;
    Servo regulador;
    IMU imu;
    public static long tempo = 280;
    public static double virar = 0.5, kp1 = 0.05 /*valor de estabilização*/, giro = 19, kP = 0.01;
    double currentAngle, error;

    {
        addComponents(
                BindingsComponent.INSTANCE,
                new SubsystemComponent(intake.INSTANCE, movimento.INSTANCE, flywheel.INSTANCE)
        );
    }


    @Override
    public void onInit(){
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
    }

    IMU.Parameters parameters = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.LEFT
            )
    );

    public class Giro extends Command {
        double targetAngle;
        double kP;
        double tolerance;
        boolean done;
        public Giro (double targetAngle, double kP, double tolerance){
            this.targetAngle = targetAngle;
            this.kP = kP;
            this.tolerance = tolerance;
            requires(/*subsystems*/);
            setInterruptible(true); //this is the default, so you don't need to specify
        }

        @Override
        public boolean isDone() { return done;} //whether or not the command is don}

        @Override
        public void update(){
            currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            error = targetAngle - currentAngle;
            double power = error * kP;
            power = Math.max(-0.5, Math.min(0.5, power));
            if (Math.abs(power) < 0.12){
                power = Math.signum(power) * 0.12;
            }
            leftBack.setPower(-power);
            leftFront.setPower(-power);
            rightFront.setPower(power);
            rightBack.setPower(power);

            if (Math.abs(error) < tolerance){
                done = true;
                isDone();
            }
            //executado em cada update do comando
        }

        public void Girar (double targetAngle, double kP, double tolerance){
            error = targetAngle - currentAngle;
            while (Math.abs(error) <  tolerance){
                currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                double power = error * kP;
                power = Math.max(-0.5, Math.min(0.5, power));
                if (Math.abs(power) < 0.12){
                    power = Math.signum(power) * 0.12;
                }

                leftBack.setPower(-power);
                leftFront.setPower(-power);
                rightBack.setPower(power);
                rightFront.setPower(power);

            }

            leftFront.setPower(0);
            leftBack.setPower(0);
        }
    }


    @Override
    public void onStartButtonPressed(){
        CommandManager.INSTANCE.scheduleCommand(
                new SequentialGroup(
                        new Giro(-45,kp1,0),
                        flywheel.INSTANCE.ligar,
                        intake.INSTANCE.atirar,
                        flywheel.INSTANCE.desligar,
                        new ParallelGroup(
                                movimento.INSTANCE.frente,
                                movimento.INSTANCE.esquerda
                        ),
                        new Delay(0.2),
                        movimento.INSTANCE.parar,
                        new Giro(-90, kp1, 0),
                        new ParallelGroup(
                                movimento.INSTANCE.frente,
                                intake.INSTANCE.ligar
                        ),
                        new Delay(0.5),
                        movimento.INSTANCE.parar,
                        intake.INSTANCE.desligar





                )
        );
    }
}
