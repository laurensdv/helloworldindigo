package elements

import indigo.Seconds

trait Element {
  def tag: String
  def update(timeDelta: Seconds): Element
}
