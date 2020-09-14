package behaviours

import akka.actor.{ActorRef, Props}
import collision.Collidable
import config.MyGameActorSystem
import indigo.Point

case class Chest(size: Integer, pos: Point, name: String, stateData: Data = ChestClosed, actorRef: ActorRef) extends Behaviour with Collidable {

  override def updateState(data: Data): Behaviour = this.copy(size, pos, name, data, actorRef)

}

object Chest {
  def apply(size: Integer, pos: Point, name: String): Chest = {
    Chest(size, pos, name, ChestClosed, MyGameActorSystem.actorSystem.actorOf(Props(classOf[ChestActor], name), name))
  }
}
