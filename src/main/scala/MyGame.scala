import behaviours.Behaviour
import config.{MyAssets, MyGameConfig}
import elements.Element
import scenes.{IntroScene, MainScene}
import indigo._
import indigo.scenes.{Scene, SceneName}
import indigoextras.subsystems.FPSCounter
import model.{IntroSceneModel, MainSceneModel, MyGameModel, MyGameViewModel}

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object MyGame extends IndigoGame[Unit, Unit, MyGameModel, MyGameViewModel] {

  override def initialModel(startupData: Unit): MyGameModel = {
    val centerPos = MyGameConfig.config.viewport.giveDimensions(MyGameConfig.magnification).center
    MyGameModel(
      MainSceneModel.initialized(
      MyGameConfig.config.viewport.giveDimensions(MyGameConfig.magnification).center,
      List[Element](
        elements.Coin("coin 1", centerPos, 60, Radians(- Math.PI / 4)),
        elements.Coin("coin 2", centerPos, 80, Radians(- Math.PI / 2))
      ),
      List[Behaviour](
        behaviours.Chest(MyGameConfig.chestSize, centerPos, "GameChest")
        )
      ),
      IntroSceneModel.initialized()
    )
  }

  override def boot(flags: Map[String, String]): BootResult[Unit] = BootResult
    .configOnly(MyGameConfig.config)
    .withAssets(MyAssets.assets)
    .withAnimations(MyAssets.animations)
    .withFonts(MyAssets.fontInfo)
    .withSubSystems(Set(FPSCounter(MyAssets.fontKey, Point(5,5), 60)))

  override def scenes(bootData: Unit): NonEmptyList[Scene[Unit, MyGameModel, MyGameViewModel]] = NonEmptyList(IntroScene, MainScene)

  override def initialScene(bootData: Unit): Option[SceneName] = Option(IntroScene.name)

  override def setup(bootData: Unit, assetCollection: AssetCollection, dice: Dice): Startup[StartupErrors, Unit] =
    Startup.Success(())

  override def initialViewModel(startupData: Unit, model: MyGameModel): MyGameViewModel = MyGameViewModel()
}
