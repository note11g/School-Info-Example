package com.note11.schoolinfoapp.util.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val today = SimpleDateFormat("M월 d일 (E)", Locale.KOREA).format(Calendar.getInstance().time)

        intent?.let {
            val title = "$today 시간표"

            val notificationData = Data.Builder()
                .putString(AlarmConst.NOTIFICATION_TITLE_INTENT, title)
                .build()

            val workRequest =
                OneTimeWorkRequestBuilder<ScheduledWorker>()
                    .setInputData(notificationData)
                    .build()

            val workManager = WorkManager.getInstance(context!!)
            workManager.enqueue(workRequest)
        }
    }
}