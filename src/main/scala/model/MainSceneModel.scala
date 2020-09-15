package model

import animation.Moveable
import behaviours.{Action, Behaviour, BehaviourRegistry}
import collision.Collidable
import elements.Element
import indigo.{Point, Seconds}

case class MainSceneModel(center: Point, elements: List[Element], behaviours: List[String]) extends AbstractSceneModel {

  def addElement(element: Element): MainSceneModel =
    this.copy(center = center, elements = element :: elements, behaviours = behaviours)

  def addBehaviour(behaviourName: String, behaviour: Behaviour): MainSceneModel = {
    BehaviourRegistry.add(behaviourName, behaviour)
    this.copy(center = center, elements = elements, behaviours = behaviours.appended(behaviourName))
  }

  def updateElements(newElements: List[Element]): MainSceneModel = {
    val updElements = newElements ++ elements.filter(e => !newElements.map(_.tag).contains(e.tag))
    this.copy(center = center, elements = updElements, behaviours)
  }

  def update(timeDelta: Seconds): MainSceneModel =
    this.copy(center = center, elements = elements.map(_.update(timeDelta)), behaviours = behaviours)

  def interact(behaviour: String, action: Action): Unit = {
    if(BehaviourRegistry.exists(behaviour))
      BehaviourRegistry(behaviour).actorRef ! action
  }

  def moveables(): List[Moveable] =
    elements
      .filter(_.isInstanceOf[Moveable])
      .map(_.asInstanceOf[Moveable])

  def collidables(): List[Collidable] =
    BehaviourRegistry
      .all(behaviours)
      .filter(_.isInstanceOf[Collidable])
      .map(_.asInstanceOf[Collidable])
}

object MainSceneModel {
  def empty: MainSceneModel = MainSceneModel(null, Nil, null)
  def centered(center: Point): MainSceneModel = MainSceneModel(center, Nil, null)
  def initialized(center: Point, elements: List[Element], actors: List[Behaviour]) = {
    val behaviourList = BehaviourRegistry.init(actors)
    MainSceneModel(center, elements, behaviourList)
  }
}

