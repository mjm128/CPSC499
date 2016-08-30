package micahsquad.com.worklogassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Micah on 8/9/2016.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder>{
    private Context context;
    private List<Record> recordList;
    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat date = new SimpleDateFormat("M/d/yyyy");
    SimpleDateFormat day = new SimpleDateFormat("EEEE");
    SimpleDateFormat time = new SimpleDateFormat("h:mm a");
    DecimalFormat formater = new DecimalFormat("0.00");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subline1, subline2, subline3, dayname;
        public ImageView itemNumber, overflow;
        View record_cardview;
        private Record record;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.record_line1);
            subline1 = (TextView) view.findViewById(R.id.record_line2);
            subline2 = (TextView) view.findViewById(R.id.record_line3);
            subline3 = (TextView) view.findViewById(R.id.record_line4);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            itemNumber = (ImageView) view.findViewById(R.id.item_number);
            record_cardview = (View) view.findViewById(R.id.record_card_view);
            dayname = (TextView) view.findViewById(R.id.record_dayname);
        }
    }

    public RecordAdapter(Context context, List<Record> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Record record = recordList.get(position);

        holder.title.setText(record.getJobName() + " • " + record.getJobPosition());

        holder.subline1.setText(startEndTime(record));
        if (record.getTimecard().getTimeWorked() >= 0) {
            holder.subline2.setText(record.getTimecard().getTimeWorked()+" hrs @ $"+ formater.format(record.getTimecard().getBasePay())+"/hr");
        }
        if (record.getTip().getPercentTip() >= 0){
            holder.subline3.setText("$"+formater.format(record.getTip().getTip())+" - "+formater.format(record.getTip().getPercentTip())+"%");

        } else if (record.getTip().getTip() >= 0) {  holder.subline3.setText("$"+formater.format(record.getTip().getTip()));}
        holder.record = record;

        String itemNumber = null;
        try {
            itemNumber = date.format(timeStampFormat.parse(record.getTimecard().getDate()));
        } catch (ParseException e){
            itemNumber = "";
        }
        try {
             holder.dayname.setText(day.format(date.parse(itemNumber)));
        } catch (ParseException e){
            itemNumber = "";
        }

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = 16f;
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);

        //Code to get gmail like character icons
        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable.IBuilder builder = TextDrawable.builder()
                                            .beginConfig()
                                                .fontSize(pixels)
                                                .bold()
                                            .endConfig()
                                            .rect();

        TextDrawable drawable = builder.build(itemNumber, generator.getColor(holder.dayname.getText()));
        holder.itemNumber.setImageDrawable(drawable);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, record);
            }
        });

        holder.record_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CreateRecordActivity.class);
                i.putExtra("jobId", record.getTimecard().getJobId());
                i.putExtra("shiftid", record.getTimecard().getShiftId());
                view.getContext().startActivity(i);
            }
        });


    }

    private String startEndTime(Record r){
        String s1, s2;
        try {
            s1 = time.format(timeStampFormat.parse(r.getTimecard().getStartTime()));
        } catch (ParseException e){
            s1 = "";
        }
        try {
            s2 = time.format(timeStampFormat.parse(r.getTimecard().getEndTime()));
        } catch (ParseException e){
            s2 = "";
        }
        if (!s2.equals("")){
            s1 = s1 + " to " + s2;
        }
        return s1;
    }

    // Showing popup menu when tapping on 3 dots
    private void showPopupMenu(View view, Record record) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_job, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(record));
        popup.show();
    }

    // Click listener for popup menu items
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private Record record;

        public MyMenuItemClickListener(Record record) {
            this.record = record;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_job_edit:
                    Intent i = new Intent(context, CreateRecordActivity.class);
                    i.putExtra("jobId", record.getTimecard().getJobId());
                    i.putExtra("shiftid", record.getTimecard().getShiftId());
                    context.startActivity(i);
                    return true;
                case R.id.action_job_delete:
                    try ( WorkLogDB db = new WorkLogDB(context)){
                        db.deleteRecord(record.getTimecard().getShiftId());
                    }
                    removeItem(record);
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public void removeItem(Record information){
        int position = recordList.indexOf(information);
        recordList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(int shiftid) {
        Record element;
        for (int i = 0; i < recordList.size(); i++){
            element = recordList.get(i);
            if (element.getTimecard().getShiftId() == shiftid){
                removeItem(element);
                return;
            }
        }

    }




}
