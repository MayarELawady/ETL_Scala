import java.io.File
import scala.io.{BufferedSource, Source}

object Extraction  {

  val myFunctions : Map[String,List[String]] = Map()
  val dataTypes: Map[String,List[String]] = Map()

  val getTable: List[BufferedSource] => List[String] = (files: List[BufferedSource]) => {
    files.map(file => {
      val lines = file.getLines.toList
      file.close()
      lines
    }).reduce((l1, l2) => l1 ++ l2)
  }

  val listFiles:String => List[BufferedSource] = (path:String) => {
    val dir = new File(path)
    val listOfFiles = dir.listFiles().toList
    listOfFiles.map { f => {
      Source.fromFile(f.toString)
    }
    }

    }


  val splitting:List[String] => List[Array[String]] = (dataset:List[String])=>{
    dataset.map(row=>{
      row.split(",")
    })
  }

  val mapOfFunctions:List[Array[String]]=> Map[String,List[String]]=(splitted:List[Array[String]])=>{
    splitted.map(row=>{
      val mylist = row(2).split("/").toList
       row(0)-> mylist
    }
    ).toMap
  }

  val mapOfDatatypes:List[Array[String]]=> Map[String,String]=(splitted:List[Array[String]])=>{
    splitted.map(row=>{
      row(0)-> row(1)

    }
    ).toMap
  }


}




