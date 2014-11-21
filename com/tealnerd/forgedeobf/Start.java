package com.TealNerd.forgedeobf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Start {

	private static HashMap<String, String> fields = new HashMap<String, String>();
	private static HashMap<String, String> methods = new HashMap<String, String>();
	private static final File directory = new File(System.getProperty("user.dir"));
	static File in = new File(directory, "/modfiles");
	static File out = new File(directory, "/ouput");
	
	public static void main(String[] args) throws Exception {
		try {
			GetFields();
			GetMethods();
			
			fixFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static ArrayList<String> readFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> lines = new ArrayList<String>();
        try {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return lines;
    }
	
	public static void fixFiles() throws Exception {
		Set<String> fieldKeys = fields.keySet();
		Set<String> methodKeys = methods.keySet();
		File[] modFiles = directory.listFiles();
		for(File f : modFiles) {
			String name = f.getName();
			if(name.contains(".java")) {
				System.out.println("Beggining fix for " + name);
				ArrayList<String> lines = readFile(f);
				f.delete();
				f.createNewFile();
				FileWriter fw = new FileWriter(f);
				for(String s : lines) {
					for(String fkey : fieldKeys) {
						if(s.contains(fkey)) {
							s = s.replace(fkey, fields.get(fkey));
						}
					}
					for(String fkey : methodKeys) {
						if(s.contains(fkey)) {
							s = s.replace(fkey, methods.get(fkey));
						}
					}
					fw.write(s);
					fw.write("\n");
				}
				fw.close();
				System.out.println("Succesfully wrote " + name);
			}
		}
	}
	
	public static void GetFields() throws IOException, FileNotFoundException{   			
    			//Get perps in file
    			URL url;
    			InputStream is = null;
    			BufferedReader br;
    			String line;    			

    			try{

    				url = new URL("https://raw.githubusercontent.com/MinecraftForge/FML/master/conf/fields.csv");

    				is = url.openStream();

    				br = new BufferedReader(new InputStreamReader(is));

    				

    				while((line = br.readLine()) != null){
    						String[] fieldLine = line.split(",");
    						fields.put(fieldLine[0], fieldLine[1]);
    				}

    			} catch (MalformedURLException mue) {

    		         mue.printStackTrace();

    		    } catch (IOException ioe) {

    		         ioe.printStackTrace();

    		    } finally {

    		        try {

    		            if (is != null) is.close();

    		        } catch (IOException ioe) {

    		            // nothing to see here

    		        }

    		}		
    	}
    
	public static void GetMethods() throws IOException, FileNotFoundException{   			
		//Get perps in file
		URL url;
		InputStream is = null;
		BufferedReader br;
		String line;    			

		try{

			url = new URL("https://raw.githubusercontent.com/MinecraftForge/FML/master/conf/methods.csv");

			is = url.openStream();

			br = new BufferedReader(new InputStreamReader(is));

			

			while((line = br.readLine()) != null){
					String[] methodLine = line.split(",");
					methods.put(methodLine[0], methodLine[1]);
			}

		} catch (MalformedURLException mue) {

	         mue.printStackTrace();

	    } catch (IOException ioe) {

	         ioe.printStackTrace();

	    } finally {

	        try {

	            if (is != null) is.close();

	        } catch (IOException ioe) {

	            // nothing to see here

	        }

	}		
}
}
