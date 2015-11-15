package com.group1.app.ungdungdoctruyen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable.Factory;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReadMangaActivity extends Activity implements OnTouchListener {

	public static final String LOG_TAG = "TTTH";
	private ImageView img;
	private Bundle bundle;
	private String pathzip;
	@SuppressWarnings("rawtypes")
	private ArrayList arrImages;
	private int i = 1;
	private Bitmap result;
	private AlertDialog.Builder builderGoto;

	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	private ImageButton imgBtnNext;
	private ImageButton imgBtnBack;
	private AlertDialog.Builder buiderBrowser;
	private AlertDialog.Builder builderMode;
	protected static final int FIT_MATRIX = 0;
	protected static final int FIT_XY = 1;
	protected static final int FIT_CENTER = 2;
	protected static final int FIT_START = 3;
	protected static final int FIT_END = 4;
	//int i = 0;
    float x1,x2;
    float y1, y2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_read_manga);
		img = (ImageView) findViewById(R.id.imageView1);

	}

	public void processoptMnuGoto() {
		if (arrImages == null) {
			return;
		}
		final String[] itemsGoto = { "Go to First Page", "Go to Middle Page",
				"Go to Last Page" };

		builderGoto = new AlertDialog.Builder(this);
		builderGoto.setTitle("Go to ");
		builderGoto.setItems(itemsGoto, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (arrImages == null) {
					Toast.makeText(getApplicationContext(),
							"Please open zip file ", 3000).show();
				} else {

					if (item == 0) {
						String firstImages = Integer.toString(0);
						Toast.makeText(getApplicationContext(), firstImages,
								5000).show();
						img.setImageBitmap((Bitmap) arrImages.get(1));
					} else if (item == 1) {
						String haftImages = Integer.toString((int) arrImages
								.size() / 2);
						Toast.makeText(getApplicationContext(), haftImages,
								5000).show();
						img.setImageBitmap((Bitmap) arrImages
								.get((int) arrImages.size() / 2));
					} else if (item == 2) {
						String totalImages = Integer.toString(arrImages.size());
						Toast.makeText(getApplicationContext(), totalImages,
								5000).show();
						img.setImageBitmap((Bitmap) arrImages.get(arrImages
								.size() - 1));
					}
				}
			}
		});
		builderGoto.show();
	}

	public void getImageList() {
		if (pathzip == null) {
			return;
		}
		try {
			FileInputStream fis;
			fis = new FileInputStream(pathzip);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = null;
			result = null;

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inTempStorage = new byte[32 * 1024];
			options.inDither = false;
			options.inPurgeable = true;
			options.inPurgeable = true;
			arrImages = new ArrayList();
			while ((ze = zis.getNextEntry()) != null) {
				result = BitmapFactory.decodeStream(zis, null, options);
				arrImages.add(result);
			}

			zis.close();
			fis.close();
			Log.i(LOG_TAG, arrImages.toString());
			Log.i(LOG_TAG, Integer.toString(arrImages.size()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getNextImage() {
		if (arrImages == null) {
			return;
		}
		img.setImageBitmap((Bitmap) arrImages.get(i));
	}

	public void getBackImage() {
		if (arrImages == null) {
			return;
		}
		img.setImageBitmap((Bitmap) arrImages.get(i));
	}

	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.i("TTTH", "onStop");
		if (arrImages != null) {
			arrImages.clear();
			arrImages = null;
		}
		super.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("TTTH", "onStart");
		bundle = getIntent().getExtras();
		if (bundle != null) {
			pathzip = bundle.getString("path");
			getImageList();
			getNextImage();
		} else {
			Log.i(LOG_TAG, "bundle is null");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (arrImages == null) {
			// Toast.makeText(getApplicationContext(), "Please open a zip file",
			// 1000).show();
			return false;
		} else {

			if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
				i = i + 1;
				getNextImage();
				if (i == arrImages.size() - 1) {
					i = 1;
					getNextImage();
				}
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
				i = i - 1;
				getBackImage();
				if (i == 0) {
					i = arrImages.size() - 1;
					getBackImage();
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean onTouchEvent(MotionEvent touchevent) 
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen we get x and y coordinate
                         case MotionEvent.ACTION_DOWN: 
                         {   
                             x1 = touchevent.getX();
                             y1 = touchevent.getY();
                             break;
                        }
                         case MotionEvent.ACTION_UP: 
                         {   
                             x2 = touchevent.getX();
                             y2 = touchevent.getY(); 

                             //if left to right sweep event on screen
                             if (x1 < x2) 
                             {  if(i>0 && i<=arrImages.size()  ){ 
                            	 i--;
                                 
                                getBackImage();
                                 }
                                 //Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                              }
                            
                             // if right to left sweep event on screen
                             if (x1 > x2)
                             {  if(i>=0 && i<(arrImages.size()-1) ){ 
                            	 i++;
                                 
                                // img.setImageResource(arrImg[i]);
                            	 Log.d("leght2",arrImages.size()+"");
                                 Log.d("leght",String.valueOf(i));
                                 getNextImage();
                                 if(i==(arrImages.size()-1)){ 
                                	 Toast.makeText(this, "Truyện đã đến trang cuối", Toast.LENGTH_LONG).show();
                                 }
                                }
                            
                                // Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                             }
                            
                             // if UP to Down sweep event on screen
                             if (y1 < y2) 
                             {
                                // Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                             }
                            
                             //if Down to UP sweep event on screen
                             if (y1 > y2)
                             {
                                 //Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                              }
                             break;
                         }
                 }
                 return false;
    }
	// Start touch
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// Dump touch event to log

		ImageView view = (ImageView) v;

		dumpEvent(event);
		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			if (arrImages == null) {
				Toast.makeText(this, "please open zip file on sdcard", 2000)
						.show();
			}
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			Log.d("TTTH", "mode=DRAG");
			mode = DRAG;
			break;
		case MotionEvent.ACTION_UP:
			imgBtnNext.setVisibility(View.VISIBLE);
			imgBtnBack.setVisibility(View.VISIBLE);
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			Log.d("TTTH", "mode=NONE");
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			Log.d("TTTH", "oldDist=" + oldDist);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
				Log.d("TTTH", "mode=ZOOM");
			}
			break;
		case MotionEvent.ACTION_MOVE:
			imgBtnNext.setVisibility(View.INVISIBLE);
			imgBtnBack.setVisibility(View.INVISIBLE);
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);

			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				Log.d("TTTH", "newDist=" + newDist);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
			}
			break;
		}
		view.setImageMatrix(matrix);
		return true; // indicate event was handled
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);

	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private void dumpEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
				"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
		Log.d("TTTH", sb.toString());
	}

	// End Touch

	

	public void selectMode() {
		if(arrImages == null){
			return;
		}
		final String[] mode = { "MATRIX", "FIT XY", "FIT CENTER", "FIT START",
				"FIT END" };
		builderMode = new AlertDialog.Builder(this);
		builderMode.setTitle("Config Mode");
		builderMode.setItems(mode, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i("TTTH", "Items of AlertDialog Mode selected !!!");
				if (which == FIT_MATRIX) {
					Log.i("TTTH", "Item FIT_MATRIX has selected");
					img.setScaleType(ScaleType.MATRIX);
				} else if (which == FIT_XY) {
					Log.i("TTTH", "Item FIT_XY has selected");
					img.setScaleType(ScaleType.FIT_XY);
				} else if (which == FIT_CENTER) {
					Log.i("TTTH", "Item FIT_CENTER has selected");
					img.setScaleType(ScaleType.FIT_CENTER);
				} else if (which == FIT_START) {
					Log.i("TTTH", "Item FIT_START has selected");
					img.setScaleType(ScaleType.FIT_START);
				} else if (which == FIT_END) {
					Log.i("TTTH", "Item FIT_END has selected");
					img.setScaleType(ScaleType.FIT_END);
				}
			}
		});
		builderMode.show();

	}

	

	
	
	

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList mArray;

		public ImageAdapter(Context context) {
			mContext = context;
		}

		public void setArray(ArrayList arrImages) {
			mArray = arrImages;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mArray.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imgw = new ImageView(mContext);
			imgw.setImageBitmap((Bitmap) mArray.get(position));
			imgw.setLayoutParams(new Gallery.LayoutParams(250, 260));
			return imgw;
		}

	}

}
