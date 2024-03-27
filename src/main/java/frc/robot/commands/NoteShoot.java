// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Shooter;

/**
 * Idea: 
 * <ul>
 * <li>In normal mode, allow the driver to control the flywheel speed and shoot using the trigger</li>
 * <li>In toggle mode, allow the driver apply a certain speed to motor using a button</li>
 * 
 * Set speed, and then shoot!
*/
public class NoteShoot extends Command {
  
  private final Shooter shooter;
  private Supplier<Double> flyWheelSpeedSupplier; // gets the speed of the shooter from a slider
  private Supplier<Boolean> shootButtonSupplier; // Pushes the note to the flywheel
  private Supplier<Boolean> activeToggleModeSupplier;
  private boolean toggleMode = false;
  public SlewRateLimiter filter = new SlewRateLimiter(OperatorConstants.kSlewConstant);

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public NoteShoot(Shooter subsystem, Supplier<Double> flywheelSpeedSupplier, Supplier<Boolean>shootButtonSupplier, Supplier<Boolean>activeToggleModeSupplier) {
    shooter = subsystem;
    this.flyWheelSpeedSupplier = flywheelSpeedSupplier;
    this.shootButtonSupplier = shootButtonSupplier;
    this.activeToggleModeSupplier = activeToggleModeSupplier;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Spin flywheel to desired speed
    if(!toggleMode){
      shooter.setShooterSpeeds(0, filter.calculate((flyWheelSpeedSupplier.get() + 1)/2));
    }else{
      shooter.setShooterSpeeds(0, OperatorConstants.kDefaultFlywheelSpeed);
    }
    
    if(activeToggleModeSupplier.get()){
      toggleMode = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Spin feeder to give note to flywheel
    shooter.setShooterSpeeds(OperatorConstants.kFeederOperatingSpeed, flyWheelSpeedSupplier.get());
    //wait a second
    Timer.delay(OperatorConstants.kSecondsToWait);
    //Then set everything back to 0.
    shooter.setShooterSpeeds(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // when we get another button, shoot it!
    return shootButtonSupplier.get();
  }
}
