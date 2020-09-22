package model

import animation.{Fixed, Movable}
import behaviours.{Behaviour, ChestClosed, ChestOpened, Data}
import config.{MyAssets, MyGameConfig}
import elements.{Coin, Dot, SmokeEffect}
import indigo._
import indigo.shared.scenegraph.SceneGraphNode

case class MyGameViewModel(caveSceneViewModel: CaveSceneViewModel) {

  def bindAnimation(tag: String, key: String): Sprite = Sprite(
    BindingKey(tag), 0, 0, 1, AnimationKey(key)
  )

  def chestClosed: Graphic = MyAssets.chestGraphic
    .withCrop(Rectangle(16, 16 + 11 * 64, 32, 32))
    .withRef(16, 16)

  def chestOpened: Graphic = MyAssets.chestGraphic
    .withCrop(Rectangle(16 + 4 * 64, 16 + 11 * 64, 32, 32))
    .withRef(16, 16 )

  def draw(data: Data): Graphic = data match {
    case ChestOpened => chestOpened
    case ChestClosed => chestClosed
  }

  def draw(moveable: Movable): SceneGraphNode = moveable match {
    case Coin(tag, _, _) => {
      bindAnimation(tag, MyGameConfig.coinAnimsKey).withRef(8,8).rotate(moveable.rotation).moveTo(moveable.pos).play()
    }
    case Dot(_, _) => Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.dotsAssetName))
      .withCrop(Rectangle(16, 16, 16, 16))
      .withRef(8, 8)
      .moveTo(moveable.pos)
  }


  def draw(fixed: Fixed): SceneGraphNode = fixed match {
    case SmokeEffect(tag, lifespan, pos) if lifespan > Seconds(0) => {
      bindAnimation(tag, MyGameConfig.smokeAnimsKey).withRef(16,16).moveTo(pos)
    }
  }

  def draw(behaviour: Behaviour):SceneGraphNode =
    draw(behaviour.stateData)
    .moveTo(behaviour.pos)

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
