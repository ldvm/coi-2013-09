/**
 *
 * @author jirihelmich
 * @created 10/3/13 10:05 PM
 * @package
 */

import scala.io.Source

val filename = "sankce.csv"
val dateFormatIn = new java.text.SimpleDateFormat("dd.m.yyyy")
val dateFormatOut = new java.text.SimpleDateFormat("yyyy-mm-dd")

val prefixes =
  """
    |@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
  """.stripMargin

val turtle = Source.fromFile(filename).getLines().drop(1).map { line =>

  val parts = line.split(",")
  val id = parts(0)
  val inspectionID = parts(1)
  val fine = parts(2)
  val law = parts(3)
  val lawDetail = parts(4)
  val date = dateFormatOut.format(dateFormatIn.parse(parts(5)))

  """
    |<http://payola.cz/coi/2013-09/sanction/{ID}>      a     <http://payola.cz/gov/coi#sanction> ;
    |                 <http://payola.cz/gov/coi#inspection>     <http://payola.cz/coi/2013-09/inspection/{inspectionID}> ;
    |                 <http://payola.cz/gov/coi#fine>           [
    |                         <http://payola.cz/gov/coi#fineValue>      "{fine}"^^xsd:integer ;
    |                         <http://payola.cz/gov/coi#fineCurrency>   "CZK" ;
    |
    |                 ] ;
    |                 <http://payola.cz/gov/coi#sanctionLegalCause>   "{law}, {lawDetail}" ;
    |                 <http://payola.cz/gov/coi#executionDate>        "{date}"^^xsd:date .
    |
  """.stripMargin.replace("{ID}",id).replace("{date}", date).replace("{inspectionID}",inspectionID).replace("{fine}",fine)
  .replace("{law}",law).replace("{lawDetail}",lawDetail)

}.mkString

print(prefixes+"\n"+turtle)