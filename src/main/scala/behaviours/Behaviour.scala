package behaviours

import akka.actor.ActorRef
import indigo.Point

trait Behaviour {
  val size: Int
  val pos: Point
  val name: String
  val actorRef: ActorRef
  val stateData: Data

  def updateState(data: Data): Behaviour
}
