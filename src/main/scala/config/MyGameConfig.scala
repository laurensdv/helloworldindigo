package config

import java.util.concurrent.TimeUnit

import indigo.GameConfig
import indigo.shared.config.AdvancedGameConfig
import indigo.shared.config.RenderingTechnology.WebGL2WithFallback

import scala.concurrent.duration.FiniteDuration

object MyGameConfig {

  val magnification = 2

  val introTimeSeconds = 6

  val chestSize = 32

  val coinAnimsKey = "coinsAnim"

  val dampingFactor = 0.3

  val timeout: FiniteDuration = FiniteDuration(1, TimeUnit.SECONDS)

  val advancedGameConfig: AdvancedGameConfig =
    AdvancedGameConfig(
      renderingTechnology = WebGL2WithFallback,
      antiAliasing = true,
      batchSize = 256,
      disableSkipModelUpdates = false,
      disableSkipViewUpdates = false
    )

  val config: GameConfig =
    GameConfig.default
      .withViewport(412, 896)
      .withFrameRate(60)
      .withMagnification(magnification)
      .withAdvancedSettings(advancedGameConfig)

}
