package cn.edu.sicnu.cs.hln.fourthhomework.model

class Card(private var suit: String, private var rank: String, var isChosen:Boolean = false, var isMatched:Boolean = false)  {
    companion object {
        val rankStrings= arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val validSuits = arrayOf("♥", "♦", "♠", "♣")
    }

    override fun toString(): String {
        return suit + rank
    }

    // 修改匹配逻辑：接收一个卡牌数组（通常是3张）
    fun match(otherCards: Array<Card>): Int {
        var score = 0
        if (otherCards.size == 3) {
            // 判断是否3张点数相同
            if (otherCards[0].rank == otherCards[1].rank && otherCards[1].rank == otherCards[2].rank) {
                score = 12 // 3张同点数给12分
            }
            // 判断是否3张花色相同
            else if (otherCards[0].suit == otherCards[1].suit && otherCards[1].suit == otherCards[2].suit) {
                score = 3  // 3张同花色给3分
            }
        }
        return score
    }
}