package $package$.server.http

import com.google.inject.{Inject, Singleton}
import com.twitter.finatra.http.Controller
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import sttp.tapir.server.finatra.{FinatraRoute, FinatraServerInterpreter, TapirController}
import $package$.server.http.FinatraTapirController._
import $package$.thrift.{PingRequest, PingService}

@Singleton
class FinatraTapirController @Inject() (client: PingService.MethodPerEndpoint) extends Controller with TapirController {
  val pingRoute: FinatraRoute =
    FinatraServerInterpreter().toRoute(pingEndpoint)(_ => client.ping(PingRequest()).map(_ => Right(Ping("pong"))))

  addTapirRoute(pingRoute)
}

object FinatraTapirController {
  case class Ping(response: String)

  val baseEndpoint: Endpoint[Unit, Unit, Unit, Any] = endpoint.in("api")

  val pingEndpoint: Endpoint[Unit, Unit, Ping, Any] = baseEndpoint.get.in("ping").out(jsonBody[Ping])

  val routes = List(
    pingEndpoint
  )
}
