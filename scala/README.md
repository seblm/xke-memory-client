memory game Scala client
========================

In ClientRunner, you can use API :
	
	- models.Game
	
	- utils.Tools.ping(url: String, body: JsValue): Future[Validation[ServerResponse, Game]]
	
	- utils.Tools.sync[T](fThings: Future[T], duration: Duration): T = result(fThings, duration)
