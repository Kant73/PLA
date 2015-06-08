package LightBot.parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import LightBot.Niveau;
import LightBot.Terrain;

public class Parser {
	
	public static void main(String[] args) {
		Parser p = new Parser("src/LightBot/levels/Niveau.xml");
		p.lire();
	}
	
	private Document doc;
	private Niveau n;
	
	/* 
	 * créer le document à partir du nom du fichier
	 */
	public Parser(String fichier){
		this.n = new Niveau();
		try{
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder(); // création d'un parseur
			this.doc = builder.parse(new File(fichier)); // création d'un Document
		}catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lire(){
		final Element racine = this.doc.getDocumentElement(); // récupération de l'Element racine
		
	    // Affichage de l'élément racine
	    System.out.println("\n*************RACINE************");
	    System.out.println(racine.getNodeName());
		
	    final NodeList racineNoeuds = racine.getChildNodes(); // récupération des personnes
	    final int nbRacineNoeuds = racineNoeuds.getLength();
		
	    System.out.println("NB = "+nbRacineNoeuds);
	    
	    for (int i = 0; i<nbRacineNoeuds; i++) {
	    	Node noeud = racineNoeuds.item(i);
	    	if(noeud.getNodeType() == Node.ELEMENT_NODE) {
	    		System.out.println("Noeuds : "+noeud.getNodeName());
	    		switch(noeud.getNodeName()){
	    		case "terrain" :
	    			System.out.println("Nous sommes dans terrain");
	    			Element elt = (Element)noeud;
	    			int lar = Integer.parseInt(elt.getAttribute("largeur"));
	    			int lon = Integer.parseInt(elt.getAttribute("longueur"));
	    			Terrain t = new Terrain(lar, lon);
	    			NodeList childTerrain = noeud.getChildNodes();
	    			for(int j=0; j<childTerrain.getLength(); j++){
	    				noeud = childTerrain.item(j);
	    		    	if(noeud.getNodeType() == Node.ELEMENT_NODE) {
	    		    		System.out.println(childTerrain.item(j).getNodeName());
	    		    	}
	    			}
	    			this.n.setTerrain(t);
	    			break;
	    		case "personnes" :
	    			break;
	    		case "programmes" :
	    			break;
	    		case "actions" :
	    			break;
	    		default :
	    			System.out.println("Nous sommes dans default");
	    			break;
	    		}
	        }
	    }	    
	}
	
	public void ecrire(){
		
	}
	
	public Niveau getNiveau(){
		return this.n;
	}

}
