package scenes

import animation.{Fixed, Movable, Timely}
import behaviours.{BehaviourRegistry, Chest, Interact}
import collision.{BoundingBox, Collidable}
import config.MyGameConfig
import elements.{Coin, SmokeEffect}
import indigo._
import indigo.scenes._
import indigo.shared.FrameContext
import indigo.shared.events.{EventFilters, GlobalEvent, MouseEvent}
import indigo.shared.scenegraph.SceneUpdateFragment
import indigo.shared.subsystems.SubSystem
import model.{MainSceneModel, MyGameModel, MyGameViewModel}

object MainScene extends Scene[Unit, MyGameModel, MyGameViewModel] {
  override type SceneModel = MainSceneModel
  override type SceneViewModel = MyGameViewModel

  override def name: SceneName = SceneName("Main")

  override def modelLens: Lens[MyGameModel, MainSceneModel] =
    Lens(model => model.mainSceneModel,
      (model, sceneModel) => model.copy(mainSceneModel = sceneModel))

  override def viewModelLens: Lens[MyGameViewModel, MyGameViewModel] = Lens.keepLatest

  override def eventFilters: EventFilters = EventFilters.Default

  override def subSystems: Set[SubSystem] = Set()

  override def updateModel(context: FrameContext[Unit], model: MainSceneModel): GlobalEvent => Outcome[MainSceneModel] = {

    case KeyboardEvent.KeyUp(Keys.SPACE) =>
      Outcome(model)
        .addGlobalEvents(SceneEvent.JumpTo(MainScene.name))

    case MouseEvent.Click(x, y) =>
      //val adjustedPosition = Point(x, y) - model.center
      val hits: List[Collidable] = BoundingBox.hits(model.clickables(), Point(x, y))

      hits.foreach {
        case Chest(_, _, tag, _, _) => {
          model.interact(tag, Interact)
        }
      }

      if (hits.length > 0) //accelerate coin if we hit something.
        Outcome(
          model.updateElements(
            model
              .moveables()
              .filter(_.isInstanceOf[Coin])
              .map(_.asInstanceOf[Coin].push(-12.0))
          )
        )
      else
        Outcome(model)

    case FrameTick =>
      val hits = model.collidables()
        .filter(c => BoundingBox(c.pos(), c.size())
          .hitCenter(MyGameConfig.horizon))
        .filter(_.isInstanceOf[Movable])
        .map(_.asInstanceOf[Movable])
        .filter(_.moved)
        .map(_.asInstanceOf[Collidable])

      val smokes = for {
        h <- hits
      } yield SmokeEffect("smoke_"+System.currentTimeMillis().hashCode(), Seconds(1), h.pos())

      println("got hits: " + smokes.length)

      Outcome(model
        .updateElements(smokes)
        .update(context.delta)
      )

    case _ =>
      Outcome(model)
  }

  override def updateViewModel(context: FrameContext[Unit], model: MainSceneModel, sceneViewModel: MyGameViewModel): GlobalEvent => Outcome[SceneViewModel] = _ => Outcome(sceneViewModel)

  override def present(context: FrameContext[Unit], model: MainSceneModel, viewModel: MyGameViewModel): SceneUpdateFragment =
    SceneUpdateFragment()
      .addGameLayerNodes(
        BehaviourRegistry.all(model.behaviours).map(viewModel.draw) // Behaviours
      ).addGameLayerNodes(
        model.moveables().map(viewModel.draw) //Moveables
      ).addGameLayerNodes(
        model.fixed()
          .filter(_.isInstanceOf[Timely])
          .map(_.asInstanceOf[Timely])
          .filter(_.lifespan > Seconds(0))
          .map(_.asInstanceOf[Fixed])
          .map(viewModel.draw) //Timelies
      )
}
