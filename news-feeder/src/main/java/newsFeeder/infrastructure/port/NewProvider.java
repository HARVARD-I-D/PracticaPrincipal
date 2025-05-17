package newsFeeder.infrastructure.port;

import newsFeeder.application.domain.New;

import java.util.ArrayList;

public interface NewProvider {
    ArrayList<New> provide();
}
