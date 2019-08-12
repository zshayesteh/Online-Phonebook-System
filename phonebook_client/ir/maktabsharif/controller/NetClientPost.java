package ir.maktabsharif.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import ir.maktabsharif.model.pojo.Role;

public class NetClientPost {
	private  URL url;
	private  String Ip;
	private  String port;
	private  String path;
	private  Object obj;
	private Role userRole;
	private String uname;
	private String pass;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public NetClientPost(String IP,String port,Object obj,String path, String uname, String pass) throws MalformedURLException{//with auth header
		setIp(IP);
		setPort(port);
		setObj(obj);
		setPath(path);
		setUname(uname);
		setPass(pass);
		setUrl(new URL("http://"+getIp()+":"+getPort()+"/phonebook_server/"+getPath()));
	}

	public NetClientPost(String IP,String port,Object obj,String path) throws MalformedURLException{//without auth header=guest
		setIp(IP);
		setPort(port);
		setObj(obj);
		setPath(path);
		setUrl(new URL("http://"+getIp()+":"+getPort()+"/phonebook_server/"+getPath()));
	}

	public String post(){
		try{
			HttpURLConnection conn = (HttpURLConnection) getUrl().openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty ("Authorization", userNamePasswordBase64(getUname(),getPass()));

			ObjectMapper mapper = new ObjectMapper();

			// Convert object to JSON string
			String input = mapper.writeValueAsString(getObj());

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			InputStream _is;
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				
				//showing entity of errors
				if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
				    _is = conn.getInputStream();
				} else {
				     /* error from server */
				    _is = conn.getErrorStream();
				}
				BufferedReader br = new BufferedReader(new InputStreamReader((_is)));
				String output="";
				String line;
				while ((line = br.readLine()) != null) {
					output=output+line;
				}
				
				
				String message= "Failed: error message: "+conn.getResponseMessage()+"_"+output;
				conn.disconnect();
				return message;
			}
			else{
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String output="";
				String line;
				while ((line = br.readLine()) != null) {
					output=output+line;
				}
				if(getObj().getClass().getName().equals("ir.maktabsharif.model.pojo.User") & getPath().equals("user/signin")){
					userRole = mapper.readValue(output, Role.class);
				}
				conn.disconnect();
				return null;
			}
		}catch(IOException e){
			return "Failed: invalid server IP or port!";
			//return e.getMessage() +" cause:"+ e.getCause();
		}
	}
	public static String userNamePasswordBase64(String username, String password) {
		return "Basic " + base64Encode (username + ":" + password);
	}

	private final static char base64Array [] = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/'
	};



	private static String base64Encode (String string)    {
		String encodedString = "";
		byte bytes [] = string.getBytes ();
		int i = 0;
		int pad = 0;
		while (i < bytes.length) {
			byte b1 = bytes [i++];
			byte b2;
			byte b3;
			if (i >= bytes.length) {
				b2 = 0;
				b3 = 0;
				pad = 2;
			}
			else {
				b2 = bytes [i++];
				if (i >= bytes.length) {
					b3 = 0;
					pad = 1;
				}
				else
					b3 = bytes [i++];
			}
			byte c1 = (byte)(b1 >> 2);
			byte c2 = (byte)(((b1 & 0x3) << 4) | (b2 >> 4));
			byte c3 = (byte)(((b2 & 0xf) << 2) | (b3 >> 6));
			byte c4 = (byte)(b3 & 0x3f);
			encodedString += base64Array [c1];
			encodedString += base64Array [c2];
			switch (pad) {
			case 0:
				encodedString += base64Array [c3];
				encodedString += base64Array [c4];
				break;
			case 1:
				encodedString += base64Array [c3];
				encodedString += "=";
				break;
			case 2:
				encodedString += "==";
				break;
			}
		}
		return encodedString;
	}
}
