package micahsquad.com.worklogassistant;

/**
 * Created by Micah on 7/20/2016.
 */
public class Job {
    private String name;
    private String position;
    private Double pay;

    public Job(){

    }

    public Job(String name, String position, Double pay){
        this.name = name;
        this.position = position;
        this.pay = pay;
    }

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
