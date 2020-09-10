package elements

import indigo.Seconds

trait Element {
  def update(timeDelta: Seconds): Element
}
