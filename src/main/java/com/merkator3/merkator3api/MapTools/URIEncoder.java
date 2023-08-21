package com.merkator3.merkator3api.MapTools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URIEncoder {

    private static final String CHARSET = StandardCharsets.UTF_8.name();

    public static String encodeURIComponent(String text) {
        if (text.isEmpty()) {
            return "";
        }
        String code;
        try {
            code = URLEncoder.encode(text, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        int textLength = text.length();
        int codeLength = code.length();
        StringBuilder builder = new StringBuilder((textLength + codeLength + 1) / 2);
        for (int i = 0; i < codeLength; ++i) {
            char entry = code.charAt(i);
            switch (entry) {
                case '+':
                    builder.append("%20");
                    break;
                case '%':
                    if (i > codeLength - 2) {
                        break;
                    }
                    char a = code.charAt(i += 1);
                    char b = code.charAt(i += 1);
                    switch (a) {
                        case '2':
                            switch (b) {
                                case '1':
                                    builder.append("!");
                                    break;
                                case '7':
                                    builder.append("'");
                                    break;
                                case '8':
                                    builder.append("(");
                                    break;
                                case '9':
                                    builder.append(")");
                                    break;
                                default:
                                    builder.append("%");
                                    builder.append(a);
                                    builder.append(b);
                                    break;
                            }
                            break;
                        case '7':
                            if (b == 'e' || b == 'E') {
                                builder.append("~");
                            } else {
                                builder.append("%");
                                builder.append(a);
                                builder.append(b);
                            }
                            break;
                        default:
                            builder.append("%");
                            builder.append(a);
                            builder.append(b);
                            break;
                    }
                    break;
                default:
                    builder.append(entry);
                    break;
            }
        }
        return builder.toString();
    }
}