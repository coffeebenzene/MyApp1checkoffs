package istd.eric.myapp1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Eric on 16/10/2016.
 */

public class DetailsAadapter extends ArrayAdapter<ImageDetail> {

    public DetailsAadapter(Context context, int resource) {
        super(context, resource);
    }

    public DetailsAadapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public DetailsAadapter(Context context, int resource, ImageDetail[] objects) {
        super(context, resource, objects);
    }

    public DetailsAadapter(Context context, int resource, int textViewResourceId, ImageDetail[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public DetailsAadapter(Context context, int resource, List<ImageDetail> objects) {
        super(context, resource, objects);
    }

    public DetailsAadapter(Context context, int resource, int textViewResourceId, List<ImageDetail> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View detailview = convertView;
        if (detailview == null) {
            detailview = LayoutInflater.from(this.getContext()).inflate(R.layout.list_detail, parent, false);
        }

        ImageDetail detail = this.getItem(position);

        ImageView icon = (ImageView) detailview.findViewById(R.id.icon);
        TextView title = (TextView) detailview.findViewById(R.id.title);
        TextView description = (TextView) detailview.findViewById(R.id.description);

        icon.setImageResource(detail.getImageid());
        title.setText(detail.getTitle());
        description.setText(detail.getDetail());

        description.setSelected(true);

        return detailview;
    }
}