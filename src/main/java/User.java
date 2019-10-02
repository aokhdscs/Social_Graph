public class User implements Comparable<User>{
    private String firstName;
    private String secondName;
    private Integer id;
    private double vulnerability;

    public User (String firstName, String secondName, Integer id) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.id = id;
        this.vulnerability = 0;
    }

    public User (String firstName, String secondName, Integer id, double vulnerability) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.id = id;
        this.vulnerability = vulnerability;
    }

    public void setVulnerability(double vulnerability)
    {
        this.vulnerability = vulnerability;
    }

    public double getVulnerability()
    {
        return this.vulnerability;
    }

    public String getFirstName ()
    {
        return this.firstName;
    }

    public String getSecondName ()
    {
        return this.secondName;
    }

    public Integer getId ()
    {
        return this.id;
    }

    public void print()
    {
        System.out.println(this.id+": "+this.firstName+" "+this.secondName);
    }

    public int compareTo(User o) {
        return vulnerability > o.getVulnerability() ? 1 : vulnerability == o.getVulnerability() ? 0 : -1;
    }
}
