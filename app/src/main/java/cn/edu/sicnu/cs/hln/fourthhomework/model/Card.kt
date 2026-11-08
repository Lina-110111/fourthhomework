package cn.edu.sicnu.cs.hln.fourthhomework.model

import java.io.Serializable

class Card(
    private var suit: String,
    private var rank: String,
    var isChosen: Boolean = false,
    var isMatched: Boolean = false
) : Serializable {

    companion object {
        val rankStrings = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val validSuits = arrayOf("♥", "♦", "♠", "♣")
    }

    override fun toString(): String = suit + rank

    fun match(otherCards: Array<Card>): Int {
        var score = 0
        val allSameRank = otherCards.all { it.rank == rank }
        val allSameSuit = otherCards.all { it.suit == suit }

        if (allSameRank) {
            score = if (otherCards.size == 2) 4 else 8
        } else if (allSameSuit) {
            score = if (otherCards.size == 2) 1 else 3
        }
        return score
    }
}