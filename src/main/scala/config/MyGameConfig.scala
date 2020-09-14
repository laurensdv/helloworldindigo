package config

import java.util.concurrent.TimeUnit

import indigo.GameConfig

import scala.concurrent.duration.FiniteDuration

object MyGameConfig {

  val magnification = 2

  val introTimeSeconds = 6

  val chestSize = 32

  val timeout: FiniteDuration = FiniteDuration(1, TimeUnit.SECONDS)

  val config: GameConfig =
    GameConfig.default
      .withViewport(412, 896)
      .withMagnification(magnification)

}
