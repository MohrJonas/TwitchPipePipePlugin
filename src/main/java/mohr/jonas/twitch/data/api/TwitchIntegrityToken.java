package mohr.jonas.twitch.data.api;

import javax.annotation.Nonnull;

public record TwitchIntegrityToken(@Nonnull String token, @Nonnull long expiration) {
}
