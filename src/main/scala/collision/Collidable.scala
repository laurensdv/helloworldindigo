package collision

import indigo.Point

trait Collidable {
  def size(): Int
  def pos(): Point
}
