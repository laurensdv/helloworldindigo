package elements

import animation.{DoublePoint, GravitySusceptible, Movable, MoveAbleProps}
import collision.Collidable
import config.MyGameConfig
import indigo.{Dice, Point, Radians, Seconds}
import utils.Conversions

case class Coin(tag: String, moveAbleProps: MoveAbleProps = MoveAbleProps(), runtime: Seconds = Seconds(0)) extends Element with GravitySusceptible with Collidable with Movable {
  val acceleration: Double = moveAbleProps.acceleration
  val speed: Double = moveAbleProps.speed
  override val size: Int = moveAbleProps.size

  override def update(timeDelta: Seconds): Coin = {
    this
      .copy(runtime = runtime + timeDelta)
      .regularMove(timeDelta)
      .gravity(timeDelta)
  }

  def regularMove(timeDelta: Seconds): Coin = {
    val newSpeed = speed + acceleration * timeDelta.toDouble
    this.copy(tag = this.tag, moveAbleProps = this.moveAbleProps.copy(
      distance = this.distance + newSpeed * timeDelta.toDouble,
    speed =
      if (Math.abs(newSpeed) < MyGameConfig.dampingFactor) 0
      else newSpeed,
    acceleration =
      if (Math.abs(newSpeed) > MyGameConfig.dampingFactor) {
      if(newSpeed < 0)
        acceleration + MyGameConfig.dampingFactor * timeDelta.toDouble
      else
        acceleration - MyGameConfig.dampingFactor * timeDelta.toDouble
      } else
      0,
      prevPos = this.pos
      ),
      runtime = this.runtime
    )
  }

  override def push(vel: Double): Coin = {
    this.copy(moveAbleProps = moveAbleProps.copy(
      rotation = rotation,
      distance = distance,
      speed = vel,
      prevPos = pos
      ),
      runtime = Seconds(0)
    )
  }

  override def accelerate(accel: Double): Coin = {
    this.copy(moveAbleProps = moveAbleProps.copy(
      rotation = rotation,
      distance = distance, 
      acceleration = accel,
      prevPos = pos
      ),
      runtime = Seconds(0)
    )
  }

  override def gravity(timeDelta: Seconds): Coin = {
    val oldPos: DoublePoint = this.doublePos

    if(pos.y < MyGameConfig.horizon) {
      val newY: Double = Math.min(MyGameConfig.horizon.toDouble - moveAbleProps.pivot.y.toDouble,
                                  oldPos.y + MyGameConfig.g * timeDelta.toDouble)

      val newAngle: Radians = Conversions.pointToRadians(oldPos.x, newY)

      this.copy(moveAbleProps = moveAbleProps.copy(
          angle = newAngle,
          distance = Conversions.originCoordinatesToDistance(oldPos.x, newY),
          rotation = rotation + Radians(Math.PI * timeDelta.toDouble),
          prevPos = pos,
        )
      )
    } else {
      this.copy(moveAbleProps = moveAbleProps.copy(
        angle = Conversions.pointToRadians(oldPos.x, MyGameConfig.horizon.toDouble - moveAbleProps.pivot.y),
        distance = Conversions.originCoordinatesToDistance(oldPos.x, MyGameConfig.horizon.toDouble - moveAbleProps.pivot.y),
        rotation = Radians(Math.PI/2),
        prevPos = pos
      )
      )
    }
  }
}

object Coin {
  def apply(tag: String, pivotPoint: Point, distance: Double, angle: Radians): Coin = {
    val rot = Radians(Dice.arbitrary(0, 6, System.currentTimeMillis() + tag.hashCode).roll.toDouble)
    new Coin(tag, MoveAbleProps(pivot = pivotPoint, distance= distance, angle = angle, rotation = rot, size=MyGameConfig.coinSize))
  }
}