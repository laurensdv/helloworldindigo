package config

import indigo.GameConfig

object MyGameConfig {

  val magnification = 3

  val config: GameConfig =
    GameConfig.default.withMagnification(magnification)
}
