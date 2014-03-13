package fu.se629.jobbe_chat.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import fu.se629.jobbe_chat.R;
import fu.se629.jobbe_chat.entity.Message;

public class ListMessageAdapter extends ArrayAdapter<Message> {

	private TextView txtMessageItem;
	private List<Message> listMessage = new ArrayList<Message>();
	private LinearLayout wrapper;

	@Override
	public void add(Message object) {
		listMessage.add(object);
		super.add(object);
	}

	public ListMessageAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.listMessage.size();
	}

	public Message getItem(int index) {
		return this.listMessage.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.message_item, parent, false);
		}

		wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

		Message msg = getItem(position);

		txtMessageItem = (TextView) row.findViewById(R.id.msg_chat);

		txtMessageItem.setText(msg.content);
		txtMessageItem.setTextColor(Color.BLACK);
		txtMessageItem.setBackgroundResource(msg.friend ? R.drawable.msg_bubble_left : R.drawable.orca_voice_bubble_right_normal_neue);
		wrapper.setGravity(msg.friend ? Gravity.LEFT : Gravity.RIGHT);

		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}
