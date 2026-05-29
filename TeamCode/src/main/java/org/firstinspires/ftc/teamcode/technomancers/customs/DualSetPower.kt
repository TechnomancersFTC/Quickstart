package org.firstinspires.ftc.teamcode.technomancers.customs

import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.hardware.powerable.Powerable

//Esse código é escrito em kotlin, infelizmente.
class DualSetPower(powerable1: Powerable, powerable2: Powerable, power: Double)
    : InstantCommand({

    powerable1.power = power
    powerable2.power = power
})