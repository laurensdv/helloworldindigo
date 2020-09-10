package elements

import indigo.{Radians, Seconds}

case class Dot(orbitDistance: Int, angle: Radians) extends Element {
  def update(timeDelta: Seconds): Dot =
    this.copy(angle = angle + Radians.fromSeconds(timeDelta))
}
