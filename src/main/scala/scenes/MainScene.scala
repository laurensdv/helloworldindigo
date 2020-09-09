package scenes

import actors.Interact
import config.{MyAssets, MyGameConfig}
import indigo.scenes._
import indigo.shared.FrameContext
import indigo.shared.events.{EventFilters, GlobalEvent, MouseEvent}
import indigo.shared.scenegraph.SceneUpdateFragment
import indigo.shared.subsystems.SubSystem
import indigo._
import model.main.Dot
import model.{MainSceneModel, MyGameModel, MyGameViewModel}

object MainScene extends Scene[Unit, MyGameModel, MyGameViewModel] {
  override type SceneModel = MainSceneModel
  override type SceneViewModel = MyGameViewModel

  override def name: SceneName = SceneName("Main")

  override def modelLens: Lens[MyGameModel, MainSceneModel] =
    Lens(model => model.mainScene,
      (model, sceneModel) => model.copy(sceneModel))

  override def viewModelLens: Lens[MyGameViewModel, MyGameViewModel] = Lens.keepLatest

  override def eventFilters: EventFilters = EventFilters.Default

  override def subSystems: Set[SubSystem] = Set()

  override def updateModel(context: FrameContext[Unit], model: MainSceneModel): GlobalEvent => Outcome[MainSceneModel] =
  {
    case MouseEvent.Click(x, y) =>
      val adjustedPosition = Point(x, y) - model.center
      //model.interact(model.chestActor, Interact)
      Outcome(
        model.addDot(
          Dot(
            Point.distanceBetween(model.center, Point(x, y)).toInt,
            Radians(
              Math.atan2(
                adjustedPosition.x.toDouble,
                adjustedPosition.y.toDouble
              )
            )
          )
        )
      )

    case FrameTick =>
      Outcome(model.update(context.delta))

    case _ =>
      Outcome(model)
  }

  override def updateViewModel(context: FrameContext[Unit], model: MainSceneModel, sceneViewModel: MyGameViewModel): GlobalEvent => Outcome[SceneViewModel] = _ => Outcome(sceneViewModel)

  def drawDots(
              center: Point,
              dots: List[Dot]
            ): List[Graphic] = {


  dots.map { dot =>
    val position = Point(
      (Math.sin(dot.angle.value) * dot.orbitDistance + center.x).toInt,
      (Math.cos(dot.angle.value) * dot.orbitDistance + center.y).toInt
    )

    Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.dotsAssetName))
      .withCrop(Rectangle(16, 16, 16, 16))
      .withRef(8, 8)
      .moveTo(position)
  }
  }

  override def present(context: FrameContext[Unit], model: MainSceneModel, viewModel: MyGameViewModel): SceneUpdateFragment =
    SceneUpdateFragment(
            viewModel.draw(model.chest).moveTo(MyGameConfig.config.viewport.giveDimensions(MyGameConfig.magnification).center)
          ).addGameLayerNodes(
      drawDots(model.center, model.dots)
    )
}
