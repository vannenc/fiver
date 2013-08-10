package ws.vannen.fiver.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;


@SuppressLint("NewApi")
public class CoreApp extends Application {
	
	private static final Boolean DEVELOPER_MODE = true;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate() {
		super.onCreate();

		     if (DEVELOPER_MODE) {
		         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		                 .detectDiskReads()
		                 .detectDiskWrites()
		                 .detectNetwork()   // or .detectAll() for all detectable problems
		                 .penaltyLog()
		                 .build());
		         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		                 //.detectLeakedSqlLiteObjects()
		                 //.detectLeakedClosableObjects()
		                 .penaltyLog()
		                 .penaltyDeath()
		                 .build());
		     }
		     super.onCreate();

	}
	
	


}
