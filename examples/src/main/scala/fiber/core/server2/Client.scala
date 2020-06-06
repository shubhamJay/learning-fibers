package fiber.core.server2

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.{AsynchronousSocketChannel, CompletionHandler}
import java.util.concurrent.TimeUnit

object Client extends App {

  (1 to 3).par.foreach { i =>
    val channel = AsynchronousSocketChannel.open()
    channel.connect(
      new InetSocketAddress("localhost", 8500),
      null,
      new CompletionHandler[Void, Void] {
        override def completed(result: Void, attachment: Void): Unit = {
          channel.write(
            ByteBuffer.wrap(s"hello from + $i".getBytes),
            null,
            new CompletionHandler[Integer, Void]() {
              override def completed(result: Integer,
                                     attachment: Void): Unit = {
                println(s"completed writing - $i")

                val buffer = ByteBuffer.allocate(1000)

                channel.read(
                  buffer,
                  60,
                  TimeUnit.SECONDS,
                  null,
                  new CompletionHandler[Integer, Void]() {
                    override def completed(result: Integer,
                                           attachment: Void): Unit = {
                      println(s"completed reading - $i")
                      println("-" + buffer.asCharBuffer().toString)
                    }

                    override def failed(exc: Throwable,
                                        attachment: Void): Unit =
                      println(s"failed reading - $i")
                  }
                )
              }

              override def failed(exc: Throwable, attachment: Void): Unit =
                println(s"failed writing - $i")
            }
          )

        }

        override def failed(exc: Throwable, attachment: Void): Unit =
          println(s"failed to Connect $i")
      }
    )

  }

  Thread.sleep(10000)
}
