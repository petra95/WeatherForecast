import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.p92rdi.extendedweathertitan.R;

import java.util.List;

/**
 * Created by antalicsp on 2016. 08. 03..
 */
public class FillingListAdapter extends ArrayAdapter<ViewFillingActivity.Mock> {

    private List<ViewFillingActivity.Mock> fillings;
    private LayoutInflater inflater;

    public FillingListAdapter(Context context, List<ViewFillingActivity.Mock> fillings) {
        super(context, 0, fillings);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.fillings = fillings;
    }

    @Override
    public int getCount() {
        return fillings.size();
    }

    @Override
    public ViewFillingActivity.Mock getItem(int position) {
        return fillings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_forecast, null);
            holder = new Holder();

            holder.tvCity = (TextView) convertView.findViewById(R.id.tvCity);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            holder.minImageView = (ImageView) convertView.findViewById(R.id.minImageView);
            holder.minDegTextView5 = (TextView) convertView.findViewById(R.id.minDegTextView5);
            holder.maxDegTextView5 = (TextView) convertView.findViewById(R.id.maxDegTextView5);
            holder.maxImageView = (ImageView) convertView.findViewById(R.id.maxImageView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ViewFillingActivity.Mock newItem = getItem(position);

        holder.tvCity.setText(String.valueOf(newItem.getFirst()));
        holder.tvDescription.setText(String.valueOf(newItem.getSecond()));
        holder.minDegTextView5.setText(String.valueOf(newItem.getSecond()));
        holder.maxDegTextView5.setText(String.valueOf(newItem.getFirst()));

        return convertView;
    }

    public class Holder {
        TextView tvCity;
        TextView tvDescription;
        ImageView minImageView;
        TextView minDegTextView5;
        TextView maxDegTextView5;
        ImageView maxImageView;
    }

}
