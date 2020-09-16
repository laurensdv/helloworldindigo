package elements

import animation.{GravitySusceptible, Movable, MoveAbleProps}
import config.MyGameConfig
import indigo.{Dice, Point, Radians, Seconds}
import utils.Conversions

case class Coin(tag: String, moveAbleProps: MoveAbleProps = MoveAbleProps(), runtime: Seconds = Seconds(0)) extends Element with GravitySusceptible with Movable {
  val acceleration: Double = moveAbleProps.acceleration
  val speed: Double = moveAbleProps.speed

  override def update(timeDelta: Seconds): Coin = {
    val newSpeed = speed + acceleration * timeDelta.toDouble
    println(newSpeed)
    println("---")
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
    ).gravity(timeDelta)
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
    def oldPos: Point = pos - moveAbleProps.pivot
    println(pos)
    if(pos.y < MyGameConfig.horizon) {
      val newY: Double = Math.min(MyGameConfig.horizon.toDouble - moveAbleProps.pivot.y.toDouble,
                                  oldPos.y.toDouble + MyGameConfig.g * timeDelta.toDouble)
      val newAngle: Radians = Conversions.pointToRadians(oldPos.x.toDouble, newY)

      println(newY - oldPos.y)
      println(newAngle - angle)
      println(Math.sqrt(Math.pow(oldPos.x.toDouble, 2) + Math.pow(newY, 2)) - distance)

      this.copy(moveAbleProps = moveAbleProps.copy(
        angle = newAngle,
        distance = Conversions.originCoordinatesToDistance(oldPos.x.toDouble, newY),
        rotation = rotation + Radians(Math.PI * timeDelta.toDouble)
      )
      )
    } else {
      this.copy(moveAbleProps = moveAbleProps.copy(
        angle = Conversions.pointToRadians(oldPos.x.toDouble, MyGameConfig.horizon.toDouble - moveAbleProps.pivot.y),
        distance = Conversions.originCoordinatesToDistance(oldPos.x.toDouble, MyGameConfig.horizon.toDouble - moveAbleProps.pivot.y),
        rotation = rotation + Radians(Math.PI * timeDelta.toDouble)
      )
      )
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