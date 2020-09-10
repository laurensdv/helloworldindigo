package behaviours

import akka.actor.FSM

trait State
trait Action
trait Data

trait MyGameActor extends FSM[State, Data]