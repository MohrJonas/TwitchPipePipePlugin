package mohr.jonas.twitch;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class AssertionMessages {

    public static @Nonnull String pathIsNotEmptyMessage(@Nonnull final String actualPath) {
        return String.format("Expected path to be empty, got '%s' instead", actualPath);
    }

    public static @Nonnull String queryIsNotEmpty(@Nonnull final String actualQuery) {
        return String.format("Expected query to be empty, got '%s' instead", actualQuery);
    }

    public static @Nonnull String pathHasWrongLength(final int actualLength,
                                                     final int... allowedLengths) {
        final var lengthsString =
                Arrays.stream(allowedLengths).mapToObj(Integer::toString).collect(Collectors.joining(", "));
        return String.format("Expected path to consist of '%s' segments, got '%d' instead",
                lengthsString, actualLength);
    }

    public static @Nonnull String pathSegmentDoesNotMatch(@Nonnull final String actualSegment,
                                                          @Nonnull final String allowedSegment) {
        return String.format("Expected path segment to equal '%s', got '%s' instead",
                allowedSegment, actualSegment);
    }
}
