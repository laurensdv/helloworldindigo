package config

import indigo.{AssetName, AssetPath, AssetType}

object MyAssets {
  val assets: Set[indigo.AssetType] = Set(
    AssetType.Image(AssetName("dots"), AssetPath("assets/dots.png"))
  )
}
