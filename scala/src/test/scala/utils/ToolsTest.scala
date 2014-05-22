package utils

import org.scalatest.{Matchers, FunSuite}
import fr.simply.fixture.StubServerFixture
import Tools._
import fr.simply.{StaticServerResponse, POST}
import fr.simply.util._
import play.api.libs.json.Json
import models.{Turn, Game, Card}
import scalaz.{Failure, Scalaz}
import Scalaz._
import org.codehaus.jackson.JsonParseException

class ToolsTest extends FunSuite with Matchers with StubServerFixture {
	val expectedGame = Game(
		gameId = 1,
	    progress = 5.0,
	    turn = Turn(
	        turnScore = 0,
			cards = List(Card("coat", "blue", found = false), Card("dog", "green", found = true)),
	        message = Option("message")
	    ),
		gameScore = 7
	)

	test("should ping distant server") {
		val route = POST(
			path = "/play",
			response = StaticServerResponse(Text_Json, toJson(expectedGame), 200)
		)

		withStubServerFixture(8080, route) { server =>
			val fGame = ping(s"http://localhost:${server.portInUse}/play", Json.parse(s"[[0, 0], [0,1]]"))
			sync(fGame) shouldBe expectedGame.success
		}
	}

	test("should handle unknown return code") {
		val route = POST(
			path = "/play",
			response = StaticServerResponse(Text_Plain, "server error", 500)
		)

		withStubServerFixture(8080, route) { server =>
			val fGame = ping(s"http://localhost:${server.portInUse}/play", Json.parse(s"[[0, 0], [0,1]]"))
			sync(fGame) shouldBe UnknownServerResponse(500, "unexpected return code 500").fail
		}
	}

	test("[code=400] should handle known return code") {
		val route = POST(
			path = "/play",
			response = StaticServerResponse(Text_Plain, "this is a known error", 400)
		)

		withStubServerFixture(8080, route) { server =>
			val fGame = ping(s"http://localhost:${server.portInUse}/play", Json.parse(s"[[0, 0], [0,1]]"))
			sync(fGame) match {
				case Failure(e) =>
					e.code shouldBe 400; e.message should include ("this is a known error")
				case _ => fail("expected scalaz.Failure message !")
			}
		}
	}

	test("should throw JsonParseException when json returned by server is malformed") {
		val route = POST(
			path = "/play",
			response = StaticServerResponse(Text_Plain, "malformed json", 200)
		)

		withStubServerFixture(8080, route) { server =>
			val fGame = ping(s"http://localhost:${server.portInUse}/play", Json.parse(s"[[0, 0], [0,1]]"))

			an [JsonParseException] should be thrownBy sync(fGame)
		}
	}

	test("should throw IllegalStateException when json validation fail") {
		val route = POST(
			path = "/play",
			response = StaticServerResponse(Text_Plain, "{\"gameId\": 1}", 200)
		)

		withStubServerFixture(8080, route) { server =>
			val fGame = ping(s"http://localhost:${server.portInUse}/play", Json.parse(s"[[0, 0], [0,1]]"))

			an [IllegalStateException] should be thrownBy sync(fGame)
		}
	}

	private def toJson(game: Game): String = {
		import models.GameJsonFormat._
		Json.toJson(expectedGame).toString()
	}
}