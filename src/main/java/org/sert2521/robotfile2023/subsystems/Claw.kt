package org.sert2521.robotfile2023.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.robotfile2023.Constants


object Claw : SubsystemBase() {
    private val motor = CANSparkMax(Constants.clawMotorID, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val encoder = motor.encoder



    fun setMotor(speed: Double) {
        motor.set(speed)
    }

    fun stopMotor(){
        motor.stopMotor()
    }
}