package java_new;

public class Department {

    private Integer deptId;
    private String deptName;
    private String loc;

    public Department(Integer deptId, String deptName, String loc){
        super();
        this.deptId = deptId;
        this.deptName = deptName;
        this.loc = loc;
    }

    public String toString() {
        return "Department[deptId=" + deptId + ", deptName=" + deptName + ", loc=" + loc + "]";
    }
}