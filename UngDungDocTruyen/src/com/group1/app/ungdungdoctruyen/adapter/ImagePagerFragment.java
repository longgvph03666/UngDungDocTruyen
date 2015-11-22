package com.group1.app.ungdungdoctruyen.adapter;



import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.group1.app.ungdungdoctruyen.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImagePagerFragment extends Fragment {
	ViewPager pager;
	String link;
	public static final int INDEX = 2;
	 public ArrayList<String> arrImg;
	ImageAdapter adapter;
	//private static final String[] IMAGE_URLS ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_load_img, container, false);
        arrImg = new ArrayList<String>();
  		 pager = (ViewPager) rootView.findViewById(R.id.pager);
  		link = getActivity().getIntent().getStringExtra("url");
		new DoGetRss().execute();
		adapter = new ImageAdapter(getActivity());
		new DoGetRss().execute();

		pager.setAdapter(adapter);
		//pager.setCurrentItem(getArguments().getInt(Constants.Extra.IMAGE_POSITION, 0));
         ImageLoader.getInstance();
		return rootView;
	}

	private  class ImageAdapter extends PagerAdapter {


		private LayoutInflater inflater;
		private DisplayImageOptions options;

		ImageAdapter(Context context) {
			inflater = LayoutInflater.from(context);
			ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
			config.threadPriority(Thread.NORM_PRIORITY - 2);
			config.denyCacheImageMultipleSizesInMemory();
			config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
			config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
			config.tasksProcessingOrder(QueueProcessingType.LIFO);
			config.writeDebugLogs(); // Remove for release app

			// Initialize ImageLoader with configuration.
			ImageLoader.getInstance().init(config.build());

			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error)
					.resetViewBeforeLoading(true)
					.cacheOnDisk(true)
					.imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.considerExifParams(true)
					.displayer(new FadeInBitmapDisplayer(300))
					.build();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return arrImg.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			assert imageLayout != null;
			ImageViewTouch imageView = (ImageViewTouch) imageLayout.findViewById(R.id.image);
			imageView.setDisplayType(DisplayType.FIT_TO_SCREEN);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
                
			ImageLoader.getInstance().displayImage(arrImg.get(position).toString(), imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			view.addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}
	class DoGetRss extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				URL url = new URL(link);
				URLConnection urlConnection = url.openConnection();
				InputStream iS = urlConnection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(iS);
				doc.getDocumentElement().normalize();
				Node nNews = doc.getElementsByTagName("channel").item(0);
				Element eNews = (Element) nNews;
				NodeList nListNews = eNews.getElementsByTagName("item");

				for(int i = 0; i<nListNews.getLength();i++){

					Node n1News = nListNews.item(i);
					String imgLink = ((Element) n1News).getElementsByTagName("link").item(0).getTextContent();
       
					arrImg.add(imgLink);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//new ImageDownloaderTask(img).execute(arrImg.get(i).toString());
			adapter.notifyDataSetChanged();
		}
	}
}