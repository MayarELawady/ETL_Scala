import java.sql.{Connection, SQLException, SQLIntegrityConstraintViolationException, SQLSyntaxErrorException}
import oracle.jdbc.pool.OracleDataSource
import java.time.LocalDateTime
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.{break, breakable}

object Loading  {

  val logs = ListBuffer[Array[String]]()
  def connOracle : Connection = {

    val oracleUser = "hr"
    val oraclePassword = "hr"
    val oracleURL = "jdbc:oracle:thin:@127.0.0.1:1521:xe"

    val ods = new OracleDataSource()
    ods.setUser(oracleUser)
    ods.setURL(oracleURL)
    ods.setPassword(oraclePassword)
    try {
      val  con = ods.getConnection()
      con
    }
    catch {
      case e : SQLException =>
        println(e.toString.substring(34))
        null
    }
  }

  val CreateTable:Map[String,String]=>Unit=(datatypes:Map[String,String])=> {
    val con = connOracle

    val query = s"""
      CREATE TABLE CUSTOMERS (${datatypes.keys.toList(0)} ${datatypes(datatypes.keys.toList(0))}  , ${datatypes.keys.toList(1)} ${datatypes(datatypes.keys.toList(1))}(100) , ${datatypes.keys.toList(2)} ${datatypes(datatypes.keys.toList(2))})
        """
    try {
      val statement = con.createStatement()
      val resultSet = statement.executeUpdate(query)
      LogWriter("Table Created")
    }
    catch {
      case e : SQLSyntaxErrorException =>{
        println("Table already exists")
        LogWriter("Table already exists")
      }
      case e : NullPointerException => {
        println("Not connectd")

      }

    }

  }

  val insertData:ListBuffer[Array[String]]=> Unit =(data:ListBuffer[Array[String]])=>{

    val con = connOracle

    data.map(row=>{

      breakable{
      if (row(0) == "-1") break
      if (row(1) == "") row(1)="NULL"
      if (row(2) == "") row(0)="NULL"

      val query = s"""
    INSERT INTO CUSTOMERS VALUES (${row(0)},${row(1)},${row(2)})"""

      try {
        val statement = con.createStatement()
        statement.executeUpdate(query)
      }
      catch {
        case e : SQLSyntaxErrorException =>{
          println(e.toString)
        }
        case e : NullPointerException => {
          println("Not connectd")}
        case e : SQLIntegrityConstraintViolationException=>{
          println("Data Already Exists")

        }

      }

    }})

  }

  val insertLogs:ListBuffer[Array[String]]=> Unit =(data:ListBuffer[Array[String]])=>{

    val con = connOracle

    data.map(row=>{

      val query = s"""
    INSERT INTO LOGS VALUES ('${row(0)}','${row(1)}')"""

      try {
        val statement = con.createStatement()
        val resultSet = statement.executeUpdate(query)
      }
      catch {
        case e : SQLSyntaxErrorException =>{
          println(e.toString)
        }
        case e : NullPointerException => {
          println("Not connectd")

        }

      }

    })
  }
  val LogWriter : String => Unit = (Description : String)=>{
    logs += Array(LocalDateTime.now().toString,Description)
  }

}
