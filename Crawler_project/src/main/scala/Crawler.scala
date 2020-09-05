import java.text.NumberFormat

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{element, elementList}

import scala.collection.immutable.ListMap

object Crawler {
  def main(args: Array[String]) {

    val browser = JsoupBrowser()
    val baseUrl = args(0) //"https://ipinfo.io"
    val primaryPage:String =args(1).toString() //"https://ipinfo.io/countries"
    val outputPath :String =args(2)
    val doc = browser.get(primaryPage)
    var map: Map[Int, ASN] = Map()
    val countries: List[net.ruippeixotog.scalascraper.model.Element] = doc >> elementList("td a")
    try {
      for (country <- countries) {
        val countryName = country.text
        val countryMain: List[net.ruippeixotog.scalascraper.model.Element] = browser.get(baseUrl + country.attr("href")) >> elementList("td a")
        for (provider <- countryMain) {
          var registry: String = ""
          var num_ip_addresses: Int = 0
          val key = provider.text.slice(2, provider.text.length)
          val asnDoc = browser.get(baseUrl + provider.attr("href"))
          val asnName = (asnDoc >> element("h3")).text
          val asnDocDivs = asnDoc >> elementList(".col-6")
          for (div <- asnDocDivs) {
            if (div.text.contains("Registry")) {
              registry = div.text.split(" ")(1)
            }
            if (div.text.contains("IP Addresses")) {
              num_ip_addresses = (NumberFormat.getNumberInstance(java.util.Locale.US).parse((div.text.split(" ")(2)))).intValue()
            }
          }
          val asn = new ASN(countryName, asnName, registry, num_ip_addresses)
          map += (key.toInt -> asn)

        }
      }
    } catch {
      case x: Exception => {
        println("Processing stopped..!")
      }
    }
    val res = ListMap(map.toSeq.sortBy(_._1): _*)
    implicit val formats = DefaultFormats
    val sortedData = write(res)
    new CustomFileWriter().writeToFile(outputPath, sortedData)
  }
}
