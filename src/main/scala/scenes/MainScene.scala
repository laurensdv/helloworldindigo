package scenes

import config.{MyAssets, MyGameConfig}
import indigo.{FrameTick, Graphic, Material, Outcome, Point, Radians, Rectangle, Signal}
import indigo.scenes._
import indigo.shared.FrameContext
import indigo.shared.events.{EventFilters, GlobalEvent, MouseEvent}
import indigo.shared.scenegraph.SceneUpdateFragment
import indigo.shared.subsystems.SubSystem
import model.main.Dot
import model.{MainSceneModel, MyGameModel, MyGameViewModel}

object MainScene extends Scene[Unit, MyGameModel, MyGameViewModel] {
  override type SceneModel = MainSceneModel
  override type SceneViewModel = Unit

  override def name: SceneName = SceneName("Main")

  override def modelLens: Lens[MyGameModel, MainSceneModel] =
    Lens(model => model.mainScene,
      (model, sceneModel) => model.copy(sceneModel))

  override def viewModelLens: Lens[MyGameViewModel, Unit] = Lens.fixed(())

  override def eventFilters: EventFilters = EventFilters.Default

  override def subSystems: Set[SubSystem] = Set()

  override def updateModel(context: FrameContext[Unit], model: MainSceneModel): GlobalEvent => Outcome[MainSceneModel] =
  {
    case MouseEvent.Click(x, y) =>
      val adjustedPosition = Point(x, y) - model.center

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

  override def updateViewModel(context: FrameContext[Unit], model: MainSceneModel, sceneViewModel: Unit): GlobalEvent => Outcome[Unit] = _ => Outcome(())

  def drawDots(
              center: Point,
              dots: List[Dot]
            ): List[Graphic] =
  dots.map { dot =>
    val position = Point(
      (Math.sin(dot.angle.value) * dot.orbitDistance + center.x).toInt,
      (Math.cos(dot.angle.value) * dot.orbitDistance + center.y).toInt
    )

    Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.assetName))
      .withCrop(Rectangle(16, 16, 16, 16))
      .withRef(8, 8)
      .moveTo(position)
  }

  override def present(context: FrameContext[Unit], model: MainSceneModel, viewModel: Unit): SceneUpdateFragment =
    SceneUpdateFragment(
      Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.assetName)),
      Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.assetName))
        .withCrop(Rectangle(16, 16, 16, 16))
        .withRef(8, 8)
        .moveTo(
          Signal
            .Orbit(MyGameConfig.config.viewport.giveDimensions(MyGameConfig.magnification).center, 30)
            .map(_.toPoint)
            .at(context.gameTime.running
            ))
        .moveBy((600.0f * context.delta.toFloat).toInt, 0),
      Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyAssets.assetName)
      )
    ).addGameLayerNodes(
      drawDots(model.center, model.dots)
    )
}
