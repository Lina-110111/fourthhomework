package cn.edu.sicnu.cs.hln.fourthhomework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import cn.edu.sicnu.cs.hln.fourthhomework.model.UserInfo

class InfoFragment : Fragment() {

    private lateinit var userInfo: UserInfo

    companion object {
        fun newInstance() = InfoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 从文件加载 UserInfo
        userInfo = FileUtils.loadObject(requireContext(), "user_info.dat") ?: UserInfo()

        updateUI(view)

        view.findViewById<Button>(R.id.btn_change).setOnClickListener {
            userInfo.name = "黄丽娜 ${System.currentTimeMillis() % 100}"
            FileUtils.saveObject(requireContext(), "user_info.dat", userInfo)
            updateUI(view)
        }
    }

    private fun updateUI(view: View) {
        view.findViewById<TextView>(R.id.tv_name).text = "姓名：${userInfo.name}"
        view.findViewById<TextView>(R.id.tv_sid).text = "学号：${userInfo.sid}"
        view.findViewById<TextView>(R.id.tv_high_score).text = "最高分：${userInfo.highScore}"
    }
}