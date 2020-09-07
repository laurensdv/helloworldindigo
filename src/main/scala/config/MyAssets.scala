package config

import indigo.{AssetName, AssetPath, AssetType}

object MyAssets {
  val assetName = AssetName("dots")

  val assets: Set[indigo.AssetType] = Set(
    AssetType.Image(assetName, AssetPath("assets/dots.png"))
  )
}
