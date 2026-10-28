package mohr.jonas.twitch;

import javax.annotation.Nonnull;

public final class AssertionFailedException extends RuntimeException {
    public AssertionFailedException(@Nonnull final String message) {
        super(message);
    }
}
