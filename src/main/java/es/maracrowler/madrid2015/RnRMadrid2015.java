package es.maracrowler.madrid2015;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RnRMadrid2015 {
	
	/**
	 * Extract the result of a runner in a line.
	 * 
	 * @param html
	 */
	public static String getDataStringBuilder(String html) {
		Document doc = Jsoup.parseBodyFragment(html);
		 
		 StringBuilder sb = new StringBuilder();
		 
//		 System.out.println(doc.getElementsByClass("nombreCorredor").html());
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
		return sb.toString();
	}
}
