import behaviours.Behaviour
import config.{MyAssets, MyGameConfig}
import scenes.{IntroScene, MainScene}
import indigo._
import indigo.scenes.{Scene, SceneName}
import model.{MainSceneModel, MyGameModel, MyGameViewModel}

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object MyGame extends IndigoGame[Unit, Unit, MyGameModel, MyGameViewModel] {

  override def initialModel(startupData: Unit): MyGameModel = {
    val centerPos = MyGameConfig.config.viewport.giveDimensions(MyGameConfig.magnification).center
    MyGameModel(MainSceneModel.initialized(
      MyGameConfig.config.viewport.giveDimensions(MyGameConfig.magnification).center,
      Nil,
      List[Behaviour](
        behaviours.Chest(MyGameConfig.chestSize, centerPos, "GameChest")
      )
    ))
  }

  override def boot(flags: Map[String, String]): BootResult[Unit] = BootResult
    .configOnly(MyGameConfig.config)
    .withAssets(MyAssets.assets)
    .withFonts(MyAssets.fontInfo)

  override def scenes(bootData: Unit): NonEmptyList[Scene[Unit, MyGameModel, MyGameViewModel]] = NonEmptyList(IntroScene, MainScene)

  override def initialScene(bootData: Unit): Option[SceneName] = Option(IntroScene.name)

  override def setup(bootData: Unit, assetCollection: AssetCollection, dice: Dice): Startup[StartupErrors, Unit] =
    Startup.Success(())

  override def initialViewModel(startupData: Unit, model: MyGameModel): MyGameViewModel = MyGameViewModel()
}
