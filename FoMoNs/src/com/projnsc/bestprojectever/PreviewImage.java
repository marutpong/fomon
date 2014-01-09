package com.projnsc.bestprojectever;

import java.io.File;

import com.projnsc.bestprojectever.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class PreviewImage extends Activity {
	private static final String TAG = "Test";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView ( R.layout.image_preview );
		Log.i(TAG, "Preview Image");
		final ImageView myImage = (ImageView) findViewById(R.id.imagePreview);
		
		
		Intent intent = getIntent();
		String filename = intent.getExtras().getString("Preview");	
		//System.out.println(filename);
		
		//filename = filename.substring(0,filename.length()-4);
		//filename += "1.jpg";
		
		/*Button but = (Button) findViewById(R.id.button1);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myImage.setImageResource(R.drawable.ic_launcher);
				
			}
		});
		*/
		
		ShowPicture(filename,myImage);
		
		
		//if(imgFile.canRead()){
			//System.out.println("exist na");
		

		}
		
	public static void ShowPicture(String fileName, ImageView myImage) { 
		File imgFile = new  File(fileName); 
		
		
//	    FileInputStream is = null; 
//	    try { 
//	        is = new FileInputStream(imgFile); 
//	    } catch (FileNotFoundException e) {
//	        Log.i("kak",String.format( "ShowPicture.java file[%s]Not Found",fileName)); 
//	        return; 
//	    } 
	    
	    
	    if(imgFile.exists()){

	        Bitmap myBitmap = BitmapFactory.decodeFile(fileName);
	        
	       
	        myImage.setImageBitmap(myBitmap);
	      
	    }
	        
	   // Bitmap myBitmap = BitmapFactory.decodeStream(is);
	    
	   // pic.setImageBitmap(myBitmap); 
	} 	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	
}
