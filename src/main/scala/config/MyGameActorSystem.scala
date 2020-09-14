package config

import akka.actor.ActorSystem

object MyGameActorSystem {
  val actorSystemName = "MyGame"
  val actorSystem = ActorSystem(actorSystemName)
}
