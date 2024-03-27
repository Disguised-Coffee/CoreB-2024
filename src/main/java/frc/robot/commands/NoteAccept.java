// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
// import frc.robot.subsystems.Launcher;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Shooter;

/** An example command that uses an example subsystem. */
public class NoteAccept extends Command {
  
  private final Shooter shooter;
  private Timer timer = new Timer(); //Keeps track of the time spent running the motors

  /**
   *
   * @param subsystem The subsystem used by this command.
   */
  public NoteAccept(Shooter subsystem) {
    shooter = subsystem;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setShooterSpeeds(OperatorConstants.kFeederOperatingSpeed, OperatorConstants.kFeederOperatingSpeed);
    timer.restart();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setShooterSpeeds(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(OperatorConstants.kTimeToAcceptNote);
  }
}
