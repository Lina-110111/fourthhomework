package cn.edu.sicnu.cs.hln.fourthhomework

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var userManager: UserInfoManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userManager = UserInfoManager(this)

        findViewById<Button>(R.id.btn_edit).setOnClickListener {
            startActivity(Intent(this, EditInfoActivity::class.java))
        }
        findViewById<Button>(R.id.btn_game).setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // 1. 刷新文字
        findViewById<TextView>(R.id.tv_name).text = getString(R.string.label_name, userManager.getInfo("name", "张三"))
        findViewById<TextView>(R.id.tv_gender).text = getString(R.string.label_gender, userManager.getInfo("gender", "男"))
        findViewById<TextView>(R.id.tv_sid).text = getString(R.string.label_sid, userManager.getInfo("sid", "20230001"))
        findViewById<TextView>(R.id.tv_phone).text = getString(R.string.label_phone, userManager.getInfo("phone", "13800000000"))
        findViewById<TextView>(R.id.tv_email).text = getString(R.string.label_email, userManager.getInfo("email", "test@edu.cn"))
        findViewById<TextView>(R.id.tv_hobby).text = getString(R.string.label_hobby, userManager.getInfo("hobby", "无"))
        findViewById<TextView>(R.id.tv_high_score).text = getString(R.string.label_high_score, userManager.getHighScore())

        // 2. 刷新头像
        val avatarBase64 = userManager.getAvatar()
        if (avatarBase64 != null) {
            try {
                val decodedBytes = Base64.decode(avatarBase64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                findViewById<ImageView>(R.id.iv_main_avatar).setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}