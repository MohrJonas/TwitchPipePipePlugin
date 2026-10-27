package mohr.jonas.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.search.filter.FilterItem;

import java.util.List;

import mohr.jonas.twitch.TwitchUrlBuilder;
import mohr.jonas.twitch.TwitchUrlParser;
import mohr.jonas.twitch.api.TwitchApiSettings;
import mohr.jonas.twitch.data.api.pagination.CursorBasedPagination;
import mohr.jonas.twitch.data.api.pagination.PositionBasedPagination;
import mohr.jonas.twitch.data.url.ChannelUrl;

public class TwitchChannelTabLinkHandlerFactory extends ListLinkHandlerFactory {
    private final TwitchApiSettings apiSettings;
    private ChannelUrl channelUrl;

    public TwitchChannelTabLinkHandlerFactory(TwitchApiSettings apiSettings) {
        this.apiSettings = apiSettings;
    }

    @Override
    public String getUrl(String id, List<FilterItem> contentFilter, List<FilterItem> sortFilter) throws ParsingException {
        return TwitchUrlBuilder.buildChannelTabUrl(
                channelUrl.channelLoginName(),
                channelUrl.channelId(),
                channelUrl.tabType(),
                apiSettings.shouldUseHelixForClips()
                        ? CursorBasedPagination.DEFAULT_CURSOR_PAGINATION
                        : PositionBasedPagination.DEFAULT_POSITION_PAGINATION
        );
    }

    @Override
    public String getId(String url) throws ParsingException {
        return url;
    }

    @Override
    public boolean onAcceptUrl(String url) throws ParsingException {
        try {
            channelUrl = TwitchUrlParser.parseChannelUrl(url);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
