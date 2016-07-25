package micahsquad.com.worklogassistant;

/**
 * Created by Micah on 7/20/2016.
 */
public class Job {
    private int jobid;
    private String name;
    private String position;
    private Double pay;

    public Job(){

    }

    public Job(int jobid, String name, String position, Double pay){
        this.jobid = jobid;
        this.name = name;
        this.position = position;
        this.pay = pay;
    }

    public int getJobId() { return jobid; }

    public void setJobId(int jobid) { this.jobid = jobid; }

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

    public Double getPay(){
        return pay;
    }

    public void setPay(Double pay){
        this.pay = pay;
    }

}
