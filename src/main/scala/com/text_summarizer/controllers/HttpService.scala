package com.text_summarizer.controllers

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.text_summarizer.ApplicationConfiguration
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext

/**
  * A service responsible for handling all http requests.
  *
  * Base on Akka-Http.
  */
object HttpService {

  private[this] val logger: Logger = LoggerFactory.getLogger(HttpService.getClass)

  private[this] val host = ApplicationConfiguration.apiHost
  private[this] val port = ApplicationConfiguration.apiPort

  implicit val actorSystem: ActorSystem = ActorSystem("text-summarizer-actor-system")
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

  /**
    * Starts the HTTP Service, and waits for a user interrupt to stop it again.
    */
  def start(): Unit = {
    val ioThread = new Thread {
      override def run(): Unit = {
        Thread.currentThread.setName("HTTP-Listener-Thread")

        // Initialize the HTTP Service with all the routes
        val binding = Http().bindAndHandle(Router.routes, host, port)

        logger.info("Web Service successfully started at http://" + host + ":" + port)

        while (!Thread.currentThread.isInterrupted) try {
          Thread.sleep(TimeUnit.MINUTES.toMillis(1))
        } catch {
          case _: Exception => Thread.currentThread.interrupt()
        }

        logger.info("Shutting down Web Service...")

        /**
          * Trigger server shutdown.
          *
          * Unbind from the port.
          * Close the spark session.
          * Terminate the Akka System.
          */
        binding.flatMap(_.unbind).onComplete(_ => actorSystem.terminate)
      }
    }

    // Handle application termination
    sys.addShutdownHook {
      logger.debug("Stopping HTTP Service...")
      ioThread.interrupt()
      ioThread.join()
      logger.debug("HTTP Service Stopped.")
    }

    ioThread.start()
  }
}
