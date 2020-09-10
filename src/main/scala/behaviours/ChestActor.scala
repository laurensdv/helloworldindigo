package behaviours

case object Closed extends State
case object Opened extends State

object Open extends Action
object Close extends Action
object Interact extends Action

object ChestClosed extends Data
object ChestOpened extends Data

protected class ChestActor(chest: Chest) extends MyGameActor {

  startWith(Closed, ChestClosed)

  when(Closed) {
    case Event(Close, _) => stay()
    case Event(Open, _) => goto(Opened).using(ChestOpened)
    case Event(Interact, _) => goto(Opened).using(ChestOpened)
    case _ => stay()
  }

  when(Opened) {
    case Event(Close, _) => goto(Closed).using(ChestClosed)
    case Event(Open, _) => stay()
    case Event(Interact, _) => goto(Closed).using(ChestClosed)
    case _ => stay()
  }

  def signalState() = {
    chest.stateData = nextStateData
    println(chest.stateData)
  }

  onTransition {
    case Opened -> Closed => signalState()
    case Closed -> Opened => signalState()
  }

  initialize()
}