package mr.cookie.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.Nullable;

public interface MovieMQListener {

    void consumeMessage(@Nullable String msg) throws JsonProcessingException;

    int getCounter();

}
