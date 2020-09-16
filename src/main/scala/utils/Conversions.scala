package utils

import indigo.{Point, Radians}

object Conversions {
  def pointToRadians(pt: Point): (Radians, Double) = {
    (pointToRadians(pt.x.toDouble, pt.y.toDouble), Math.sqrt(Math.pow(pt.x.toDouble, 2) + Math.pow(pt.y.toDouble, 2)))
  }

  def pointToRadians(x:Double,y: Double): Radians = {
      if(x != 0) Radians(Math.atan2(y, x))
      else if (y <= 0) Radians(-Math.PI/2)
      else Radians(Math.PI/2)
  }

  def originCoordinatesToDistance(x:Double, y: Double): Double =
    Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))
}
