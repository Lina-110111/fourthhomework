package cn.edu.sicnu.cs.hln.fourthhomework.model

class CardMatchingGame(count: Int)  {
    var score = 0
        private set

    private var cards: MutableList<Card> = mutableListOf()

    val MISMATCH_PENALTY = 2
    val MATCH_BONUS = 4
    val COST_TO_CHOOSE = 1

    init {
        reset(count)
    }

    // 新增 reset 方法
    fun reset(count: Int) {
        score = 0
        val deck = Deck()
        cards.clear()
        for (i in 1..count) {
            val card: Card? = deck.drawRandomCard()
            if (card != null) {
                cards.add(card)
            }
        }
    }

    fun cardAtIndex(index: Int): Card {
        return cards[index]
    }

    // 获取总牌数
    val cardCount: Int get() = cards.size

    fun chooseCardAtIndex(index: Int) {
        val card = cardAtIndex(index)
        if (!card.isMatched) {
            if (card.isChosen) {
                card.isChosen = false // 再次点击取消选中
            } else {
                // 找出当前已被选中的牌
                val chosenCards = cards.filter { it.isChosen && !it.isMatched }

                // 如果加上当前这张牌，总数达到了 3 张，则进行判定
                if (chosenCards.size == 2) {
                    val matchCards = arrayOf(chosenCards[0], chosenCards[1], card)
                    val matchScore = card.match(matchCards)

                    if (matchScore > 0) {
                        score += matchScore * MATCH_BONUS
                        // 3张牌都标记为已匹配
                        chosenCards.forEach { it.isMatched = true }
                        card.isMatched = true
                        card.isChosen = false // 匹配后不需要高亮
                    } else {
                        score -= MISMATCH_PENALTY
                        // 匹配失败，取消前两张的选中状态
                        chosenCards.forEach { it.isChosen = false }
                    }
                }
                score -= COST_TO_CHOOSE
                card.isChosen = true
            }
        }
    }
}