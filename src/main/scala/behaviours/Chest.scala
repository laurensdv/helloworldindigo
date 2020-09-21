package behaviours

import akka.actor.{ActorRef, Props}
import animation.Fixed
import collision.Clickable
import config.MyGameActorSystem
import indigo.Point

case class Chest(size: Int, pos: Point, name: String, stateData: Data = ChestClosed, actorRef: ActorRef) extends Behaviour with Fixed with Clickable {
  override def updateState(data: Data): Behaviour = this.copy(size, pos, name, data, actorRef)
}

object Chest {
  def apply(size: Integer, pos: Point, name: String): Chest = {
    Chest(size, pos, name, ChestClosed, MyGameActorSystem.actorSystem.actorOf(Props(classOf[ChestActor], name), name))
  }
}
