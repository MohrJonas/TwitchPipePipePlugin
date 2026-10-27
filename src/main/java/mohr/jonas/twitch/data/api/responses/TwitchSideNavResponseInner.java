package mohr.jonas.twitch.data.api.responses;

import javax.annotation.Nonnull;

public record TwitchSideNavResponseInner(
        @Nonnull String streamerName,
        @Nonnull String streamerLoginName,
        @Nonnull String title,
        int viewCount
) {
}
