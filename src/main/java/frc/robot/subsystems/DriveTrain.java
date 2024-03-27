// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanIDs;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.AutonomousConstants.Driving;

public class Drivetrain extends SubsystemBase {
  
  // Hopefully this does work. TODO
  private WPI_TalonSRX left1 = new WPI_TalonSRX(CanIDs.FRONTLEFT_MOTOR), 
                  left2 = new WPI_TalonSRX(CanIDs.REARLEFT_MOTOR), 
                  right1 = new WPI_TalonSRX(CanIDs.FRONTRIGHT_MOTOR), 
                  right2 = new WPI_TalonSRX(CanIDs.REARRIGHT_MOTOR);

  private DifferentialDrive drive;
  private double maxDriveSpeedLimit = DrivetrainConstants.kDefaultDriveSpeed, 
                  maxTurnSpeedLimit = DrivetrainConstants.kDefaultTurnSpeed;

  public Faults _faults = new Faults();

  public Drivetrain() {
    //set motor configs here
    right1.setInverted(true);
    right2.setInverted(true);
    left2.follow(left1,FollowerType.PercentOutput);
    right2.follow(right1,FollowerType.PercentOutput);

    drive = new DifferentialDrive(left1, right1);
    drive.setDeadband(DrivetrainConstants.kDefaultDeadband);
  }

  /**
   * Allows the Drivetrain to be controlled with just a joystick
   * What I love using.
   * 
   * @param xSpeed Forwards speed
   * @param zRotation Rotational speed
   */
  public void arcadeDrive(double xSpeed, double zRotation) {
    // [] ensure that deadband doesn't need to be put for rotation parameter.
    drive.arcadeDrive(xSpeed, zRotation);
    System.out.printf("arcade: %.3f, %.3f\n", xSpeed, zRotation);
  }

  /**
   * Drives the motors
   * 
   * @param speedL Input for Left motor
   * @param speedR  Input for Right motor
   */
  public void setMotors(double speedL, double speedR){
    drive.tankDrive(speedL * maxDriveSpeedLimit, speedR* maxTurnSpeedLimit);
  }

  public Pair<Double, Double> getRawDistance(){
    return new Pair<>(left1.getSelectedSensorPosition(), right1.getSelectedSensorPosition());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    placeMotorVitals(left1, "Left1");
    placeMotorVitals(left2, "Left2");
    placeMotorVitals(right1, "Right1");
    placeMotorVitals(right2, "Right2");
    SmartDashboard.putNumber("Max Drive speed", maxDriveSpeedLimit);
    // SmartDashboard.putBoolean("Motors", false);
  }
  
  /**
   * Outputs voltage, temp, and output of a motor to SmartDashboard
   * 
   * IMPORTANT: comment out lines to receive less info!
   * 
   * @param m_motor WPI_TalonSRX object to get data from 
   * @param motor_ID String, needed to identify which motor
   */
  private void placeMotorVitals(WPI_TalonSRX m_motor, String motor_ID){
    SmartDashboard.putNumber(motor_ID + "Voltage", m_motor.getBusVoltage());
  }

  /**
   * Enables motor braking in all wheels in DriveSubsystm
   * 
   * @param isEnabled ~ whether to enable motor braking when input is 0 or not.
   */
  public void enableBrakes(boolean isEnabled){
    // Translate the boolean to a IdleMode and an associated String
    String key = "Drivetrain Idle Mode";
    NeutralMode mode;
    String sd_mode;
    if(isEnabled){
      mode = NeutralMode.Brake;
      sd_mode = "Brake";
    }
    else{
      mode = NeutralMode.Coast;
      sd_mode = "Coast";
    }
    // so that we can put it in SmartDashboard
    left1.setNeutralMode(mode);
    left2.setNeutralMode(mode);
    right1.setNeutralMode(mode);
    right2.setNeutralMode(mode);
    
    SmartDashboard.putString(key, sd_mode);
  }

  /**
   * Changes the speed constant multiplier to allow the bot
   * to use the full capabilities of the motors.
   * 
   * @param isEnabled - boolean: true --> enable limit
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

  /**
   * Converts ticks to meters and vice versa all in one method.
   * 
   * @param value double ~ value taken in, either meters or ticks
   * @param getMeters boolean ~ whether that value was in meters or ticks 
   * @return the 
   */
  public double tickMeterConvert(double value, boolean getMeters){
    // (condition) ? value_if_true : value_if_false;
    return (getMeters) ? (value * Driving.kTicksToMeters): (value / Driving.kTicksToMeters);
  }
}
