package mohr.jonas.twitch.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;

public class UrlEncoder {

    public static @Nonnull String encode(@Nonnull final String toEncode)
    {
        try
        {
            return URLEncoder.encode(toEncode, StandardCharsets.UTF_8);
        }
        // Fallback to old method on old android version
        catch (NoSuchMethodError _) {
            try {
                //noinspection CharsetObjectCanBeUsed
                return URLEncoder.encode(toEncode, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
