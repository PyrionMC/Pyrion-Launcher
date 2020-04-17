package fr.eno.launcher.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import fr.eno.launcher.LauncherApp;
import fr.eno.launcher.main.Launcher;

public class MCFilesUtils
{
	public static String[] nativesName = {"jinput-dx8.dll", "jinput-dx8_64.dll", "jinput-raw.dll", "jinput-raw_64.dll", "libjinput-linux.so", "libjinput-linux64.so", "libjinput-osx.dylib", "liblwjgl.dylib", "liblwjgl.so", "liblwjgl64.so", "libopenal.so", "libopenal64.so", "lwjgl.dll", "lwjgl64.dll", "openal.dylib", "OpenAL32.dll", "OpenAL64.dll", "SAPIWrapper_x64.dll", "SAPIWrapper_x86.dll"};
	
	public static void getNativesFiles()
	{
		Launcher launcher = new Launcher();
		
		getNativesFileByName(launcher);
	}
	
	public static void getNativesFileByName(Launcher launcher)
	{
		File folder = new File(launcher.MC_DIR + "\\natives\\");
		System.out.println("Checking Natives Files...");
		LauncherApp.labelText = "Checking Natives Files...";
		
		for(String nativeName : nativesName)
		{
			String nativePath = ResourceManager.extract("/fr/eno/launcher/natives/" + nativeName);
			File tempNativeFile = new File(nativePath);
			File finalNativeFile = new File(folder, nativeName);
			
			try
			{
				org.apache.commons.io.FileUtils.copyFile(tempNativeFile, finalNativeFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Successfully Checked Natives Files !");
	}
	
	public static void getForgeJar()
	{
		Launcher launcher = new Launcher();
		System.out.println("Checking Forge Jar...");
		LauncherApp.labelText = "Checking Forge Jar...";
		
		File folder = new File(launcher.MC_DIR + "\\libs\\");
		String forgeUrl = "https://files.minecraftforge.net/maven/net/minecraftforge/forge/1.12.2-14.23.5.2847/forge-1.12.2-14.23.5.2847-universal.jar";
		File finalForgeJar = new File(folder, "forge-1.12.2-14.23.5.2847.jar");
		
		if(!finalForgeJar.exists() || finalForgeJar.length() != 4884700)
		{
			try
			{
				FileUtils.copyURLToFile(new URL(forgeUrl), finalForgeJar);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("Successfully Checked Forge Jar at : " + finalForgeJar.getAbsolutePath());
	}
	
	public static File getJsonFileByName(String jsonName, Launcher launcher)
	{
		File folder = new File(launcher.MC_DIR, "assets\\indexes");
		
		String jsonPath = ResourceManager.extract("/fr/eno/launcher/json/" + jsonName);
		File tempJsonFile = new File(jsonPath);
		File finalJsonFile = new File(folder, jsonName);
		
		try
		{
			org.apache.commons.io.FileUtils.copyFile(tempJsonFile, finalJsonFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return finalJsonFile;	
	}
}
