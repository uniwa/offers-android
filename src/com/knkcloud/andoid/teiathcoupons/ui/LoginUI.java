package com.knkcloud.andoid.teiathcoupons.ui;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.knkcloud.andoid.teiathcoupons.R;
import com.knkcloud.andoid.teiathcoupons.customize.EncryptDecrypt;
import com.knkcloud.andoid.teiathcoupons.customize.FileManage;
import com.knkcloud.andoid.teiathcoupons.data.LogIn;
import com.knkcloud.andoid.teiathcoupons.utils.AStatus;

public class LoginUI extends Activity {
	
	 EditText clientinitialscreeneditTextUsername; 
	 EditText clientinitialscreeneditTextpass;
	 CheckBox clientinitialscreencheckbox;
	 boolean nullusername=true;
	 boolean nullpass=true;;
	 boolean checked=false;
	 boolean emptyfile=false;
	 boolean asyncisfinished=false;
	 String FILENAME ="tei.txt";
	 ArrayList<String>filevalues= new ArrayList<String>();
	 ArrayList<String>Decryptedfilevalues= new ArrayList<String>();
	 String usr;
	 String pwd;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 setContentView(R.layout.login);
		 
		 ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
		 imageButton1.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
					Intent intent = new Intent(LoginUI.this,WebViewActivity.class);
					intent.putExtra("id", 0);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(intent);
			}
		});
		 
		 
		 ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
		 imageButton2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginUI.this,WebViewActivity.class);
				intent.putExtra("id", 1);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
			}
		});
		 
		 
		 
		 
		 
		 
		 
		 
		 
		    clientinitialscreeneditTextUsername = (EditText)findViewById(R.id.clientinitialscreeneditTextUsername);
		    TextView clientinitialscreenUsername = (TextView)findViewById(R.id.clientinitialscreenUsername);
		    clientinitialscreeneditTextpass = (EditText)findViewById(R.id.clientinitialscreeneditTextpass);
		    TextView clientinitialscreentextViewpass = (TextView)findViewById(R.id.clientinitialscreentextViewpass);
		   	ImageButton clientinitialscreenbtn = (ImageButton)findViewById(R.id.clientinitialscreenbtn); 
		    clientinitialscreencheckbox =(CheckBox)findViewById(R.id.clientinitialscreencheckbox);
		    clientinitialscreencheckbox.setTextColor(Color.rgb(102, 102, 102));
		    clientinitialscreenUsername.setTextColor(Color.rgb(102, 102, 102));
		    clientinitialscreentextViewpass.setTextColor(Color.rgb(102, 102, 102));   
		    File file = getApplicationContext().getFileStreamPath(FILENAME);
		   //elegxos ean uparxei to arxeio
		    if(file.exists())
		    { 
		    	FileManage filemanipulation = new FileManage(getApplicationContext());
		          //anoikse arxeio
		    	filemanipulation.FileOpen(FILENAME);
		    	String fileisempty =   filemanipulation.FileRead(FILENAME);
				// elegxos ean einai adeio to arxeio  
				if(fileisempty.equals(null) || fileisempty.equals("") || fileisempty==null || fileisempty=="")
				{
					emptyfile=true;
				}
				//kleise arxeio
				filemanipulation.FileInputClose(FILENAME);
				//ean to arxeio den einai adeio
				if(emptyfile==false)
			{
				//anoikse to
				filemanipulation.FileOpen(FILENAME);
		         //vale tis KRUPTOGRAFIMENES times se ena arraylist<String> apo to arxeio pou periexei kai kena 
				filevalues = filemanipulation.FileReadtoArray(FILENAME);
		        //kleise arxeio
				filemanipulation.FileInputClose(FILENAME);
				//vale tis times tou parapanw Arraylist<string> xwris tis kenes times se ena neo
				for(int i=0; i<filevalues.size()-1; i++)
				{
					if(filevalues.get(i).equals("")||filevalues.get(i).equals(null)|filevalues.get(i)==""||filevalues.get(i)=="")
					{
					// mi kaneis tipota
					}
					else
					{  //vale tis decrypted  times poy upirxan sto arxeio kai twra einai sto arraylist<String> se ena neo arraylist<String> pou den periexei kena
						Decryptedfilevalues.add(filevalues.get(i));
					}
				}
				//pare tin teleutai timi tou arxeiou pu einai i katastasi tou remember me true i false
				String state=filevalues.get(filevalues.size()-1);
				//kane ena object krutografisis/apokrutpografisis
				EncryptDecrypt pp = new EncryptDecrypt(getApplicationContext());
		        if(state.equals("true")||state=="true" )
		        {
		        	//an i katastasi itan clicked tote kane decrypt tis 2 times toy arraylist<string>
		        	String PlainUsername = pp.Decrypt(Decryptedfilevalues.get(0));
		        	String Plainpass = pp.Decrypt(Decryptedfilevalues.get(1));
		    		//vale tis times sta pedia
		    		clientinitialscreeneditTextUsername.setText(PlainUsername);
		    		clientinitialscreeneditTextpass.setText(Plainpass);
		    		clientinitialscreencheckbox.setChecked(true);
		        }
		        else
		        {   //ean to state den itan clicked oi formes einai adeies
		        	clientinitialscreeneditTextUsername.setText("");
		    		clientinitialscreeneditTextpass.setText("");
		    		clientinitialscreencheckbox.setChecked(false);
		        }
			}	
		 }
		    clientinitialscreenbtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				  
		//to onoma den einai keno
					nullusername=false ;
					//to pass den  einai keno			
							nullpass=false;
					if(clientinitialscreeneditTextUsername.getText().toString()==""||clientinitialscreeneditTextUsername.getText().toString()==null||clientinitialscreeneditTextUsername.getText().toString().equals("")||clientinitialscreeneditTextUsername.getText().toString().equals(null))			
		    		{
					//to onoma  einai keno
		    		nullusername=true;	
		    		}			
		    		if(clientinitialscreeneditTextpass.getText().toString()==""||clientinitialscreeneditTextpass.getText().toString()==null||clientinitialscreeneditTextpass.getText().toString().equals("")||clientinitialscreeneditTextpass.getText().toString().equals(null))			
		    		{
		    			//to pass einai keno	
		    			nullpass=true;
		    		}			
		    		if(nullusername==false && nullpass==false)	
		    		{//ean den einai kena swse tis times sto arxeio
		    			StoreInFile();
		    			//check internet status
		    			if (haveNetworkConnection() ==true)
		    			{
		    				usr=clientinitialscreeneditTextUsername.getText().toString();
		                    pwd=clientinitialscreeneditTextpass.getText().toString();
		             //dimioyrgia tou json pou stelnete sto login
		                    StringBuilder sb = new StringBuilder();
		                    sb.append("{");
		                    sb.append("\"User\":");
		                    sb.append("{");
		                    sb.append("\"username\":");
		                    sb.append("\"");
		                    sb.append(usr);
		                    sb.append("\"");
		                    sb.append(",");
		                    sb.append("\"password\":");
		                    sb.append("\"");
		                    sb.append(pwd);
		                    sb.append("\"");
		                    sb.append("}");
		                    sb.append("}");
		      		    new PostLogin(LoginUI.this).execute(sb.toString());
		    			}
		    			else
		    			{
		  		Toast.makeText(getApplicationContext(), R.string.checkinternet, Toast.LENGTH_LONG).show();
		    			}
		    		}
		    		else
		    		{
		    Toast.makeText(getApplicationContext(), R.string.enternameandpass, Toast.LENGTH_LONG).show();
		    		}	
				}
			}); 
	}
	
	
	
	//elenxe ena uparxei sundesi sto internet
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
       return haveConnectedWifi || haveConnectedMobile;
    }	
	
	
	
    //apothikseuse sto arxeio kruptografimena ta username kai password
    public void StoreInFile()
    {
    	EncryptDecrypt pp = new EncryptDecrypt(getApplicationContext());		
		FileManage filemanipulation = new FileManage(getApplicationContext());	
    	if(clientinitialscreencheckbox.isChecked())
    	{
    		String username = clientinitialscreeneditTextUsername.getText().toString();
    		String password = clientinitialscreeneditTextpass.getText().toString();
    		String state = "true";
    		String Cipherusername=pp.Encrypt(username);
    		String Cipherpassword=pp.Encrypt(password);
    		filemanipulation.FileCreate(FILENAME, MODE_PRIVATE);
    		filemanipulation.FileWrite(FILENAME, Cipherusername);
    		filemanipulation.FileChangeLine(FILENAME);
    		filemanipulation.FileWrite(FILENAME, Cipherpassword);
    		filemanipulation.FileChangeLine(FILENAME);
    		filemanipulation.FileWrite(FILENAME, state);
    		filemanipulation.FileOutputClose(FILENAME);		
    	}
    	else
    	{
    		String username = clientinitialscreeneditTextUsername.getText().toString();
    		String password = clientinitialscreeneditTextpass.getText().toString();
    		String state = "false";
    		String Cipherusername=pp.Encrypt(username);
    		String Cipherpassword=pp.Encrypt(password);
    		filemanipulation.FileCreate(FILENAME, MODE_PRIVATE);
    		filemanipulation.FileWrite(FILENAME, Cipherusername);
    		filemanipulation.FileChangeLine(FILENAME);
    		filemanipulation.FileWrite(FILENAME, Cipherpassword);
    		filemanipulation.FileChangeLine(FILENAME);
    		filemanipulation.FileWrite(FILENAME, state);
    		filemanipulation.FileOutputClose(FILENAME);			
    	}
    }	
	
    
 // to async pou paizei to login
private class PostLogin extends LogIn {
	public PostLogin(Context context) {
		super(context);
	}
	@Override
	public void onSuccess(String result) {
		//Toast.makeText(getApplicationContext(), result, 1).show();
		if(result.contains("403"))
		{
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.faillogin), Toast.LENGTH_LONG).show();
		}
		else if(result.contains("200"))
		{
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.successlogin), Toast.LENGTH_LONG).show();
			Intent intent = new Intent(LoginUI.this, MainUI.class);
			startActivity(intent);
			
		}
	//	Toast.makeText(getApplicationContext(), getResources().getString(R.string.successlogin), 1).show();
		//Toast.makeText(getApplicationContext(), getResources().getString(R.string.faillogin), 1).show();
	}
	@Override
	public void onFail(AStatus cause) {
		//Toast.makeText(getApplicationContext(), cause.getMessage(), Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.checkinternet), Toast.LENGTH_LONG).show();
	}
	@Override
	public void onWhatEver() {
		//Toast.makeText(getApplicationContext(), "Υπήρξε κάποιο πρόβλημα με το διακομηστή.", Toast.LENGTH_LONG).show();
	}
}





}
