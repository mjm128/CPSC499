package micahsquad.com.worklogassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.DecimalFormat;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Micah on 7/20/2016.
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Job> jobList;
    String letter;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, positionAndPay;
        public ImageView itemletter, overflow;
        View cardview;
        private Job job;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            positionAndPay = (TextView) view.findViewById(R.id.position);
            itemletter = (ImageView) view.findViewById(R.id.item_letter);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardview = (View) view.findViewById(R.id.card_view);
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DecimalFormat formater = new DecimalFormat("0.00#");
        final Job job = jobList.get(position);

        SpannableStringBuilder positionAndPay = new SpannableStringBuilder();
        positionAndPay.append(job.getPosition());
        int boldStart = positionAndPay.length();
        positionAndPay.append("  ");
        positionAndPay.setSpan(new ForegroundColorSpan(0xFF212121), boldStart, positionAndPay.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        boldStart = positionAndPay.length();
        positionAndPay.append("$" + formater.format(job.getPay()));
        positionAndPay.setSpan(new ForegroundColorSpan(0xFF43A047), boldStart, positionAndPay.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.title.setText(job.getName());
        holder.positionAndPay.setText(positionAndPay);
        holder.job = job;

        //Code to get gmail like character icons
        letter = String.valueOf(job.getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder().buildRect(letter, generator.getColor(job.getName()));
        holder.itemletter.setImageDrawable(drawable);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, job);
            }
        });

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(view.getClass().getName(), "Testing");
                Toast.makeText(mContext, job.getJobId() + " is selected!", Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(new Intent(view.getContext(), CreateRecordActivity.class));
            }
        });
    }

    // Showing popup menu when tapping on 3 dots
    private void showPopupMenu(View view, Job job) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_job, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(job));
        popup.show();
    }

    // Click listener for popup menu items
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private Job job;

        public MyMenuItemClickListener(Job job) {
            this.job = job;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_job_edit:
                    Toast.makeText(mContext, "Edit Job", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_job_delete:
                    WorkLogDB db = new WorkLogDB(mContext);
                    db.deleteJob(job.getJobId());
                    removeItem(job);
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

    public void removeItem(Job information){
        int position = jobList.indexOf(information);
        jobList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(int job_id) {
        Job element;
        for (int i = 0; i < jobList.size(); i++){
            element = jobList.get(i);
            if (element.getJobId() == job_id){
                removeItem(element);
                return;
            }
        }

    }
}