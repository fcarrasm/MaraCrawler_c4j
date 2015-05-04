package es.maracrowler.madrid2015;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.maracrowler.utils.EscapeHtml;
import es.maracrowler.utils.MaraUtils;

public class RnRMadrid2015 {
	
	
	private static final String MEDIA = "media";
	private static final String MARATON = "maraton";

	public static String getDataRunnersMaraton(Document doc) {
		return getDataRunners(doc, MARATON);
	}
	
	public static String getDataRunnersMediaMaraton(Document doc) {
		return getDataRunners(doc , MEDIA);
	}
	
	/**
	 * Extract the result of a runner in a line.
	 * 
	 * @param html
	 */
	private static String getDataRunners(Document doc, String type) {
		 
		 StringBuilder sb = new StringBuilder();
		 
//		 System.out.println(doc.getElementsByClass("nombreCorredor").html());
		 String name = doc.getElementsByClass("nombreCorredor").html();
		 if(name!=null){
			name = EscapeHtml.unescapeHTML(name); 
		 }
		 sb.append(name).append(";");
		 
		 
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
			 //System.out.println(sb);
			 
			 // Puestos
			 Element datos1 = elementsByClass.get(1);
			 Elements ebt1 = datos1.getElementsByTag("td");
			 for (int i = 0; i <=2 ; i++) {
				 String field = ebt1.get(3+i).html();
				 if(field!=null && !"".equals(field) && field.contains("/")){
					 String[] ff = field.split("/");
					 if(ff[0]!=null){
						 sb.append(ff[0].trim()).append(";");
					 }else{
						 sb.append("").append(";");
					 }
					 
					 if(ff[1]!=null){
						 sb.append(ff[1].trim()).append(";");
					 }else{
						 sb.append("").append(";");
					 }
					 
 				 }
			 }
			 //System.out.println(sb);

			 
			 // Puestos de control
			 Element datos2 = elementsByClass.get(2);
			 Elements ebt2 = datos2.getElementsByTag("td"); 
			 int cell = 0;
			 if(type.equals(MARATON)){
				 cell = 59;
			 }else if (type.equals(MEDIA)) {
				 cell = 29;
			 }
			 
			 for (int j = 0; j <=cell; j++) {
				sb.append(ebt2.get(6+(j)).ownText()+ ";");
			 }
			 //System.out.println(sb);
		 }
		return sb.toString();
	}

    /**
     * Extrae listado de dorsales RnRMadrid 2015.
     * 
     * @param doc
     */
	public static void getBibs2015(Document doc, String fileName) {
		int j = 10; 
		for (int i = 0; i <199; i++) {
			try{
		         
		        MaraUtils.writeFileLine(fileName, doc.getElementsByTag("td").get(j).ownText()+";",true);
				j = j + 9;
			} catch(Exception e){
				System.out.println("Puesto no existe");
			}
		}
	}
	
	/**
	 * Remplaza los dorsales femeninos por su numero de url.
	 * 
	 * @param dorsal
	 * @return
	 */
	public static String replaceFamaleDorsalName(String dorsal) {

		int dorsalLength = dorsal.length();
		if(dorsalLength==2){
			dorsal = dorsal.replace("F", "10000");
		}else if (dorsalLength==3) {
			dorsal = dorsal.replace("F", "1000");
		}else if (dorsalLength==4) {
			dorsal = dorsal.replace("F", "100");
		}else if (dorsalLength==5) {
			dorsal = dorsal.replace("F", "10");
		}else if (dorsalLength==6) {
			dorsal = dorsal.replace("F", "1");
		}

        return dorsal;
	}
}
