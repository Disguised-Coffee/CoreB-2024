// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants{
    public static final int 
                            //DRIVING
                            kDriverControllerPort = 0,
                            kMaxSpeedButton = 3,
                            kMaxTurnRateButton = 4, // TODO CHANGE
                            //SHOOTING
                            kShooterButton = 4,
                            kAcceptButton = 2, // TODO CHECK.
                            kToggleModeButton = 3;
    public static final double kDefaultFlywheelSpeed = 0.8,
                            kSecondsToWait = 1.5,
                            kTimeToAcceptNote = 0.5,
                            kSlewConstant = 0.5,
                            kFeederOperatingSpeed = 0.5;

  }

  public static class AutonomousConstants{
    // Should be used when intially searching for a tag
    public final class Searching{
      public static final double  kMaxSearchingTime = 12,
                                  kSearchTurnSpeed = 0.5;
    }
    public final class Driving{
      public static final double kDistanceThresholdFromTag = 1;
      public static final int kTimeByTagUntilTimeOut = 12;
      public static final double kTicksToMeters = 1.0; //TODO
    }
  }
  
  public static class ManipulatorConstants {
    public static final double  kLauncherSpeed = 1.0,
                                kFeederSpeed = 0.3;
  }

  public static class CanIDs{
    public static final int 
                            //DRIVETRAIN
                            FRONTLEFT_MOTOR = 4, 
                            REARLEFT_MOTOR = 5, 
                            FRONTRIGHT_MOTOR = 7, 
                            REARRIGHT_MOTOR = 6,
                            //SHOOTER
                            FLYWHEEL_MOTOR = 3, 
                            FEEDER_MOTOR = 2,
                            //PIGEON
                            PIGEON_COMP = 8;
  }

  public static class DrivetrainConstants{
    public static final double  kDefaultDriveSpeed = 0.7,
                                kDefaultTurnSpeed = 1,
                                kDefaultDeadband = 0.05;
  }
  
}
