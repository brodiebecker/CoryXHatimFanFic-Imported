/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final static DriveTrain driveTrain = new DriveTrain();

  private final PlsWork plsWork = new PlsWork(driveTrain);
  private final Autonomous autonomous = new Autonomous(driveTrain);
   /**
   * The Driver Joystick declaration and the button definitions associated with it.
   */

  public static final Joystick driverJoystick = new Joystick(0);
  
  
  /**
   * The Operator Joystick declaration and the button definitions associated with it.
   */ 

  private static final Joystick operatorJoystick = new Joystick(1);

  private final static JoystickButton changeHandlerPositionButton = new JoystickButton(operatorJoystick, Constants.operatorButtonRightJoyClick);
  private final static JoystickButton windLauncherUpButton = new JoystickButton(operatorJoystick, Constants.operatorButtonX);
  private final static JoystickButton windLauncherDownButton = new JoystickButton(operatorJoystick, Constants.operatorButtonY);
  private final static JoystickButton lowerLauncherButton  = new JoystickButton(operatorJoystick, Constants.operatorButtonLB);
  private final static JoystickButton raiseLauncherButton = new JoystickButton(operatorJoystick, Constants.operatorButtonRB);
  private final static JoystickButton runWinchButton = new JoystickButton(operatorJoystick, Constants.operatorButtonBack);
  private final static JoystickButton disengageStopBallSoneloidButton = new JoystickButton(operatorJoystick, Constants.operatorButtonLeftJoyClick);



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    
    driveTrain.setDefaultCommand(plsWork);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //Chooses which command will run during autonomous
    return autonomous;
  }
  public static double getDriverAxis(int axis) {
    if (axis == 1 || axis == 5) {
      return -driverJoystick.getRawAxis(axis);
    } else {
      return driverJoystick.getRawAxis(axis);
    }
  }

  public static double getOperatorAxis(int axis) {
     if (axis == 1 || axis == 5) {
         return -operatorJoystick.getRawAxis(axis);
        } else {
         return operatorJoystick.getRawAxis(axis);
        }
    }
    public static boolean handlerPositionValue() {
        return changeHandlerPositionButton.get();
      }
    
      public static boolean climbButtonValue() {
        return runWinchButton.get();
      }
    
      public static boolean launcherButVal(){
        return windLauncherUpButton.get();
      }
    
      public static boolean stopWindin(){
        return windLauncherDownButton.get();
      }
    
      public static boolean lowerButVal(){
        return lowerLauncherButton.get();
      }
      public static boolean raiseButVal(){
        return raiseLauncherButton.get();
      }
    
      public static boolean disengageStopBallSoneloidButtonValue() {
        return disengageStopBallSoneloidButton.get();
      }
}

