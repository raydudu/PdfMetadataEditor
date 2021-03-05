package pmedit;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version {
    protected static String version;
    public static class VersionTuple {
    	public int major;
    	public int minor;
    	public int patch;
    	public String tag = "";
    	public boolean parseSuccess = false;
    	
    	
    	public  VersionTuple(String version) {
    		this(version, "^(\\d+)\\.(\\d+)\\.(\\d+)-?(\\S*)$");
    	}
    	public VersionTuple(String version, String pattern) {
    		Matcher matcher = Pattern.compile(pattern).matcher(version);
    		if(matcher.find()){
    			major = Integer.parseInt(matcher.group(1));
    			minor = Integer.parseInt(matcher.group(2));
    			patch = Integer.parseInt(matcher.group(3));
    			if( matcher.groupCount() > 3){
	    			tag = matcher.group(4);
	    			if( tag == null){
	    				tag = "";
	    			}
    			}
    			parseSuccess = true;
    		} else {
    			tag = "dev";
    		}
    	}

		public String getAsString(){
    		return major + "." +
					minor + "." +
					patch + ((tag.length() >0) ? ("-" + tag) : "");
    	}
    }

	public static VersionTuple get() {
    	if( version == null){
        	Properties prop = new Properties();
    		try {
				prop.load(VersionTuple.class.getClassLoader().getResourceAsStream("pmedit/version.properties"));
				version = prop.getProperty("app.version", "0.0.0-dev");
				System.out.println("Version " + version);
			} catch (IOException e) {
				e.printStackTrace();
				version = "0.0.0-dev";
			}
    	}
    	return new VersionTuple( version );
    }
}
