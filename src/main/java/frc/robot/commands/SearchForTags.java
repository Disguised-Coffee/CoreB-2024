// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.AutonomousConstants.Searching;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class SearchForTags extends Command {
  
  private Drivetrain drivetrain;
  private Limelight limelight;
  private Timer timer;

  public SearchForTags(Drivetrain m_drivetrain, Limelight m_limelight){
    
    
    addRequirements(m_drivetrain, m_limelight); // check if limelight subsys agrees with this [probably not...] []
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Searching for a tag...");
    timer.start();
  }

  //Spin around to find a tag
  @Override
  public void execute() {
    drivetrain.setMotors(-Searching.kSearchTurnSpeed, Searching.kSearchTurnSpeed);
    // if we took too long to find the tag, then quit ;-;
    if(searchedTooLong()){
      SearchForTags.super.cancel();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    System.out.println("Took " + timer.get() + " seconds to find the tag.");
    if(!interrupted){
      System.out.println("An apriltag has been found");
    }else{
      System.out.println("Hey! I'm watching here!!");
    }
  }

  // Should return true when an Apriltag has been found.
  @Override
  public boolean isFinished() {
    return limelight.hasTarget();
  }
  
  // true if we take too long to find the tag.
  private boolean searchedTooLong(){
    return timer.hasElapsed(Searching.kMaxSearchingTime);
  }
}
