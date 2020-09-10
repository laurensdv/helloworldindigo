package model

import behaviours.{Action, Behaviour}
import collision.Collidable
import elements.Element
import indigo.{Point, Seconds}

case class MainSceneModel(center: Point, elements: List[Element], behaviours: List[Behaviour]) {

  def addElement(element: Element): MainSceneModel =
    this.copy(elements = element :: elements, behaviours = behaviours)

  def addBehaviour(behaviour: Behaviour): MainSceneModel =
    this.copy(elements = elements, behaviours = behaviour :: behaviours)

  def update(timeDelta: Seconds): MainSceneModel =
    this.copy(elements = elements.map(_.update(timeDelta)), behaviours = behaviours)

  def interact(behaviour: Behaviour, action: Action): Unit = {
    behaviour.actorRef ! action
  }

  def collidables(): List[Collidable] =
    behaviours
      .filter(a => a.isInstanceOf[Collidable])
      .map(_.asInstanceOf[Collidable])
}

object MainSceneModel {
  def empty: MainSceneModel = MainSceneModel(null, Nil, Nil)
  def centered(center: Point): MainSceneModel = MainSceneModel(center, Nil, Nil)
  def initialized(center: Point, dots: List[Element], actors: List[Behaviour]) = MainSceneModel(center, dots, actors)
}

