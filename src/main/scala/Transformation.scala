
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.{break, breakable}

object Transformation {

  val newData = ListBuffer[Array[String]]()

  val checkPositiveId: String => Int = (number: String) => {
    if (number.toInt < 0)
      -1
    else
      0
  }

  val checkPositiveAge: String => String = (number: String) => {

    if (number.toInt < 0)
      number.toInt.abs.toString
    else
      number
  }


  val checkGreater1000: String => String = (number: String) => {

    if (number.toInt > 1000)
      number
    else
      (number.toInt + 1000).toString

  }

  val checkAnon: String => String = (name: String) => {
    var asci = ""
    name.foreach(character => {
      asci += character.toInt.toString
    })
    asci
  }

  val upper: String => String = (name: String) => {

    name.toUpperCase

  }

  val transform: (List[Array[String]], Map[String, List[String]]) => ListBuffer[Array[String]] = (data: List[Array[String]]
                                                                                                  , functions: Map[String, List[String]]) => {

    var newId = ""
    var newName = ""
    var newAge = ""
    var check = 0

    data.foreach(row => {
      try{

          functions("id").foreach(function => {
            if (function == "pos") {
              check = checkPositiveId(row(0).replaceAll("\\D+", ""))
            }
            if (function == "greater1000")
              newId = checkGreater1000(row(0).replaceAll("\\D+", ""))

          })

          functions("name").foreach(function => {

            if (function == "uppercase")
              newName = upper(row(1))

            if (function == "anon")
              newName = checkAnon(row(1))

          })


          functions("age").foreach(function => {
            try {
              if (function == "pos")
                newAge = checkPositiveAge(row(2))

            }
            catch {
              case e: ArrayIndexOutOfBoundsException => newAge = "NULL"
            }
          })}
            catch{
            case e :NumberFormatException => newId = "-1"
          }
            if (check == -1)
              break
            else
              newData += Array(newId, newName, newAge)
        })
    newData
      }

}

