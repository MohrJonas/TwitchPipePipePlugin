package mohr.jonas.twitch;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

public final class Assertions {

    private static final String defaultAssertionMessage = "Assertion failed";

    public static void assertThat(@Nonnull final Supplier<Boolean> toValidate) {
        assertThat(toValidate, defaultAssertionMessage);
    }

    public static void assertThat(@Nonnull final Supplier<Boolean> toValidate,
                                  @Nonnull final String message) {
        if (!toValidate.get())
            throw new AssertionFailedException(message);
    }
}
