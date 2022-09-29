//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class QueryHelper {
    private static final String IDENTIFIER = "[._[\\P{Z}&&\\P{Cc}&&\\P{Cf}&&\\P{P}]]+";
    private static final Pattern ALIAS_MATCH;

    public static String detectAlias(String query) {
        Matcher matcher = ALIAS_MATCH.matcher(query);
        return matcher.find() ? matcher.group(2) : null;
    }

    private QueryHelper() {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("(?<=from)");
        sb.append("(?:\\s)+");
        sb.append(String.format("(%s)", "[._[\\P{Z}&&\\P{Cc}&&\\P{Cf}&&\\P{P}]]+"));
        sb.append("(?:\\sas)*");
        sb.append("(?:\\s)+");
        sb.append("(?!(?:where|group by|order by))(\\w+)");
        ALIAS_MATCH = Pattern.compile(sb.toString(), 2);
    }
}
