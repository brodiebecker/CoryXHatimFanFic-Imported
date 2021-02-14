// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class Autonomous extends CommandBase {
  /** Creates a new Drive. */

  private DriveTrain driveTrain;

  private double autoStartTime;
  private double currentTime;

  public Autonomous(DriveTrain drivesub) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivesub);
    driveTrain = drivesub;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    autoStartTime = edu.wpi.first.wpilibj.Timer.getFPGATimestamp();
    RobotContainer.driveTrain.gyroStabilization(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentTime=edu.wpi.first.wpilibj.Timer.getFPGATimestamp()-autoStartTime;
RobotContainer.driveTrain.autonomousMotorControll(.3, 45, 0);
    SmartDashboard.putNumber("CurrentTime", currentTime);
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