package model

import indigo.Seconds

final case class IntroSceneModel(runtime: Seconds = Seconds(0)) extends AbstractSceneModel {

  def update(timeDelta: Seconds): IntroSceneModel = {
    println(this.runtime)
    this.copy(runtime + timeDelta)
  }

  def reset(): IntroSceneModel =
    this.copy(Seconds(0))
}

object IntroSceneModel {
  def initialized(): IntroSceneModel = IntroSceneModel()
}
