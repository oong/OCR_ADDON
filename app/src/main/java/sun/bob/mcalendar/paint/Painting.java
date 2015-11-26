package sun.bob.mcalendar.paint;

import android.content.res.Resources;
import android.content.res.TypedArray;

import sun.bob.mcalendar.R;

/**
 * Created by oong on 2015-11-25.
 */
public class Painting {

    private final int imageId;
    private final String title;
    private final String year;
    private final String location;

    private Painting(int imageId, String title, String year, String location) {
        this.imageId = imageId;
        this.title = title;
        this.year = year;
        this.location = location;
    }

    public Painting(int imageId) {
        this.imageId = imageId;
        title = "title";
        year = "2015";
        location = "location";
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getLocation() {
        return location;
    }

//        public static Painting[] getAllPaintings(Resources res) {
//            String[] titles = res.getStringArray(R.array.paintings_titles);
//            String[] years = res.getStringArray(R.array.paintings_years);
//            String[] locations = res.getStringArray(R.array.paintings_locations);
//            TypedArray images = res.obtainTypedArray(R.array.paintings_images);
//
//            int size = titles.length;
//            Painting[] paintings = new Painting[size];
//
//            for (int i = 0; i < size; i++) {
//                paintings[i] = new Painting(images.getResourceId(i, -1), titles[i], years[i], locations[i]);
//            }
//
//            images.recycle();
//
//            return paintings;
//        }

    @Override
    public String toString() {
        return "Painting{" +
                "imageId=" + imageId +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
