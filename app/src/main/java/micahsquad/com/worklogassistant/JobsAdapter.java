package micahsquad.com.worklogassistant;

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

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.DecimalFormat;

import java.util.List;
/**
 * Created by Micah on 7/20/2016.
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Job> jobList;
    String letter, nextLetter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView itemletter, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            itemletter = (ImageView) view.findViewById(R.id.item_letter);
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


        //Code to get gmail like character icons
        letter = String.valueOf(job.getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder().buildRect(letter, generator.getColor(job.getName()));
        holder.itemletter.setImageDrawable(drawable);

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
                    WorkLogDB db = new WorkLogDB(mContext);
                    //db.deleteJob();
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