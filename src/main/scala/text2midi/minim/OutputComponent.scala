package text2midi.minim

import ddf.minim.AudioOutput

/**
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
trait OutputComponent {
  def out: AudioOutput
}

trait LineOutComponent extends OutputComponent { this: MinimComponent =>
  override def out = minim.getLineOut()
}
