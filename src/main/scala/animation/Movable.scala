package animation

import indigo.{Point, Radians, Seconds}

trait Movable {
  val tag: String
  val moveAbleProps: MoveAbleProps
  val runTime: Seconds = Seconds(0)

  def pos: Point = Point((moveAbleProps.distance * Math.cos(moveAbleProps.angle.value)).toInt,
                         (moveAbleProps.distance * Math.sin(moveAbleProps.angle.value)).toInt) + moveAbleProps.pivot

  def rotation: Radians = moveAbleProps.rotation
  def angle: Radians = moveAbleProps.angle
  def distance: Double = moveAbleProps.distance

  def push(vel: Double): Movable
  def accelerate(accel: Double): Movable
}
