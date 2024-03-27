// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;


/**
 * Concept:
 * 
 * Flywheel ramps up for shooting and then feeder places note onto shooter flywheel
 * 
 * 
 * 
 */
public class Shooter extends SubsystemBase {
  
  private CANSparkMax feeder = new CANSparkMax(CanIDs.FEEDER_MOTOR, MotorType.kBrushed);
  private CANSparkMax flywheel = new CANSparkMax(CanIDs.FLYWHEEL_MOTOR, MotorType.kBrushed);

  // private RelativeEncoder encoder;

  public Shooter() {
    // encoder = m_feeder.getEncoder();
    feeder.setInverted(false);
    flywheel.setInverted(false);
  }

  public void setShooterSpeeds(double feederSpeed, double flywheelSpeed){
    feeder.set(feederSpeed);
    flywheel.set(flywheelSpeed);
  }

  public void release(double xSpeed) {
    // [] ensure that deadband doesn't need to be put for rotation parameter.
    System.out.println("Releasing the Note!");
    feeder.set(ManipulatorConstants.kFeederSpeed);
  }

  public void accept(double xSpeed, double zRotation) {
    // [] ensure that deadband doesn't need to be put for rotation parameter.
    System.out.println("Accepting the Note!");
    feeder.set(ManipulatorConstants.kFeederSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    placeMotorVitals(feeder, "Feeder");
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
    String key = "Shooter Motors";
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
    if(feeder.setIdleMode(mode) != REVLibError.kOk) {
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
   * @param isEnabled - boolean: true --> enable limit
   */
  // public void enableMaxDriveSpeedOutput(boolean isEnabled){
  //   // variable = (condition) ? value_if_true : value_if_false;
  //   //
  //   // Clarification:
  //   // if (condition) is true, then it is value_if_true. Else, it is value_if_false.
  //   maxDriveSpeedLimit = (isEnabled) ? 1.0 : DrivetrainConstants.kDefaultDriveSpeed;
  //   maxTurnSpeedLimit = (isEnabled) ? 1.0 : DrivetrainConstants.kDefaultDriveSpeed;
  // }
}
