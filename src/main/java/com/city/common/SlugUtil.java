package com.city.common;

import java.text.Normalizer;

public class SlugUtil {

    public static String toSlug(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}