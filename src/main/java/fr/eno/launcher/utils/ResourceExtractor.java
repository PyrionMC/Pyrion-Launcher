package fr.eno.launcher.utils;

/**
 * A helper to copy resources from a JAR file into a directory.
 */
public final class ResourceExtractor
{
	/*
	public void extractFromJAR(String dest)
	{
	     try
	     {
	      String home = getClass().getProtectionDomain().
	                    getCodeSource().getLocation().toString().
	                    substring(6);
	      JarFile jar = new JarFile(home);
	      ZipEntry entry = jar.getEntry("mydb.mdb");
	      File efile = new File(dest, entry.getName());

	      InputStream in =
	         new BufferedInputStream(jar.getInputStream(entry));
	      OutputStream out =
	         new BufferedOutputStream(new FileOutputStream(efile));
	      byte[] buffer = new byte[2048];
	      for (;;)  {
	        int nBytes = in.read(buffer);
	        if (nBytes <= 0) break;
	        out.write(buffer, 0, nBytes);
	      }
	      out.flush();
	      out.close();
	      in.close();
	     }
	     catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	  */
}
