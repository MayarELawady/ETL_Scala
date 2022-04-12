import Extraction._
import Loading._
import Transformation._

object Run extends App {

val run:Unit => Unit  = _ =>{

  try{
    val data = getTable(listFiles("C:\\Users\\ahmed\\OneDrive\\Desktop\\Scala_Project"))
    LogWriter(s"Read ${listFiles("C:\\Users\\ahmed\\OneDrive\\Desktop\\Scala_Project").length.toString} Files")
  val metaData = getTable(listFiles("C:\\Users\\ahmed\\OneDrive\\Desktop\\Metadata"))
  LogWriter("Read Metadata File")
  val splitted_data = splitting(data)    // to be accessed with index
  val splitted_meta = splitting(metaData)
  val mapFunctionss = mapOfFunctions(splitted_meta)
  val mapDataTypes = mapOfDatatypes(splitted_meta)
  transform(splitted_data,mapFunctionss)
  LogWriter("Data Transformed")
  CreateTable(mapDataTypes)
  insertData(newData)
  LogWriter("Data Inserted")
  insertLogs(logs)
  }
  catch{
    case e : NullPointerException => println("Wrong Path")
    case e : UnsupportedOperationException => println("Empty Directory")

  }
}
  run()



}
