package LightBot.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import LightBot.cases.*;

public class Parser {
	
	private Document doc;
	private Niveau n;
	
	public static void main(String[] args) {
		Parser p = new Parser("src/LightBot/levels/Niveau.xml");
		p.lire();
		p.getNiveau().getTerrain().affiche();
	}
	
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
		
	    
	    for (Node noeud: getChildren(racine)){
			System.out.println("Noeuds : "+noeud.getNodeName());
			switch(noeud.getNodeName()){
			case "terrain" :
				System.out.println("Nous sommes dans terrain");
				int lar = getIntNodeAttribute(noeud, "largeur");
				int lon = getIntNodeAttribute(noeud, "longueur");
				Terrain t = new Terrain(lar, lon);
				for(Node childTerrain : getChildren(noeud)){
		    		System.out.println("Sous-noeud : "+childTerrain.getNodeName());
		    		switch(childTerrain.getNodeName()){
		    		case "nbActionsPossible" :
		    			System.out.println("Actions possibles = "+childTerrain.getTextContent());
		    			t.setNbActionsPossible( getNodeTextToInt(childTerrain));
		    			break;
		    		case "ensembleDeCase" :
		    			Case[][] tableau = new Case[lar][lon];
		    			for(Node noeudCase:getChildren(childTerrain)){
	    		    		System.out.println("Sous-Sous-Noeuds : "+noeudCase.getNodeName());
	    		    		int x = getIntNodeAttribute(noeudCase, "x");
    		    			int y = getIntNodeAttribute(noeudCase, "y");
    		    			int h = getIntNodeAttribute(noeudCase, "h");
	    		    		switch(getNodeAttribute(noeudCase,"type")){
	    		    		case "normal":
	    		    			tableau[x][y] = new Normal(h);
	    		    			break;
	    		    		case "lampe" :
	    		    			tableau[x][y] = new Lampe(h);
	    		    			break;
	    		    		default :
	    		    			break;
	    		    		}
		    			}
		    			t.setEnsembleDeCase(tableau);
		    			n.setTerrain(t);
		    			break;
		    		default : break;
		    		}
				}
				this.n.setTerrain(t);
				break;
			case "personnes" :
				System.out.println("Nous sommes dans personnes");
				break;
			case "programmes" :
				System.out.println("Nous sommes dans programmes");
				break;
			case "actions" :
				System.out.println("Nous sommes dans actions");
				break;
			default :
				System.out.println("Nous sommes dans default");
				break;
			}	
	    }
	}
	
	public void ecrire(){
		
	}
	
	public Niveau getNiveau(){
		return this.n;
	}
	
	private int getIntNodeAttribute(Node noeud, String attr){
		try{
			return Integer.parseInt(getNodeAttribute(noeud, attr));
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}		
	}
	
	private String getNodeAttribute(Node noeud, String attr){
		return ((Element)noeud).getAttribute(attr);
	}
	
	private int getNodeTextToInt(Node noeud){
		return Integer.parseInt(noeud.getTextContent());
	}
	
	private ArrayList<Node> getChildren(Node noeud){
		ArrayList<Node> nodes=new ArrayList<Node>();
		NodeList children=noeud.getChildNodes();
		for (int i = 0; i<children.getLength(); i++) {
	    	if(children.item(i).getNodeType() == Node.ELEMENT_NODE) 
	    		nodes.add(children.item(i));
		}
	    return nodes;
	}

}
