import processing.core._

object Main extends PApplet {

  override def setup = {
    size(200,200);
    background(0);
  }

  override def draw = {
    stroke(255);
    if (mousePressed) {
      line(mouseX,mouseY,pmouseX,pmouseY);
    }
  }

  def main(args: Array[String]): Unit = {
    println("Hello, world")

    val image = new PImage(1, 1)


  }
}
