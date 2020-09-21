package animation

import indigo.{Point, Radians, Seconds}

trait Movable {
  val tag: String
  val moveAbleProps: MoveAbleProps
  val runTime: Seconds = Seconds(0)

  val pos: Point = Point((moveAbleProps.distance * Math.cos(moveAbleProps.angle.value)).toInt,
                         (moveAbleProps.distance * Math.sin(moveAbleProps.angle.value)).toInt) + moveAbleProps.pivot

  val moved: Boolean = moveAbleProps.prevPos != pos

  val rotation: Radians = moveAbleProps.rotation
  val angle: Radians = moveAbleProps.angle
  val distance: Double = moveAbleProps.distance

  def push(vel: Double): Movable
  def accelerate(accel: Double): Movable
}
