package actors

import akka.actor.{ActorSystem, Props}

case object Closed extends State
case object Opened extends State

object Open extends Action
object Close extends Action
object Interact extends Action

sealed trait ChestData extends Data
object ChestClosed extends ChestData
object ChestOpened extends ChestData

case class Chest(data: ChestData) extends MyGameActor {

  startWith(Closed, data)

  when(Closed) {
    case Event(Close, _) => stay().using(ChestClosed)
    case Event(Open, _) => goto(Opened).using(ChestOpened)
    case Event(Interact, _) => goto(Opened).using(ChestOpened)
  }

  when(Opened) {
    case Event(Close, _) => goto(Opened).using(ChestClosed)
    case Event(Open, _) => stay().using(ChestOpened)
    case Event(Interact, _) => goto(Opened).using(ChestClosed)
  }

  initialize()
}

object ChestRef {
  def apply(data: ChestData, actorSystem: ActorSystem, name: String) = {
    actorSystem.actorOf(Props(Chest(data)), name)
  }
}