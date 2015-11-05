package cc.utils;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class DummyData {
    public final String fieldName;
    public final String fieldValue;
    public DummyData(String field, String value)
    {
        fieldName = field;
        fieldValue = value;
    }

    public DummyData(String field, Object value)
    {
        fieldName = field;
        fieldValue = value.toString();
    }

    public String toString()
    {
        String ret = "";
        ret = ret.concat("||").concat(fieldName).concat(":").concat(fieldValue.toString());
        return ret;
    }

    public static DummyData makeNull()
    {
        return new DummyData("null","null");
    }
}