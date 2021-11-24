package $package$.server.http

import com.google.inject
import com.twitter.finagle.Http
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.tracing.{BroadcastTracer, ConsoleTracer}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.finatra.SwaggerFinatra
import com.twitter.util.Duration

class FinatraTapirServer extends HttpServer {
  override protected def defaultHttpPort: String = ":8080"

  override protected def modules: Seq[inject.Module] = Seq(PingClientModule)

  override protected def disableAdminHttpServer: Boolean = true

  override protected def configureHttp(router: HttpRouter): Unit = {
    val swaggerDocs: String = OpenAPIDocsInterpreter().toOpenAPI(FinatraTapirController.routes, "Sample", "1.0").toYaml

    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[FinatraTapirController]
      .add(new SwaggerFinatra(swaggerDocs))
  }

  override protected def configureHttpServer(server: Http.Server): Http.Server =
    server
      .withTracer(
        BroadcastTracer(
          Seq(
            ConsoleTracer
          )
        )
      )
      .withRequestTimeout(Duration.fromSeconds(30))
}
