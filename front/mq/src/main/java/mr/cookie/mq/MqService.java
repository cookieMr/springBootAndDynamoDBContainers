package mr.cookie.mq;

import mr.cookie.models.mq.Movie;
import org.jetbrains.annotations.NotNull;

public interface MqService {

    void insert(@NotNull Movie movie);

    int getQueueSize();

}
