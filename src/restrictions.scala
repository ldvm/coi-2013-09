/**
 *
 * @author jirihelmich
 * @created 10/3/13 10:05 PM
 * @package
 */

import scala.io.Source

val filename = "zakazy.csv"

val prefixes =
  """
    |@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
  """.stripMargin

val turtle = Source.fromFile(filename).getLines().drop(1).map { line =>

  val parts = line.split(";")
  val inspectionID = parts(0)
  val sortiment = parts(1)
  val volume = parts(2)
  val unit = parts(3)
  val totalPrice = parts(4)
  val legalCause = parts(5)

  """
    |[]     a   <http://payola.cz/gov/coi#restriction> ;
    |           <http://payola.cz/gov/coi#inspection>     <http://payola.cz/coi/2013-09/inspection/{inspectionID}> ;
    |           <http://payola.cz/gov/coi#sortiment>      "{sortiment}" ;
    |           <http://payola.cz/gov/coi#volume>         "{volume}"^^xsd:integer ;
    |           <http://payola.cz/gov/coi#volumeUnits>         "{unit}" ;
    |           <http://payola.cz/gov/coi#totalPrice>     "{totalPrice"}^^xsd:float ;
    |           <http://payola.cz/gov/coi#restrictionLegalCause>   "{legalCause}" .
    |
  """.stripMargin.replace("{inspectionID}",inspectionID).replace("{sortiment}",sortiment)
  .replace("{volume}",volume).replace("{totalPrice}",totalPrice).replace("{legalCause}",legalCause).replace("{unit}",unit)

}.mkString

print(prefixes+"\n"+turtle)