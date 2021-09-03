package com.note11.schoolinfoapp.util.alarm

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.note11.schoolinfoapp.network.time.TimeManager
import com.note11.schoolinfoapp.util.DataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class ScheduledWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {
            val title = inputData.getString(AlarmConst.NOTIFICATION_TITLE_INTENT).toString()

            DataUtil(context).getUserInfoOnce()?.let {
                val timeTableList = TimeManager.getTimeTable(it)

                timeTableList?.let { list ->
                    var message = ""
                    list.map { v -> message += "${v.period}교시 ${v.subjectTitle}\n" }
                    NotificationUtil(context).sendPush(title, message)
                }
            }
        }

        Result.success()
    }
}