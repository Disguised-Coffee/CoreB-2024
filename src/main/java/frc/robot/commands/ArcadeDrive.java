// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * 
 *  Thanks SeanSun6814!!!!!
 * 
 */

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ArcadeDrive extends Command {
  
  private final Drivetrain m_Drivetrain;
  private final Supplier<Double> speedFunction, turnFunction;

  private final Supplier<Boolean> maxSpeedFunction, maxTurnRateFunction;

  /**
   * 
   * @param subsystem The subsystem used by this command.
   */
  public ArcadeDrive(Drivetrain subsystem, Supplier<Double> speed, Supplier<Double> turnRate, Supplier<Boolean> increaseDriveSpeed, Supplier<Boolean> increaseTurnRate) {
    m_Drivetrain = subsystem;
    speedFunction = speed;
    turnFunction = turnRate;
    maxSpeedFunction = increaseDriveSpeed;
    maxTurnRateFunction = increaseTurnRate;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("ArcadeDrive CMD started!");
    m_Drivetrain.enableBrakes(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double realTimeSpeed = speedFunction.get();
    System.out.print(realTimeSpeed);
    double realTimeTurnRate = -turnFunction.get();
    System.out.println(realTimeTurnRate);
    m_Drivetrain.arcadeDrive(realTimeSpeed, realTimeTurnRate);
    m_Drivetrain.enableMaxDriveSpeedOutput(maxSpeedFunction.get());
    m_Drivetrain.enableMaxTurnRateOutput(maxTurnRateFunction.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("ArcadeDrive CMD ended!");
    m_Drivetrain.enableBrakes(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
