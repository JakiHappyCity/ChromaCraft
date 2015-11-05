package cc.utils;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class Coord2D {

    public float x;
    public float z;

    public Coord2D(float i, float j)
    {
        this.x = i;
        this.z = j;
    }

    public Coord2D()
    {
        this(0,0);
    }

    public String toString()
    {
        return "||x:"+x+"||z:"+z;
    }

    public static Coord2D fromString(String data)
    {
        DummyData[] dd = DataStorage.parseData(data);
        float cX = Float.parseFloat(dd[0].fieldValue);
        float cZ = Float.parseFloat(dd[1].fieldValue);
        return new Coord2D(cX,cZ);
    }

}