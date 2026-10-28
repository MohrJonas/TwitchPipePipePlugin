package mohr.jonas.twitch.data.url;

import javax.annotation.Nonnull;

import mohr.jonas.twitch.data.TwitchChannelTabLinkType;
import mohr.jonas.twitch.data.api.pagination.Pagination;

public record ChannelUrl(@Nonnull String channelLoginName,
                         @Nonnull String channelId,
                         @Nonnull TwitchChannelTabLinkType tabType,
                         @Nonnull Pagination pagination) {
}
