package es.maracrowler;

import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

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
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             
//             System.out.println(html);
             
             getDataStringBuilder(html);
			
            
             
             Set<WebURL> links = htmlParseData.getOutgoingUrls();

//             System.out.println("Text length: " + text.length());
//             System.out.println("Html length: " + html.length());
//             System.out.println("Number of outgoing links: " + links.size());
         }
    }

	private void getDataStringBuilder(String html) {
		Document doc = Jsoup.parseBodyFragment(html);
		 
		 StringBuilder sb = new StringBuilder();
		 
		 System.out.println(doc.getElementsByClass("nombreCorredor").html());
		 sb.append(doc.getElementsByClass("nombreCorredor").html()).append(";");
		 
		 
		 Elements elementsByClass = doc.getElementsByClass("datosDetalle");
		 if(elementsByClass!=null && elementsByClass.size()!=0){
			 // Datos personales
			 Element datos0 = elementsByClass.get(0);
			 Elements ebt0 = datos0.getElementsByTag("td");
			 for (int i = 0; i <=4 ; i++) {
				String field = ebt0.get(5+i).html();
				// Si es pais solo las 3 ultimas letras
				if(i==3 && field!=null && !"".equals(field) && field.length()>3){
					field = field.substring(field.length()-3, field.length());
				}
				sb.append(field).append(";");
			 }
			 System.out.println(sb);
			 
			 // Puestos
			 Element datos1 = elementsByClass.get(1);
			 Elements ebt1 = datos1.getElementsByTag("td");
			 for (int i = 0; i <=2 ; i++) {
				 String field = ebt1.get(3+i).html();
				 sb.append(field).append(";");
			 }
			 System.out.println(sb);

			 
			 // Pustos de control
			 Element datos2 = elementsByClass.get(2);
			 Elements ebt2 = datos2.getElementsByTag("td"); 
			 for (int j = 0; j <=59; j++) {
				sb.append(ebt2.get(6+(j)).ownText()+ ";");
			 }
			 System.out.println(sb);
		 }
	}
}