package ir.maktabsharif.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ir.maktabsharif.model.pojo.Contact;
import ir.maktabsharif.model.pojo.ContactLiteDTO;
import ir.maktabsharif.model.pojo.Role;

public class NetClientGet {

	private  URL url;
	private  String Ip;
	private  String port;
	private  String path;
	private Role userRole;
	private Contact contact;
	private List<ContactLiteDTO> liteContactList;
	private List<Contact> fullContactList;
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

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<ContactLiteDTO> getLiteContactList() {
		return liteContactList;
	}

	public void setLiteContactList(List<ContactLiteDTO> liteContactList) {
		this.liteContactList = liteContactList;
	}

	public List<Contact> getFullContactList() {
		return fullContactList;
	}

	public void setFullContactList(List<Contact> fullContactList) {
		this.fullContactList = fullContactList;
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

	public NetClientGet(String IP,String port,String path) throws MalformedURLException{//without auth header
		setIp(IP);
		setPort(port);
		setPath(path);
		setUrl(new URL("http://"+getIp()+":"+getPort()+"/phonebook_server/"+getPath()));
		liteContactList=new ArrayList<ContactLiteDTO>();
	}

	public NetClientGet(String IP,String port,String path,String uname,String pass) throws MalformedURLException{//with auth header
		setIp(IP);
		setPort(port);
		setPath(path);
		setUname(uname);
		setPass(pass);
		setUrl(new URL("http://"+getIp()+":"+getPort()+"/phonebook_server/"+getPath()));
		liteContactList=new ArrayList<ContactLiteDTO>();
	}

	public String get(){
		try {

			HttpURLConnection conn = (HttpURLConnection) getUrl().openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty ("Authorization", userNamePasswordBase64(getUname(),getPass()));

			ObjectMapper mapper = new ObjectMapper();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				//String message= "Failed : HTTP error code : " + conn.getResponseCode()+ " , HTTP error message: "+conn.getResponseMessage();
				String message= "Failed: error message: "+conn.getResponseMessage();
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

				if(getPath().equals("contact/getAll/liteContact")){
					liteContactList=mapper.readValue(output, new TypeReference<List<ContactLiteDTO>>(){});
				}
				else if(getPath().startsWith("contact/")){
					contact = mapper.readValue(output, Contact.class);
				}
				else if(getPath().startsWith("contact/getAll/fullContact")){
					fullContactList=mapper.readValue(output, new TypeReference<List<Contact>>(){});
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
