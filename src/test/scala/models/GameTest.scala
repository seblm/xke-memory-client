package models

import org.scalatest.{Matchers, FunSuite}
import play.api.libs.json.{JsError, JsSuccess, Json}
import scala.collection.immutable.IndexedSeq
import models.GameJsonFormat._

class GameTest extends FunSuite with Matchers {

	test("game json parsing") {
		val json =
			"""
			  |{
			  |	"gameId": 1,
			  |	"progress": 5.0,
			  |	"turn": {
			  |		"turnScore": 0,
			  |		"cards": [
			  |		{
			  |			"symbol": "coat",
			  |			"color": "blue",
			  |			"found": false
			  |		},
			  |		{
			  |			"symbol": "dog",
			  |			"color": "green",
			  |			"found": false
			  |		}
			  |		],
			  |		"message": "coucou"
			  |	},
			  |	"gameScore": 7
			  |}
			""".stripMargin

		Json.parse(json).validate[Game] match {
			case JsSuccess(game, _) =>
				game.gameId shouldBe 1
				game.turn.cards should have size 2
				game.turn.message shouldBe Option("coucou")
			case JsError(errors) =>	println(errors); fail("parsing failed")
		}
	}

	test("with no message param") {
		val json =
			"""
			  |{
			  |	"gameId": 1,
			  |	"progress": 5.0,
			  |	"turn": {
			  |		"turnScore": 0,
			  |		"cards": [
			  |		{
			  |			"symbol": "coat",
			  |			"color": "blue",
			  |			"found": false
			  |		},
			  |		{
			  |			"symbol": "dog",
			  |			"color": "green",
			  |			"found": false
			  |		}
			  |		]
			  |	},
			  |	"gameScore": 7
			  |}
			""".stripMargin

		Json.parse(json).validate[Game] match {
			case JsSuccess(game, _) =>
				game.gameId shouldBe 1
				game.turn.cards should have size 2
				game.turn.message shouldBe None
			case JsError(errors) =>	println(errors); fail("parsing failed")
		}
	}

	test("or null value message param") {
		val json =
			"""
			  |{
			  |	"gameId": 1,
			  |	"progress": 5.0,
			  |	"turn": {
			  |		"turnScore": 0,
			  |		"cards": [
			  |		{
			  |			"symbol": "coat",
			  |			"color": "blue",
			  |			"found": false
			  |		},
			  |		{
			  |			"symbol": "dog",
			  |			"color": "green",
			  |			"found": false
			  |		}
			  |		],
			  |     "message": null
			  |	},
			  |	"gameScore": 7
			  |}
			""".stripMargin

		Json.parse(json).validate[Game] match {
			case JsSuccess(game, _) =>
				game.gameId shouldBe 1
				game.turn.cards should have size 2
				game.turn.message shouldBe None
			case JsError(errors) =>	println(errors); fail("parsing failed")
		}
	}

	test("create available coord") {
		val gameDimension = 2

		val result: IndexedSeq[Coord] = for {
			x <- (0 until gameDimension)
			y <- (0 until gameDimension)
		} yield { Coord(x,y) }

		result should have size 4
	}

	test("spike") {
		var memory: Map[String, Set[String]] = Map("toto" -> Set("titi"))

		memory += ("toto" -> memory.get("toto").map { value => value + "tata" }.getOrElse(Set("tata")))

		println(memory.head)
	}
}