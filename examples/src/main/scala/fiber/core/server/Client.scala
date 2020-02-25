package fiber.core.server

import requests.Response

object Client extends App {

  import scala.concurrent.ExecutionContext.Implicits._
  import scala.concurrent.duration.DurationInt
  import scala.concurrent.{Await, Future}

  val value: IndexedSeq[Future[Response]] = (1 to 100).map { _ =>
    Future(requests.get("http://localhost:8500"))
  }

  val value1: IndexedSeq[Response] =
    Await.result(Future.sequence(value), 100.seconds)

  value1.foreach(x => println(x.contents.foreach(y => print(y.toChar))))
}
