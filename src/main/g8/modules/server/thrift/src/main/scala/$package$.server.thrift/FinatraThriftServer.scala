package $package$.server.thrift

import com.twitter.finagle.ThriftMux
import com.twitter.finagle.tracing.{BroadcastTracer, ConsoleTracer}
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter

class FinatraThriftServer extends ThriftServer {
  override def name: String = "finatra-thrift-server"

  override protected def defaultThriftPort: String = ":9090"

  override protected def disableAdminHttpServer: Boolean = true

  override protected def configureThrift(router: ThriftRouter): Unit =
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[AccessLoggingFilter]
      .filter[StatsFilter]
      .filter[ExceptionMappingFilter]
      .add[PingServiceController]

  override protected def configureThriftServer(server: ThriftMux.Server): ThriftMux.Server =
    server
      .withLabel("thrift-server")
      .withTracer(
        BroadcastTracer(
          Seq(
            ConsoleTracer
          )
        )
      )
      .withAdmissionControl
      // default: unbounded
      .concurrencyLimit(10)
}
