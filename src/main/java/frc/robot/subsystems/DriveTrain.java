package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Ports.*;

public class DriveTrain extends SubsystemBase {

    private CANSparkMax left1 = new CANSparkMax(MOTOR_LEFT1, MotorType.kBrushed), 
                        left2 = new CANSparkMax(MOTOR_LEFT2, MotorType.kBrushed), 
                        right1 = new CANSparkMax(MOTOR_RIGHT1, MotorType.kBrushed), 
                        right2 = new CANSparkMax(MOTOR_RIGHT2, MotorType.kBrushed);
    private DifferentialDrive drive;

    public DriveTrain() {

        left2.follow(left1);
        right2.follow(right1);

        drive = new DifferentialDrive(left1, right2);
        drive.setDeadband(RobotDriveBase.kDefaultDeadband);
    }

    // OLD, VERIFY THIS.
    // The Problem: Left1 needs to be inverted but the motor locks when inverted.
    //              Inverting it on the software side fixes it, but requires a
    //              significant amount of customized behaviour.

    public void arcadeDrive(double xSpeed, double zRotation) {
        // This is just the source code from DifferentialDrive#arcadeDrive except
        // that it calls #setMotors so we can properly invert 
        System.out.printf("arcade: %.3f, %.3f\n", xSpeed, zRotation);

        xSpeed *= -1;
        
        var m_maxOutput = RobotDriveBase.kDefaultMaxOutput;

        xSpeed = MathUtil.applyDeadband(xSpeed, RobotDriveBase.kDefaultDeadband);
        zRotation = MathUtil.applyDeadband(zRotation, RobotDriveBase.kDefaultDeadband);
    
        var speeds = DifferentialDrive.arcadeDriveIK(xSpeed, zRotation, false);
    
        drive.tankDrive(speeds.left * m_maxOutput, speeds.right * m_maxOutput);
    }

    public void setMotors(double speedL, double speedR){
        drive.tankDrive(speedL, speedR);
    }

    public void stop() {
        System.out.print("Executing Stop func in Drive Train!");
    }

}
