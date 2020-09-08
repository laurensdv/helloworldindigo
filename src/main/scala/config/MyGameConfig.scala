package config

import indigo.GameConfig

object MyGameConfig {

  val magnification = 3

  val introTimeSeconds = 20

  val config: GameConfig =
    GameConfig.default.withMagnification(magnification)
}
