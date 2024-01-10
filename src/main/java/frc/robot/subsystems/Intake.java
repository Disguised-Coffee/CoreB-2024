package frc.robot.subsystems;

// import com.ctre.phoenix6.hardware.

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Ports.*;

public class Intake extends SubsystemBase {

    private CANSparkMax arm  = new CANSparkMax(ARM, MotorType.kBrushless);
    private VictorSPX intake = new VictorSPX(INTAKE);

    public Intake() {
        intake.setNeutralMode(NeutralMode.Brake);
    }

    public void setArm(double speed) {
        arm.set(speed);
    }

    public void setIntake(double speed) {
        intake.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        arm.set(0);
        intake.set(ControlMode.Disabled, 0);
    }

}
