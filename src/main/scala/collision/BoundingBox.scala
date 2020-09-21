package collision

import indigo.Point

case class BoundingBox (ll: Point, lr: Point, ul: Point, ur: Point) {
  def hitInside(pt: Point): Boolean =
    ll.x < pt.x & lr.x > pt.x & ll.y < pt.y & ul.y > pt.y

  def hit(pt: Point): Boolean =
    ll.x <= pt.x & lr.x >= pt.x & ll.y <= pt.y & ul.y >= pt.y

  def hit(y: Int): Boolean =
    ll.y <= y & ul.y >= y

  def hitInside(y: Int): Boolean =
    ll.y < y & ul.y > y

  def hitCenter(y: Int): Boolean =
    ll.y + (ul.y - ll.y) / 2 == y

}

object BoundingBox {
  def apply(ll: Point, lr: Point, ul: Point, ur: Point): BoundingBox = new BoundingBox(ll, lr, ul, ur)
  def apply(center: Point, size: Integer): BoundingBox =
    new BoundingBox(
      Point(center.x - size/2, center.y - size/2),
      Point(center.x + size/2, center.y - size/2),
      Point(center.x - size/2, center.y + size/2),
      Point(center.x + size/2, center.y + size/2),
    )
  def hits(collidables: List[Collidable], pos: Point, includeEdge: Boolean = true): List[Collidable] = {
    if(includeEdge)
      collidables.filter(c => BoundingBox(c.pos(), c.size()).hit(pos))
    else
      collidables.filter(c => BoundingBox(c.pos(), c.size()).hitInside(pos))
  }

}