package mr.cookie.models.mappers;

import mr.cookie.models.dto.MovieDto;
import mr.cookie.models.dynamodb.MovieDB;
import mr.cookie.models.mq.Movie;
import org.jetbrains.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MovieMapper {

    @Nullable Movie mapFromDto(@Nullable MovieDto source);

    @Nullable MovieDto mapToDto(@Nullable Movie source);

    @Nullable Movie mapFromDynamoDb(@Nullable MovieDB source);

    @Nullable MovieDB mapToDynamoDb(@Nullable Movie source);

}
