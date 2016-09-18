package micahsquad.com.worklogassistant;

/**
 * Created by Micah on 7/20/2016.
 */
public class Job {
    private long jobid;
    private String name;
    private String position;
    private double pay;
    private String rounding;
    private String overtime1;
    private String overtime2;

    public Job(){

    }

    public Job(long jobid, String name, String position, double pay, String rounding, String overtime1, String overtime2){
        this.jobid = jobid;
        this.name = name;
        this.position = position;
        this.pay = pay;
        this.rounding = rounding;
        this.overtime1 = overtime1;
        this.overtime2 = overtime2;
    }

    public long getJobId() { return jobid; }

    public void setJobId(long jobid) { this.jobid = jobid; }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPosition(){
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public double getPay(){
        return pay;
    }

    public void setPay(double pay){
        this.pay = pay;
    }

    public String getRounding() { return rounding; }

    public void setRounding(String rounding) { this.rounding = rounding; }

    public String getOvertime2() {
        return overtime2;
    }

    public void setOvertime2(String overtime2) {
        this.overtime2 = overtime2;
    }

    public String getOvertime1() {
        return overtime1;
    }

    public void setOvertime1(String overtime1) {
        this.overtime1 = overtime1;
    }

}
