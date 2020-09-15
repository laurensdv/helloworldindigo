package animation

import indigo.{Point, Radians}

trait Moveable {
  val tag: String
  val distance: Int
  val rad: Radians
  val rotation: Radians
  val pivot: Point = Point(0, 0)
  val rotSpeed: Radians = Radians(0)
  val radSpeed: Double = 0
  val floatSpeed: Double = 0
  val radAccelleration: Double = 0
  val floatAcceleration: Double = 0
  val floatDamping: Double = 0
  val radDamping: Double = 0

  def resRadSpeed: Double = Math.min(radSpeed - radDamping, 0)
  def resFloatSpeed: Double = Math.min(floatSpeed - floatDamping, 0)

  def pos: Point = Point((distance * Math.cos(rad.value)).toInt, (distance * Math.sin(rad.value)).toInt) + pivot

  def accelerate(accel: Double): Moveable
}
