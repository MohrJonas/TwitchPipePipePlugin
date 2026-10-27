package mohr.jonas.twitch.data.api.responses.clip;

import org.jetbrains.annotations.Nullable;

public record TwitchHelixClipResponseInner(
        boolean hasMoreEntries,
        @Nullable String cursor,
        TwitchHelixClipResponseInnerEntry[] entries
) {
}
