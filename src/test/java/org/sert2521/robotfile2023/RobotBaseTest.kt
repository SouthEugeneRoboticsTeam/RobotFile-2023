package org.sert2521.robotfile2023

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import org.sert2521.robotfile2023.subsystems.Claw

internal class RobotBaseTest
{
    // To learn more about how to write unit tests, see the
    // JUnit 5 User Guide at https://junit.org/junit5/docs/current/user-guide/

    @Test
    fun `2 plus 2 should equal 4`()
    {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `claw stopMotor sets speed to zero`()
    {
        Claw.stopMotor()
        assertEquals(0.0, Claw.getMotorSpeed())
    }

    @Test
    fun `claw setSpeed sets speed to input value when within 10ms of present speed`()
    {
        // GIVEN the speed starts at a known value
        val startingSpeed : Double  = Claw.getMotorSpeed()

        // WHEN the speed is incremented by a small value
        val newSpeed : Double = startingSpeed + 5.0
        Claw.setMotor(newSpeed)

        // THEN the resulting speed will be the set speed
        assertEquals(newSpeed, Claw.getMotorSpeed())
    }

    @Test
    fun `claw ramps up to speed at a safe rate`()
    {
        // GIVEN the speed starts at a known value
        val startingSpeed : Double  = Claw.getMotorSpeed()

        // WHEN the speed is incremented by a large value
        val newSpeed : Double = startingSpeed + 600.0
        Claw.setMotor(newSpeed)

        // THEN the resulting speed will be the set speed only after some elapsed time
        //  The elapsed time is represented by ticks which count calls to Claw.periodic()
        //  in a real FRC robot the periodic functions are called at a rate of 50hz
        // So for this claw it should ramp up to speed over 50s
        // TODO: write another test which expects a linear ramp over that time
        // Note: we cap the number of cycles to prevent infinite loop in the test
        val maxTicks : Int = 100
        var ticks : Int = 0
        while ( (Claw.getMotorSpeed() < newSpeed) && (ticks < maxTicks) ) {
            Claw.periodic()
            ticks++
        }
        assertEquals(newSpeed, Claw.getMotorSpeed())
        assertEquals(Claw.getTimeToRampUp(), ticks)
    }

    @Test
    fun `claw setSpeed rejects negative values`()
    {
        assertEquals(0.0, Claw.getMotorSpeed())
    }

    @Test
    fun `claw setSpeed rejects speeds greater than safe`()
    {
        assertEquals(0.0, Claw.getMotorSpeed())
    }
}

