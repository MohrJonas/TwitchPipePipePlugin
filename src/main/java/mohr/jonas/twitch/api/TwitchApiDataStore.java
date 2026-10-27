package mohr.jonas.twitch.api;

import mohr.jonas.twitch.data.api.TwitchIntegrityToken;

import javax.annotation.Nullable;

public final class TwitchApiDataStore {
    public @Nullable TwitchIntegrityToken integrityToken = null;
    public @Nullable String deviceId = null;
    public @Nullable String sessionId = null;
}