// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanIDs;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
  
  private CANSparkMax left1 = new CANSparkMax(CanIDs.FL_MOTOR, MotorType.kBrushed), 
                        left2 = new CANSparkMax(CanIDs.FR_MOTOR, MotorType.kBrushed), 
                        right1 = new CANSparkMax(CanIDs.FR_MOTOR, MotorType.kBrushed), 
                        right2 = new CANSparkMax(CanIDs.RR_MOTOR, MotorType.kBrushed);
  private DifferentialDrive drive;
  private double maxDriveSpeedLimit = DrivetrainConstants.kDefaultDriveSpeed, 
                  maxTurnSpeedLimit = DrivetrainConstants.kDefaultTurnSpeed;

  public Drivetrain() {
    left2.follow(left1);
    right2.follow(right1);

    // so we have positive parameter values
    right1.setInverted(true);
    right2.setInverted(true);
    left1.setInverted(false);
    left2.setInverted(false);

    drive = new DifferentialDrive(left1, right1);
    drive.setDeadband(0.05);
  }

  public void arcadeDrive(double xSpeed, double zRotation) {
    // [] ensure that deadband doesn't need to be put for rotation parameter.
    drive.arcadeDrive(xSpeed, zRotation);
    System.out.printf("arcade: %.3f, %.3f\n", xSpeed, zRotation);
  }

  public void setMotors(double speedL, double speedR){
    drive.tankDrive(speedL * maxDriveSpeedLimit, speedR* maxTurnSpeedLimit);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    placeMotorVitals(left1, "Left1");
    placeMotorVitals(left2, "Left2");
    placeMotorVitals(right1, "Right1");
    placeMotorVitals(right2, "Right2");
  }
  
  /**
   * Outputs voltage, temp, and output of a motor to SmartDashboard
   * 
   * IMPORTANT: comment out lines to receive less info!
   * 
   * @param m_motor CANSparkMax object to get data from 
   * @param motor_ID String, needed to identify which motor
   */
  private void placeMotorVitals(CANSparkMax m_motor, String motor_ID){
    SmartDashboard.putNumber(motor_ID + "Voltage", m_motor.getBusVoltage());
    SmartDashboard.putNumber(motor_ID + "Temperature", m_motor.getMotorTemperature());
    // SmartDashboard.putNumber(motor_ID + "Output", m_motor.getAppliedOutput());
  }

  /**
   * Enables motor braking in all wheels in DriveSubsystm
   * 
   * @param isEnabled ~ whether to enable motor braking when input is 0 or not.
   */
  public void enableBrakes(boolean isEnabled){
    // Translate the boolean to a IdleMode and an associated String
    String key = "Drivetrain Idle Mode";
    IdleMode mode;
    String sd_mode;
    if(isEnabled){
      mode = IdleMode.kBrake;
      sd_mode = "Brake";
    }
    else{
      mode = IdleMode.kCoast;
      sd_mode = "Coast";
    }
    // so that we can put it in SmartDashboard
    //       **[] Make sure that this also applies to follower motors 
    if(left1.setIdleMode(mode) != REVLibError.kOk || left2.setIdleMode(mode) != REVLibError.kOk || right1.setIdleMode(mode) != REVLibError.kOk || right2.setIdleMode(mode) != REVLibError.kOk) {
      SmartDashboard.putString(key, "Error ~ Couldn't setIdleMode().");
    }
    else{
      SmartDashboard.putString(key, sd_mode);
    }
  }


  /**
   * Changes the speed constant multiplier to allow the bot
   * to use the full capabilities of the motors.
   * 
   * @param isEnabled - boolean: true --> disable limit
   */
  public void enableMaxDriveSpeedOutput(boolean isEnabled){
    // variable = (condition) ? value_if_true : value_if_false;
    //
    // Clarification:
    // if (condition) is true, then it is value_if_true. Else, it is value_if_false.
    maxDriveSpeedLimit = (isEnabled) ? 1.0 : DrivetrainConstants.kDefaultDriveSpeed;
    maxTurnSpeedLimit = (isEnabled) ? 1.0 : DrivetrainConstants.kDefaultDriveSpeed;
  }
  
  /**
   * Changes the speed constant multiplier to allow the bot
   * to use the full capabilities of the motors.
   * 
   * @param isEnabled - boolean: true --> disable limit
   */
  public void enableMaxTurnRateOutput(boolean isEnabled){
    maxTurnSpeedLimit = (isEnabled) ? 1.0 : DrivetrainConstants.kDefaultTurnSpeed;
  }
}
