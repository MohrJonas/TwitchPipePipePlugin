package mohr.jonas.twitch.data.api.responses.nowLive;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public record TwitchHelixNowLiveResponseInner(
        boolean hasMoreEntries,
        @Nullable String cursor,
        List<TwitchNowLiveResponseEntry> liveEntries
) {
}
