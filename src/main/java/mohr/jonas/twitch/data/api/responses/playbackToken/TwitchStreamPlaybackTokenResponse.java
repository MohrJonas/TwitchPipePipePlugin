package mohr.jonas.twitch.data.api.responses.playbackToken;

import com.grack.nanojson.JsonObject;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mohr.jonas.twitch.data.api.TwitchExtensionsData;
import mohr.jonas.twitch.data.api.responses.TwitchBaseResponse;

public final class TwitchStreamPlaybackTokenResponse extends TwitchBaseResponse<TwitchVodPlaybackTokenResponseInner> {

    public TwitchStreamPlaybackTokenResponse(@Nullable String[] errors,
                                             @Nonnull TwitchExtensionsData extensions,
                                             @Nonnull JsonObject data) {
        super(errors, extensions, data);
    }

    @Override
    protected TwitchVodPlaybackTokenResponseInner ParseData(@NotNull JsonObject data) {
        final var streamPlaybackAccessToken = data.getObject("streamPlaybackAccessToken");
        return new TwitchVodPlaybackTokenResponseInner(
                streamPlaybackAccessToken.getString("signature"),
                streamPlaybackAccessToken.getString("value")
        );
    }
}
