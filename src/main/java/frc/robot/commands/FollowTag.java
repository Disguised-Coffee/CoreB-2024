// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.Timer;

/** An example command that uses an example subsystem. */
public class FollowTag extends Command {
  
  private Drivetrain drivetrain;
  private Limelight limelight;

  private PIDController pidController;

  private Timer isByTagTimer;
  private Timer tagOutOfFrameTimer;

  private Transform3d lastKnownTagPosition;

  public FollowTag(Drivetrain m_drivetrain, Limelight m_limelight){
    drivetrain = m_drivetrain;
    limelight = m_limelight;
    
    addRequirements(drivetrain, limelight); // check if limelight subsys agrees with this [probably not...] []
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Searching for a tag...");
    
    addRequirements(drivetrain, limelight);
  }

  // Drive towards the april tag
  // @Override
  // public void execute() {
  //   // get current positional stuff
  //   Transform3d pose = limelight.getTargetPos();
  //   // if the pose isn't null, then update last known
  //   if(pose != null){
  //     //if the timer is still going, let's stop that..
  //     if(tagOutOfFrameTimer.get() > 0){
  //       tagOutOfFrameTimer.stop(); 
  //       tagOutOfFrameTimer.reset();
  //     }       
      
  //     // Update this incase we lose the tag.
  //     lastKnownTagPosition = pose;

      

  //     // And keep on driving...
  //     // If not within the stopping threshold
  //     if(){
  //       //if we are outside the threshold, stop the timer as well.
  //       if(isByTagTimer.get() > 0){
  //         isByTagTimer.stop(); // do we need this? []
  //         isByTagTimer.reset();
  //       }


  //       //get current set speed rn
  //       System.out.println("Let's keep...");

  //       // TRANSLATE TO SWITCH CASE LATER [][]
  //       // Doubles can be multiplied by values of 10 so that 
  //       // values can still be preserved so tha

  //       //If centered with april tag, move towards it
        
  //       //This is the tankdrive version, which I think could be better implemented 
  //       // with arcade drive? []
  //       if(){
  //         System.out.println("Moving forwards!");

  //       }
  //       // If off towards left, move right
  //       else if(){
  //         System.out.println("Moving Right from Left deviation!");
  //       }

  //       // If off towards right, move left
  //       else if(){
  //         System.out.println("Moving Right from Left deviation!");
  //       }
  //       else{
  //         System.out.println("uh.. SOMETHING IS WRONG, FollowTag.java @ execute!!");
  //       }
  //       drivetrain.setMotors(0, 0);

  //     }
  //     //If we are within the threshold, we should stop moving towards the target.
  //     else{
  //       // Start the timer.
  //       if(isByTagTimer.get() == 0){
  //         isByTagTimer.start();
  //       }
  //       System.out.println("By the tag, time elapsed: " + isByTagTimer.get());
  //     }
  //   }
  //   // If all of sudden we don't find the april tag, 
  //   // then spin in the last know direction that we saw it in.
  //   else{
  //     System.out.println("Uhh... where did it go?");

  //     // TODO [] I want to know what the time gives back when the timer hasn't been used.
  //     int time = (int) tagOutOfFrameTimer.get();

  //     int direction = -1; //this should be the sign of the X value from pose.

  //     if(time == 0){
  //       //Initialize what to do.
  //       tagOutOfFrameTimer.start();
  //     }
  //     else if(time < 6){
  //       // set it in current direction
  //     }
  //     else if(time >= 6){
  //       // set it in opposite direction
  //     }
  //     // Then just quit if nothing work ;-;
  //     else if(searchedTooLong()){
  //       FollowTag.super.cancel();
  //     }

  //     // Spin to search.
  //     drivetrain.setMotors(-Searching.kSearchTurnSpeed * direction, Searching.kSearchTurnSpeed * direction);
  //   }    
  // }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(interrupted){
      System.out.println("I was soooo close...");
    }else{
      System.out.println("I got it!");
    }
  }

  // Should return true when we've been by the april tag for long enough.
  @Override
  public boolean isFinished() {
    return hasBeenByTagTooLong();
  }

  /**
   * 
   * @return
   */
  // if the robot has been by the tag too long
  public boolean hasBeenByTagTooLong(){
    return isByTagTimer.hasElapsed(AutonomousConstants.Driving.kTimeByTagUntilTimeOut);
  }

  // true if we've been searching for too long.
  private boolean searchedTooLong(){
    return tagOutOfFrameTimer.hasElapsed(AutonomousConstants.Searching.kMaxSearchingTime);
  }
}
