package mohr.jonas.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import mohr.jonas.twitch.TwitchUrlBuilder;
import mohr.jonas.twitch.TwitchUrlParser;
import mohr.jonas.twitch.api.TwitchApiClient;
import mohr.jonas.twitch.api.TwitchThumbnailURLGenerator;
import mohr.jonas.twitch.data.api.pagination.PositionBasedPagination;
import mohr.jonas.twitch.data.api.responses.stream.TwitchStreamResponseInner;

public final class TwitchChannelStreamExtractor extends ChannelTabExtractor {

    @Nonnull
    private final TwitchApiClient apiClient;
    private TwitchStreamResponseInner response;
    private String channelId;

    public TwitchChannelStreamExtractor(final @Nonnull StreamingService service,
                                        final @Nonnull ListLinkHandler linkHandler,
                                        final @Nonnull TwitchApiClient apiClient) {
        super(service, linkHandler);
        this.apiClient = apiClient;
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        if (response.streamTitle() == null)
            return new InfoItemsPage<>(Collections.emptyList(), null, Collections.emptyList());
        var item = new StreamInfoItem(
                getServiceId(),
                TwitchUrlBuilder.buildStreamUrlFromChannelName(
                        response.streamerName(),
                        channelId,
                        PositionBasedPagination.DEFAULT_POSITION_PAGINATION
                ),
                response.streamTitle(),
                StreamType.LIVE_STREAM
        );
        item.setUploaderName(response.streamerName());
        item.setViewCount(response.viewerCount());
        item.setThumbnailUrl(TwitchThumbnailURLGenerator.getThumbnailURLForStream(response.streamerLoginName()));
        return new InfoItemsPage<>(List.of(item), null, Collections.emptyList());
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        return getInitialPage();
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException,
            ExtractionException {
        try {
            final var channelUrl = TwitchUrlParser.parseChannelUrl(getUrl());
            channelId = channelUrl.channelId();
            response = apiClient.getUnauthorized().getStreamInformation(
                    downloader,
                    channelUrl.channelLoginName()
            ).getData();
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }
}
