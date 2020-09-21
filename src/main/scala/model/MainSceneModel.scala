package model

import animation.{Fixed, Movable, Timely}
import behaviours.{Action, Behaviour, BehaviourRegistry}
import collision.{Clickable, Collidable}
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
    this.copy(center = center, elements = elements
        .map(_.update(timeDelta))
        .filter(p => if (p.isInstanceOf[Timely]) p.asInstanceOf[Timely].lifespan > Seconds(0) else true ) //clean up old timely objects
      , behaviours = behaviours)

  def interact(behaviour: String, action: Action): Unit = {
    if(BehaviourRegistry.exists(behaviour))
      BehaviourRegistry(behaviour).actorRef ! action
  }

  def moveables(): List[Movable] =
    elements
      .filter(_.isInstanceOf[Movable])
      .map(_.asInstanceOf[Movable])

  def collidables(): List[Collidable] =
    elements
      .filter(_.isInstanceOf[Collidable])
      .map(_.asInstanceOf[Collidable])

  def clickables(): List[Clickable] =
    BehaviourRegistry
      .all(behaviours)
      .filter(_.isInstanceOf[Clickable])
      .map(_.asInstanceOf[Clickable])

  def fixed(): List[Fixed] =
    elements
      .filter(_.isInstanceOf[Fixed])
      .map(_.asInstanceOf[Fixed])

}

object MainSceneModel {
  def empty: MainSceneModel = MainSceneModel(null, Nil, null)
  def centered(center: Point): MainSceneModel = MainSceneModel(center, Nil, null)
  def initialized(center: Point, elements: List[Element], actors: List[Behaviour]) = {
    val behaviourList = BehaviourRegistry.init(actors)
    MainSceneModel(center, elements, behaviourList)
  }
}

