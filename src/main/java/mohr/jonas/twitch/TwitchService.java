package mohr.jonas.twitch;

import static org.schabi.newpipe.extractor.StreamingService.ServiceInfo.MediaCapability.VIDEO;
import static org.schabi.newpipe.extractor.StreamingService.ServiceInfo.MediaCapability.LIVE;

import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.ChannelTabExtractor;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.plugin.PluginPreferences;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;

import java.util.List;

import mohr.jonas.twitch.api.TwitchApiClient;
import mohr.jonas.twitch.api.TwitchApiSettings;
import mohr.jonas.twitch.extractors.TwitchChannelClipExtractor;
import mohr.jonas.twitch.extractors.TwitchChannelExtractor;
import mohr.jonas.twitch.extractors.TwitchChannelStreamExtractor;
import mohr.jonas.twitch.extractors.TwitchChannelVodExtractor;
import mohr.jonas.twitch.extractors.TwitchClipExtractor;
import mohr.jonas.twitch.extractors.TwitchPlaylistExtractor;
import mohr.jonas.twitch.extractors.TwitchSearchExtractor;
import mohr.jonas.twitch.extractors.TwitchStreamExtractor;
import mohr.jonas.twitch.extractors.TwitchVodExtractor;
import mohr.jonas.twitch.kiosks.TwitchLiveKiosk;
import mohr.jonas.twitch.linkHandlers.TwitchChannelLinkHandlerFactory;
import mohr.jonas.twitch.linkHandlers.TwitchChannelTabLinkHandlerFactory;
import mohr.jonas.twitch.linkHandlers.TwitchLiveKioskLinkHandlerFactory;
import mohr.jonas.twitch.linkHandlers.TwitchPlaylistLinkHandlerFactory;
import mohr.jonas.twitch.linkHandlers.TwitchSearchQueryHandlerFactory;
import mohr.jonas.twitch.linkHandlers.TwitchStreamLinkHandlerFactory;

/**
 * Skeleton service. The serviceId is assigned by the host and must be passed on unchanged.
 * Every capability you do not support can return null or throw; implement the ones you do
 * (start with {@link #getStreamLHFactory()} and {@link #getStreamExtractor(LinkHandler)}).
 */
public class TwitchService extends StreamingService {

    public static final String BaseUrl = "https://twitch.tv";

    private TwitchApiClient twitchApiClient;

    private final PluginPreferences preferences;

    public TwitchService(final int id, final PluginPreferences preferences) {
        super(id, "Twitch", List.of(LIVE, VIDEO));
        this.preferences = preferences;
    }

    private TwitchApiClient getOrInitializeTwitchClient() {
        if (twitchApiClient == null)
            twitchApiClient = new TwitchApiClient(
                    new TwitchApiSettings(
                            preferences.getBoolean("twitch_use_token_kiosk", false),
                            preferences.getBoolean("twitch_use_token_categories", false),
                            preferences.getBoolean("twitch_use_token_vods", false),
                            preferences.getBoolean("twitch_use_token_clips", false),
                            preferences.getBoolean("twitch_use_token_search", false)
                    ),
                    getTokens()
            );
        return twitchApiClient;
    }

    @Override
    public String getBaseUrl() {
        return BaseUrl;
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return new TwitchStreamLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return new TwitchChannelLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return new TwitchChannelTabLinkHandlerFactory(getOrInitializeTwitchClient().getApiSettings());
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return new TwitchPlaylistLinkHandlerFactory();
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return new TwitchSearchQueryHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getCommentsLHFactory() {
        return null;
    }

    @Override
    public SearchExtractor getSearchExtractor(final SearchQueryHandler queryHandler) {
        return new TwitchSearchExtractor(this, queryHandler, getOrInitializeTwitchClient());
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return null;
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        try {
            final var list = new KioskList(this);
            final var streamHandler =
                    new TwitchLiveKioskLinkHandlerFactory(getOrInitializeTwitchClient().getApiSettings());
            list.addKioskEntry((streamingService, url, _) ->
                            new TwitchLiveKiosk(streamingService, streamHandler.fromUrl(url),
                                    getOrInitializeTwitchClient()),
                    streamHandler,
                    TwitchLiveKiosk.KIOSK_ID
            );
            list.setDefaultKiosk(TwitchLiveKiosk.KIOSK_ID);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChannelExtractor getChannelExtractor(final ListLinkHandler linkHandler)
            throws ExtractionException {
        return new TwitchChannelExtractor(this, linkHandler, getOrInitializeTwitchClient());
    }

    @Override
    public ChannelTabExtractor getChannelTabExtractor(final ListLinkHandler linkHandler)
            throws ExtractionException {
        final var channelUrl = TwitchUrlParser.parseChannelUrl(linkHandler.getUrl());
        return switch (channelUrl.tabType()) {
            case CLIPS ->
                    new TwitchChannelClipExtractor(this, linkHandler,
                            getOrInitializeTwitchClient());
            case VIDEOS ->
                    new TwitchChannelVodExtractor(this, linkHandler, getOrInitializeTwitchClient());
            case LIVE ->
                    new TwitchChannelStreamExtractor(this, linkHandler,
                            getOrInitializeTwitchClient());
            default -> throw new IllegalStateException("Unexpected value: " + channelUrl.tabType());
        };
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(final ListLinkHandler linkHandler)
            throws ExtractionException {
        return new TwitchPlaylistExtractor(this, linkHandler, getOrInitializeTwitchClient());
    }

    @Override
    public StreamExtractor getStreamExtractor(final LinkHandler linkHandler)
            throws ExtractionException {
        final var url = linkHandler.getUrl();
        try {
            TwitchUrlParser.parseChannelUrl(url);
            return new TwitchStreamExtractor(this, linkHandler, getOrInitializeTwitchClient());
        } catch (Exception ignored) {
        }
        try {
            TwitchUrlParser.parseClipIdFromClipUrl(url);
            return new TwitchClipExtractor(this, linkHandler, getOrInitializeTwitchClient());
        } catch (Exception ignored) {
        }
        try {
            TwitchUrlParser.parseVodIdFromVodUrl(url);
            return new TwitchVodExtractor(this, linkHandler, getOrInitializeTwitchClient());
        } catch (Exception ignored) {
        }
        throw new ExtractionException("Cannot get StreamExtractor for url " + url);
    }

    @Override
    public CommentsExtractor getCommentsExtractor(final ListLinkHandler linkHandler)
            throws ExtractionException {
        return null;
    }
}
