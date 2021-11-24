package $package$.server.thrift

import $package$.thrift.{PingResponse, PingService}
import $package$.thrift.PingService.Ping
import com.twitter.finatra.thrift.Controller
import com.twitter.util.Future

class PingServiceController extends Controller(PingService) {
  handle(Ping) { args =>
    logger.info("Ping request")
    Future.value(PingResponse())
  }
}
