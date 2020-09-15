package elements

import animation.Moveable
import config.MyGameConfig
import indigo.{Dice, Point, Radians, Seconds}

case class Coin(tag: String, pivotPoint: Point, distance: Int, rad: Radians, rotation: Radians, override val floatAcceleration: Double = 0) extends Element with Moveable {
  override val pivot: Point = pivotPoint

  override def update(timeDelta: Seconds): Coin =
    this.copy(rotation = rotation,
      distance = distance + (floatAcceleration * timeDelta.toDouble * timeDelta.toDouble).toInt,
      floatAcceleration = Math.min(0, floatAcceleration - MyGameConfig.dampingFactor)
    )

  override def accelerate(accel: Double): Coin =
    this.copy(rotation = rotation,
      distance = distance, 
      floatAcceleration = accel
    )
}

object Coin {
  def apply(tag: String, pivotPoint: Point, distance: Int, rad: Radians): Coin = {
    val rot = Radians(Dice.arbitrary(0, 6, System.currentTimeMillis() + tag.hashCode).roll.toDouble)
    println(rot)
    new Coin(tag, pivotPoint, distance, rad, rot)
  }
}