package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore;

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
	Mat source;
	GripPipeLineTape pipe;

	@Override
	public void robotInit() {
		camera = CameraServer.getInstance().startAutomaticCapture();
		System.out.print(camera.getVideoMode().pixelFormat().valueOf().getValue());
		camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 320, 240, 30);
		camera.setExposureManual(15);
		camera.setExposureHoldCurrent();
		
		source = new Mat();
		
		pipe = new GripPipelineTape();
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {

		}
	}

	@Override
	public void teleopPeriodic() {
		pipe.process(source);
	}

	@Override
	public void testPeriodic() {
	}
}

