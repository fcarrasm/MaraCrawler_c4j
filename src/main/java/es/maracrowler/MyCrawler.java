package es.maracrowler;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import es.maracrowler.madrid2015.RnRMadrid2015;
import es.maracrowler.utils.MaraUtils;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
//         return !FILTERS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/");
         return !FILTERS.matcher(href).matches();
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             // String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             
//             System.out.println(html);
             
             // PARSE HTML
             Document doc = Jsoup.parseBodyFragment(html);

             
//             RnRMadrid2015.getBibs2015(doc,"dorsalesMediaMadrid2015.properties");
             
             // MARATON DE MADRID 2015
//             String dataRunnerM = RnRMadrid2015.getDataRunnersMaraton(doc);   
//             MaraUtils.writeFileLine("RnRMadrid2015.csv", dataRunnerM, false);
             
             // MEDIA MARATON DE MADRID 2015
             String dataRunnerMM = RnRMadrid2015.getDataRunnersMediaMaraton(doc);   
             MaraUtils.writeFileLine("MediaRnRMadrid2015.csv", dataRunnerMM, false);
             
              // Set<WebURL> links = htmlParseData.getOutgoingUrls();

//             System.out.println("Text length: " + text.length());
//             System.out.println("Html length: " + html.length());
//             System.out.println("Number of outgoing links: " + links.size());
         }
    }

}