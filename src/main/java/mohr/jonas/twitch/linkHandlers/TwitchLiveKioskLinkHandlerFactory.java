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

public class TwitchLiveKioskLinkHandlerFactory extends ListLinkHandlerFactory {

    private final TwitchApiSettings apiSettings;

    public TwitchLiveKioskLinkHandlerFactory(TwitchApiSettings apiSettings) {
        this.apiSettings = apiSettings;
    }

    @Override
    public String getUrl(String id, List<FilterItem> contentFilter, List<FilterItem> sortFilter) throws ParsingException, UnsupportedOperationException {
        return TwitchUrlBuilder.buildLiveKioskUrlFromPagination(
                apiSettings.shouldUseHelixForKiosk()
                        ? CursorBasedPagination.DEFAULT_CURSOR_PAGINATION
                        : PositionBasedPagination.DEFAULT_POSITION_PAGINATION
        );
    }

    @Override
    public String getId(String url) throws ParsingException, UnsupportedOperationException {
        return "live";
    }

    @Override
    public boolean onAcceptUrl(String url) throws ParsingException {
        try {
            TwitchUrlParser.parsePaginationFromKioskUrl(url);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
