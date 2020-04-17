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
import fr.eno.launcher.auth.AuthenticationException;
import fr.eno.launcher.main.Launcher;
import fr.theshark34.openlauncherlib.LaunchException;

public class ForgeFilesUtils
{
	public static double forgeLibraries = 0;
	public static double forgeDownloaded = 0;
	
	public static void readJsonForgeFile()
	{
		Launcher launcher = new Launcher();
		System.out.println("Checking Forge Libraries...");
		LauncherApp.labelText = "Checking Forge Libraries...";
		
		JsonParser parser = new JsonParser();
        try
        {
            JsonObject obj = parser.parse(new FileReader(MCFilesUtils.getJsonFileByName("forge.json", launcher))).getAsJsonObject();
            JsonArray libs = (JsonArray) obj.get("libraries");
            forgeLibraries = libs.size();                   
            
            int i = 0;
            for(@SuppressWarnings("unused") JsonElement object : libs)
            {
            	if(i < libs.size())
            	{
            		String name = libs.get(i).getAsJsonObject().get("name").toString();
                    
                    if(libs.get(i).getAsJsonObject().get("url") != null)
                    {                            	
                    	forgeDownloaded++;
                    	
                    	getForgeFileByName(name, launcher);
                    }
                    else
                    {
                    	forgeDownloaded++;
                    	
                    	getMavenFileByName(name, launcher);
                    }
                    
                    i++;
            	}
            }
            
            System.out.println("Forge Libraries Successfully Checked !");
            
            launchAfter();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	private static void launchAfter()
	{
		Launcher launcher = new Launcher();
		
		if(!LauncherApp.isCrack)
		{
			try
			{
				launcher.authAndLaunch();
				System.out.println("Account : Premium");
			}
			catch (AuthenticationException | LaunchException | IOException e)
			{
				e.printStackTrace();
			}
			
			LauncherApp.setClosed = true;
		}
		else
		{
			launcher.launchCrack();
			System.out.println("Account : Crack");
			LauncherApp.setClosed = true;
		}
	}
	
	
	
	public static void getMavenFileByName(String repoName, Launcher l) throws MalformedURLException, IOException
	{
		try
		{
			String name = repoName.replace("\"", "");
			
			String maven = null;
			
			String[] names = name.split(":");
			
			String dir = names[0];
			//System.out.println(name);
			String mc = "net.minecraft:launchwrapper:1.12";
			//System.out.println(mc);
			if(name.equalsIgnoreCase(mc))
			{
				maven = "https://oblivion-repo.com/modpacks/libraries/";
			}
			else if(name.equalsIgnoreCase("lzma:lzma:0.0.1"))
			{
				maven = "https://repo.spongepowered.org/maven/";
			}
			else if(name.equalsIgnoreCase("java3d:vecmath:1.5.2"))
			{
				maven = "https://maven.geotoolkit.org/";
			}
			else
			{
				 maven = "https://repo.maven.apache.org/maven2/";
			}
			String dir2 = dir.replace(".", ":");
			String[] dir1 = dir2.split(":");
			//System.out.println(dir1.length);
			String finalDir = null;
			if(dir1.length == 4)
			{
				finalDir = dir1[0] + "/" + dir1[1] + "/" + dir1[2] + "/" + dir1[3] + "/";
			}
			else if(dir1.length == 3)
			{
				finalDir = dir1[0] + "/" + dir1[1] + "/" + dir1[2] + "/";
			}
			else if(dir1.length == 2)
			{
				if(dir == "org.scala-lang")
				{
					finalDir = dir1[0] + "/" + dir1[1] + "/" + "modules" + "/";
				}
				else
				{
					finalDir = dir1[0] + "/" + dir1[1] + "/";
				}
			}
			else if(dir1.length == 1)
			{
				finalDir = dir1[0] + "/";
			}
			
			String dirName = names[1];
			String version = names[2];
	        String jarName = names[1] + "-" + names[2] + ".jar";
	        
	        String finalStringUrl = maven + finalDir + dirName + "/" + version + "/" + jarName;
	       // System.out.println(finalStringUrl);
	        File folder = new File(l.MC_DIR + "\\libs\\");
			folder.mkdirs();
			File finalFile = new File(folder, jarName);
			
			System.out.println((int) forgeDownloaded + " / " + (int) forgeLibraries + " Forge Libraries");
	        //System.out.println(finalStringUrl);
			
			if(!finalFile.exists())
			{
				 FileUtils.copyURLToFile(new URL(finalStringUrl), finalFile, 100000, 100000);       
			     //System.out.println(new File(folder, jarName).getAbsolutePath());
			}
			else
			{
				if(name.equalsIgnoreCase("java3d:vecmath:1.5.2") && finalFile.length() != 318956)
				{
					FileUtils.copyURLToFile(new URL(finalStringUrl), finalFile);
				}
				else if(name.equalsIgnoreCase("org.ow2.asm:asm-all:5.2") && finalFile.length() != 247787)
				{
					FileUtils.copyURLToFile(new URL(finalStringUrl), finalFile);					
				}			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void getForgeFileByName(String repoName, Launcher l) throws MalformedURLException, IOException
	{
		try
		{
			String maven = "http://files.minecraftforge.net/maven/";
			
			String name = repoName.replace("\"", "");
			
			
			String[] names = name.split(":");
			
			String dir = names[0];
			String dir2 = dir.replace(".", ":");
			String[] dir1 = dir2.split(":");
			String finalDir = null;
			if(dir1.length == 4)
			{
				finalDir = dir1[0] + "/" + dir1[1] + "/" + dir1[2] + "/" + dir1[3] + "/";
			}
			else if(dir1.length == 3)
			{
				finalDir = dir1[0] + "/" + dir1[1] + "/" + dir1[2] + "/";
			}
			else if(dir1.length == 2)
			{
				finalDir = dir1[0] + "/" + dir1[1] + "/";			
			}
			
			String dirName = names[1];
			String version = names[2];
	        String jarName = names[1] + "-" + names[2] + ".jar";
	        
	        String finalStringUrl = null;
	        if(name.equalsIgnoreCase("com.typesafe.akka:akka-actor_2.11:2.3.3") || name.equalsIgnoreCase("com.typesafe:config:1.2.1"))
	        {
	        	finalStringUrl = "https://repo.maven.apache.org/maven2/" + finalDir + dirName + "/" + version + "/" + jarName;
	        }
	        else
	        {
	        	finalStringUrl = maven + finalDir + dirName + "/" + version + "/" + jarName;
	        }
	        
	        System.out.println((int) forgeDownloaded + " / " + (int) forgeLibraries + " Forge Libraries");        
	        
	        File folder = new File(l.MC_DIR + "\\libs\\");
			folder.mkdirs();
			File finalFile = new File(folder, jarName);
			
			if(!finalFile.exists())
			{
				FileUtils.copyURLToFile(new URL(finalStringUrl), finalFile);        
		        //System.out.println(new File(folder, jarName).getAbsolutePath());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
}
