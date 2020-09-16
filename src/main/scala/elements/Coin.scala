package elements

import animation.{GravitySusceptable, MoveAbleProps, Moveable}
import config.MyGameConfig
import indigo.{Dice, Point, Radians, Seconds}

case class Coin(tag: String, moveAbleProps: MoveAbleProps = MoveAbleProps(), runtime: Seconds = Seconds(0)) extends Element with Moveable with GravitySusceptable {
  val acceleration: Double = moveAbleProps.acceleration
  val speed: Double = moveAbleProps.speed

  override def update(timeDelta: Seconds): Coin = {
    println(acceleration)
    val newSpeed = speed + acceleration * timeDelta.toDouble
    val appliedGravity = gravity(timeDelta)
    appliedGravity.copy(tag = tag, moveAbleProps = appliedGravity.moveAbleProps.copy(
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

  override def gravity(timeDelta: Seconds): Coin = {
    def oldPos = pos - moveAbleProps.pivot

    println(MyGameConfig.horizon - pos.y)
    if(pos.y <= MyGameConfig.horizon) {
      val diffY: Double = MyGameConfig.g * timeDelta.toDouble
      val newY: Double = oldPos.y.toDouble + diffY
      val newAngle =
        if (oldPos.y == 0) {
          if(oldPos.x > 0) Radians(0)
          else Radians(Math.PI)
        } else {
          if(oldPos.x != 0) Radians(Math.atan(newY / oldPos.x.toDouble))
          else
            if(oldPos.y < 0) Radians(-Math.PI/2)
            else Radians(Math.PI/2)
        }

      println(angle)
      println(newAngle)
      println(diffY)
      println(newY / Math.sin(newAngle.value))

      this.copy(moveAbleProps = moveAbleProps.copy(
        angle = newAngle,
        distance = newY / Math.sin(newAngle.value)
      ))
    } else {
      this.copy()
    }
  }
}

object Coin {
  def apply(tag: String, pivotPoint: Point, distance: Double, angle: Radians): Coin = {
    val rot = Radians(Dice.arbitrary(0, 6, System.currentTimeMillis() + tag.hashCode).roll.toDouble)
    println(rot)
    new Coin(tag, MoveAbleProps(pivot = pivotPoint, distance= distance, angle = angle, rotation = rot))
  }
}