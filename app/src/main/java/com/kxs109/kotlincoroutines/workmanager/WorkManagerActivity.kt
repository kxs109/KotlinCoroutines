package com.kxs109.kotlincoroutines.workmanager

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.kxs109.kotlincoroutines.R


/**
 * @des:
 * @author: zhh
 * @date: 2022/9/25 15
 */
@RequiresApi(Build.VERSION_CODES.N)
class WorkManagerActivity : AppCompatActivity() {
    private val receiver = CameraReceiver()

    private val myConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .addContentUriTrigger(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true)
        .addContentUriTrigger(MediaStore.Images.Media.INTERNAL_CONTENT_URI, true)
        .build()

    private val request = OneTimeWorkRequestBuilder<UpImageWorker>()
        .setConstraints(myConstraints)
        .addTag("A")
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<View>(R.id.common_btn).setOnClickListener {
            start()
//        }

        WorkManager.getInstance(application)
            .getWorkInfoByIdLiveData(request.id)
            .observeForever{
                Log.e("tag",""+it.state)
                if (it.state == WorkInfo.State.SUCCEEDED){
                    Thread.sleep(500)
                    start()
                }
            }
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.hardware.action.NEW_PICTURE")
        intentFilter.addDataType("image/*")
        registerReceiver(receiver, intentFilter)
    }

    private fun start() {
        WorkManager.getInstance(application).enqueueUniqueWork(
            IMAGE_MANIPULATION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun cancelWork() {
        WorkManager.getInstance(application).cancelWorkById(request.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelWork()
        unregisterReceiver(receiver)
    }
}