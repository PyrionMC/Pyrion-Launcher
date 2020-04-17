package fr.eno.launcher;

import fr.eno.launcher.utils.ForgeFilesUtils;
import fr.eno.launcher.utils.MinecraftFilesUtils;
import fr.eno.launcher.utils.ObjectsUtils;

public class ProgressBar
{	
	public static void versionProgressBar()
	{
		LauncherApp.bar.setProgress(0d);
		
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				boolean run = true;				
				
				while(run)
				{
					double dl = MinecraftFilesUtils.versionDownloaded;
					double libs = MinecraftFilesUtils.versionLibraries;
					LauncherApp.bar.setProgress(dl / libs);
					
					if((int) MinecraftFilesUtils.versionDownloaded == (int) MinecraftFilesUtils.versionLibraries)
					{
						run = false;
					}
				}
			}
		});		
		t.start();
	}
	
	public static void forgeProgressBar()
	{
		LauncherApp.bar.setProgress(0d);
		
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				boolean run = true;				
				
				while(run)
				{
					double dl = ForgeFilesUtils.forgeDownloaded;
					double libs = ForgeFilesUtils.forgeLibraries;
					LauncherApp.bar.setProgress(dl / libs);
					
					if((int) ForgeFilesUtils.forgeDownloaded == (int) ForgeFilesUtils.forgeLibraries)
					{
						run = false;					
					}
				}
			}
		});		
		t.start();
	}
	
	public void objectsProgressBar()
	{
		LauncherApp.bar.setVisible(true);
		LauncherApp.bar.setProgress(0d);
		
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				boolean run = true;				
				
				while(run)
				{
					double dl = ObjectsUtils.objectDownloaded;
					double libs = ObjectsUtils.objectLibraries;
					LauncherApp.bar.setProgress(dl / libs);
					
					if((int) ObjectsUtils.objectDownloaded == (int) ObjectsUtils.objectLibraries)
					{
						run = false;
					}
				}
			}
		});		
		t.start();
	}
}