package behaviours

object BehaviourRegistry {
  protected var behaviours: Map[String, Behaviour] = Map.empty

  def init(behaviourList: List[Behaviour]) = {
    behaviours = behaviourList.map(_.name).zip(behaviourList).toMap
    behaviours.keys.toList
  }

  def add(behaviourName: String, behaviour: Behaviour): Unit = {
    behaviours = behaviours.updated(behaviourName, behaviour)
  }
  def changeBehaviourState(behaviourName: String, data: Data): Unit = {
    behaviours = behaviours.updated(behaviourName, behaviours(behaviourName).updateState(data))
  }

  def update(behaviourName: String, behaviour: Behaviour): Unit = {
    behaviours = behaviours.updated(behaviourName, behaviour)
  }

  def apply(behaviourName: String): Behaviour =
    behaviours(behaviourName)

  def exists(behaviourName: String): Boolean =
    behaviours.contains(behaviourName)

  def all() = behaviours.values.toList

  def all(filters: List[String]) =
    behaviours.view.filterKeys(filters.contains(_)).values.toList

}