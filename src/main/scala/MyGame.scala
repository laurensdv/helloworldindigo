import config.{MyAssets, MyGameConfig}
import indigo.scenes.{Scene, SceneName}
import scenes.MainScene
import indigo._
import model.{MainSceneModel, MyGameModel, MyGameViewModel}

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object MyGame extends IndigoGame[Unit, Unit, MyGameModel, MyGameViewModel] {

  override def initialModel(startupData: Unit): MyGameModel =
    MyGameModel(MainSceneModel.initial(MyGameConfig.config.viewport.giveDimensions(MyGameConfig.magnification).center))

  override def boot(flags: Map[String, String]): BootResult[Unit] = BootResult
    .configOnly(MyGameConfig.config)
    .withAssets(MyAssets.assets)

  override def scenes(bootData: Unit): NonEmptyList[Scene[Unit, MyGameModel, MyGameViewModel]] = NonEmptyList(MainScene)

  override def initialScene(bootData: Unit): Option[SceneName] = Option(MainScene.name)

  override def setup(bootData: Unit, assetCollection: AssetCollection, dice: Dice): Startup[StartupErrors, Unit] =
    Startup.Success(())

  override def initialViewModel(startupData: Unit, model: MyGameModel): MyGameViewModel = MyGameViewModel()
}
