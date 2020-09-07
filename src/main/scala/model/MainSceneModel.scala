package model

import indigo.{Point, Seconds}

case class MainSceneModel(center: Point, dots: List[Dot]) {
  def addDot(dot: Dot): MainSceneModel =
  this.copy(dots = dot :: dots)

  def update(timeDelta: Seconds): MainSceneModel =
  this.copy(dots = dots.map(_.update(timeDelta)))
}

object MainSceneModel {
  def initial(center: Point): MainSceneModel = MainSceneModel(center, Nil)
}

