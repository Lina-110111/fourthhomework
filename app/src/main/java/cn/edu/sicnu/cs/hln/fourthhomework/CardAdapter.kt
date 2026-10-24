package cn.edu.sicnu.cs.hln.fourthhomework

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnu.cs.hln.fourthhomework.model.CardMatchingGame

class CardAdapter(
    private val game: CardMatchingGame,
    private val onCardClick: (Int) -> Unit // 点击回调
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.btn_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = game.cardAtIndex(position)
        val button = holder.button

        // 根据卡牌状态更新 UI
        if (!card.isChosen) {
            button.setBackgroundResource(R.drawable.card_bg)
            button.text = ""
        } else {
            button.setBackgroundColor(Color.WHITE)
            button.text = card.toString()
        }

        if (card.isMatched) {
            button.alpha = 0.5f
        } else {
            button.alpha = 1.0f
        }

        // 绑定点击事件
        button.setOnClickListener {
            onCardClick(position)
        }
    }

    override fun getItemCount(): Int = game.cardCount
}