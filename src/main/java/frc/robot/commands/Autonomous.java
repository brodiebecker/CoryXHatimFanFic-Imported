// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AlphaMotors;
import frc.robot.subsystems.DriveTrain;

public class Autonomous extends CommandBase {
  /** Creates a new Drive. */

  private DriveTrain driveTrain;

  private double startTime;

  public Autonomous(DriveTrain drivesub) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivesub);
    driveTrain = drivesub;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (edu.wpi.first.wpilibj.Timer.getFPGATimestamp() < startTime) {
      RobotContainer.driveTrain.autonomousMotorControll(.25, 0, 0);
    }else{
      RobotContainer.driveTrain.autonomousMotorControll(0, 0, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}