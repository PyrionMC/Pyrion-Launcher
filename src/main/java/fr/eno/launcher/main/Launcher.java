package fr.eno.launcher.main;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import fr.eno.launcher.LauncherApp;
import fr.eno.launcher.Pyrion;
import fr.eno.launcher.auth.AuthPoints;
import fr.eno.launcher.auth.AuthenticationException;
import fr.eno.launcher.auth.Authenticator;
import fr.eno.launcher.auth.model.AuthAgent;
import fr.eno.launcher.auth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.Saver;

public class Launcher
{
	public final GameVersion MC_VERSION = new GameVersion("1.12.2", Pyrion.PYRION_TYPE);
	public final GameInfos MC_INFOS = new GameInfos("pyrion", MC_VERSION, new GameTweak[] { Pyrion.PYRION_TWEAK });
	public final File MC_DIR = MC_INFOS.getGameDir();
	public final File MC_CRASH_FOLDER = new File(MC_DIR, "crashes");
	private AuthInfos authInfos = null;
	public Saver saver = new Saver(new File(MC_DIR, "launcher.properties"));
	public AuthResponse authResponse = null;
	private String accessToken = null;

	public void launch() throws LaunchException, IOException, AuthenticationException
	{
		ExternalLaunchProfile elp = MinecraftLauncher.createExternalProfile(MC_INFOS, GameFolder.BASIC, authInfos);
		ExternalLauncher launcher = new ExternalLauncher(elp);
		elp.getVmArgs().add("-Djava.library.path=" + this.MC_DIR.getAbsolutePath() + "\\natives\\");
		launcher.launch();
	}

	public void launchCrack()
	{
		this.authInfos = new AuthInfos(LauncherApp.getUsername(), generateNewToken(), UUID.randomUUID().toString());

		try
		{
			ExternalLaunchProfile elp = MinecraftLauncher.createExternalProfile(MC_INFOS, GameFolder.BASIC, authInfos);
			ExternalLauncher launcher = new ExternalLauncher(elp);
			elp.getVmArgs().add("-Djava.library.path=" + this.MC_DIR.getAbsolutePath() + "\\natives\\");
			launcher.launch();
		} catch (LaunchException e)
		{
			e.printStackTrace();
		}
	}

	public void authAndLaunch() throws AuthenticationException, LaunchException, IOException
	{
		Authenticator auth = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse response = auth.authenticate(AuthAgent.MINECRAFT, LauncherApp.getUsername(), LauncherApp.getPassword(), "");

		this.authResponse = response;
		this.saver.set("username", LauncherApp.username.getText());
		this.accessToken = response.getAccessToken();
		this.authInfos = new AuthInfos(LauncherApp.username.getText(), this.accessToken, UUID.randomUUID().toString());

		// Authenticator auth = new Authenticator(Authenticator.MOJANG_AUTH_URL,
		// AuthPoints.NORMAL_AUTH_POINTS);
		// auth.validate(this.authResponse.getAccessToken());

		this.launch();
	}

	public Launcher()
	{
		if (!MC_CRASH_FOLDER.exists())
			MC_CRASH_FOLDER.mkdirs();

		if (!MC_DIR.exists())
			MC_DIR.mkdirs();

		File saver1 = new File(MC_DIR, "launcher.properties");

		if (!saver1.exists())
		{
			try
			{
				saver1.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void auth() throws AuthenticationException
	{
		this.authResponse = this.authentication(LauncherApp.username.getText(), LauncherApp.password.getText());
		this.saver.set("username", LauncherApp.username.getText());
		this.accessToken = this.authResponse.getAccessToken();
		this.authInfos = new AuthInfos(LauncherApp.username.getText(), this.accessToken, UUID.randomUUID().toString());

		Authenticator auth = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		auth.validate(this.accessToken);
	}

	public AuthResponse authentication(String username, String password) throws AuthenticationException
	{
		Authenticator auth = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse response = auth.authenticate(AuthAgent.MINECRAFT, username, password, "");
		return response;
	}

	private static final SecureRandom secureRandom = new SecureRandom(); // threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // threadsafe

	public static String generateNewToken()
	{
		byte[] randomBytes = new byte[357];
		secureRandom.nextBytes(randomBytes);
		return base64Encoder.encodeToString(randomBytes);
	}
}
