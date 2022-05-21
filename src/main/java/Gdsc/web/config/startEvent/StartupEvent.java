package Gdsc.web.config.startEvent;

import Gdsc.web.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final PostService service;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        try {
            service.initialIndexing();
        } catch (InterruptedException e) {
            log.error("Failed to reindex entities ",e);
        }
    }
}
