package vn.unigap.common;

import org.junit.jupiter.api.Test;
import vn.unigap.common.helper.StringParser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelperTest {
    @Test
    public void testStringToIdList() {
        String inputString = "-64-32-10-";
        List<Integer> expectedList = Arrays.asList(64,32,10);
        List<Integer> result = StringParser.StringToIdList(inputString);

        assertEquals(expectedList, result);
    }

    @Test
    public void testListIdToString() {
        List<Integer> inputList = Arrays.asList(1, 2, 3, 4, 5);
        String expectedString = "1-2-3-4-5";

        String result = StringParser.ListIdToString(inputList);
        System.out.println(result);
        assertEquals(expectedString, result);
    }
}
