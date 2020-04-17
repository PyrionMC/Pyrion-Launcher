package fr.eno.launcher;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;

public class Pyrion
{
	public static final GameTweak PYRION_TWEAK = new GameTweak() {
		
		@Override
		public String getTweakClass(GameInfos infos)
		{
			return "net.minecraftforge.fml.common.launcher.FMLTweaker";
		}
		
		@Override
		public String getName()
		{
			return "FML Tweaker";
		}
	};
	
	
	public static final GameType PYRION_TYPE = new GameType()
	{		
		@Override
		public String getName()
		{
			return "Pyrion Game Type";
		}
		
		@Override
		public String getMainClass(GameInfos infos)
		{
			return "net.minecraft.launchwrapper.Launch";
		}
		
		@Override
		public ArrayList<String> getLaunchArgs(GameInfos infos, GameFolder folder, AuthInfos authInfos)
		{
            ArrayList<String> arguments = new ArrayList<String>();

            arguments.add("--username=" + authInfos.getUsername());

            arguments.add("--accessToken");
            if(LauncherApp.isCrack)
            {
            	arguments.add(UUID.randomUUID().toString().replace("-", ""));
            }
            else
            {
            	arguments.add(authInfos.getAccessToken());
            }
            

            if (authInfos.getClientToken() != null)
            {
                arguments.add("--clientToken");
                arguments.add(authInfos.getClientToken());
            }

            arguments.add("--version");
            arguments.add(infos.getGameVersion().getName());

            arguments.add("--gameDir");
            arguments.add(infos.getGameDir().getAbsolutePath());

            arguments.add("--assetsDir");
            File assetsDir = new File(infos.getGameDir(), folder.getAssetsFolder());
            arguments.add(assetsDir.getAbsolutePath());

            arguments.add("--assetIndex");

            String version = infos.getGameVersion().getName();

            int first = version.indexOf('.');
            int second = version.lastIndexOf('.');

            if (first != second)
            {
                version = version.substring(0, version.lastIndexOf('.'));
            }

            arguments.add(version);

            arguments.add("--userProperties");
            arguments.add("{}");
            
            if(!LauncherApp.isCrack)
            {
            	arguments.add("--uuid");
                arguments.add(authInfos.getUuid());

                arguments.add("--userType");
                arguments.add("legacy");
            }
            else
            {
            	arguments.add("--uuid");
                arguments.add(UUID.randomUUID().toString());

                arguments.add("--userType");
                arguments.add("plain");
            }
            

            return arguments;
        }
	};

}
