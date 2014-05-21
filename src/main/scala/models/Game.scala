package models

case class Game(gameId: Int, progress: Double, turn: Turn, gameScore: Double)
case class Turn(turnScore: Double, cards: List[Card], message: Option[String])
case class Card(symbol: String, color: String, found: Boolean) {
	def ==(card: Card): Boolean = this.symbol == card.symbol && this.color == card.color
}

case class Coord(x: Int, y: Int) { override def toString = s"[$x,$y]" }

object GameJsonFormat {
	import play.api.libs.json.Json

	implicit val cardJsonFormat = Json.format[Card]
	implicit val turnJsonFormat = Json.format[Turn]
	implicit val gridJsonFormat = Json.format[Game]
}