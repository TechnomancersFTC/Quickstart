package org.firstinspires.ftc.teamcode.technomancers.customs

import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.hardware.powerable.Powerable

//Esse código é escrito em kotlin, infelizmente.
class PowerE(powerable1: Powerable, powerable2: Powerable, power1: Double,power2: Double)
    : InstantCommand({

    powerable1.power = power1
    powerable2.power = power2
})