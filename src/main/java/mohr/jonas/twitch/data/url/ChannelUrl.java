package mohr.jonas.twitch.data.url;

import mohr.jonas.twitch.data.TwitchChannelTabLinkType;
import mohr.jonas.twitch.data.api.pagination.Pagination;

import javax.annotation.Nonnull;

public record ChannelUrl(@Nonnull String channelLoginName,
                         @Nonnull String channelId,
                         @Nonnull TwitchChannelTabLinkType tabType,
                         @Nonnull Pagination pagination) {
}
