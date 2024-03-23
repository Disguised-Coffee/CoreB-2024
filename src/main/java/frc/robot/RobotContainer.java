// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArcadeDrive;
// import frc.robot.commands.Autos;
// import frc.robot.commands.Shoot;
import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.Limelight;
// import frc.robot.subsystems.Feeder;
// import frc.robot.subsystems.Launcher;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final Launcher m_Launcher = new Launcher();
  private final Drivetrain r_Drivetrain = new Drivetrain();
  // private final Feeder m_Feeder = new Feeder();

  //Test Limelight
  // public final Limelight m_Limelight = new Limelight();

  /**
   * Idea:
   * 
   * While active, look for limelight
   */


  private final Joystick m_Joystick = new Joystick(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    // ()-> is a lambda thing, which is familiar when you look at JS
    r_Drivetrain.setDefaultCommand(new ArcadeDrive(r_Drivetrain, 
                      () -> m_Joystick.getRawAxis(1), 
                      () -> m_Joystick.getRawAxis(0), 
                      () -> m_Joystick.getRawButton(OperatorConstants.kMaxSpeedButton),  //CHECK THESE []
                      () -> m_Joystick.getRawButton(OperatorConstants.kMaxTurnRateButton)
    ));
  }

  /**
   * Use this method to define your trigger->command mappings. 
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null; // TODO []
  }
}
