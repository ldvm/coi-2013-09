/**
 *
 * @author jirihelmich
 * @created 10/3/13 10:05 PM
 * @package
 */

import scala.io.Source

val filename = "kontroly.csv"
val dateFormatIn = new java.text.SimpleDateFormat("dd.m.yyyy")
val dateFormatOut = new java.text.SimpleDateFormat("yyyy-mm-dd")

val prefixes =
  """
    |@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
  """.stripMargin

val turtle = Source.fromFile(filename).getLines().drop(1).map { line =>

  val parts = line.split(";")
  val id = parts(0)
  val date = dateFormatOut.format(dateFormatIn.parse(parts(1)))
  val nuts = parts(3)
  val regionName = parts(4)
  val municipalityNuts = parts(5)
  val municipalityName = parts(6)

    """
      |<http://payola.cz/coi/2013-09/inspection/{ID}>      a     <http://payola.cz/gov/coi#inspection> ;
      |                 <http://payola.cz/gov/coi#inspectedOn>     "{date}"^^xsd:date ;
      |                 <http://payola.cz/gov/coi#inspectionPlace>     [
      |                        a      <http://ec.europa.eu/eurostat/ramon/ontologies/geographic.rdf#LAURegion> ;
      |                        <http://ec.europa.eu/eurostat/ramon/ontologies/geographic.rdf#regionCode>  "{nuts}"
      |                 ] .
      |
    """.stripMargin.replace("{ID}",id).replace("{date}", date).replace("{nuts}",municipalityNuts)

}.mkString

print(prefixes+"\n"+turtle)