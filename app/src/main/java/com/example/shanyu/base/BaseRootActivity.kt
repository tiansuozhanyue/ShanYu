package com.example.shanyu.base

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shanyu.utils.FileUtil
import com.example.shanyu.utils.LogUtil
import com.example.shanyu.utils.StringUtil
import com.example.shanyu.widget.ChangePhotoDialog

abstract class BaseRootActivity : AppCompatActivity() {

    private var dialog: ChangePhotoDialog? = null
    private var callBack: GetPhotoCallBack? = null
    private var photoOutputUri: Uri? = null
    private var photoUri: Uri? = null
    private var outputX = 400
    private var outputY = 400
    private var aspectX = 1
    private var aspectY = 1

    abstract fun initView()

    /**
     * 选择图片
     *
     * @param title    标题内容
     * @param size     [outputX  outputY  aspectX  aspectY]
     * @param callBack 剪裁后的图片返回
     */
    fun selectPhoto(title: String, size: IntArray?, callBack: GetPhotoCallBack) {
        if (StringUtil.isEmpty(title) || size == null || size.size != 4)
            return
        outputX = size[0]
        outputY = size[1]
        aspectX = size[2]
        aspectY = size[3]
        this.callBack = callBack
        dialog = ChangePhotoDialog(this)
        dialog!!.setTitle(title)
        dialog!!.setMessage1("相册")
        dialog!!.setMessage2("拍照")
        dialog!!.setCancelMessage("取消")
        dialog!!.isShowCancel = false
        dialog!!.setCanceledOnTouchOutside(true)
        dialog!!.setTvDialogCancelClickListener { dialog!!.dismiss() }
        dialog!!.setTvDialogCameraClickListener("拍照") {
            dialog!!.dismiss()
            startCamera()
        }
        dialog!!.setTvDialogPhotoClickListener("相册") {
            dialog!!.dismiss()
            openPic()
        }
        dialog!!.show()
    }

    //相册
    private fun openPic() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK)
    }


    // 拍照
    private fun startCamera() {
        photoUri = FileUtil.getUri(this, FileUtil.IMAGE)
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        //开启临时权限
            takePhotoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        startActivityForResult(takePhotoIntent, REQUEST_CODE_CAMERA)
    }

    // 裁剪图片
    private fun cropPhoto(inputUri: Uri?) {
        photoOutputUri = FileUtil.getUri(this, FileUtil.IMAGE)
        val cropPhotoIntent = Intent("com.android.camera.action.CROP")
        cropPhotoIntent.setDataAndType(inputUri, "image/*")
        cropPhotoIntent.putExtra("outputX", outputX)
        cropPhotoIntent.putExtra("outputY", outputY)
        cropPhotoIntent.putExtra("aspectX", aspectX)
        cropPhotoIntent.putExtra("aspectY", aspectY)

        val resInfoList = packageManager.queryIntentActivities(cropPhotoIntent, PackageManager.MATCH_DEFAULT_ONLY)
        if (resInfoList.size == 0) {
            LogUtil.i("没有合适的应用程序")
            return
        }
        val resInfoIterator = resInfoList.iterator()
        while (resInfoIterator.hasNext()) {
            val resolveInfo = resInfoIterator.next() as ResolveInfo
            val packageName = resolveInfo.activityInfo.packageName
            grantUriPermission(packageName, photoOutputUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri)
        startActivityForResult(cropPhotoIntent, REQUEST_CODE_CUTTING)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_PICK       //相册
            -> if (data != null) {
                cropPhoto(data.data)
            }

            REQUEST_CODE_CAMERA// 拍照
            -> if (resultCode == -1) {
                cropPhoto(photoUri)
            }

            REQUEST_CODE_CUTTING // 拿到从相册选择截取后的剪切数据
            -> {
                if (data == null) {
                    return
                }
                //            File file = FileUtil.getFilePathFromURI(this,photoOutputUri);
                val filePath = FileUtil.getFilePathFromURI(this, photoOutputUri)

                if (filePath!!.exists() && callBack != null) {
                    callBack!!.selectPhotoCallback(photoOutputUri, filePath)
                } else {
                    Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK = 1//相册
        private const val REQUEST_CODE_CAMERA = 2 //拍照
        private const val REQUEST_CODE_CUTTING = 3//裁剪
    }

}