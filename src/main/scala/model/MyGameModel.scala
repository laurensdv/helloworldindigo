package model

import indigo.Seconds

final case class MyGameModel(mainScene: MainSceneModel, runtime: Seconds = Seconds(0)) {
  def update(timeDelta: Seconds): MyGameModel = {
    println(this.runtime)
    this.copy(mainScene, runtime + timeDelta)
  }

  def reset(): MyGameModel =
    this.copy(mainScene, Seconds(0))
}
