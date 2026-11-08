package cn.edu.sicnu.cs.hln.fourthhomework

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cs.hln.fourthhomework.model.CardMatchingGame
import cn.edu.sicnu.cs.hln.fourthhomework.model.UserInfo

class GameFragment : Fragment() {

    private var game: CardMatchingGame? = null
    private var matchCount: Int = 2
    private lateinit var adapter: CardAdapter
    private lateinit var tvScore: TextView

    companion object {
        private const val ARG_MATCH_COUNT = "match_count"

        fun newInstance(matchCount: Int): GameFragment {
            val fragment = GameFragment()
            val args = Bundle()
            args.putInt(ARG_MATCH_COUNT, matchCount)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchCount = arguments?.getInt(ARG_MATCH_COUNT) ?: 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvScore = view.findViewById(R.id.tv_score)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val fileName = "game_${matchCount}.dat"

        // 恢复历史信息
        game = FileUtils.loadObject(requireContext(), fileName)
        if (game == null) {
            game = CardMatchingGame(24, requiredMatchCount = matchCount)
        }

        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 6 else 4
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        // 复用作业五的 CardAdapter
        adapter = CardAdapter(game!!) { position ->
            game!!.chooseCardAtIndex(position)
            updateView()
        }
        recyclerView.adapter = adapter

        view.findViewById<Button>(R.id.btn_reset).setOnClickListener {
            game!!.reset(24)
            updateView()
        }

        updateView()
    }

    private fun updateView() {
        val currentGame = game ?: return
        tvScore.text = "得分：${currentGame.score}"
        adapter.notifyDataSetChanged()
    }

    // 页面停止时保存进度
    override fun onStop() {
        super.onStop()
        val fileName = "game_${matchCount}.dat"
        game?.let {
            FileUtils.saveObject(requireContext(), fileName, it)
            updateHighestScore(it.score)
        }
    }

    private fun updateHighestScore(currentScore: Int) {
        var userInfo: UserInfo = FileUtils.loadObject(requireContext(), "user_info.dat") ?: UserInfo()
        if (currentScore > userInfo.highScore) {
            userInfo.highScore = currentScore
            FileUtils.saveObject(requireContext(), "user_info.dat", userInfo)
        }
    }
}