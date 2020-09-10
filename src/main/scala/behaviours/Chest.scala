package behaviours

import akka.actor.Props
import collision.Collidable
import config.MyGameActorSystem
import indigo.Point

case class Chest(size: Integer, pos: Point, name: String) extends Behaviour with Collidable {
  var stateData: Data = ChestClosed

  lazy val actorRef = MyGameActorSystem.actorSystem.actorOf(Props(classOf[ChestActor], this), name)
}
