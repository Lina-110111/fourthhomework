package cn.edu.sicnu.cs.hln.fourthhomework

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.allViews
import cn.edu.sicnu.cs.hln.fourthhomework.model.CardMatchingGame // 确保你的模型在这个包下

class GameActivity : AppCompatActivity() {
    private lateinit var game: CardMatchingGame
    private lateinit var tvScore: TextView
    private val cardButtons = mutableListOf<Button>()
    private lateinit var userManager: UserInfoManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        userManager = UserInfoManager(this)

        tvScore = findViewById(R.id.tv_score)
        game = CardMatchingGame(16)

        val rootView = findViewById<ViewGroup>(R.id.main)
        var index = 0

        // 遍历界面上的按钮
        for (v in rootView.allViews) {
            // 只要 Button，且排除底部的重置和退出按钮
            if (v is Button && v.id != R.id.btn_reset && v.id != R.id.btn_exit) {
                val i = index
                cardButtons.add(v)
                v.setOnClickListener {
                    game.chooseCardAtIndex(i) // 控制器调用模型
                    updateView()              // 刷新视图
                }
                index++
            }
        }

        // 重置按钮
        findViewById<Button>(R.id.btn_reset).setOnClickListener {
            game = CardMatchingGame(16)
            updateView()
        }

        // 退出按钮
        findViewById<Button>(R.id.btn_exit).setOnClickListener {
            userManager.saveHighScore(game.score) // 保存最高分
            finish()
        }

        updateView()
    }

    // 更新视图
    private fun updateView() {
        tvScore.text = getString(R.string.score_format, game.score)

        for (i in 0 until 16) {
            val card = game.cardAtIndex(i)
            val button = cardButtons[i]

            if (!card.isChosen) {
                // 未选中：显示卡背图
                button.setBackgroundResource(R.drawable.card_bg)
                button.text = ""
            } else {
                // 选中：显示白底和文字
                button.setBackgroundColor(Color.WHITE)
                button.text = card.toString()
            }

            // 匹配成功变半透明
            if (card.isMatched) {
                button.alpha = 0.5f
            } else {
                button.alpha = 1.0f
            }
        }
    }
}