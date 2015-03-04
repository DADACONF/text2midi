import processing.core._
import math._
import scala.util.Random

object Main extends PApplet {

  def main(args: Array[String]) = {
    val test = new Main
    val frame = new javax.swing.JFrame("Test")
    frame.getContentPane().add(test)

    test.init
    frame.setBounds(0, 0, Window.Width, Window.Height)
    frame.setVisible(true)
  }
}

object Window {
  val Height = 720
  val Width = 1280
}

class Main extends PApplet {
  var angle: Int = 0

  override def setup() = {
    size(Window.Width, Window.Height)
    background(102)
    smooth()
    noStroke()
    fill(0, 102)
  }

  override def draw() = {
    angle += 10
    val value = cos(toRadians(angle)) * 6.0
    for (a <- 0 to 360 by 75) {
      val xoff = cos(toRadians(a)) * value
      val yoff = sin(toRadians(a)) * value
      fill(Random.nextInt(255))

      ellipse(
        (mouseX + xoff).toFloat,
        (mouseY + yoff).toFloat,
        value.toFloat,
        value.toFloat)
    }
    fill(255)
    ellipse(mouseX, mouseY, 2, 2)
  }
}