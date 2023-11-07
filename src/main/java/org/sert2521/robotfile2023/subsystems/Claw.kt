package org.sert2521.robotfile2023.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.robotfile2023.Constants


object Claw : SubsystemBase() {
    private val motor = CANSparkMax(Constants.clawMotorID, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val encoder = motor.encoder
    private const val timeToRampUp : Int = 50

    private var desiredSpeed : Double = 0.0
    private var speedIncrementPerCycle : Double = 0.0
    override fun periodic() {
        super.periodic()
        val presentSpeed = motor.get()

        val presentSpeedIsCloseToDesiredSpeedLow : Boolean =  ( (desiredSpeed - 0.01) <= presentSpeed )
        val presentSpeedIsCloseToDesiredSpeedHigh : Boolean = ( (desiredSpeed + 0.01) >= presentSpeed )
        val presentSpeedIsCloseToDesiredSpeed : Boolean = presentSpeedIsCloseToDesiredSpeedHigh && presentSpeedIsCloseToDesiredSpeedLow

        if ( false == presentSpeedIsCloseToDesiredSpeed ) {
            motor.set(presentSpeed + speedIncrementPerCycle)
        } else {
            speedIncrementPerCycle = 0.0
        }

    }
    fun setMotor(speed: Double) {
        val maxInstantChange : Double = 10.0
        val presentSpeed : Double = motor.get()
        val speedDifference : Double =  speed - presentSpeed

        if (speed >= 0.00) {
            desiredSpeed = speed
        }

        if ( speedDifference < maxInstantChange ) {
            motor.set(desiredSpeed)
        } else {
            speedIncrementPerCycle = speedDifference / timeToRampUp
        }
    }

    fun stopMotor(){
        motor.stopMotor()
    }

    fun getMotorSpeed() : Double {
        return motor.get()
    }
    fun getTimeToRampUp() : Int {
        return timeToRampUp
    }
}