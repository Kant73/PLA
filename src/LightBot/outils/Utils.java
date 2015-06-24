package LightBot.outils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class Utils {
	
	public static void main(String[] pArgs){
		System.out.println(Utils.getPath("LightBot/Img/case0.png"));
	}
	
	public static String getStringFullPath(String relativeFilename){
		String pathFilename;
		try{
			pathFilename = Utils.class.getClassLoader().getResource(relativeFilename).getPath();
			pathFilename=pathFilename.substring(pathFilename.lastIndexOf('!')+1);// Récupération du path qui commence à la racine du jar
			if (pathFilename.startsWith("/")) pathFilename= pathFilename.substring(1, pathFilename.length()); //Transforme en chemin relatif
			pathFilename= pathFilename.replace("%20", " ").replace("\\", "/");
			return pathFilename;
		}catch(NullPointerException e){
			return "";
		}
	}
	
	public static String getStringPath(String relativeFilename){
		String pathFilename=Utils.getStringFullPath(relativeFilename);
		if(pathFilename.length()-relativeFilename.length()>0)pathFilename=pathFilename.substring(pathFilename.length()-relativeFilename.length());
		return pathFilename;
	}
	
	
	public static Path getPath(String relativeFilename){
		return Paths.get(Utils.getStringPath(relativeFilename));
	}
	
	public static InputStream getInputStream(String path){
		return Utils.class.getClassLoader().getResourceAsStream(Utils.getStringPath(path));
	}
	
	public static ArrayList<String> getListFiles(String path){
    	final File jarFile = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " "));
    	ArrayList<String> listFiles=new ArrayList<String>();
    	try {
	    	if(jarFile.isFile()) { //Lancement à partir d'un fichier jar
	    	    JarFile jar = new JarFile(jarFile);				
	    	    final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
	    	    while(entries.hasMoreElements()) {
	    	        final String name = entries.nextElement().getName();
	    	        if (name.startsWith(path) && path.length()!=name.length()) listFiles.add(name.replace("\\","/"));
	    	    }
	    	    jar.close();
	    	}else{ // Lancement avec IDE
	    	    final URL url = Utils.class.getResource("/" + path.replace("%20", " "));
	    	    if (url != null) {
	    	        try {
	    	            final File apps = new File(url.toURI());
	    	            for (File app : apps.listFiles()) {
	    	            	String nomFichier=app.toPath().toString().replace("\\", "/");
	    	                listFiles.add(nomFichier.substring(nomFichier.lastIndexOf("/")-path.length()+1));
	    	            }
	    	        } catch (URISyntaxException ex) {}
	    	    }
	    	}    	
    	} catch (IOException e) {}
    	if(listFiles.size()!=0)Collections.sort(listFiles);
    	return listFiles;
    }
}
