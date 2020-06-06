package fiber.core.server

import requests.Response

object Client extends App {

  import scala.concurrent.ExecutionContext.Implicits._
  import scala.concurrent.duration.DurationInt
  import scala.concurrent.{Await, Future}

  val value: Future[List[Response]] = Future.traverse((1 to 100).toList) { i =>
    Thread.sleep(1000)
    Future(requests.get(s"http://localhost:8500/$i"))
  }

  val value1: List[Response] =
    Await.result(value, 100.seconds)

  value1.foreach(x => println(x.contents.foreach(y => print(y.toChar))))
}
