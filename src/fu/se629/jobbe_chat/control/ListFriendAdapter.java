package fu.se629.jobbe_chat.control;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fu.se629.jobbe_chat.R;
import fu.se629.jobbe_chat.entity.Friend;

public class ListFriendAdapter extends ArrayAdapter<Friend> {

	private final Context context;
	private final ArrayList<Friend> friendArrayList;

	public ListFriendAdapter(Context context, ArrayList<Friend> friendArrayList) {

		super(context, R.layout.friend_item, friendArrayList);

		this.context = context;
		this.friendArrayList = friendArrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.friend_item, parent,
				false);

		TextView friendName = (TextView) rowView.findViewById(R.id.friendName);
		TextView lastMessenge = (TextView) rowView
				.findViewById(R.id.lastMessenge);
		TextView timeLastMessenge = (TextView) rowView
				.findViewById(R.id.timeLastMessenge);

		friendName.setText(friendArrayList.get(position).friendName);
		lastMessenge.setText(friendArrayList.get(position).lastMessage.content);
		timeLastMessenge.setText(friendArrayList.get(position)
				.lastMessage.time);

		return rowView;
	}
//
//	class LoadAvataAsyncTask extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			pd.show();
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			try {
//				// URL url = new URL(
//				// "http://a3.twimg.com/profile_images/670625317/aam-logo-v3-twitter.png");
//
//				image = downloadBitmap("http://a3.twimg.com/profile_images/670625317/aam-logo-v3-twitter.png");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			pd.dismiss();
//			if (image != null) {
//				iv.setImageBitmap(image);
//			}
//
//		}
//	}
//
//	private Bitmap downloadBitmap(String url) {
//		// initilize the default HTTP client object
//		final DefaultHttpClient client = new DefaultHttpClient();
//
//		// forming a HttoGet request
//		final HttpGet getRequest = new HttpGet(url);
//		try {
//
//			HttpResponse response = client.execute(getRequest);
//
//			// check 200 OK for success
//			final int statusCode = response.getStatusLine().getStatusCode();
//
//			if (statusCode != HttpStatus.SC_OK) {
////				Log.w("ImageDownloader", "Error " + statusCode
////						+ " while retrieving bitmap from " + url);
//				return null;
//
//			}
//
//			final HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				InputStream inputStream = null;
//				try {
//					// getting contents from the stream
//					inputStream = entity.getContent();
//
//					// decoding stream data back into image Bitmap that android
//					// understands
//					image = BitmapFactory.decodeStream(inputStream);
//
//				} finally {
//					if (inputStream != null) {
//						inputStream.close();
//					}
//					entity.consumeContent();
//				}
//			}
//		} catch (Exception e) {
//			// You Could provide a more explicit error message for IOException
//			getRequest.abort();
////			Log.e("ImageDownloader", "Something went wrong while"
////					+ " retrieving bitmap from " + url + e.toString());
//		}
//
//		return image;
//	}
}