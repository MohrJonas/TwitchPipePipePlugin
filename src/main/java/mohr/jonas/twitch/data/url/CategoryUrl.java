package mohr.jonas.twitch.data.url;

import javax.annotation.Nonnull;

import mohr.jonas.twitch.data.api.pagination.Pagination;

public record CategoryUrl(@Nonnull String categoryName,
                          @Nonnull String categorySlug,
                          @Nonnull String categoryId,
                          @Nonnull Pagination pagination) {
}
