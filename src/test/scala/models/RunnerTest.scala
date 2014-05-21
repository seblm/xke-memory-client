package models

import org.scalatest.{Matchers, FunSuite}
import play.api.libs.json.Json
import utils.Tools._

class RunnerTest extends FunSuite with Matchers {

	ignore("run ...") {
		val gameDimension = 6

		var availableCoords: Set[Coord] = (for {
			x <- (0 until gameDimension)
			y <- (0 until gameDimension)
		} yield { Coord(x,y) }).toSet

		println("start game with : " + availableCoords.size)

		var memory: Map[Card, Set[Coord]] = Map()

		while(!availableCoords.isEmpty) {
			// TODO prendre au pif
			val firstCoord = availableCoords.head
			val secondCoord = availableCoords.tail.head

			val request = Json.parse(s"[${firstCoord.toString}, ${secondCoord.toString}]")

			val game = sync(ping("http://172.16.31.113:3000/play", request))

			println(s"turn : $game")
			/*game.turn.cards match {
				case first :: second :: Nil => {
					val firstCard: Card = Card(first.symbol, first.color, first.found)
					memory += (firstCard -> memory.get(firstCard).map { coords => coords + firstCoord }.getOrElse(Set(firstCoord)))

					val secondCard: Card = Card(second.symbol, second.color, second.found)
					memory += (secondCard -> memory.get(secondCard).map { coords => coords + secondCoord }.getOrElse(Set(secondCoord)))

					availableCoords = availableCoords.filterNot { coord => coord == firstCoord || coord == secondCoord }
				}

				case _ => throw new IllegalStateException("il n'a pas envoyé deux cartes !!!!")
			}*/
		}
	}
}