package Gdsc.web.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Response<T> {

    private final static int SUCCESS = 200;
    private final static int NOT_FOUND = 400;
    private final static int FAILED = 500;
    private final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String NOT_FOUND_MESSAGE = "NOT FOUND";
    private final static String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private final static String INVALID_ACCESS_TOKEN = "Invalid access token.";
    private final static String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    private final static String NOT_EXPIRED_TOKEN_YET = "Not expired token yet.";

    private final ApiResponseHeader header;
    private final Map<String, T> body;

    public static <T> Response<T> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new Response(new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), map);
    }

    public static <T> Response<T> fail(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);
        return new Response(new ApiResponseHeader(FAILED, FAILED_MESSAGE), map);
    }

    public static <T> Response<T> invalidAccessToken() {
        return new Response(new ApiResponseHeader(FAILED, INVALID_ACCESS_TOKEN), null);
    }

    public static <T> Response<T> invalidRefreshToken() {
        return new Response(new ApiResponseHeader(FAILED, INVALID_REFRESH_TOKEN), null);
    }

    public static <T> Response<T> notExpiredTokenYet() {
        return new Response(new ApiResponseHeader(FAILED, NOT_EXPIRED_TOKEN_YET), null);
    }
    public static <T> Response<T> unauthorized() {
        return new Response(new ApiResponseHeader(401, "Unauthorized"), null);
    }
}