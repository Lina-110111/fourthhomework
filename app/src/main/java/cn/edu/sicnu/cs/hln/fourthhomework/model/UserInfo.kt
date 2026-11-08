package cn.edu.sicnu.cs.hln.fourthhomework.model

import java.io.Serializable

class UserInfo(
    var name: String = "huang",
    var gender: String = "女",
    var sid: String = "2024110111",
    var phone: String = "13800000000",
    var email: String = "test@edu.cn",
    var hobby: String = "旅游,阅读",
    var highScore: Int = 0
) : Serializable