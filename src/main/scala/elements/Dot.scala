package elements

import animation.Moveable
import indigo.{Radians, Seconds}

case class Dot(tag: String, distance: Int, rad: Radians, rotation: Radians = Radians(0)) extends Element with Moveable {
  override val radSpeed: Double = 1

  def update(timeDelta: Seconds): Dot =
    this.copy(rad = rad + Radians(radSpeed) * Radians.fromSeconds(timeDelta), rotation = rotation)

  override def accelerate(accel: Double): Moveable =
    this.copy(rotation = rotation)
}
