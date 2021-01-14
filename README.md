# Build Broadcast Receiver

```kotlin
class NotificationReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        val repeatingIntent = Intent(context, RepeatingActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //replace the same old activity when activity is already opened
        val requestCode = 100
	val pendingIntent = PendingIntent.getActivity(context, requestCode, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context!!, "notify")
        val notification = notificationBuilder.setContentTitle("Demo App Notification")
            .setContentIntent(pendingIntent)
            .setContentText("New Notification From Demo App")
            .setTicker("New Message Alert!")
            .setAutoCancel(false)
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        notificationManager?.notify(0, notification)

    }
}
```

# Register Receiver on Manifest

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.paigesoftware.localschedulednotification">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.LocalScheduledNotification">
        <activity android:name=".RepeatingActivity"/>
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <receiver android:name=".NotificationReceiver"/>
    </application>

</manifest>
```

# Set Alarm Manager with request code

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        button_set_notification.setOnClickListener {

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 22)
            calendar.set(Calendar.MINUTE, 10)
            val intent = Intent(this, NotificationReceiver::class.java)
	    val requestCode = 100
            val pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = getSystemService(ALARM_SERVICE) as? AlarmManager
            alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        }

    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notify channel"
            val description = "channel for test"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel =  NotificationChannel("notify", name, importance)
            notificationChannel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
```
