package cn.edu.sicnu.cs.hln.fourthhomework

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream

class EditInfoActivity : AppCompatActivity() {
    private lateinit var userManager: UserInfoManager
    private var currentAvatarBase64: String? = null // 用于临时保存当前头像的 Base64 编码

    // 拍照回调
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            // 1. 显示在界面上
            findViewById<ImageView>(R.id.iv_avatar).setImageBitmap(bitmap)
            // 2. 将图片转换成 Base64 字符串保存到变量中
            currentAvatarBase64 = bitmapToBase64(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)
        userManager = UserInfoManager(this)

        // 绑定组件
        val etName = findViewById<EditText>(R.id.et_name)
        val etSid = findViewById<EditText>(R.id.et_sid)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val rgGender = findViewById<RadioGroup>(R.id.rg_gender)
        val rbMale = findViewById<RadioButton>(R.id.rb_male)
        val rbFemale = findViewById<RadioButton>(R.id.rb_female)
        val cbTravel = findViewById<CheckBox>(R.id.cb_travel)
        val cbRead = findViewById<CheckBox>(R.id.cb_read)
        val cbGame = findViewById<CheckBox>(R.id.cb_game)
        val cbShop = findViewById<CheckBox>(R.id.cb_shop)

        val ivAvatar = findViewById<ImageView>(R.id.iv_avatar)
        val ivCamera = findViewById<ImageView>(R.id.iv_camera)

        // 如果有历史头像，显示出来
        val savedAvatarBase64 = userManager.getAvatar()
        if (savedAvatarBase64 != null) {
            val bitmap = base64ToBitmap(savedAvatarBase64)
            if (bitmap != null) ivAvatar.setImageBitmap(bitmap)
        }

        // 点击拍照
        val openCamera = {
            try {
                takePictureLauncher.launch(null)
            } catch (e: Exception) {
                Toast.makeText(this, "无法打开相机", Toast.LENGTH_SHORT).show()
            }
        }
        ivAvatar.setOnClickListener { openCamera() }
        ivCamera.setOnClickListener { openCamera() }

        // 回显文字数据
        etName.setText(userManager.getInfo("name", "张三"))
        etSid.setText(userManager.getInfo("sid", "20230001"))
        etPhone.setText(userManager.getInfo("phone", "13800000000"))
        etEmail.setText(userManager.getInfo("email", "test@edu.cn"))

        if (userManager.getInfo("gender", "男") == "男") rbMale.isChecked = true else rbFemale.isChecked = true

        val hobbies = userManager.getInfo("hobby", "").split(",")
        cbTravel.isChecked = hobbies.contains("旅游")
        cbRead.isChecked = hobbies.contains("阅读")
        cbGame.isChecked = hobbies.contains("游戏")
        cbShop.isChecked = hobbies.contains("购物")

        // 取消按钮
        findViewById<Button>(R.id.btn_cancel).setOnClickListener { finish() }

        // 保存按钮
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val gender = if (rbMale.isChecked) "男" else "女"
            val hobbyList = mutableListOf<String>()
            if (cbTravel.isChecked) hobbyList.add("旅游")
            if (cbRead.isChecked) hobbyList.add("阅读")
            if (cbGame.isChecked) hobbyList.add("游戏")
            if (cbShop.isChecked) hobbyList.add("购物")
            val hobbyStr = hobbyList.joinToString(",")

            // 保存文字信息
            userManager.saveInfo(
                etName.text.toString(), gender, etSid.text.toString(),
                etPhone.text.toString(), etEmail.text.toString(), hobbyStr
            )

            // 保存头像信息（如果有新拍的照片）
            if (currentAvatarBase64 != null) {
                userManager.saveAvatar(currentAvatarBase64!!)
            }

            finish()
        }
    }

    // 工具方法：Bitmap 转 Base64
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream) // 压缩质量50%，减小体积
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    // 工具方法：Base64 转 Bitmap
    private fun base64ToBitmap(base64Str: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            null
        }
    }
}