package mohr.jonas.twitch;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

public final class StringUtils {

    private static final Pattern allBlankPattern = Pattern.compile("^\\s*$");

    public static byte[] stringToBytes(final String string) {
        return string.getBytes(Charset.defaultCharset());
    }

    public static boolean isAllBlank(@Nonnull final String s) {
        return allBlankPattern.matcher(s).matches();
    }
}
