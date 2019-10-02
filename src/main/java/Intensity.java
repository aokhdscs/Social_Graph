public class Intensity {
    private double status;
    private double likes;
    private double reposts;
    private double photos;
    private double groups;

    public Intensity (double s, double l, double r, double p, double g)
    {
        status = s;
        likes = l;
        reposts = r;
        photos = p;
        groups = g;
    }

    public Intensity()
    {
        status = 0;
        groups = 0;
        likes = 0;
        reposts = 0;
        photos = 0;
    }

    public double getLikes() {
        return likes;
    }

    public double getGroups() {
        return groups;
    }

    public double getPhotos() {
        return photos;
    }

    public double getReposts() {
        return reposts;
    }

    public double getStatus() {
        return status;
    }

    public void setGroups(double groups) {
        this.groups = groups;
    }

    public void setLikes(double likes) {
        this.likes = likes;
    }

    public void setPhotos(double photos) {
        this.photos = photos;
    }

    public void setReposts(double reposts) {
        this.reposts = reposts;
    }

    public void setStatus(double status) {
        this.status = status;
    }
}