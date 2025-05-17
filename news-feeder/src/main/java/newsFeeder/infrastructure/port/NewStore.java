package newsFeeder.infrastructure.port;

import newsFeeder.application.domain.New;

public interface NewStore {
    void save(New New);
}
