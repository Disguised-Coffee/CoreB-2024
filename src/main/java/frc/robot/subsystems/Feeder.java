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

public class Feeder extends SubsystemBase {
  
  // change this later...
  private CANSparkMax m_feeder = new CANSparkMax(CanIDs.M_LAUNCHER, MotorType.kBrushless);

  // private RelativeEncoder encoder;

  public Feeder() {
    // encoder = m_feeder.getEncoder();
    m_feeder.setInverted(false);
  }


  //prob better for PID control to be here.
  public void release(double xSpeed) {
    // [] ensure that deadband doesn't need to be put for rotation parameter.
    System.out.println("Releasing the Note!");
    m_feeder.set(ManipulatorConstants.kFeederSpeed);
  }

  public void accept(double xSpeed, double zRotation) {
    // [] ensure that deadband doesn't need to be put for rotation parameter.
    System.out.println("Accepting the Note!");
    m_feeder.set(ManipulatorConstants.kFeederSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    placeMotorVitals(m_feeder, "Feeder");
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

  public void placeMotorPosVelo(CANSparkMax m_motor, String m_name){
    SmartDashboard.putNumber(m_name + " Velocity", m_motor.getEncoder().getVelocity());
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
    if(m_feeder.setIdleMode(mode) != REVLibError.kOk) {
      SmartDashboard.putString(key, "Error ~ Couldn't setIdleMode().");
    }
    else{
      SmartDashboard.putString(key, sd_mode);
    }
  }
}
