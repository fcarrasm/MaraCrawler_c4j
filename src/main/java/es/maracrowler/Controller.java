package es.maracrowler;

import java.util.Date;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import es.maracrowler.madrid2015.RnRMadrid2015;
import es.maracrowler.utils.MaraUtils;

public class Controller {

	public Controller() {
	}

	public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        // To only crawl the pages which you added as a seed, set the MaxDepthOfCrawling to 0.
        config.setMaxDepthOfCrawling(0);
        config.setPolitenessDelay(800);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        
        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        // MARATON MADRID 2015
//        crawlsBibsRnRMadrid2015(controller);
//        controller = crawlsResultsRnRMadrid2015(controller);

        // MEDIA MARATON MADRID 2015
        controller = crawlsResultsMediaRnRMadrid2015(controller);
        
        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        long startTimeMillis = System.currentTimeMillis();
        Date startDate = new Date(startTimeMillis);
		System.out.println("[Start Crawling] At " + startDate);
		
        controller.start(MyCrawler.class, numberOfCrawlers);
		
        long endTimeMillis = System.currentTimeMillis();
        Date endDate = new Date(endTimeMillis);
        System.out.println("[End Crawling] At [" + endDate + "] total time [" + MaraUtils.getDurationBreakdown(endTimeMillis-startTimeMillis) + "]");
	}

	/**
	 * MARATON DE MADRID 2015
	 * @param controller
	 * @return
	 */
	private static CrawlController crawlsResultsMaratonRnRMadrid2015(CrawlController controller) {
		return crawlsResultsRnRMadrid2015(controller,"dorsalesRnRMadrid2015","http://www.resultadoscarreras.es/detalleIndependiente.php?pag=det&idi=ES&car=6&eve=1&pid=");
	}
	/**
	 * MEDIA MARAON MADRID 2015
	 * @param controller
	 * @return
	 */
	private static CrawlController crawlsResultsMediaRnRMadrid2015(CrawlController controller) {
		return crawlsResultsRnRMadrid2015(controller,"dorsalesMediaRnRMadrid2015","http://www.resultadoscarreras.es/detalleIndependiente.php?pag=det&idi=ES&car=6&eve=2&pid=" );
	}
	
	/**
	 * Extraccion de los datos de los corredores de la RnR Madrid 2015.
	 * @param controller
	 * @return
	 */
	private static CrawlController crawlsResultsRnRMadrid2015(CrawlController controller, String propertie, String url) {
		String bibs = MaraUtils.getPropertie("dorsales.properties", propertie);
        String[] bibs_Array = null;
        if(bibs!=null && !"".equals(bibs)){
        	bibs_Array = bibs.split(";");
        }
        
        for (int i = 0; i < bibs_Array.length; i++) {
        	String dorsal = bibs_Array[i];
        	if(dorsal!=null && !"".equals(dorsal) && dorsal.startsWith("F")){
        		dorsal = RnRMadrid2015.replaceFamaleDorsalName(dorsal);
        	}
			controller.addSeed(url+dorsal);
		}
		return controller;
	}
	
	/**
	 * Extraccion de los dorsales de la RnRMadrid 2015.
	 * 
	 * @param controller
	 */
	private static CrawlController crawlsBibsRnRMadrid2015(CrawlController controller) {
		// Extract BIBS
        for (int i = 0; i <=58; i++) {
            controller.addSeed("http://www.resultadoscarreras.es/index.php?pag=res&idi=ES&car=6&eve=1&par=11&cla=1&dor=&nom=&ape=&pai=&nup="+i);
		}
		return controller;
	}
	
	/**
	 * Extraccion de los dorsales de la RnRMadrid 2015.
	 * 
	 * @param controller
	 */
	private static CrawlController crawlsBibsMediaRnRMadrid2015(CrawlController controller) {
		// Extract BIBS
        for (int i = 0; i <=36; i++) {
            controller.addSeed("http://www.resultadoscarreras.es/index.php?pag=res&idi=ES&car=6&eve=2&par=6&cla=1&dor=&nom=&ape=&pai=&nup="+i);
		}
		return controller;
	}
	
	
}
