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


    public Job(){

    }

    public Job(long jobid, String name, String position, double pay, String rounding){
        this.jobid = jobid;
        this.name = name;
        this.position = position;
        this.pay = pay;
        this.rounding = rounding;
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

}
