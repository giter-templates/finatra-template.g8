package $package$.server.http

import $package$.thrift.PingService
import com.google.inject.{Provides, Singleton}
import com.twitter.finagle.Thrift
import com.twitter.finagle.tracing.{BroadcastTracer, ConsoleTracer}
import com.twitter.inject.TwitterModule

object PingClientModule extends TwitterModule {
  @Provides
  @Singleton
  def client(): PingService.MethodPerEndpoint =
    Thrift.client
      .withLabel("thrift-client")
      .withTracer(
        BroadcastTracer(
          Seq(
            ConsoleTracer
          )
        )
      )
      .build[PingService.MethodPerEndpoint]("inet!thrift-server:9090", "thrift-client")
}
