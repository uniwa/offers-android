package com.knkcloud.andoid.teiathcoupons.customize;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.util.Log;


/**
 * Utility class, handles files on the android device
 * @author Karpouzis Koutsourakis Ntinopoulos
 *
 */
public class FileManage {
	FileInputStream fin = null;		
	FileOutputStream fos = null;   
    Context context;		

	
	/**
	 * Utility class, handles files on the android device
	 * @param context the application context
	 */
	public FileManage(Context context){
		this.context = context;

	}
	
	/**
	 * Creates a new file
	 * @param filename the name of the file
	 * @param mode	the operation mode
	 * @return	true if operation completed with no errors, false otherwise
	 */
	public boolean FileCreate(String filename, int mode){
		try {
			fos = context.openFileOutput(filename, mode);
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}	
	}

	/**
	 * opens a file
	 * @param filename the name of the file
	 * @return	true if operation completed with no errors, false otherwise
	 */
	public boolean FileOpen(String filename){
		try {
			fin = context.openFileInput(filename);
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}		
	}

	/**
	 * Reads a file
	 * @param filename the name of the file to be readed
	 * @return the contents of the file in a single string form
	 */
	public String FileRead(String filename){
          InputStreamReader isr = new InputStreamReader(fin);
		  BufferedReader br = new BufferedReader(isr);
		  String line="";
		  StringBuilder text = new StringBuilder("");
		  	try {
		  		while ((line = br.readLine()) != null) {
		  		    text.append(line);
		  		    text.append("\n");
		  			}
		  	}catch (IOException e) {
		  	}
		
		return text.toString();	
	}

	/**
	 * Reads a file
	 * @param filename the name of the file to be readed
	 * @return the contents of the file in an arraylist form
	 */
	public ArrayList<String> FileReadtoArray(String filename){
		

          InputStreamReader isr = new InputStreamReader(fin);
		  BufferedReader br = new BufferedReader(isr);
		  String line="";
		  ArrayList <String>lines = new ArrayList<String>();
		  	try {
		  		while ((line = br.readLine()) != null) {
		  		    lines.add(line);

		  			}
		  	}catch (IOException e) {

		  	}
		
		return lines;	
	}

	
    /**
     * Writes a string into a file
     * @param filename	the name of the file
     * @param input	the sting to be saved on the file
     * @return true if operation completed with no errors, false otherwise 
     */
    public boolean FileWrite(String filename, String input){
    	if(fos!=null){
		     try {
				fos.write(input.getBytes());
				return true;
			} catch (Exception e) {
				Log.e("karp", "error"+Log.getStackTraceString(e));
				return false;
			}
    	}
    	else{ 
    		return false;  
    	}
	}
 
    
    /**
     * adds an empty line into file
     * @param filename the name of the file
     * @return	true if operation completed with no errors, false otherwise
     */
    public boolean FileChangeLine(String filename){
    	String tmp ="\r\n";
    	if(fos!=null){
		     try {
				fos.write(tmp.getBytes());
				return true;
			} catch (IOException e) {
				return false;
			}
    	}
    	else{   
    		return false;  
    	}
	}
    
    

	/**
	 * Closes a file, which had been used as output
	 * @param filename the name of the file
	 * @return true if operation completed with no errors, false otherwise
	 */
	public boolean FileOutputClose(String filename){
		try {
			fos.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Closes a file, which had been used as input
	 * @param filename the name of the file
	 * @return true if operation completed with no errors, false otherwise
	 */
	public boolean FileInputClose(String filename){
		try {
			fin.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}
