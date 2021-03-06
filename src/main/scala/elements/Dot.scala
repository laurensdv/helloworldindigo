package elements

import animation.{MoveAbleProps, Movable}
import indigo.{Radians, Seconds}

case class Dot(tag: String, moveAbleProps: MoveAbleProps = MoveAbleProps()) extends Element with Movable {

  def update(timeDelta: Seconds): Dot =
    this.copy(moveAbleProps = moveAbleProps.copy(
      angle = moveAbleProps.angle + Radians(moveAbleProps.angularSpeed) * Radians.fromSeconds(timeDelta),
      rotation = moveAbleProps.rotation
    )
    )

  override def push(vel: Double): Dot = this.copy()

  override def accelerate(accel: Double): Dot =
    this.copy()
}
