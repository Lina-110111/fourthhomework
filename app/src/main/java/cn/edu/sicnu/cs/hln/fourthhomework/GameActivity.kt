package cn.edu.sicnu.cs.hln.fourthhomework

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cs.hln.fourthhomework.model.CardMatchingGame

class GameActivity : AppCompatActivity() {
    private lateinit var game: CardMatchingGame
    private lateinit var tvScore: TextView
    private lateinit var adapter: CardAdapter
    private lateinit var userManager: UserInfoManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        userManager = UserInfoManager(this)

        tvScore = findViewById(R.id.tv_score)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // 初始化 24 张牌的模型
        game = CardMatchingGame(24)

        // 判断横竖屏决定列数
        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 6 else 4
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)

        // 初始化 Adapter
        adapter = CardAdapter(game) { position ->
            game.chooseCardAtIndex(position)
            updateView()
        }
        recyclerView.adapter = adapter

        // 重置按钮
        findViewById<Button>(R.id.btn_reset).setOnClickListener {
            game.reset(24)
            updateView()
        }

        // 退出按钮
        findViewById<Button>(R.id.btn_exit).setOnClickListener {
            userManager.saveHighScore(game.score)
            finish()
        }

        updateView()
    }

    private fun updateView() {
        tvScore.text = getString(R.string.score_format, game.score)
        adapter.notifyDataSetChanged()
    }

    // --- 新增：加载游戏页面的菜单 ---
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 直接复用主界面的菜单文件
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // --- 新增：处理菜单点击事件 ---
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_about) {
            AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok, null)
                .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}