package cn.edu.sicnu.cs.hln.fourthhomework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val infoFragment = InfoFragment.newInstance()
    private val game2Fragment = GameFragment.newInstance(2)
    private val game3Fragment = GameFragment.newInstance(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNv = findViewById<BottomNavigationView>(R.id.bottomNv)

        if (savedInstanceState == null) {
            // 初始化时，把三个 Fragment 都加进去，但只显示第一个
            supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, infoFragment)
                .add(R.id.frameLayout, game2Fragment)
                .add(R.id.frameLayout, game3Fragment)
                .hide(game2Fragment)
                .hide(game3Fragment)
                .commit()
        }

        bottomNv.setOnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()
            // 先全部隐藏
            transaction.hide(infoFragment)
            transaction.hide(game2Fragment)
            transaction.hide(game3Fragment)

            // 再显示对应的那个
            when (item.itemId) {
                R.id.nav_info -> transaction.show(infoFragment)
                R.id.nav_game2 -> transaction.show(game2Fragment)
                R.id.nav_game3 -> transaction.show(game3Fragment)
            }
            transaction.commit()
            true
        }
    }
}