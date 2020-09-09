package model

import actors.{Chest, ChestClosed, ChestData, ChestOpened}
import config.MyAssets
import indigo.{Graphic, Material, Rectangle}

case class MyGameViewModel() {
  private lazy val chestGraphic = Graphic(Rectangle(0, 0, 448, 768), 1, Material.Textured(MyAssets.destructibleObjectsName))

  def chestClosed = chestGraphic
    .withCrop(Rectangle(16, 16 + 11 * 64, 32, 32))
    .withRef(16, 16)

  def chestOpened = chestGraphic
    .withCrop(Rectangle(16, 16 + 11 * 64, 32, 32))
    .withRef(16, 16)

  def draw(chestData: ChestData): Graphic = chestData match {
    case ChestOpened => chestOpened
    case ChestClosed => chestClosed
  }
}
