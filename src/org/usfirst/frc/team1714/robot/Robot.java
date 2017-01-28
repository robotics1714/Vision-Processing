package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.*;
import org.opencv.core.*;
import org.opencv.imgproc.*;

import com.ctre.CANTalon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	UsbCamera camera;
	Mat source, out;
	GripPipelineTape pipe;
	CvSink sink;
	CvSource outstream;
	int centerX;
	CANTalon talon;

	@Override
	public void robotInit() {
		drive = new RobotDrive(0, 1);
		
		camera = CameraServer.getInstance().startAutomaticCapture();
		System.out.print(camera.getVideoMode().toString());
		camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 320, 240, 30);
		camera.setExposureManual(5);
		camera.setExposureHoldCurrent();
		camera.setWhiteBalanceManual(4500);
		camera.setWhiteBalanceHoldCurrent();
		
		outstream = CameraServer.getInstance().putVideo("Mask", 320, 240);
		
		source = new Mat();
		out = new Mat();
		sink = CameraServer.getInstance().getVideo();

		pipe = new GripPipelineTape();
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopPeriodic() {
		sink.grabFrame(source);
		pipe.process(source);
		out = pipe.maskOutput();
		if(!pipe.filterContoursOutput().isEmpty())
		{
			Rect r = Imgproc.boundingRect(pipe.filterContoursOutput().get(0));
			centerX = r.x + (r.width / 2);
			System.out.println(((double)centerX)/320);
			drive.tankDrive(0, ((double)centerX)/320);
		}
		else
		{
			System.out.println("it's 0 time");
			drive.tankDrive(0, 0);
		}
		outstream.putFrame(out);
	}

	@Override
	public void testPeriodic() {
	}
}

