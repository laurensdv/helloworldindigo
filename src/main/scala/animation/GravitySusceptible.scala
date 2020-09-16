package animation

import indigo.Seconds

trait GravitySusceptible {
  def gravity(timeDelta: Seconds): GravitySusceptible
}
