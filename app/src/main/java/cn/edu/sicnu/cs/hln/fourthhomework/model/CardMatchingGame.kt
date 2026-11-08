package cn.edu.sicnu.cs.hln.fourthhomework.model

import java.io.Serializable

class CardMatchingGame(val count: Int, val requiredMatchCount: Int = 2) : Serializable {
    var score = 0
        private set

    private var cards: MutableList<Card> = mutableListOf()

    val MISMATCH_PENALTY = 2
    val MATCH_BONUS = 4
    val COST_TO_CHOOSE = 1

    init {
        reset(count)
    }

    fun reset(count: Int) {
        score = 0
        val deck = Deck()
        cards.clear()
        for (i in 1..count) {
            val card = deck.drawRandomCard()
            if (card != null) cards.add(card)
        }
    }

    fun cardAtIndex(index: Int): Card = cards[index]
    val cardCount: Int get() = cards.size

    fun chooseCardAtIndex(index: Int) {
        val card = cardAtIndex(index)
        if (!card.isMatched) {
            if (card.isChosen) {
                card.isChosen = false
            } else {
                val chosenCards = cards.filter { it.isChosen && !it.isMatched }

                // 根据 requiredMatchCount 判断是否满足匹配条件（2张或3张）
                if (chosenCards.size == requiredMatchCount - 1) {
                    val matchCards = chosenCards.toMutableList()
                    matchCards.add(card)

                    val matchScore = card.match(matchCards.toTypedArray())
                    if (matchScore > 0) {
                        score += matchScore * MATCH_BONUS
                        matchCards.forEach { it.isMatched = true }
                        card.isChosen = false
                    } else {
                        score -= MISMATCH_PENALTY
                        chosenCards.forEach { it.isChosen = false }
                    }
                }
                score -= COST_TO_CHOOSE
                card.isChosen = true
            }
        }
    }
}