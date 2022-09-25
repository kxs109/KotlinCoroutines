package com.kxs109.kotlincoroutines.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * @des:
 * @author: zhh
 * @date: 2022/9/25 13
 */
class UpImageWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            if (isStopped){
                Result.failure()
            }else {
                Log.e("tag", Thread.currentThread().name + "上传相片")
                Result.success()
            }
        } catch (throwable: Throwable) {
            Log.e("tag", "上传相片失败" + throwable.message)
            Result.failure()
        }
    }

    override fun onStopped() {
        Log.e("tag","任务结束")
        super.onStopped()
    }
}