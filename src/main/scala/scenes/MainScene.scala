package scenes

import behaviours.{BehaviourRegistry, Chest, Interact}
import collision.BoundingBox
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
    Lens(model => model.mainScene,
      (model, sceneModel) => model.copy(sceneModel, model.introScene))

  override def viewModelLens: Lens[MyGameViewModel, MyGameViewModel] = Lens.keepLatest

  override def eventFilters: EventFilters = EventFilters.Default

  override def subSystems: Set[SubSystem] = Set()

  override def updateModel(context: FrameContext[Unit], model: MainSceneModel): GlobalEvent => Outcome[MainSceneModel] = {

    case MouseEvent.Click(x, y) =>
      //val adjustedPosition = Point(x, y) - model.center

      BoundingBox.hits(model.collidables(), Point(x, y)).foreach {
        case Chest(_, _, tag, _, _) => model.interact(tag, Interact)
      }

      Outcome(
        model
        //          .addDot(
        //            Dot(
        //              Point.distanceBetween(model.center, Point(x, y)).toInt,
        //              Radians(
        //                Math.atan2(
        //                  adjustedPosition.x.toDouble,
        //                  adjustedPosition.y.toDouble
        //                )
        //              )
        //            )
        //          )
      )

    case FrameTick =>
      Outcome(model.update(context.delta))

    case _ =>
      Outcome(model)
  }

  override def updateViewModel(context: FrameContext[Unit], model: MainSceneModel, sceneViewModel: MyGameViewModel): GlobalEvent => Outcome[SceneViewModel] = _ => Outcome(sceneViewModel)

  override def present(context: FrameContext[Unit], model: MainSceneModel, viewModel: MyGameViewModel): SceneUpdateFragment =
    SceneUpdateFragment()
      //.addGameLayerNodes(
      //  viewModel.drawDots(model.center, model.dots)
      //)
      .addGameLayerNodes(
        BehaviourRegistry.all(model.behaviours).map(viewModel.draw)
      )
}
