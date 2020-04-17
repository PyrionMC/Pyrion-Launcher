package fr.eno.launcher.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.eno.launcher.LauncherApp;
import fr.eno.launcher.ProgressBar;
import fr.eno.launcher.main.Launcher;

public class ObjectsUtils
{
	public static double objectDownloaded = 0;
	public static double objectLibraries = ObjectsList.objectLibraries;
	
	public static void getObjectsFile()
	{		
		Thread t = new Thread(new Runnable()
		{
			@SuppressWarnings("static-access")
			@Override
			public void run()
			{
				getClientJar();
				getIndexFile();
				
				ObjectsList list = new ObjectsList();
				String mcUrl = "http://resources.download.minecraft.net/";
				Launcher l = new Launcher();
				File folder = new File(l.MC_DIR, "assets/objects/");
				folder.mkdirs();
				System.out.println("Checking Objects...");
				LauncherApp.labelText = "Checking Objects...";
				
				try
				{
					JsonParser parser = new JsonParser();
					
					JsonObject obj = parser.parse(new FileReader(MCFilesUtils.getJsonFileByName("1.12.json", l))).getAsJsonObject();
			        JsonArray objects = (JsonArray) obj.get("objects");
			        //System.out.println("Objects > " + objects);
			        
			        for(int u = 0; u < list.objects.length; u++)
			        {
			        	System.out.println((int) u + " / " + (int) list.objectLibraries + " Objects");
			        	
			        	if(list.objects[u] != null)
			        	{
			        		objectDownloaded = (list.objects.length -(list.objects.length - u));
			        		
			        		JsonObject object = objects.get(0).getAsJsonObject().get(list.objects[u]).getAsJsonObject();
				        	String hash1 = object.get("hash").toString();
				        	String size = object.get("size").toString();
				        	String hash = hash1.replace("\"", "");
				        	
				        	String hashBegin = "" + hash.charAt(0) + hash.charAt(1);
				        	String finalURL = mcUrl + hashBegin + "/" + hash;
				        	String finalPath = folder.getAbsolutePath() + "\\" + hashBegin + "\\" + hash;
				        	File create = new File(folder, folder.getAbsolutePath() + "\\" + hashBegin);
				        	create.mkdirs();
				        	
				        	File finalFile = new File(finalPath);
				        	
				        	int fileSize = (int) finalFile.length();
				        	int jarSize = Integer.valueOf(size);
				        	
				        	if(!finalFile.exists() || fileSize != jarSize)
				        	{
				        		FileUtils.copyURLToFile(new URL(finalURL), finalFile);
				        	}
			        	}
			        	else
			        	{}
			        }
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
				MinecraftFilesUtils.readJsonVersionFile();
				ProgressBar.versionProgressBar();
			}
		});
		t.start();
	}
	
	private static void getClientJar()
	{
		System.out.println("Checking Minecraft Jar...");
		LauncherApp.labelText = "Checking Minecraft Jar...";
		String clientUrl = "https://launcher.mojang.com/v1/objects/0f275bc1547d01fa5f56ba34bdc87d981ee12daf/client.jar";
		Launcher launch = new Launcher();
		File finalFile = new File(launch.MC_DIR, "minecraft.jar");
		try
		{
			if(!finalFile.exists() || (int) finalFile.length() != 10180113)
				FileUtils.copyURLToFile(new URL(clientUrl), new File(launch.MC_DIR, "minecraft.jar"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Minecraft Jar successfully Checked !");
	}
	
	private static void getIndexFile()
	{
		System.out.println("Checking Index File...");
		LauncherApp.labelText = "Checking Index File...";
		String url = "https://launchermeta.mojang.com/v1/packages/1584b57c1a0b5e593fad1f5b8f78536ca640547b/1.12.json";
		Launcher l = new Launcher();
		File folder = new File(l.MC_DIR, "assets/indexes/");
		folder.mkdirs();
		
		File json = new File(folder, "1.12.json");
		
		if(!json.exists())
		{
			try
			{
				FileUtils.copyURLToFile(new URL(url), json);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("Index File Successfully Checked !");
	}
}
