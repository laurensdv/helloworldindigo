package behaviours

import akka.actor.ActorRef
import indigo.Point

trait Behaviour {
  val size: Integer
  val pos: Point
  val actorRef: ActorRef
  var stateData: Data
}
