package com.app.activity.book;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.xutils.x;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.app.activity.R;
import com.app.activity.note.NoteAddActivity;
import com.app.dao.DaoMs;
import com.app.utils.ToastUtils;


@ContentView(R.layout.activity_book_detail)
public class BookDetailActivity extends ActionBarActivity {
	
	@ViewInject(R.id.tv_book_content)
	private TextView tv_book_content;
	@ViewInject(R.id.add_note)
	private TextView add_note;
	@ViewInject(R.id.encode)
	private TextView encode;
	
	private String encode_style = "utf-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initStyle();
        initText();
        setOnclickListener();
        
    }
    
    
    private void initStyle(){
    	
    	
    	try {
			int textSize = DaoMs.getInstance().dbFind().getTextSize();
			int type = DaoMs.getInstance().dbFind().getType();
			tv_book_content.setTextSize(textSize);
			if(type==0){
				tv_book_content.setBackgroundColor(Color.GRAY);
				tv_book_content.setTextColor(Color.WHITE);
			}else{
				tv_book_content.setBackgroundColor(Color.WHITE);
				tv_book_content.setTextColor(Color.BLACK);
			}
			
			
		} catch (DbException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	try {
			ToastUtils.makeLongText(DaoMs.getInstance().dbFind().getTextSize()+"****", getApplicationContext());
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			tv_book_content.append(DaoMs.getInstance().dbFind().getTextSize()+"--"+DaoMs.getInstance().dbFind().getTextSize());
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    private void initText(){
    	
    	Intent it = getIntent();
    	String filename = it.getStringExtra("filename");
    	File file = new File("/sdcard/"+filename);
    	
    	try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file),encode_style));
			
			String line = br.readLine();
			int count = 0;
			while(line!=null){
				
				count++;
				tv_book_content.append(line);
				
				
				line = br.readLine();
				
				if(count==300){
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    
    private void setOnclickListener(){
    	this.add_note.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),NoteAddActivity.class));
				
			}
		});
    	
    	this.encode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if("UTF-8".equals(encode.getText().toString().trim())){
					encode.setText("GBK");
					encode_style =  "GBK";
					
				}else{
					encode.setText("UTF-8");
					encode_style = "utf-8";
				}
				tv_book_content.setText("");
				initStyle();
				initText();
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
