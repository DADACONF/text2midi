package text2midi

import processing.core.PApplet

/**
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
trait DrawAware { this: PApplet =>
  private val awareness = new Awareness

  def mainDraw(): Unit
  final def beforeDraw(block: => Unit): Unit = awareness.before(() => block)
  final def afterDraw(block: => Unit): Unit  = awareness.after(() => block)

  final override def draw() = awareness.run { mainDraw() }
}
