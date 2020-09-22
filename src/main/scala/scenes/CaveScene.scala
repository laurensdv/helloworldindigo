package scenes

import indigo.scenes.{Lens, Scene, SceneName}
import indigo.shared.{FrameContext, Outcome}
import indigo.shared.events.{EventFilters, GlobalEvent}
import indigo.shared.scenegraph.SceneUpdateFragment
import indigo.shared.subsystems.SubSystem
import model.{CaveSceneModel, CaveSceneViewModel, MyGameModel, MyGameViewModel}

object CaveScene extends Scene[Unit, MyGameModel, MyGameViewModel] {
  override type SceneModel = CaveSceneModel
  override type SceneViewModel = CaveSceneViewModel

  override def name: SceneName = SceneName("Cave")

  override def modelLens: Lens[MyGameModel, CaveSceneModel] =
    Lens(model => model.caveSceneModel,
      (model, sceneModel) => model.copy(caveSceneModel = sceneModel))

  override def viewModelLens: Lens[MyGameViewModel, CaveSceneViewModel] = Lens.fixed()

  override def eventFilters: EventFilters = EventFilters.Default

  override def subSystems: Set[SubSystem] = Set()

  override def updateModel(context: FrameContext[Unit], model: CaveSceneModel): GlobalEvent => Outcome[CaveSceneModel] = {
    case _ => Outcome(model)
  }

  override def updateViewModel(context: FrameContext[Unit], model: CaveSceneModel, viewModel: CaveSceneViewModel): GlobalEvent => Outcome[CaveSceneViewModel] = {
    case _ => Outcome(viewModel)
  }

  override def present(context: FrameContext[Unit], model: CaveSceneModel, viewModel: CaveSceneViewModel): SceneUpdateFragment = {
    SceneUpdateFragment.empty
  }
}
