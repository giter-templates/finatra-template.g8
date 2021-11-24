#@namespace scala $package$.thrift

struct PingRequest {}

struct PingResponse {}

exception PingError {}

service PingService {

  PingResponse ping(1: PingRequest pr) throws (1: PingError pe);
}
