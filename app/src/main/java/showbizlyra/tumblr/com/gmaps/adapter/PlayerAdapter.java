package showbizlyra.tumblr.com.gmaps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import showbizlyra.tumblr.com.gmaps.Player;
import showbizlyra.tumblr.com.gmaps.R;

/**
 * Created by GalihPW on 14/12/2016.
 */

public class PlayerAdapter extends BaseAdapter{
    private Context mContext;

    private Player[] mPlayer;

    public PlayerAdapter(Context Context, Player[] Player){
        mContext = Context;
        mPlayer = Player;
    }

    @Override
    public int getCount() {
        return mPlayer.length;
    }

    @Override
    public Object getItem(int position) {
        return mPlayer[position];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.player, null);
            viewHolder = new ViewHolder();
            viewHolder.nama = (TextView)convertView.findViewById(R.id.tvUname);
            viewHolder.score = (TextView)convertView.findViewById(R.id.tvScore);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Player player = mPlayer[position];
        viewHolder.nama.setText(player.getNama() + "");
        viewHolder.score.setText(player.getScore() + "");

        return convertView;
    }

    private static class ViewHolder{
        TextView nama;
        TextView score;
    }
}
