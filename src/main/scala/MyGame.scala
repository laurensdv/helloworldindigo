import behaviours.Behaviour
import config.{MyAssets, MyGameConfig}
import elements.Element
import indigo.{Group, IndigoGame}
import scenes.{CaveScene, IntroScene, MainScene}
import indigo.json.Json
import indigo.platform.assets.{AssetCollection, LoadedTextAsset}
import indigo.scenes.{Scene, SceneName}
import indigoextras.subsystems.FPSCounter
import indigo._
import model._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits._
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object MyGame extends IndigoGame[Unit, Group, MyGameModel, MyGameViewModel] {

  override def initialModel(startupData: Group): MyGameModel = {
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
      IntroSceneModel.initialized(),
      CaveSceneModel()
    )
  }

  override def boot(flags: Map[String, String]): BootResult[Unit] = {
    val gameViewport = {
      (flags.get("width"), flags.get("height")) match {
        case (Some(w), Some(h)) =>
          GameViewport(w.toInt, h.toInt)

        case _ =>
          GameViewport(MyGameConfig.width, MyGameConfig.height)
      }

    }
    println(gameViewport.width)
    println(gameViewport.height)

    val matchingPaths = MyAssets.assets
      .filter(_.isInstanceOf[AssetType.Text])
      .map(_.asInstanceOf[AssetType.Text])
      .filter(_.name == MyAssets.terrianData)
      .toList

    val loadedTextAssets: Future[Seq[LoadedTextAsset]] = indigo.platform.assets.AssetLoader.loadTextAssets(matchingPaths)

    val data: Future[Seq[List[Iterable[Option[Animation]]]]] = loadedTextAssets.map(st => for {
      s <- st.map(f => f.data)
      t <- Json.tiledMapFromJson(s)
      g <- t.parseAnimations(MyAssets.terrianImage)
    } yield g)

    println("Animations")
    data.foreach { l => println(l.flatten.flatten) }

    BootResult
      .configOnly(MyGameConfig.config.withViewport(gameViewport))
      .withAssets(MyAssets.assets)
      .withAnimations(MyAssets.animations)
      .withFonts(MyAssets.fontInfo)
      .withSubSystems(Set(FPSCounter(MyAssets.fontKey, Point(5, 5), 60)))
  }

  override def scenes(bootData: Unit): NonEmptyList[Scene[Group, MyGameModel, MyGameViewModel]] = NonEmptyList(IntroScene, MainScene, CaveScene)

  override def initialScene(bootData: Unit): Option[SceneName] = Option(IntroScene.name)

  override def setup(bootData: Unit, assetCollection: AssetCollection, dice: Dice): Startup[Group] = {


    println(assetCollection.findTextDataByName(MyAssets.terrianData))

    val maybeTiledMap = for {
      j <- assetCollection.findTextDataByName(MyAssets.terrianData)
      t <- Json.tiledMapFromJson(j)
      g <- t.toGroup(MyAssets.terrianImage)
    } yield g


    maybeTiledMap match {
      case None =>
        Startup.Failure("Could not generate TiledMap from data.")

      case Some(tiledMap) =>
        Startup.Success(tiledMap)
    }
  }

  override def initialViewModel(startupData: Group, model: MyGameModel): MyGameViewModel = MyGameViewModel(CaveSceneViewModel())
}
