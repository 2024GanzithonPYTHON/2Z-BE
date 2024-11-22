package com.pj2z.pj2zbe.recommend.dto.request;

import java.util.List;

public record RecommendRequest(
        Long userId,
        String setting,
        List<String> choices
) {
}
