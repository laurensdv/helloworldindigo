package config

import indigo.GameConfig

object MyGameConfig {

  val magnification = 2

  val introTimeSeconds = 6

  val config: GameConfig =
    GameConfig.default
      .withViewport(412, 896)
      .withMagnification(magnification)

}
