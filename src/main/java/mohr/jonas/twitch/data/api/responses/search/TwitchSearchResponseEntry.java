package mohr.jonas.twitch.data.api.responses.search;

public record TwitchSearchResponseEntry(boolean isLive, String name, String streamTitle,
                                        int streamViewers, String thumbnailUrl, String gameName,
                                        String avatarUrl) {
}
