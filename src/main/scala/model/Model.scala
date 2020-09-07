package model
import indigo.{Point, Seconds}

case class Model(center: Point, dots: List[Dot]) {
  def addDot(dot: Dot): Model =
    this.copy(dots = dot :: dots)

  def update(timeDelta: Seconds): Model =
    this.copy(dots = dots.map(_.update(timeDelta)))
}

object Model {
  def initial(center: Point): Model = Model(center, Nil)
}
