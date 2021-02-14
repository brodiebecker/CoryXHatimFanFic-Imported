/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrainSubsystem.
   */

  private TalonFX frontLeftDriveMotor;
  private TalonFX frontRightDriveMotor;
  private TalonFX rearLeftDriveMotor;
  private TalonFX rearRightDriveMotor;

  public AlphaMotors motorFL;
  public AlphaMotors motorFR;
  public AlphaMotors motorRL;
  public AlphaMotors motorRR;

  public static double isSpeed;

  public double l;
  public double w;
  public double r;
  private static boolean gyroEnabled;

  public double deleteMeMore;
  public long ahh;

  public DriveTrain() {
    motorFL = new AlphaMotors(2, 1, 12, 10, 0);
    motorFR = new AlphaMotors(4, 3, 18, 17, 1);
    motorRL = new AlphaMotors(6, 5, 14, 13, 2);
    motorRR = new AlphaMotors(8, 7, 16, 15, 3);

    //Not used, deprecated by AlphaMotors Clcass
    
    // frontLeftDriveMotor = new TalonFX(1);
    // frontRightDriveMotor = new TalonFX(3);
    // rearLeftDriveMotor = new TalonFX(5);
    // rearRightDriveMotor = new TalonFX(7);

    isSpeed = 0;
    l = Constants.length;
    w = Constants.width;
    r = Math.sqrt((l * l) + (w * w));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveSwerveAxis(double leftX, double leftY, double rightX, double mod) {
    leftY *= -1;

    double a = leftX - rightX * (l / r);
    double b = leftX + rightX * (l / r);
    double d = (leftY - rightX * (w / r));
    double c = (leftY + rightX * (w / r));

    double FRDesiredSpeed = (Math.sqrt((b * b) + (d * d)));
    double RRDesiredSpeed = (Math.sqrt((a * a) + (d * d)));
    double FLDesiredSpeed = (Math.sqrt((b * b) + (c * c)) * -1);
    double RLDesiredSpeed = (Math.sqrt((a * a) + (c * c)) * -1);

    double FRAngle = (Math.atan2(b, d) / Math.PI);
    double RRAngle = (Math.atan2(a, d) / Math.PI);
    double FLAngle = Math.atan2(b, c) / Math.PI;
    double RLAngle = Math.atan2(a, c) / Math.PI;

    motorFL.drive(FLDesiredSpeed, FLAngle, mod);
    motorFR.drive(FRDesiredSpeed, FRAngle, mod);
    motorRR.drive(RRDesiredSpeed, RRAngle, mod);
    motorRL.drive(RLDesiredSpeed, RLAngle, mod);

    ahh = (long) FLAngle;
    deleteMeMore = FLAngle;
  }

  //Allowing to set gyro stabilization

  public void gyroStabilization(int enabled) {
    switch (enabled){
      case 0:
      gyroEnabled = false;
      break;
      case 1:
      gyroEnabled = true;
      break;
    }
  }

      // Maintaining heading via gyro - DISABLED, CAUSING ISSUES!
      public void driveWithGyro(double y, double x, double rotation, double modifier, String type) {
      if(gyroEnabled) {
        if (Math.abs(rotation) > .5) {
          RobotContainer.gyro.reset();
        }else if (Math.abs(rotation) < .2 && Math.abs(RobotContainer.gyro.getAngle()) > 2) {
          rotation -= .5;
        } else if (Math.abs(rotation) < .2 && RobotContainer.gyro.getAngle() < -2) {
          rotation += .5;
        }
      }

      switch (type) {
        case "TeleOp":
      moveSwerveAxis(x, y, rotation, modifier);
      break;
      case "Auto":
      autonomousMotorControll(modifier, y, rotation);
      break;
      }
    }

  public static void autonomousMotorControll(double speed, double direction, double rotation) {

    // CONTROLLING ROTATION

        // Maintaining heading via gyro - DISABLED, CAUSING ISSUES!

/*
        if(gyroEnabled) {
          if (Math.abs(rotation) > .5) {
            RobotContainer.gyro.reset();
          }else if (Math.abs(rotation) < .2 && Math.abs(RobotContainer.gyro.getAngle()) > 2) {
            rotation -= .5;
          } else if (Math.abs(rotation) < .2 && RobotContainer.gyro.getAngle() < -2) {
            rotation += .5;
          }
        }
        */
    // CONTROLLING DIRECTION

    if (direction > 360) {
      direction = 1;
    }else if(direction < 0) {
      direction = 360 + direction;
    }

    // Declaring variables used;
    double y;
    double x;
    int quadrant;

    // Ensuring that direction cannot lie on an axis, otherwise will divide by 0
    if (direction == 90 || direction == 180 || direction == 270 || direction == 360) {
      direction--;
    }
    if(direction == 0) {
      direction++;
    }

    // Setting quadrants in order to decide which operation to use

    if (direction > 0 && direction < 90) {
      quadrant = 1;
    } else if (direction > 90 && direction < 180) {
      quadrant = 2;
    } else if (direction > 180 && direction < 270) {
      quadrant = 3;
    } else if (direction > 270 && direction < 360) {
      quadrant = 4;
    } else {
      quadrant = 0;
    }

    // Converting degrees to Radians, as that is default for Java
    double directionRadians = direction * 0.01745329252;

    // Changing operations depending on what quadrant rotation is in

    switch (quadrant) {
      case 1:
        x = Math.cos((direction * 0.01745329252));
        y = Math.sin((direction * 0.01745329252));
        break;
      case 2:
        y = Math.cos(90 - (direction * 0.01745329252));
        x = Math.sin(90 - (direction * 0.01745329252));
        break;
      case 3:
        y = Math.cos(180 - (direction * 0.01745329252));
        x = Math.sin(180 - (direction * 0.01745329252));
        break;
      case 4:
        x = Math.cos(270 - (direction * 0.01745329252));
        y = Math.sin(270 - (direction * 0.01745329252));
        break;
      default:
        x = 0;
        y = 0;
        break;
    }

    y *= speed;
    x *= speed * -1;

    // Inputting results into drive programming.
    RobotContainer.driveTrain.moveSwerveAxis(y, x, rotation, 1);
  }

  public void zeroAllEncoders() {
    motorFL.zeroEncoder();
    motorFR.zeroEncoder();
    motorRL.zeroEncoder();
    motorRR.zeroEncoder();
  }

  public void findAllZeros() {
    motorFL.findZero();
    motorFR.findZero();
    motorRL.findZero();
    motorRR.findZero();
  }

  public void zeroAllEncodersBasedOnProx() {
    motorFL.zeroEncoderBasedOnProx();
    motorFR.zeroEncoderBasedOnProx();
    motorRL.zeroEncoderBasedOnProx();
    motorRR.zeroEncoderBasedOnProx();
  }
}
