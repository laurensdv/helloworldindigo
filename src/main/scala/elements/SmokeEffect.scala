package elements

import animation.{Fixed, Timely}
import indigo.{Point, Seconds}

case class SmokeEffect(tag: String, lifespan: Seconds, pos: Point) extends Element with Fixed with Timely {
  override def update(timeDelta: Seconds): Element = this.copy(lifespan = lifespan - timeDelta)
}

object SmokeEffect {
  def apply(tag: String, lifespan: Seconds, pos: Point) = new SmokeEffect(tag, lifespan, pos)
}