package utils

import play.api.libs.json.{JsError, Json, JsValue, JsSuccess}
import scala.concurrent.Future
import play.api.libs.ws.{Response, WS}
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await._
import models.Game
import concurrent.duration._
import scalaz.{Scalaz, Validation}
import Scalaz._


object Tools {
	def ping(url: String, body: JsValue): Future[Validation[ServerResponse, Game]] = {
		WS.url(url)
			.post(body)
			.map { response =>
				response.status match {
				case 200 => parseSuccessResponse(response)
				case 400 => KnownServerResponse(400, response.body).fail
				case code => UnknownServerResponse(code, s"unexpected return code $code").fail
			}
		}
	}

	def sync[T](fThings: Future[T], duration: Duration = 2 seconds): T = result(fThings, duration)

	private def parseSuccessResponse(response: Response): Validation[Nothing, Game] = {
		import models.GameJsonFormat._

		Json.parse(response.body).validate[Game] match {
			case JsSuccess(game, _) => game.success
			case JsError(errors) => throw new IllegalStateException(s"error when parsing server response - $errors")
		}
	}
}

trait ServerResponse {
	def code: Int
	def message: String
}
case class UnknownServerResponse(code: Int, message: String) extends ServerResponse
case class KnownServerResponse(code: Int, message: String) extends ServerResponse