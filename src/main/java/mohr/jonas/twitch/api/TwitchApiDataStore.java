package mohr.jonas.twitch.api;

import javax.annotation.Nullable;

import mohr.jonas.twitch.data.api.TwitchIntegrityToken;

public final class TwitchApiDataStore {
    public @Nullable TwitchIntegrityToken integrityToken = null;
    public @Nullable String deviceId = null;
    public @Nullable String sessionId = null;
}