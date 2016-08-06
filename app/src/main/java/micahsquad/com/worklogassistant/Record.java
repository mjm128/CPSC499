package micahsquad.com.worklogassistant;

/**
 * Created by Micah on 8/5/2016.
 */
public class Record {
    private TimeCard timecard;
    private Tip tip;

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

    public class TimeCard {
        private int shiftid;
        private int jobid;
        private String date, startTime, endTime, breakStart1, breakEnd1, lunchStart1, lunchEnd1,
                breakStart2, breakEnd2, lunchStart2, lunchEnd2, breakStart3, breakEnd3,
                lunchStart3, lunchEnd3, breakStart4, breakEnd4, lunchStart4, lunchEnd4,
                breakStart5, breakEnd5,
                basePay, comment;

        public int getShiftid() {
            return shiftid;
        }

        public void setShiftid(int shiftid) {
            this.shiftid = shiftid;
        }

        public int getJobid() {
            return jobid;
        }

        public void setJobid(int jobid) {
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

        public String getBasePay() {
            return basePay;
        }

        public void setBasePay(String basePay) {
            this.basePay = basePay;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    private class Tip {

    }

}

