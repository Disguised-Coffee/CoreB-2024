/**
 * Fixed version of the Limelight system for NT4.
 * 
 *  Last updated as of: 2/9/24
 * 
 */
package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
    // Made by the one and only Alex.
    private static final NetworkTable TABLE = NetworkTableInstance.getDefault().getTable("limelight");
    private final DoubleArraySubscriber poseSubscriber;
    private final IntegerSubscriber idSubscriber;

    /**
     * Initializes the funny limelight subsystem by binding the network table settings.
     */
    public Limelight(){
        // setup subscribers
        poseSubscriber = TABLE.getDoubleArrayTopic("targetpose_robotspace").subscribe(new double[] {-1});
        idSubscriber = TABLE.getIntegerTopic("targetpose_robotspace").subscribe(-1);
    }

    /**
     * 
     * Determines if Apriltag is in sight of Limeligt
     * 
     * @return if April tag has been detected.
     */
    public boolean hasTarget() {
        return getTargetPos() != null;
    }


    public int getTargetID(){
        System.out.println(String.format("Getting ID of %d", (int)idSubscriber.get()));
        return (int) idSubscriber.get();
    }
    /**
     * Retrieves the position of a limelight
     * 
     * @return
     *  'null' if a target hasn't been detected,
     *  a Transform3d object if it is.
     *  
     */
    public Transform3d getTargetPos() {
        var pose = poseSubscriber.get(); // No publisher needed, just some lambda stuff?
        

        System.out.println("Getting pose from Limelight: \n" + pose); // CHECK ME!!! [] 1

        // explore Pairs later. (https://github.com/BotDogs4645/SY2024-CORE-A/blob/master/src/main/java/frc/robot/subsystems/Limelight.java)

        if (pose[0] == -1) return null;
        
        return new Transform3d(
            new Translation3d(pose[0], pose[1], pose[2]), // Translation (X,Y,Z) 
            new Rotation3d(pose[3], pose[4], pose[5]) // Rotation(Roll,Pitch,Yaw)
        );
    }

    /*
     * Probably useless for now?
     */
    public Transform3d calculateLocation() {
        var pos = getTargetPos();
        if (pos == null) return null; // Cannot calculate location if we can't see a target!
        
        // TODO
        return null;
    }

}