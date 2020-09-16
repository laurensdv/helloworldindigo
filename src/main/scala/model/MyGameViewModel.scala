package model

import animation.Movable
import behaviours.{Behaviour, ChestClosed, ChestOpened, Data}
import config.{MyAssets, MyGameConfig}
import elements.{Coin, Dot}
import indigo.shared.scenegraph.SceneGraphNode
import indigo._

case class MyGameViewModel() {
  protected lazy val chestGraphic = Graphic(Rectangle(0, 0, 448, 768), 1, Material.Textured(MyAssets.destructibleObjectsName))

  def coinAnimation(tag: String): Sprite = Sprite(
    BindingKey(tag), 0, 0, 1, AnimationKey(MyGameConfig.coinAnimsKey)
  )



  def chestClosed: Graphic = chestGraphic
    .withCrop(Rectangle(16, 16 + 11 * 64, 32, 32))
    .withRef(16, 16)

  def chestOpened: Graphic = chestGraphic
    .withCrop(Rectangle(16 + 4 * 64, 16 + 11 * 64, 32, 32))
    .withRef(16, 16 )

  def draw(data: Data): Graphic = data match {
    case ChestOpened => chestOpened
    case ChestClosed => chestClosed
  }

  def draw(behaviour: Behaviour): Graphic =
    draw(behaviour.stateData)
      .moveTo(behaviour.pos)

  def draw(moveable: Movable): SceneGraphNode = moveable match {
    case Coin(tag, _, _) => {
      coinAnimation(tag).rotate(moveable.rotation).moveTo(moveable.pos).play()
    }
    case Dot(_, _) => Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.dotsAssetName))
      .withCrop(Rectangle(16, 16, 16, 16))
      .withRef(8, 8)
      .moveTo(moveable.pos)
  }

  def drawDots(center: Point, dots: List[Dot]): List[Graphic] = {
    dots.map { dot =>
      val position = Point(
        (Math.sin(dot.angle.value) * dot.distance + center.x).toInt,
        (Math.cos(dot.angle.value) * dot.distance + center.y).toInt
      )

      Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.dotsAssetName))
        .withCrop(Rectangle(16, 16, 16, 16))
        .withRef(8, 8)
        .moveTo(position)
    }
  }
}
