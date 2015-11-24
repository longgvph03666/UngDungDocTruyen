package com.group1.app.ungdungdoctruyen.adapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.group1.app.ungdungdoctruyen.MangaInforActivity;
import com.group1.app.ungdungdoctruyen.OpenFileActivity;
import com.group1.app.ungdungdoctruyen.R;
import com.group1.app.ungdungdoctruyen.ReadMangaActivity;

import com.group1.app.ungdungdoctruyen.items.ChapterItems;



public class ChapterAdapter extends ArrayAdapter<ChapterItems> {
	File file ;
	String name;
	private ProgressDialog mProgressDialog;
	Context context;
	int layoutResourceId;
	
	ArrayList<ChapterItems> chapters = new ArrayList<ChapterItems>();
	 private AlertDialog.Builder builder;
	public ChapterAdapter(Context context, int layoutResourceId,
			ArrayList<ChapterItems> chapters) {
		super(context, layoutResourceId, chapters);
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.chapters = chapters;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		ChapterWrapper wrapper = null;

		if (item == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			item = inflater.inflate(layoutResourceId, parent, false);
		    wrapper = new ChapterWrapper();
			wrapper.Chapter = (TextView) item.findViewById(R.id.tvChapter);
			wrapper.imgDownload = (ImageView) item.findViewById(R.id.imgDownload);
			item.setTag(wrapper);
		} else {
			wrapper = (ChapterWrapper) item.getTag();
		}

		final ChapterItems chapers = chapters.get(position);
		wrapper.Chapter.setText(chapers.getChapter());
		
		
		wrapper.imgDownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String filename =chapers.getUrlDown();
				String name = filename.substring(filename.lastIndexOf('/') + 1);
				
				file	= new File("/sdcard/DownloadTruyen/"+name);
				if(file.exists()){
					Intent in = new Intent(context,
							ReadMangaActivity.class);
				    
					in.putExtra("path", file.getPath());
					context.startActivity(in);
		          
				}
				else{
				//Toast.makeText(context, student.getName().toString(), Toast.LENGTH_LONG).show();
				final String itemDownload[] = { "Download"};
				Log.i("TTTH", "Clicked on Item Chapter download");
				builder = new AlertDialog.Builder(context);
				builder.setTitle("Download chapter ");
				builder.setIcon(R.drawable.download);
				builder.setItems(itemDownload, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
							mProgressDialog = new ProgressDialog(context);
							mProgressDialog.setMessage("Please wait");
							mProgressDialog.setIndeterminate(false);
							mProgressDialog.setMax(100);
							mProgressDialog
									.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
							DownloadFile downloadFile = new DownloadFile();
						
							
							downloadFile.execute(chapers.getUrlDown());
							mProgressDialog.show();
							
					}
				});
				builder.show();
				}
			}
		});
		
		

		return item;

	}

	static class ChapterWrapper {
		TextView Chapter;
		ImageView imgDownload;
		TextView age;
		TextView address;
		Button edit;
		Button delete;
	}
	public class DownloadFile extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			int count;
			try {
				
			    
				String filename =params[0];
				String name = filename.substring(filename.lastIndexOf('/') + 1);
				
				file	= new File("/sdcard/DownloadTruyen/"+name);
				URL url = new URL(params[0]);
				URLConnection conn = url.openConnection();
				conn.connect();
				int lenghtOfFile = conn.getContentLength();
				// Download file
				InputStream input = new BufferedInputStream(url.openStream());
				String fileName = filename.substring(filename.lastIndexOf('/') + 1);
				OutputStream output = new FileOutputStream("/sdcard/DownloadTruyen/"+fileName);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					// publishing the progress....
					publishProgress((int) (total * 100 / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				// TODO: handle exception

			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mProgressDialog.dismiss();
			
			Intent in = new Intent(context,
					ReadMangaActivity.class);
		    
			in.putExtra("path", file.getPath());
			context.startActivity(in);
          
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			mProgressDialog.setProgress(values[0]);

		}

	}
    
	
}

