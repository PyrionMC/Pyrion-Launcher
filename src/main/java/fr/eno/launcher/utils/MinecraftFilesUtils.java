package fr.eno.launcher.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.eno.launcher.LauncherApp;
import fr.eno.launcher.ProgressBar;
import fr.eno.launcher.main.Launcher;

public class MinecraftFilesUtils
{
	public static double versionLibraries = 0;
	public static double versionDownloaded = 0;
	
	public static void readJsonVersionFile()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				MCFilesUtils.getNativesFiles();
				MCFilesUtils.getForgeJar();
				
				Launcher launcher = new Launcher();
				System.out.println("Checking Version Libraries...");
				LauncherApp.labelText = "Checking Version Libraries...";
				
				JsonParser parser = new JsonParser();
				
		        try
		        {
		            JsonObject obj = parser.parse(new FileReader(MCFilesUtils.getJsonFileByName("1.12.2.json", launcher))).getAsJsonObject();
		            JsonArray libs = (JsonArray) obj.get("libraries");
		            versionLibraries = libs.size();
		            
		            int i = 0;
		            for(@SuppressWarnings("unused") JsonElement libraries : libs)
		            {
		            	if(i <= libs.size())
		            	{
		            		JsonObject download = libs.get(i).getAsJsonObject().get("downloads").getAsJsonObject();
		                	
		                	if(download.has("artifact"))
		                	{
		                		i++;
		                    	versionDownloaded++;
		                    	
		                		JsonObject artifact = download.get("artifact").getAsJsonObject();
		                    	String url = artifact.getAsJsonObject().get("url").toString().replace("\"", "");
		                    	String path = artifact.getAsJsonObject().get("path").toString().replace("\"", "");
		                    	int size = Integer.valueOf(artifact.getAsJsonObject().get("size").toString());
		                    	
		                    	getVersionFileByName(url, path, launcher, size); 
		                	}
		                	else
		                	{
		                		i++;
		                		versionDownloaded++;
		                		
		                		JsonObject classifiers = download.get("classifiers").getAsJsonObject();
		                		JsonObject natives = classifiers.get("natives-windows").getAsJsonObject();
		                		String url = natives.get("url").toString();
		                		String path = natives.get("path").toString();
		                		int size = Integer.valueOf(natives.get("size").toString());
		                		
		                		getVersionFileByName(url, path, launcher, size);
		                	}
		            	}            	        	
		            }
		            
		            System.out.println("Version Libraries Successfully Checked !");
		            
		            ForgeFilesUtils.readJsonForgeFile();
		            ProgressBar.forgeProgressBar();
		        }
		        catch (Exception e)
		        {
		            e.printStackTrace();
		        }
			}
		});
		t.start();
	}
	
	public static void getVersionFileByName(String url1, String path1, Launcher l, int size) throws MalformedURLException, IOException
	{
		String url = url1.replace("\"", "");
		String path = path1.replace("\"", "");
		
		String[] paths = path.split("/");
		
		String jarName = paths[paths.length - 1];
		File folder = new File(l.MC_DIR + "\\libs\\");
		folder.mkdirs();
		File finalFile = new File(folder, jarName);
		
		System.out.println((int) versionDownloaded + " / " + (int) versionLibraries + " Version Libraries");
		
		if(!finalFile.exists() || finalFile.length() != size)
		{
			FileUtils.copyURLToFile(new URL(url), finalFile);
		}
	}
}
