package istd.eric.myapp1;

/**
 * Created by Eric on 16/10/2016.
 */

public class ImageDetail {
    private int imageid;
    private String title;
    private String detail;

    public ImageDetail(int imageid, String title, String detail){
        this.imageid = imageid;
        this.title = title;
        this.detail = detail;
    }

    public int getImageid() {
        return imageid;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}