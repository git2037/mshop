package com.mshop.app.product.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileConstant {

    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024L; //5MB

    @Getter
    @AllArgsConstructor
    public enum ImageType {
        JPEG("image/jpeg"),
        PNG("image/png"),
        WEBP("image/webp");
        private final String value;

        public static List<String> getFileSupported() {
            return Arrays.stream(ImageType.values())
                    .map(Enum::name)
                    .toList();
        }

        public static Set<String> getContentTypes() {
            return Arrays.stream(ImageType.values())
                    .map(imageType -> imageType.value)
                    .collect(Collectors.toUnmodifiableSet());
        }
    }
}