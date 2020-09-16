package elements

import animation.{MoveAbleProps, Moveable}
import config.MyGameConfig
import indigo.{Dice, Point, Radians, Seconds}

case class Coin(tag: String, moveAbleProps: MoveAbleProps = MoveAbleProps(), runtime: Seconds = Seconds(0)) extends Element with Moveable {
  val acceleration = moveAbleProps.acceleration
  val speed = moveAbleProps.speed

  override def update(timeDelta: Seconds): Coin = {
    println(acceleration)
    val newSpeed = speed + acceleration * timeDelta.toDouble
    this.copy(tag = tag, moveAbleProps = moveAbleProps.copy(
        distance = distance + newSpeed * timeDelta.toDouble,
        speed =
          if (Math.abs(newSpeed) < MyGameConfig.dampingFactor) 0
          else newSpeed,
        acceleration =
          if (Math.abs(newSpeed) > MyGameConfig.dampingFactor)
            acceleration - MyGameConfig.dampingFactor * timeDelta.toDouble
          else
            0
      ), runtime = runtime + timeDelta
    )
  }

  override def push(vel: Double): Coin = {
    this.copy(moveAbleProps = moveAbleProps.copy(
      rotation = rotation,
      distance = distance,
      speed = vel
      ),
      runtime = Seconds(0)
    )
  }

  override def accelerate(accel: Double): Coin = {

    this.copy(moveAbleProps = moveAbleProps.copy(
      rotation = rotation,
      distance = distance, 
      acceleration = accel
      ),
      runtime = Seconds(0)
    )
  }
}

object Coin {
  def apply(tag: String, pivotPoint: Point, distance: Double, angle: Radians): Coin = {
    val rot = Radians(Dice.arbitrary(0, 6, System.currentTimeMillis() + tag.hashCode).roll.toDouble)
    println(rot)
    new Coin(tag, MoveAbleProps(pivot = pivotPoint, distance= distance, angle = angle, rotation = rot))
  }
}