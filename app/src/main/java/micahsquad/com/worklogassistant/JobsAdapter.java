package micahsquad.com.worklogassistant;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

import java.util.List;
/**
 * Created by Micah on 7/20/2016.
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Job> jobList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public JobsAdapter(Context mContext, List<Job> jobList) {
        this.mContext = mContext;
        this.jobList = jobList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DecimalFormat formater = new DecimalFormat("0.00#");
        Job job = jobList.get(position);
        holder.title.setText(job.getName());
        holder.count.setText(job.getPosition() + " - $" + formater.format(job.getPay()));

        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    // Showing popup menu when tapping on 3 dots
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_job, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    // * Click listener for popup menu items
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_job_edit:
                    Toast.makeText(mContext, "Edit Job", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_job_delete:
                    Toast.makeText(mContext, "Delete Job", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }
}