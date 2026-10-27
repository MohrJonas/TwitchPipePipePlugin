package mohr.jonas.twitch.data.url;

import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public record SearchUrl(@Nonnull String query, @Nullable String[] contentTypes) {
}
