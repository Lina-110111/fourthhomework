package cn.edu.sicnu.cs.hln.fourthhomework

import android.content.Context

class UserInfoManager(context: Context) {
    private val sp = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)

    fun saveInfo(name: String, gender: String, sid: String, phone: String, email: String, hobby: String) {
        sp.edit().apply {
            putString("name", name)
            putString("gender", gender)
            putString("sid", sid)
            putString("phone", phone)
            putString("email", email)
            putString("hobby", hobby)
            apply()
        }
    }

    fun getInfo(key: String, default: String): String = sp.getString(key, default) ?: default

    // 新增：保存头像的 Base64 字符串
    fun saveAvatar(base64Str: String) {
        sp.edit().putString("avatar_base64", base64Str).apply()
    }

    // 新增：读取头像的 Base64 字符串
    fun getAvatar(): String? {
        return sp.getString("avatar_base64", null)
    }

    fun saveHighScore(score: Int) {
        val current = sp.getInt("high_score", 0)
        if (score > current) {
            sp.edit().putInt("high_score", score).apply()
        }
    }
    fun getHighScore(): Int = sp.getInt("high_score", 0)
}