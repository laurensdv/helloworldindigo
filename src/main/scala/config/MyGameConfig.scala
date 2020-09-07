package config

import indigo.{AssetName, GameConfig}

object MyGameConfig {
  val assetName = AssetName("dots")
  val magnification = 3

  val config: GameConfig =
    GameConfig.default.withMagnification(magnification)
}
