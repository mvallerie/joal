package org.araymond.joal.web.messages.outgoing.impl.speed;

import org.araymond.joal.core.events.speed.SeedingSpeedsHasChangedEvent;
import org.araymond.joal.core.torrent.torrent.InfoHash;
import org.araymond.joal.web.messages.outgoing.MessagePayload;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

public class SeedingSpeedHasChangedPayload implements MessagePayload {
    private final List<SpeedPayload> speeds;

    public SeedingSpeedHasChangedPayload(final SeedingSpeedsHasChangedEvent event) {
        this.speeds = event.getSpeeds().entrySet().stream()
                .map(entry -> new SpeedPayload(entry.getKey(), String.valueOf(entry.getValue().getBytesPerSeconds())))
                .collect(Collectors.toList());
    }

    public List<SpeedPayload> getSpeeds() {
        return speeds;
    }

    public static final class SpeedPayload {
        private final InfoHash infoHash;
        private final String bytesPerSeconds;

        public SpeedPayload(final InfoHash infoHash, final String bytesPerSeconds) {
            this.infoHash = infoHash;
            this.bytesPerSeconds = bytesPerSeconds;
        }

        public InfoHash getInfoHash() {
            return infoHash;
        }

        public String getBytesPerSeconds() {
            return bytesPerSeconds;
        }
    }
}
