package cool.scx.object.test;

import cool.scx.object.ScxObject;
import dev.scx.reflect.TypeReference;
import org.testng.annotations.Test;

import java.util.List;

public class ConvertValueTest {

    public static void main(String[] args) {
        test1();
    }

    @Test
    public static void test1() {

        var a = ScxObject.convertValue("123", int.class);
        var b = ScxObject.convertValue("123", int[].class);
        var c = ScxObject.convertValue("123", int[][].class);
        var d = ScxObject.convertValue("123", List.class);
        var e = ScxObject.convertValue("123", new TypeReference<List<List<Integer>>>() {});

        var a1 = ScxObject.convertValue(new Object[]{"123"}, int.class);
        var b1 = ScxObject.convertValue(new Object[]{"123"}, int[].class);
        var c1 = ScxObject.convertValue(new Object[]{"123"}, int[][].class);
        var d1 = ScxObject.convertValue(new Object[]{"123"}, List.class);
        var e1 = ScxObject.convertValue(new Object[]{"123"}, new TypeReference<List<List<Integer>>>() {});


        var a2 = ScxObject.convertValue(new Object[][]{new Object[]{"123"}}, int.class);
        var b2 = ScxObject.convertValue(new Object[][]{new Object[]{"123"}}, int[].class);
        var c2 = ScxObject.convertValue(new Object[][]{new Object[]{"123"}}, int[][].class);
        var d2 = ScxObject.convertValue(new Object[][]{new Object[]{"123"}}, List.class);
        var e2 = ScxObject.convertValue(new Object[][]{new Object[]{"123"}}, new TypeReference<List<List<Integer>>>() {});
        System.out.println();
    }


}
