package config

import akka.actor.ActorSystem

object MyGameActorSystem {
  val actorSystem = ActorSystem("MyGame")
}
