package text2midi

import processing.core.PApplet

/**
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
trait SetupAware { this: PApplet =>
  private val awareness = new Awareness

  def applicationSetup(): Unit
  final def beforeSetup(block: => Unit): Unit = awareness.before(() => block)
  final def afterSetup(block: => Unit): Unit = awareness.after(() => block)

  final override def setup() = awareness.run {
    applicationSetup()
  }
}

class Awareness {
  type Op = () => Unit

  private[this] var befores: List[Op] = Nil
  private[this] var afters: List[Op] = Nil

  final def before(block: () => Unit): Unit = {
    befores = block :: befores
  }

  final def after(block: () => Unit): Unit =  {
    afters = block :: afters
  }

  final def run(block: => Unit) = {
    befores foreach (block => block())
    block
    afters foreach (block => block())
  }
}