package in.tosc.digitaloceanapp.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.tosc.digitaloceanapp.R;
import in.tosc.digitaloceanapp.activities.DropletCreateActivity;
import in.tosc.doandroidlib.objects.Image;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final List<Image> imageList;
    private int position;
    private Context context;
    private int selectedposition = -1;
    ViewHolder prevholder = null;
    public static final String TAG = "ImageAdapter";

    public ImageAdapter(List<Image> items, Context context) {
        imageList = items;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_image_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imageName.setText(imageList.get(position).getName());
        this.position = holder.getAdapterPosition();
        holder.imageDistribution.setText(imageList.get(position).getDistribution());
        if(selectedposition!=-1&&position==selectedposition){
            selectImage(position,holder);
        }
        else {
            deselectImage(position, holder);
        }


        holder.imageCard.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if ((Boolean) holder.imageImage.getTag()) {
                    deselectImage(position, holder);
                } else {
                    selectImage(position, holder);
                    if (selectedposition != -1) {
                        deselectImage(selectedposition, prevholder);
                    }
                }
                selectedposition = position;
                prevholder = holder;
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageImage;
        TextView imageName;
        TextView imageDistribution;
        CardView imageCard;

        ViewHolder(View view) {
            super(view);
            imageImage = (ImageView) view.findViewById(R.id.imageImage);
            imageName = (TextView) view.findViewById(R.id.imageName);
            imageDistribution = (TextView) view.findViewById(R.id.imageDistribution);
            imageCard = (CardView) view.findViewById(R.id.imagecard);
        }
    }

    public void selectImage(int position, ViewHolder holder) {
        int selectorImage = 0;
        switch (imageList.get(position).getDistribution()) {
            case "CoreOS":
                selectorImage = R.drawable.coreos_selected;
                break;
            case "FreeBSD":
                selectorImage = R.drawable.freebsd_selected;
                break;
            case "Fedora":
                selectorImage = R.drawable.fedora_selected;
                break;
            case "Debian":
                selectorImage = R.drawable.debian_selected;
                break;
            case "CentOS":
                selectorImage = R.drawable.centos_selected;
                break;
            case "Ubuntu":
                selectorImage = R.drawable.ubuntu_selected;
                break;

        }
        holder.imageImage.setBackground(ContextCompat.getDrawable(context, selectorImage));
        DropletCreateActivity.getDroplet().setImage(imageList.get(position));
        Log.e("OnClick", imageList.get(position).getDistribution());
        holder.imageImage.setTag(true);
    }

    public void deselectImage(int position, ViewHolder holder) {
        switch (imageList.get(position).getDistribution()) {
            case "CoreOS":
                holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.coreos));
                break;
            case "FreeBSD":
                holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.freebsd));
                break;
            case "Fedora":
                holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.fedora));
                break;
            case "Debian":
                holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.debian));
                break;
            case "CentOS":
                holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.centos));
                break;
            case "Ubuntu":
                holder.imageImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ubuntu));
                break;
        }
        holder.imageImage.setTag(false);
    }
}
