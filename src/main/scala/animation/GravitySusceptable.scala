package animation

import indigo.Seconds

trait GravitySusceptable {
  def gravity(timeDelta: Seconds): GravitySusceptable
}
