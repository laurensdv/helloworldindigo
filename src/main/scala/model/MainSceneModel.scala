package model

import actors.{Action, Chest, ChestClosed, ChestData, ChestRef}
import akka.actor.{ActorRef, ActorSystem}
import indigo.{Point, Seconds}
import model.main.Dot

case class MainSceneModel(center: Point, dots: List[Dot]) {
  val actorSystem = ActorSystem("MyGame")
  val chestData: ChestData = ChestClosed
  val chestActor: ActorRef = ChestRef(chestData, actorSystem, "My Game's Chest")

  def addDot(dot: Dot): MainSceneModel =
    this.copy(dots = dot :: dots)

  def update(timeDelta: Seconds): MainSceneModel =
    this.copy(dots = dots.map(_.update(timeDelta)))

  def interact(actorRef: ActorRef, action: Action): Unit = {
    actorRef ! action
  }
}

object MainSceneModel {
  def initial(center: Point): MainSceneModel = MainSceneModel(center, Nil)
}

