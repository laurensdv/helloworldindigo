import config.{MyAssets, MyGameConfig}
import indigo._
import model.Model

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object MyGameSandbox extends IndigoSandbox[Unit, Model] {

  val config: GameConfig = MyGameConfig.config

  val assets: Set[indigo.AssetType] = MyAssets.assets

  val animations: Set[Animation] =
    Set()

  val fonts: Set[FontInfo] =
    Set()

  def setup(assetCollection: AssetCollection, dice: Dice): Startup[StartupErrors, Unit] =
    Startup.Success(())

  def initialModel(startupData: Unit): Model =
    Model.initial(
      config.viewport.giveDimensions(MyGameConfig.magnification).center
    )

  def updateModel(context: FrameContext[Unit], model: Model): GlobalEvent
    => Outcome[Model] =
    _ => Outcome(model)

  def present(context: FrameContext[Unit], model: Model): SceneUpdateFragment =
    SceneUpdateFragment(
      Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyGameConfig.assetName)),
      Graphic(Rectangle(0, 0, 32, 32), 1, Material.Textured(MyGameConfig.assetName))
        .withCrop(Rectangle(16, 16, 16, 16))
        .withRef(8, 8)
        .moveTo(
          Signal
            .Orbit(config.viewport.giveDimensions(MyGameConfig.magnification).center, 30)
            .map(_.toPoint)
            .at(context.gameTime.running
        ))
        .moveBy((600.0f * context.delta.toFloat).toInt, 0)
    )



}
