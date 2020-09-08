package scenes

import config.{MyAssets, MyGameConfig}
import indigo.scenes._
import indigo.shared.FrameContext
import indigo.shared.events.{EventFilters, GlobalEvent}
import indigo.shared.scenegraph.SceneUpdateFragment
import indigo.shared.subsystems.SubSystem
import indigo._
import model.{MyGameModel, MyGameViewModel}

object IntroScene extends Scene[Unit, MyGameModel, MyGameViewModel] {
  override type SceneModel = MyGameModel
  override type SceneViewModel = Unit

  override def name: SceneName = SceneName("Intro")

  override def modelLens: Lens[MyGameModel, MyGameModel] =
    Lens.keepLatest

  override def viewModelLens: Lens[MyGameViewModel, Unit] = Lens.fixed(())

  override def eventFilters: EventFilters = EventFilters.Default

  override def subSystems: Set[SubSystem] = Set()

  override def updateModel(context: FrameContext[Unit], model: MyGameModel): GlobalEvent => Outcome[MyGameModel] = {
    case KeyboardEvent.KeyUp(Keys.SPACE) =>
      Outcome(model)
        .addGlobalEvents(SceneEvent.JumpTo(MainScene.name))

    case FrameTick if(model.runtime <= Seconds(MyGameConfig.introTimeSeconds.toDouble)) =>
        Outcome(model.update(context.delta))

    case FrameTick if (model.runtime > Seconds(MyGameConfig.introTimeSeconds.toDouble)) =>
      Outcome(model.reset())
          .addGlobalEvents(SceneEvent.JumpTo(MainScene.name))

    case _ => Outcome(model)
  }


  override def updateViewModel(context: FrameContext[Unit], model: MyGameModel, sceneViewModel: Unit): GlobalEvent => Outcome[Unit] = _ => Outcome(())

  override def present(context: FrameContext[Unit], model: MyGameModel, viewModel: Unit): SceneUpdateFragment =
  {
    val horizontalCenter: Int = (MyGameConfig.config.viewport.width / MyGameConfig.config.magnification) / 2
    val verticalMiddle: Int   = (MyGameConfig.config.viewport.height / MyGameConfig.config.magnification) / 2

    def drawTitleText(center: Int, middle: Int): List[SceneGraphNode] =
      List(
        Text("Hello!", center, middle - 20, 1, MyAssets.fontKey).alignCenter,
        Text("A new game", center, middle - 5, 1, MyAssets.fontKey).alignCenter,
        Text("Made with Indigo", center, middle + 10, 1, MyAssets.fontKey).alignCenter
      )

    SceneUpdateFragment.empty
      .addUiLayerNodes(drawTitleText(horizontalCenter, verticalMiddle))
  }
}
