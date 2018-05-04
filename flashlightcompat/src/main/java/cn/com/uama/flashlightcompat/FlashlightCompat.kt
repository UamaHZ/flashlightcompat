package cn.com.uama.flashlightcompat

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import android.support.v4.content.ContextCompat
import io.reactivex.disposables.Disposable

/**
 * 请求相机权限结果事件
 */
class CameraPermissionResultEvent(val granted: Boolean)

object FlashlightCompat {

    fun switch(context: Context, on: Boolean) = if (on) {
        turnOn(context)
    } else {
        turnOff(context)
    }

    fun turnOn(context: Context) {
        // 判断是否有相机权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            lateinit var disposable: Disposable
            disposable = RxBus.toObservable(CameraPermissionResultEvent::class.java)
                    .map { it.granted }
                    .subscribe {
                        if (it) {
                            turnOn(context)
                        }
                        disposable.dispose()
                    }

            // 启动一个透明的 activity 来申请权限
            val intent = Intent(context, RequestCameraPermissionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FlashlightM.turnOn(context)
        } else {
            FlashlightLegacy.turnOn()
        }
    }

    fun turnOff(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FlashlightM.turnOff(context)
        } else {
            FlashlightLegacy.turnOff()
        }
    }

}

private object FlashlightLegacy {
    private var camera: Camera? = null

    fun turnOn() {
        try {
            if (camera == null) camera = Camera.open()
            val cameraParams = camera?.parameters
            cameraParams?.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            camera?.parameters = cameraParams
            camera?.startPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun turnOff() {
        camera?.stopPreview()
        camera?.release()
        camera = null
    }
}

@TargetApi(Build.VERSION_CODES.M)
private object FlashlightM {

    private var cameraManager: CameraManager? = null

    fun turnOn(context: Context) {
        if (cameraManager == null) {
            cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        }

        val cameraId = cameraManager?.cameraIdList?.get(0)
        cameraManager?.setTorchMode(cameraId, true)
    }

    fun turnOff(context: Context) {
        val cameraId = cameraManager?.cameraIdList?.get(0)
        cameraManager?.setTorchMode(cameraId, false)
    }
}
