package com.petrifiednightmares.singularityChess;

import static org.acra.ReportField.ANDROID_VERSION;
import static org.acra.ReportField.STACK_TRACE;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

import com.petrifiednightmares.singularityChess.logging.ACRA_Reporting;

@ReportsCrashes(formKey = ACRA_Reporting.FORM_KEY, mode = ReportingInteractionMode.TOAST, customReportContent = {
		ANDROID_VERSION, STACK_TRACE }, forceCloseDialogAfterToast = false, resToastText = R.string.crash_toast)
public class SingularityChess extends Application
{
	@Override
	public void onCreate()
	{
		ACRA.init(this);
		super.onCreate();
	}
}
