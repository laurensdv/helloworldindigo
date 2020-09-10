package model

import behaviours.{Behaviour, ChestClosed, ChestOpened, Data}
import config.MyAssets
import indigo.{Graphic, Material, Point, Rectangle}
import elements.Dot

case class MyGameViewModel() {
  private lazy val chestGraphic = Graphic(Rectangle(0, 0, 448, 768), 1, Material.Textured(MyAssets.destructibleObjectsName))

  def chestClosed = chestGraphic
    .withCrop(Rectangle(16, 16 + 11 * 64, 32, 32))
    .withRef(16, 16)

  def chestOpened = chestGraphic
    .withCrop(Rectangle(16 + 4 * 64, 16 + 11 * 64, 32, 32))
    .withRef(16, 16 )

  def draw(data: Data): Graphic = data match {
    case ChestOpened => chestOpened
    case ChestClosed => chestClosed
  }

  def draw(behaviour: Behaviour): Graphic = draw(behaviour.stateData).moveTo(behaviour.pos)

  def drawDots(center: Point, dots: List[Dot]): List[Graphic] = {
    dots.map { dot =>
      val position = Point(
        (Math.sin(dot.angle.value) * dot.orbitDistance + center.x).toInt,
        (Math.cos(dot.angle.value) * dot.orbitDistance + center.y).toInt
      )

      Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.dotsAssetName))
        .withCrop(Rectangle(16, 16, 16, 16))
        .withRef(8, 8)
        .moveTo(position)
    }
  }
}
