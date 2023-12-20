package vn.unigap.common.helper;

import org.springframework.http.HttpStatus;
import vn.unigap.common.EnumStatusCode;
import vn.unigap.common.exception.ApiException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringParser {
    static public List<Integer> StringToIdList(String string) {
        try {
            String[] parts = string.split("-");
            return Arrays.stream(parts)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new ApiException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Invalid integer format in the input string");
        }
    }

    static public String ListIdToString(List<Integer> list) {
        if (list == null) {
            throw new ApiException(EnumStatusCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Input list cannot be null");
        }

        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining("-"));
    }


}
