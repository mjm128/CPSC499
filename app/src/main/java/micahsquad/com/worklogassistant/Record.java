package micahsquad.com.worklogassistant;

/**
 * Created by Micah on 8/5/2016.
 */
public class Record {
    private TimeCard timecard;
    private Tip tip;
    private String jobName, jobPosition;

    public Record(){
        timecard = new TimeCard();
        tip = new Tip();
    }

    public String getJobName(){
        return jobName;
    }

    public void setJobName(String jobName){
        this.jobName = jobName;
    }

    public String getJobPosition(){
        return jobPosition;
    }

    public void setJobPosition(String jobPosition){
        this.jobPosition = jobPosition;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public TimeCard getTimecard() {
        return timecard;
    }

    public void setTimecard(TimeCard timecard) {
        this.timecard = timecard;
    }

    public static class TimeCard {
        private long shiftid;
        private long jobid;
        private String date, startTime, endTime, breakStart1, breakEnd1, lunchStart1, lunchEnd1,
                breakStart2, breakEnd2, lunchStart2, lunchEnd2, breakStart3, breakEnd3,
                lunchStart3, lunchEnd3, breakStart4, breakEnd4, lunchStart4, lunchEnd4,
                breakStart5, breakEnd5, comment;

        private double basePay, timeWorked, shiftPay, totalPay;

        public double getShiftPay() {
            return shiftPay;
        }

        public void setShiftPay(double shiftPay) {
            this.shiftPay = shiftPay;
        }

        public double getTotalPay() {
            return totalPay;
        }

        public void setTotalPay(double totalPay) {
            this.totalPay = totalPay;
        }

        public double getTimeWorked() {
            return timeWorked;
        }

        public void setTimeWorked(double timeWorked) {
            this.timeWorked = timeWorked;
        }

        public long getShiftId() {
            return shiftid;
        }

        public void setShiftId(long shiftid) {
            this.shiftid = shiftid;
        }

        public long getJobId() {
            return jobid;
        }

        public void setJobId(long jobid) {
            this.jobid = jobid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getBreakStart1() {
            return breakStart1;
        }

        public void setBreakStart1(String breakStart1) {
            this.breakStart1 = breakStart1;
        }

        public String getBreakEnd1() {
            return breakEnd1;
        }

        public void setBreakEnd1(String breakEnd1) {
            this.breakEnd1 = breakEnd1;
        }

        public String getLunchStart1() {
            return lunchStart1;
        }

        public void setLunchStart1(String lunchStart1) {
            this.lunchStart1 = lunchStart1;
        }

        public String getLunchEnd1() {
            return lunchEnd1;
        }

        public void setLunchEnd1(String lunchEnd1) {
            this.lunchEnd1 = lunchEnd1;
        }

        public String getBreakStart2() {
            return breakStart2;
        }

        public void setBreakStart2(String breakStart2) {
            this.breakStart2 = breakStart2;
        }

        public String getBreakEnd2() {
            return breakEnd2;
        }

        public void setBreakEnd2(String breakEnd2) {
            this.breakEnd2 = breakEnd2;
        }

        public String getLunchStart2() {
            return lunchStart2;
        }

        public void setLunchStart2(String lunchStart2) {
            this.lunchStart2 = lunchStart2;
        }

        public String getLunchEnd2() {
            return lunchEnd2;
        }

        public void setLunchEnd2(String lunchEnd2) {
            this.lunchEnd2 = lunchEnd2;
        }

        public String getBreakStart3() {
            return breakStart3;
        }

        public void setBreakStart3(String breakStart3) {
            this.breakStart3 = breakStart3;
        }

        public String getBreakEnd3() {
            return breakEnd3;
        }

        public void setBreakEnd3(String breakEnd3) {
            this.breakEnd3 = breakEnd3;
        }

        public String getLunchStart3() {
            return lunchStart3;
        }

        public void setLunchStart3(String lunchStart3) {
            this.lunchStart3 = lunchStart3;
        }

        public String getLunchEnd3() {
            return lunchEnd3;
        }

        public void setLunchEnd3(String lunchEnd3) {
            this.lunchEnd3 = lunchEnd3;
        }

        public String getBreakStart4() {
            return breakStart4;
        }

        public void setBreakStart4(String breakStart4) {
            this.breakStart4 = breakStart4;
        }

        public String getBreakEnd4() {
            return breakEnd4;
        }

        public void setBreakEnd4(String breakEnd4) {
            this.breakEnd4 = breakEnd4;
        }

        public String getLunchStart4() {
            return lunchStart4;
        }

        public void setLunchStart4(String lunchStart4) {
            this.lunchStart4 = lunchStart4;
        }

        public String getLunchEnd4() {
            return lunchEnd4;
        }

        public void setLunchEnd4(String lunchEnd4) {
            this.lunchEnd4 = lunchEnd4;
        }

        public String getBreakStart5() {
            return breakStart5;
        }

        public void setBreakStart5(String breakStart5) {
            this.breakStart5 = breakStart5;
        }

        public String getBreakEnd5() {
            return breakEnd5;
        }

        public void setBreakEnd5(String breakEnd5) {
            this.breakEnd5 = breakEnd5;
        }

        public double getBasePay() {
            return basePay;
        }

        public void setBasePay(double basePay) {
            this.basePay = basePay;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public static class Tip {
        private long shiftId, jobId, tipId;
        private double tip, ccTip, tippedOut, percentTip, sales, tax, revenue;
        private String comment;

        public long getShiftId() {
            return shiftId;
        }

        public void setShiftId(long shiftId) {
            this.shiftId = shiftId;
        }

        public long getJobId() {
            return jobId;
        }

        public void setJobId(long jobId) {
            this.jobId = jobId;
        }

        public long getTipId() {
            return tipId;
        }

        public void setTipId(long tipId) {
            this.tipId = tipId;
        }

        public double getTip() {
            return tip;
        }

        public void setTip(Double tip) {
            this.tip = tip;
        }

        public double getCcTip() {
            return ccTip;
        }

        public void setCcTip(Double ccTip) {
            this.ccTip = ccTip;
        }

        public double getTippedOut() {
            return tippedOut;
        }

        public void setTippedOut(Double tippedOut) {
            this.tippedOut = tippedOut;
        }

        public double getPercentTip() {
            return percentTip;
        }

        public void setPercentTip(Double percentTip) {
            this.percentTip = percentTip;
        }

        public double getSales() {
            return sales;
        }

        public void setSales(Double sales) {
            this.sales = sales;
        }

        public double getTax() {
            return tax;
        }

        public void setTax(Double tax) {
            this.tax = tax;
        }

        public double getRevenue() {
            return revenue;
        }

        public void setRevenue(Double revenue) {
            this.revenue = revenue;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

}

