package animation

import indigo.{Point, Radians}

case class MoveAbleProps(
   distance: Double = 0,
   angle: Radians = Radians(0),
   speed: Double = 0,
   angularSpeed: Double = 0,
   rotation: Radians = Radians(0),
   acceleration: Double = 0,
   pivot: Point = Point(0, 0),
   rotSpeed: Radians = Radians(0),
   prevPos: Point = Point(0,0),
   size: Int = 0
)
