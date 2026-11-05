package mohr.jonas.twitch.data.api.responses.playbackToken;

import com.grack.nanojson.JsonObject;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mohr.jonas.twitch.data.api.TwitchExtensionsData;
import mohr.jonas.twitch.data.api.responses.TwitchBaseResponse;

public final class TwitchVodPlaybackTokenResponse extends TwitchBaseResponse<TwitchStreamPlaybackTokenResponseInner> {

    public TwitchVodPlaybackTokenResponse(@Nullable String[] errors,
                                          @Nonnull TwitchExtensionsData extensions,
                                          @Nonnull JsonObject data) {
        super(errors, extensions, data);
    }

    @Override
    protected TwitchStreamPlaybackTokenResponseInner ParseData(@NotNull JsonObject data) {
        final var streamPlaybackAccessToken = data.getObject("videoPlaybackAccessToken");
        return new TwitchStreamPlaybackTokenResponseInner(
                streamPlaybackAccessToken.getString("signature"),
                streamPlaybackAccessToken.getString("value")
        );
    }
}
